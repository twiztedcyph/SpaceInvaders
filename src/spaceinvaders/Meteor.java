
package spaceinvaders;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Twiz
 */
public class Meteor
{
    private Rectangle metRect;  //Rectangle used for collision detection.
    private BufferedImage metSpriteOne, metSpriteTwo, metSpriteThree, 
            currentSprite; // meteor sprites.
    private int metXPos, metYPos, metVel, metTTL; //meteor x,y and velocity
    private Random rand;
    private AffineTransform at;
    private double metRotate = 0.01;
    
    public Meteor() throws IOException
    {
        metRect = new Rectangle();
        metRect.setSize(40, 40);
        rand = new Random();
        metXPos = (rand.nextInt(700)) + 50;
        metYPos = -50;
        metVel = rand.nextInt(4) + 2;
        
        /*
         * Setup the meteor sprites to be used in the program. 
         * Only one will be randomly selected used at a time.
         */
        setupMetSprite();
        
        switch(rand.nextInt(3))
        {
            // "Randomly" choose a meteor graphic to be used.
            case 0:
                currentSprite = metSpriteOne;
                break;
            case 1:
                currentSprite = metSpriteTwo;
                break;
            case 2:
                currentSprite = metSpriteThree;
                break;
            default:
                //This should never be used.
                break;
        }
        // set meteor graphic time to live based on meteor's speed.
        metTTL = (800 / metVel) + 200;
    }
    
    public void drawMet(Graphics2D g2d)
    {
        //Draw the meteor at its current position.
        //Affine transform used to individualy rotate meteors
        at = new AffineTransform();
        at.translate(metXPos, metYPos);
        at.rotate(metRotate);
        at.scale(0.2, 0.2);
        at.translate(-currentSprite.getWidth() / 2, -currentSprite.getHeight() / 2);
        g2d.drawImage(currentSprite, at, null);
    }
    
    public void updateMeteor(double delta)
    {
        //update the meteor's position based on elapsed time.
        metRotate += 0.01;
        metTTL--;
        metYPos += metVel * delta;
        metRect.setLocation(metXPos - 20, metYPos - 20);
    }
    
    private void setupMetSprite() throws IOException
    {
        //Initialize all images to be used in this class.
        metSpriteOne = ImageIO.read(new File("Images\\met_sprite_one.png"));
        metSpriteTwo = ImageIO.read(new File("Images\\met_sprite_two.png"));
        metSpriteThree = ImageIO.read(new File("Images\\met_sprite_three.png"));
    }
    
    public boolean isValid()
    {
        //Check if the meteor time to live has expired.
        if(metTTL > 0)
        {
            return true;
        }else
        {
            return false;
        }
    }
    
    public Rectangle getMetRect()
    {
        // This rectangle is used in collision detection.
        return this.metRect;
    }
    
    public void setMetTTL(int newTTL)
    {
        this.metTTL = newTTL;
    }
}
