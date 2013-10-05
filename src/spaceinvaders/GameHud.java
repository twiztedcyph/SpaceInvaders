
package spaceinvaders;

/**
 *
 * @author Twiz
 */
public class GameHud
{
    /*
     * Lives and score data.
     */
    
    private int lives, points;
    
    public GameHud()
    {
        lives = 3;
        points = 0;
    }
    
    public void resetLives()
    {
        this.lives = 3;
    }
    
    public void resetScore()
    {
        this.points = 0;
    }
    
    public void minusLives()
    {
        this.lives--;
    }
    
    public int getLives()
    {
        return lives;
    }
    
    public void plusScore()
    {
        this.points++;
    }
    
    public int getPoints()
    {
        return points;
    }
}
