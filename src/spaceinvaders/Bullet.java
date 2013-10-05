
package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Twiz
 */
public class Bullet
{
    /*
     * This class will define the bullet object for when the ship shoots.
     */
    
    //Bullet x, y and velocity.
    private int bullXPos, bullYPos, bulletVel, bulletTTL;
    private BufferedImage bulletSprite; //Bullet graphic.
    private Rectangle bulletRect;
    private AffineTransform at;

    public Bullet(int bullXPos, int bullYPos, int bulletVel) throws IOException
    {
        this.bullXPos = bullXPos;
        this.bullYPos = bullYPos;
        this.bulletVel = bulletVel;
        bulletRect = new Rectangle();
        bulletTTL = 550 / this.bulletVel;
        this.setBulletSprite();
    }
    
    public void drawBullet(Graphics2D g2d)
    {
        /*
         * Draw the bullet at its current position in the 2d space.
         */
        at = new AffineTransform();
        at.translate(bullXPos, bullYPos);
        at.scale(0.15, 0.1);
        g2d.drawImage(bulletSprite, at, null);
        g2d.setColor(Color.WHITE);
        g2d.draw(bulletRect);
    }
    
    public void updateBullet(double delta)
    {
        //Update the bullets position based on time passed..
        bulletTTL--;
        bullYPos -= bulletVel * delta;
        bulletRect.setBounds(bullXPos, bullYPos, 3, 13);
    }
    
    private void setBulletSprite() throws IOException
    {
        // initialize the bullet image to be used.
        bulletSprite = ImageIO.read(new File("Images\\shoot_sprite.png"));
    }
    
    public boolean isValid()
    {
        /*
         * Used to check if a bullet should still be displayed or 
         * if it can be removed from the bullet arraylist.
         */
        if(bulletTTL > 0)
        {
            return true;
        }else
        {
            return false;
        }
    }
    
    public int getBulletY()
    {
        // Get the y position of this bullet.
        return this.bullYPos;
    }
    
    public Rectangle getBullRect()
    {
        // Rectangle used to check for bullet collisions.
        return this.bulletRect;
    }
    
    public void setBulletTTL(int newTTL)
    {
        this.bulletTTL = newTTL;
    }
}
