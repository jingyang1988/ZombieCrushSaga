/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import zombiecrush.ui.ZombieCrushMiniGame;

/**
 *
 * @author Jing
 */
public class BackHandler implements ActionListener{
    private ZombieCrushMiniGame game;
    
    public BackHandler(ZombieCrushMiniGame initMiniGame)
    {
       game = initMiniGame;
    }
    public void actionPerformed(ActionEvent ae)
    {
        // IF THERE IS A GAME UNDERWAY, COUNT IT AS A LOSS
     
        // RESET THE LEVEL
        game.switchToSplashScreen();
    }
}

