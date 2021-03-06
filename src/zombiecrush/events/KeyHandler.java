/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.events;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static zombiecrush.ZombieCrushConstants.GAME_SCREEN_STATE;
import zombiecrush.data.ZombieCrushDataModel;
import zombiecrush.data.ZombieCrushMove;
import zombiecrush.ui.ZombieCrushMiniGame;

/**
 * This event handler lets us provide additional custom responses
 * to key presses while Mahjong is running.
 * 
 * @author Richard McKenna and jing
 */
public class KeyHandler extends KeyAdapter
{
    // THE MAHJONG GAME ON WHICH WE'LL RESPOND
    private ZombieCrushMiniGame game;

    /**
     * This constructor simply inits the object by 
     * keeping the game for later.
     * 
     * @param initGame The Mahjong game that contains
     * the back button.
     */    
    public KeyHandler(ZombieCrushMiniGame initGame)
    {
        game = initGame;
    }
    
    /**
     * This method provides a custom game response to when the user
     * presses a keyboard key.
     * 
     * @param ke Event object containing information about the event,
     * like which key was pressed.
     */
    @Override
    public void keyPressed(KeyEvent ke)
    {
        // CHEAT BY ONE MOVE. NOTE THAT IF WE HOLD THE C
        // KEY DOWN IT WILL CONTINUALLY CHEAT
        if (ke.getKeyCode() == KeyEvent.VK_L)
        {
            ZombieCrushDataModel data = (ZombieCrushDataModel)game.getDataModel();
            data.setCurrentScore();
            data.setCurrentMove();
      
        }
        else if(ke.getKeyCode() == KeyEvent.VK_W){
            ZombieCrushDataModel data = (ZombieCrushDataModel)game.getDataModel();
            data.setCurrentScoreH();
            data.setCurrentMove();
           
        }
    }
}