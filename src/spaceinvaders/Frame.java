
package spaceinvaders;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Cypher
 */
public class Frame extends Canvas
{
    private BufferStrategy bs;  //Buffer stategy for double buffed graphics.
    private JPanel panel;   //Container for the canvas.
    private JFrame frame;   //Container for the panel.
    private boolean runGame, isPause, readyToFire, showFps; //Enables the game loop to be stopped.
    private int fps = 0, avgFps = 0;    //Used to measure the fps of the game.
    private double shootTimer;
    private Ship ship;  // Ship class.
    private BufferedImage backgroundImage, livesImage, scoreImage, gameOverImage;  //The star, lives and score images.
    private Random rand;
    private StarField sField;
    private MetBullList metAndBull;
    private GameHud hud;
    private long now;
    private AudioInputStream audioInput;
    private Clip soundClip;
    
    public Frame()
    {
        shootTimer = 5; 
        showFps = false;
        runGame = true;
        isPause = false;
        readyToFire = true;
        rand = new Random();
        now = System.nanoTime();
        //Initialize the frame and set the title.
        frame = new JFrame("Space invaders by Twiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Initialize the panel equal to the frames content pane.
        panel = (JPanel) frame.getContentPane();
        //Set the size of the panel.
        panel.setPreferredSize(new Dimension(800, 600));
        
        //Set the canvas to ignore repainting as we will do this ourselves.
        this.setIgnoreRepaint(true);
        //set the size of the canvas.
        this.setBounds(0, 0, 800, 600);
        //Add the canvas to the panel.
        panel.add(Frame.this);
        
        frame.pack();
        //Set the frame to open in the center of the screen.
        frame.setLocationRelativeTo(null);
        //Set the frame to be visible.
        frame.setVisible(true);
        
        //Create the buffer strategy.
        this.createBufferStrategy(2);
        bs = this.getBufferStrategy();
        try
        {
            ship = new Ship();
            this.setBackground();
        } catch (IOException ioe)
        {
            System.out.println("Sprite loading error: " + ioe);
        }
        sField = new StarField();
        metAndBull = new MetBullList();
        hud = new GameHud();
        //Request focus
        this.requestFocus();
        
        this.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                //Action on key press...
                switch(e.getKeyCode())
                {
                    case 37:
                        ship.setMoveLeft(true);
                        break;
                    case 39:
                        ship.setMoveRight(true);
                        break;
                    case 32:
                        try
                        {
                            //now = System.nanoTime();
                            if(readyToFire)
                            {
                                metAndBull.addBullet(ship.getShipX() + 61, 530);
                                
                                shootSound();
                                
                                readyToFire = false;
                            }
                            
                        } catch (Exception ex)
                        {
                            System.out.println(ex);
                        }
                        break;
                    case 89:
                        if(isPause && hud.getLives() == 0)
                        {
                            hud.resetLives();
                            hud.resetScore();
                            isPause = false;
                            metAndBull.resetAll();
                            runLoop();
                            
                        }
                        break;
                    case 78:
                    case 27:
                        if(isPause && hud.getLives() == 0)
                        {
                            System.exit(0);
                        }
                        break;
                    case 70:
                        if(showFps)
                        {
                            showFps = false;
                        }else
                        {
                            showFps = true;
                        }
                        System.out.println(showFps);
                        break;
                    default:
                        
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                System.out.println(e.getKeyCode());
                //Action on key release....
                switch(e.getKeyCode())
                {
                    case 37:
                        ship.setMoveLeft(false);
                        break;
                    case 39:
                        ship.setMoveRight(false);
                        break;
                    default:
                        
                        break;
                }
            }
        });
        
        
        try
        {
            setMusic();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            System.out.println("Error in music setup. " + e);
        }
        //Run the game loop thread.
        runLoop();
    }
    
    /*
     * This method will handle all of the rendering stuff.
     */
    private void render()
    {
        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        g2d.drawImage(backgroundImage, 0, 0, 800, 600, null);
        g2d.drawImage(scoreImage, 5, 10, 50, 30, this);
        g2d.drawImage(livesImage, 5, 50, 50, 30, null);
        
        sField.drawStarField(g2d);
        metAndBull.drawBullet(g2d);
        ship.drawShip(g2d);
        metAndBull.drawMeteors(g2d);
        
        g2d.setColor(Color.red);
        if(showFps)
        {
            g2d.drawString("FPS: " + String.valueOf(avgFps), 5, 110);
        }
        g2d.drawString(String.valueOf(hud.getPoints()), 65, 35);
        g2d.drawString(String.valueOf(hud.getLives()), 65, 75);
        if(isPause)
        {
            g2d.drawImage(gameOverImage, 0, 0, 800, 600, null);
        }
        g2d.dispose();
        bs.show();
    }
    
    //Run the game loop in a separate thread.
    private void runLoop()
    {
        Thread theLoop = new Thread()
        {
            @Override
            public void run()
            {
                loop();
            }
        };
        theLoop.start();
    }
    
    /*
     * Heavy inspiration for this loop from http://www.java-gaming.org
     * Variables names may have been changed to protect the innocent.
     */
    private void loop()
    {
        long lastFpsTime = 0;
        long lastTime = System.nanoTime();
        final int TARGET_FPS = 62;  //The intended FPS of the game.
        final long OPT_TIME = 1000000000 / TARGET_FPS;
        
        while(runGame)
        {
            if(!isPause)
            {
                if(hud.getLives() <= 0)
                {
                    isPause = true;
                }
                now = System.nanoTime();
                long updateLength = now - lastTime;
                lastTime = now;

                /*
                 * This value will be used as a form of 
                 * timing for ship and meteor updates.
                 */
                double delta = updateLength / ((double) OPT_TIME);
                lastFpsTime += updateLength;
                fps++;

                if(!readyToFire)
                {
                    shootTimer -= 0.5 * delta;

                    if(shootTimer <= 0)
                    {
                        readyToFire = true;
                        shootTimer = 5;
                    }
                }

                if(lastFpsTime >= 1000000000)
                {
                    avgFps = fps;
                    lastFpsTime = 0;
                    fps = 0;
                }


                render();
                ship.updateShip(delta);

                metAndBull.updateMeteors(delta);
                metAndBull.deleteMeteor();
                metAndBull.checkCollisions(ship.getShipRect(), hud);
                metAndBull.updateBullet(delta);
                metAndBull.checkHits(hud);
                metAndBull.deleteBullet();
                if(sField.getSize() < 20)
                {
                    switch(rand.nextInt(100))
                    {
                        case 55:
                            try
                            {
                                sField.addStar();
                            } catch (Exception e)
                            {
                            }
                            break;
                        case 22:
                            try
                            {
                                metAndBull.addMeteor();
                            } catch (Exception e)
                            {
                            }
                            break;
                        default:

                            break;
                    }
                }

                sField.updateStarField(delta);
                sField.removeStar();

                try
                {
                    long sleepTimer = ((lastTime - System.nanoTime() + OPT_TIME ) / 1000000);
                    if( sleepTimer > 0 )
                    {
                        Thread.sleep(( lastTime - System.nanoTime() + OPT_TIME ) / 1000000);
                    }else
                    {
                        Thread.sleep(15);
                    }

                }catch(Exception e)
                {
                    System.out.println("Error in thread sleep: " + e);
                }
            }
        }
    }
    
    /*
     * Set the picture to be used as a background.
     */
    private void setBackground() throws IOException
    {
        backgroundImage = ImageIO.read(new File("Images\\night_sky.jpg"));
        scoreImage = ImageIO.read(new File("Images\\score_sprite.png"));
        livesImage = ImageIO.read(new File("Images\\lives_sprite.png"));
        gameOverImage = ImageIO.read(new File("Images\\gameover_sprite.png"));
    }
    
    private void setMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        this.audioInput = AudioSystem.getAudioInputStream(new File("Music\\Heart.wav"));
        this.soundClip = AudioSystem.getClip();
        soundClip.open(audioInput);
        
    }
    
    private void shootSound()
    {
        Thread playSound = new Thread()
        {
            @Override
            public void run()
            {
                soundClip.setFramePosition(0);
                soundClip.start();
            }
        };
        playSound.start();
    }
}
