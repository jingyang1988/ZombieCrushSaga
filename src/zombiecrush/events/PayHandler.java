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
public class PayHandler implements ActionListener {
     private ZombieCrushMiniGame game;
     private ZombieCrushDataModel data;
     boolean smash=false;
    public PayHandler(ZombieCrushMiniGame initGame)
    {
        game = initGame;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
     game.setSmash();
      System.out.println("SMASH BEGIN");
    }
    
   
}
