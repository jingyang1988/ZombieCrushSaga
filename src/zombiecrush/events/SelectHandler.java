/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.events;
import zombiecrush.ui.ZombieCrushMiniGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import zombiecrush.data.ZombieCrushDataModel;
import zombiecrush.file.ZombieCrushFileManager;
import static zombiecrush.ZombieCrushConstants.*;
/**
 *
 * @author Jing
 */
public class SelectHandler implements ActionListener{
    private ZombieCrushMiniGame game;
     // HERE'S THE LEVEL TO LOAD
    private String levelFile;
    
     public SelectHandler(ZombieCrushMiniGame initGame,String initLevelFile)
    {
        game = initGame;
      levelFile=initLevelFile;
       
    }
    /**
     * Here is the event response. This code is executed when
     * the user clicks on a button for selecting a level
     * which is how the user starts a game. Note that the game 
     * data is already locked for this thread before it is called, 
     * and that it will be unlocked after it returns.
     * 
     * @param ae the event object for the button press
     */
    @Override
    public void actionPerformed(ActionEvent ae)
    {
 // WE ONLY LET THIS HAPPEN IF THE SPLASH SCREEN IS VISIBLE
     
            // GET THE GAME'S DATA MODEL, WHICH IS ALREADY LOCKED FOR US
            ZombieCrushDataModel data = (ZombieCrushDataModel)game.getDataModel();
            ZombieCrushFileManager fileManager = game.getFileManager();
            fileManager.loadLevel(levelFile);
         
            // GO TO THE GAME
            game.switchToScoreScreen();
            
        
       
        
    }
}
