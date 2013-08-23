
package spaceinvaders;

import java.awt.Graphics2D;
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
    private int bullXPos, bullYPos, bulletVel;
    private BufferedImage bulletSprite;
    private AffineTransform at;

    public Bullet(int bullXPos, int bullYPos, int bulletVel) throws IOException
    {
        this.bullXPos = bullXPos;
        this.bullYPos = bullYPos;
        this.bulletVel = bulletVel;
        this.setBulletSprite();
    }
    
    public void drawBullet(Graphics2D g2d)
    {
        at = new AffineTransform();
        at.translate(bullXPos, bullYPos);
        at.scale(0.15, 0.1);
        g2d.drawImage(bulletSprite, at, null);
    }
    
    public void updateBullet(double delta)
    {
        bullYPos -= bulletVel * delta;
    }
    
    private void setBulletSprite() throws IOException
    {
        bulletSprite = ImageIO.read(new File("C:\\Users\\Cypher\\Documents\\image\\shoot_sprite.png"));
    }
}
