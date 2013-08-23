
package spaceinvaders;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Cypher
 */
public class Ship
{
    private int shipX;  //This will be the position of the ship.
    private BufferedImage shipSprite;   //This will be the ship image.
    private Rectangle shipRect;    //This will be the rect used for collision detection.
    private final int Y_POS = 520; //The Y pos of the ship.
    private boolean moveLeft = false, moveRight = false;
    private AffineTransform at;
    
    /*
     * Ship constructor.
     */
    public Ship() throws IOException
    {
        //set the sprite to be used for the ship.
        this.setShipSprite();
        shipX = 400;
        //initialize and setup the rectangle.
        shipRect = new Rectangle();
        shipRect.setLocation(shipX, Y_POS);
        shipRect.setSize(new Dimension(50, 50));
    }
    
    /*
     * Set the sprite to be used for the ship.
     */
    private void setShipSprite() throws IOException
    {
        shipSprite = ImageIO.read(new File("C:\\Users\\Cypher\\Documents\\image\\ship_sprite.png"));
    }
    
    public void drawShip(Graphics2D g2d)
    {
        /*
         * Affine transform used so that the ship sprite can be rotated and
         * manipulated separately from the g2d stuff....
         */
        at = new AffineTransform();
        //Set the position.
        at.translate(shipX, Y_POS);
        //Set the size.
        at.scale(0.2, 0.2);
        //Draw the ship.
        g2d.drawImage(shipSprite, at, null);
        
    }
    
    public void updateShip(double delta)
    {
        /*
         * Ship movement is done here...
         */
        if(moveLeft)
        {
            if(this.shipX > 1)
            {
                shipX -= (int)(4 * delta);
            }
        }
        if(moveRight)
        {
            if(this.shipX < 741)
            {
                shipX += (int)(4 * delta);
            }
        }
    }

    /*
     * Setter methods for the ship movement booleans.
     */
    public void setMoveLeft(boolean moveLeft)
    {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight)
    {
        this.moveRight = moveRight;
    }
}
