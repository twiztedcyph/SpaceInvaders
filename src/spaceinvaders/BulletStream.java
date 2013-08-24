
package spaceinvaders;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Cypher
 */
public class BulletStream
{
    /*
     * This will be the stream of bullets. The bullet object
     * is defined in the bullet class.
     */
    private List bulletStream;
    
    public BulletStream()
    {
        bulletStream = Collections.synchronizedList(new ArrayList<Bullet>());
    }
    
    public void addBullet(int shipX, int shipY) throws IOException
    {
        bulletStream.add(new Bullet(shipX, shipY, 5));
    }
    
    public synchronized void updateBullet(double delta)
    {
        for (int i = 0; i < bulletStream.size(); i++)
        {
            Bullet b = (Bullet) bulletStream.get(i);
            if(b.isValid())
            {
                b.updateBullet(delta);
            }
        }
    }
    
    public synchronized void drawBullet(Graphics2D g2d)
    {
        for (int i = 0; i < bulletStream.size(); i++)
        {
            Bullet b = (Bullet) bulletStream.get(i);
            if(b.isValid())
            {
                b.drawBullet(g2d);
            }
        }
    }
    
    public synchronized void deleteBullet()
    {
        for (int i = 0; i < bulletStream.size(); i++)
        {
            Bullet b = (Bullet) bulletStream.get(i);
            if(!b.isValid())
            {
                bulletStream.remove(i);
                System.out.println("Bulet stream size" + bulletStream.size() + " " + b.getBulletY());
            }
        }
    }
}
