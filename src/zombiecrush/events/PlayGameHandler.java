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
public class PlayGameHandler implements ActionListener{
    private ZombieCrushMiniGame game;
    
    public PlayGameHandler(ZombieCrushMiniGame initGame)
    {
        game = initGame;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        
        // RESET THE GAME AND ITS DATA
        game.switchToSagaScreen();
    }
    
}
