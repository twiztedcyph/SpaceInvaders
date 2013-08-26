
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
 * @author Cypher
 */
public class Bullet
{
    /*
     * This class will define the bullet(s) when the ship shoots.
     */
    private int bullXPos, bullYPos, bulletVel, bulletTTL;  //Bullet x, y and velocity.
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
        at = new AffineTransform();
        at.translate(bullXPos, bullYPos);
        at.scale(0.15, 0.1);
        g2d.drawImage(bulletSprite, at, null);
        g2d.setColor(Color.WHITE);
        g2d.draw(bulletRect);
    }
    
    public void updateBullet(double delta)
    {
        bulletTTL--;
        bullYPos -= bulletVel * delta;
        bulletRect.setBounds(bullXPos, bullYPos, 3, 13);
    }
    
    private void setBulletSprite() throws IOException
    {
        bulletSprite = ImageIO.read(new File("Images\\shoot_sprite.png"));
    }
    
    public boolean isValid()
    {
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
        return this.bullYPos;
    }
    
    public Rectangle getBullRect()
    {
        return this.bulletRect;
    }
}
