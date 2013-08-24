
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
public class StarField
{
    /*
     * This will be a randomly generated moving (from top to bottom) star field
     * to make the ship look as though it's traveling forwards. The stars are
     * defined in the Star class.
     */
    
    private List starField;
    
    public StarField()
    {
        starField = Collections.synchronizedList(new ArrayList<Star>());
    }
    
    public synchronized void addStar() throws IOException
    {
        starField.add(new Star());
        System.out.println(this.starField.size());
    }
    
    public synchronized void drawStarField(Graphics2D g2d)
    {
        for (int i = 0; i < starField.size(); i++)
        {
            Star s = (Star) starField.get(i);
            if(s.isValid())
            {
                s.drawStar(g2d);
            }
        }
    }
    
    public synchronized void updateStarField(double delta)
    {
        for (int i = 0; i < starField.size(); i++)
        {
            Star s = (Star) starField.get(i);
            if(s.isValid())
            {
                s.updateStar(delta);
            }
        }
    }
    
    public synchronized void removeStar()
    {
        for (int i = 0; i < starField.size(); i++)
        {
            Star s = (Star) starField.get(i);
            if(!s.isValid())
            {
                starField.remove(i);
                System.out.println("Removed one: " + i + " " + s.getTTL() + " " + s.getY());
            }
        }
    }
    
    public int getSize()
    {
        return starField.size();
    }
}
