/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.events;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import properties_manager.PropertiesManager;
import zombiecrush.ZombieCrush;
import zombiecrush.ui.ZombieCrushMiniGame;
/**
 *
 * @author Jing
 */
public class ScrollUpHandler implements ActionListener{
    private ZombieCrushMiniGame game;
     PropertiesManager props = PropertiesManager.getPropertiesManager();
     ArrayList<String> levels = props.getPropertyOptionsList(ZombieCrush.ZombieCrushPropertyType.LEVEL_OPTIONS);
     
    public ScrollUpHandler(ZombieCrushMiniGame initGame)
    {
        game = initGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       game.move=0;
       //move the sprite
     if(game.getGUIDecor().get("BACKGROUND_TYPE").getY()>=-30){
        game.getGUIDecor().get("BACKGROUND_TYPE").setVy(0);
        
          for (int i = 0; i <levels.size(); i++)
        {
            game.getGUIButtons().get(levels.get(i)).setVy(0); 
        }
     }
     else{
       game.getGUIDecor().get("BACKGROUND_TYPE").setVy(8);
        for (int i = 0; i <levels.size(); i++)
        {
            game.getGUIButtons().get(levels.get(i)).setVy(8); 
        }
     }
     }
    }

  
