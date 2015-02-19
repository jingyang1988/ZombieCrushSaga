/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.events;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import properties_manager.PropertiesManager;
import zombiecrush.ZombieCrush;
import static zombiecrush.ZombieCrushConstants.BACKGROUND_TYPE;
import zombiecrush.ui.ZombieCrushMiniGame;
/**
 *
 * @author Jing
 */
public class ScrollDownHandler implements ActionListener{
    private ZombieCrushMiniGame game;
     PropertiesManager props = PropertiesManager.getPropertiesManager();
     ArrayList<String> levels = props.getPropertyOptionsList(ZombieCrush.ZombieCrushPropertyType.LEVEL_OPTIONS);
    public ScrollDownHandler(ZombieCrushMiniGame initGame)
    {
        game = initGame;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        
      game.move=0;
      if(game.getGUIDecor().get("BACKGROUND_TYPE").getY()<-2300){
      game.getGUIDecor().get("BACKGROUND_TYPE").setVy(0);
      
      for (int i = 0; i <levels.size(); i++)
        {
            game.getGUIButtons().get(levels.get(i)).setVy(0); 
        }
      }
       else{  
       game.getGUIDecor().get("BACKGROUND_TYPE").setVy(-8);
       for (int i = 0; i <levels.size(); i++)
        {
            game.getGUIButtons().get(levels.get(i)).setVy(-8); 
        }
       
      //game.getGUIButtons().get("SELECTLEVEL_BUTTON_TYPE").setVy(-5);
      }
    }
}
