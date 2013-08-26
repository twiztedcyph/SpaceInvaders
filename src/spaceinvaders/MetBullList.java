
package spaceinvaders;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Cypher
 */
public class MetBullList
{
    private List meteorShower;
    private List bulletStream;
    
    public MetBullList()
    {
        meteorShower = Collections.synchronizedList(new ArrayList<Meteor>());
        bulletStream = Collections.synchronizedList(new ArrayList<Bullet>());
    }
    
    public void addMeteor() throws IOException
    {
        meteorShower.add(new Meteor());
    }
    
    public synchronized void drawMeteors(Graphics2D g2d)
    {
        for (int i = 0; i < meteorShower.size(); i++)
        {
            Meteor m = (Meteor) meteorShower.get(i);
            if(m.isValid())
            {
                m.drawMet(g2d);
            }
        }
    }
    
    public synchronized void updateMeteors(double delta)
    {
        for (int i = 0; i < meteorShower.size(); i++)
        {
            Meteor m = (Meteor) meteorShower.get(i);
            if(m.isValid())
            {
                m.updateMeteor(delta);
            }
        }
    }
    
    public synchronized void checkCollisions(Rectangle ship)
    {
        for (int i = 0; i < meteorShower.size(); i++)
        {
            Meteor m = (Meteor) meteorShower.get(i);
            if(ship.intersects(m.getMetRect()))
            {
                m.setMetTTL(0);
            }
        }
    }
    
    public synchronized void deleteMeteor()
    {
        for (int i = 0; i < meteorShower.size(); i++)
        {
            Meteor m = (Meteor) meteorShower.get(i);
            if(!m.isValid())
            {
                meteorShower.remove(i);
            }
        }
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
            }
        }
    }
    
    public synchronized void checkHits()
    {
        for (int i = 0; i < bulletStream.size(); i++)
        {
            Bullet b = (Bullet) bulletStream.get(i);
            for (int j = 0; j < meteorShower.size(); j++)
            {
                Meteor m = (Meteor) meteorShower.get(j);
                if(m.isValid())
                {
                    if(b.getBullRect().intersects(m.getMetRect()))
                    {
                        m.setMetTTL(0);
                    }
                }
            }
        }
    }
}
