
package spaceinvaders;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Cypher
 */
public class Star
{
    
    private BufferedImage starSpriteOne, starSpriteTwo, starSpriteThree, usedSprite;
    private int starXPos, starYPos, starVel, starSize, starTTL;
    private Random rand;
    
    public Star() throws IOException
    {
        setupStarSprite();
        rand = new Random();
        starXPos = rand.nextInt(750) + 25;
        starYPos = -50;
        switch(rand.nextInt(3))
        {
            case 0:
                usedSprite = starSpriteOne;
                break;
            case 1:
                usedSprite = starSpriteTwo;
                break;
            case 2:
                usedSprite = starSpriteThree;
                break;
            default:
                System.out.println("Something went horribly wrong... "
                        + "Call the police.");
                break;
        }
        starVel = rand.nextInt(3) + 2;
        starSize = (starVel) * starVel;
        starTTL = (750 / starVel) + 350;
    }
    
    public void updateStar(double delta)
    {
        starYPos += (starVel * delta);
        starTTL--;
    }
    
    public void drawStar(Graphics2D g2d)
    {
        g2d.drawImage(usedSprite, starXPos, starYPos, starSize, starSize, null);
    }
    
    private void setupStarSprite() throws IOException
    {
        starSpriteOne = ImageIO.read(new File("Images\\star_sprite_one.png"));
        starSpriteTwo = ImageIO.read(new File("Images\\star_sprite_two.png"));
        starSpriteThree = ImageIO.read(new File("Images\\star_sprite_three.png"));
    }
    
    public int getTTL()
    {
        return starTTL;
    }
    
    public int getY()
    {
        return this.starYPos;
    }
    
    public boolean isValid()
    {
        if(starTTL <= 0)
        {
            return false;
        }else
        {
            return true;
        }
    }
}
