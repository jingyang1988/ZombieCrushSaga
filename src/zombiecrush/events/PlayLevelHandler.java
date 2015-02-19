/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.events;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import zombiecrush.file.ZombieCrushFileManager;
import zombiecrush.ui.ZombieCrushMiniGame;
/**
 *
 * @author Jing
 */
public class PlayLevelHandler implements ActionListener{
     private ZombieCrushMiniGame game;
         // HERE'S THE LEVEL TO LOAD
    private String levelFile;
    public PlayLevelHandler(ZombieCrushMiniGame initGame,String initLevelFile)
    {
        game = initGame;
        levelFile=initLevelFile;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
         // UPDATE THE DATA
           
        game.switchToGameScreen();
    }
}
