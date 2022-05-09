
package fruitninjagame;

import java.awt.Point;


public interface IGameActions{

public IGameObject createGameObject();
/*
*@return created game object
*/

public void updateLives(IGameObject g);
/*update lives when a fruit falls off screen*/

public void sliceObjects(IGameObject g,Point p);
/*
* it is used to slice fruits located in your swiping region
This method can take your swiping region as parameters (they
depend on how you calculate it).
*/
public void setStrategy();
/*sets objects velocity and time interval betweeen throwing objects
according to the player's score
*/
 public void readHighScore();
 /*read highscore from file */
public void updateHighScore(int newHighScore);
  /*upload new highscore when the player beats it*/
public void saveGame();
/*
*saves the current state of the game
*/
public void loadGame();
/*
*loads the last saved state of the game
*/
public void ResetGame();/*
*resets the game to its initial state
*/

}
