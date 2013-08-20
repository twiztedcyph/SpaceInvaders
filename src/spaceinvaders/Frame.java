
package spaceinvaders;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private boolean runGame = true; //Enables the game loop to be stopped.
    private int fps = 0, avgFps;
    private Ship ship;
    
    public Frame()
    {
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
        } catch (IOException ex)
        {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        runLoop();
    }
    
    /*
     * This method will handle all of the rendering stuff.
     */
    private void render()
    {
        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 800, 600);
        g2d.setColor(Color.red);
        g2d.drawString("FPS: " + String.valueOf(avgFps), 10, 15);
        ship.drawShip(g2d);
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
        final int TARGET_FPS = 60;
        final long OPT_TIME = 1000000000 / TARGET_FPS;
        
        while(runGame)
        {
            long now = System.nanoTime();
            long updateLength = now - lastTime;
            lastTime = now;
            
            double delta = updateLength / ((double) OPT_TIME);
            
            lastFpsTime += updateLength;
            fps++;
            
            if(lastFpsTime >= 1000000000)
            {
                avgFps = fps;
                lastFpsTime = 0;
                fps = 0;
            }
            
            render();
            
            try
            {
                Thread.sleep(( lastTime - System.nanoTime() + OPT_TIME ) / 1000000);
            }catch(Exception e)
            {
                
            }
        }
    }
}
