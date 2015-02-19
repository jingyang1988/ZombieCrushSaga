/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.events;

import zombiecrush.ui.ZombieCrushMiniGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Jing
 */
public class ResetGameHandler implements ActionListener{

    private ZombieCrushMiniGame game;
    public ResetGameHandler(ZombieCrushMiniGame initGame)
    {
        game = initGame;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        
        // RESET THE GAME AND ITS DATA
        game.reset();
    }
}
