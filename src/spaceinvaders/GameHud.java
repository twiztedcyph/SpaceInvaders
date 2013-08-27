
package spaceinvaders;

/**
 *
 * @author Cypher
 */
public class GameHud
{
    /*
     * Hoping to provide some kind of HUD for the player from this class.
     * Not sure how I will implement it just yet.... 
     * Will work it out as I go along.
     */
    
    private int lives, points;
    
    public GameHud()
    {
        lives = 3;
        points = 0;
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
