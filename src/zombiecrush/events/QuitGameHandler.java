/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.events;

import zombiecrush.ui.ZombieCrushMiniGame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 *
 * @author Jing
 */
public class QuitGameHandler  extends WindowAdapter{
    private ZombieCrushMiniGame game;
    
    
    public QuitGameHandler(ZombieCrushMiniGame initMiniGame)
    {
        game = initMiniGame;
    }
    
    /**
     * This method is called when the user clicks the window'w X. We 
     * respond by giving the player a loss if the game is still going on.
     * 
     * @param we Window event object.
     */
    @Override
    public void windowClosing(WindowEvent we)
    {
        // IF THE GAME IS STILL GOING ON, END IT AS A LOSS
       
        // AND CLOSE THE ALL
        System.exit(0);
    }
}
