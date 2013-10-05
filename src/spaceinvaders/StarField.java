
package spaceinvaders;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Twiz
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
    }
    
    public synchronized void drawStarField(Graphics2D g2d)
    {
        /*
         * Only draws stars that are still on the screen.
         */
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
        //update star position based on time passed.
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
        /*
         * Remove a star object from the array 
         * once it is no longer valid (on screen).
         */
        for (int i = 0; i < starField.size(); i++)
        {
            Star s = (Star) starField.get(i);
            if(!s.isValid())
            {
                starField.remove(i);
            }
        }
    }
    
    public int getSize()
    {
        // Get the current size of the star field.
        return starField.size();
    }
}
