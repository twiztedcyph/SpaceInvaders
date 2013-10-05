
package spaceinvaders;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Twiz
 */
public class MetBullList
{
    private List meteorShower;
    private List bulletStream;
    
    public MetBullList()
    {
        //Synchronized array lists for meteors and bullets.
        meteorShower = Collections.synchronizedList(new ArrayList<Meteor>());
        bulletStream = Collections.synchronizedList(new ArrayList<Bullet>());
    }
    
    public void addMeteor() throws IOException
    {
        meteorShower.add(new Meteor());
    }
    
    public synchronized void drawMeteors(Graphics2D g2d)
    {
        //Draw all valid meteors.
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
        //update all valid meteors.
        for (int i = 0; i < meteorShower.size(); i++)
        {
            Meteor m = (Meteor) meteorShower.get(i);
            if(m.isValid())
            {
                m.updateMeteor(delta);
            }
        }
    }
    
    public synchronized void checkCollisions(Rectangle ship, GameHud hud)
    {
        // check for any collisions between bullets and meteors.
        for (int i = 0; i < meteorShower.size(); i++)
        {
            Meteor m = (Meteor) meteorShower.get(i);
            if(ship.intersects(m.getMetRect()))
            {
                m.setMetTTL(0);
                hud.minusLives();
            }
        }
    }
    
    public synchronized void deleteMeteor()
    {
        // Delete any expired meteors.
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
        // add a new bullet to the array at the ships current position.
        bulletStream.add(new Bullet(shipX, shipY, 5));
    }
    
    public synchronized void updateBullet(double delta)
    {
        //update all bullet positions based on elapsed time.
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
        //draw all bullets at their current positions.
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
        //delete all bullets with a ttl <= 0;
        for (int i = 0; i < bulletStream.size(); i++)
        {
            Bullet b = (Bullet) bulletStream.get(i);
            if(!b.isValid())
            {
                bulletStream.remove(i);
            }
        }
    }
    
    public synchronized void checkHits(GameHud hud)
    {
        // Collision detection system.
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
                        m.setMetTTL(1);
                        b.setBulletTTL(2);
                        hud.plusScore();
                    }
                }
            }
        }
    }
    
    public void resetAll()
    {
        /*
         * Reset the current bullet and meteor arraylists. 
         * Used for starting a new game.
         */
        meteorShower = Collections.synchronizedList(new ArrayList<Meteor>());
        bulletStream = Collections.synchronizedList(new ArrayList<Bullet>());
    }
}
