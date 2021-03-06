/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Jing
 */
public class ZombieCrushConstants {
        // EACH SCREEN HAS ITS OWN BACKGROUND TYPE
    public static final String BACKGROUND_TYPE = "BACKGROUND_TYPE";
     public static final String SAGASCREEN_TYPE = "SAGASCREEN_TYPE";
     
    public static final String BASIC_TYPE = "BASIC_TYPE";
    public static final String SPECIAL_TYPE = "SPECIAL_TYPE";
     public static final String TILE_SPRITE_TYPE_PREFIX = "TILE_";
    
    

    // IN-GAME UI CONTROL TYPES
    public static final String PLAYGAME_BUTTON_TYPE = "PLAYGAME_BUTTON_TYPE";
    public static final String RESETGAME_BUTTON_TYPE = "RESETGAME_BUTTON_TYPE";
    public static final String QUITGAME_BUTTON_TYPE = "QUITGAME_BUTTON_TYPE";
    public static final String SELECTLEVEL_BUTTON_TYPE = "SELECTLEVEL_BUTTON_TYPE";
    public static final String PLAYLEVEL_BUTTON_TYPE = "PLAYLEVEL_BUTTON_TYPE";
        public static final String QUITLEVEL_BUTTON_TYPE = "QUITLEVEL_BUTTON_TYPE";
    public static final String CLOSELEVEL_BUTTON_TYPE = "CLOESELEVEL_BUTTON_TYPE";
    public static final String SCROLLUP_BUTTON_TYPE = "SCROLLUP_BUTTON_TYPE";
    public static final String SCROLLDOWN_BUTTON_TYPE = "SCROLLDOWN_BUTTON_TYPE";
    public static final String PAY_BUTTON_TYPE = "PAY_BUTTON_TYPE";
    public static final String MOVE_TYPE = "MOVE_TYPE";
    public static final String SCORE_TYPE = "SCORE_TYPE";
    public static final String MALLET_TYPE = "MALLET_TYPE";
     public static final String TRANS_TYPE = "TRANS_TYPE";
    public static final String FAKE_TYPE = "FAKE_TYPE";
    public static final String CLOSESAGA_BUTTON_TYPE = "CLOSESAGA_BUTTON_TYPE";
    public static final String STAR_TYPE = "STAR_TYPE";
    

    // DIALOG TYPES
    public static final String STATS_DIALOG_TYPE = "STATS_DIALOG_TYPE";
    public static final String WIN_DIALOG_TYPE = "WIN_DIALOG_TYPE";
    public static final String LOSS_DIALOG_TYPE = "LOSS_DIALOG_TYPE";
    
    // WE'LL USE THESE STATES TO CONTROL SWITCHING BETWEEN THE FOUR
    public static final String SPLASH_SCREEN_STATE = "SPLASH_SCREEN_STATE";
    public static final String GAME_SCREEN_STATE = "GAME_SCREEN_STATE";
    public static final String SAGA_SCREEN_STATE = "SAGA_SCREEN_STATE"; 
    public static final String LEVEL_SCORE_SCREEN_STATE = "LEVEL_SCORE_SCREEN_STATE"; 

    
    //THE STATS FOR STARMETER CONTROL
    public static final String STAR_EMPTY_STATE = "STAR_EMPTY_STATE";
    public static final String STAR1_STATE = "STAR1_STATE";
    public static final String STAR2_STATE = "STAR2_STATE"; 
    public static final String STAR3_STATE = "STAR3_STATE"; 
    
    // THE TILES MAY HAVE 4 STATES:
        // - INVISIBLE_STATE: USED WHEN ON THE SPLASH SCREEN, MEANS A TILE
            // IS NOT DRAWN AND CANNOT BE CLICKED
        // - VISIBLE_STATE: USED WHEN ON THE GAME SCREEN, MEANS A TILE
            // IS VISIBLE AND CAN BE CLICKED (TO SELECT IT), BUT IS NOT CURRENTLY SELECTED
        // - SELECTED_STATE: USED WHEN ON THE GAME SCREEN, MEANS A TILE
            // IS VISIBLE AND CAN BE CLICKED (TO UNSELECT IT), AND IS CURRENTLY SELECTED     
        // - NOT_AVAILABLE_STATE: USED FOR A TILE THE USER HAS CLICKED ON THAT
            // IS NOT FREE. THIS LET'S US GIVE THE USER SOME FEEDBACK
    public static final String INVISIBLE_STATE = "INVISIBLE_STATE";
    public static final String VISIBLE_STATE = "VISIBLE_STATE";
    public static final String SELECTED_STATE = "SELECTED_STATE";
    public static final String INCORRECTLY_SELECTED_STATE = "NOT_AVAILABLE_STATE";
    public static final String MOUSE_OVER_STATE = "MOUSE_OVER_STATE";
    public static final String NOTPLAYED_STATE = "NOTPLAYED_STATE";
     public static final String NOTSEEN_STATE = "NOTSEEN_STATE";

    // THE BUTTONS MAY HAVE 2 STATES:
        // - INVISIBLE_STATE: MEANS A BUTTON IS NOT DRAWN AND CAN'T BE CLICKED
        // - VISIBLE_STATE: MEANS A BUTTON IS DRAWN AND CAN BE CLICKED
        // - MOUSE_OVER_STATE: MEANS A BUTTON IS DRAWN WITH SOME HIGHLIGHTING
            // BECAUSE THE MOUSE IS HOVERING OVER THE BUTTON

    // UI CONTROL SIZE AND POSITION SETTINGS
    
    // OR POSITIONING THE LEVEL SELECT BUTTONS
    public static final int LEVEL_BUTTON_WIDTH = 200;
    public static final int LEVEL_BUTTON_MARGIN = 5;
    public static final int LEVEL_BUTTON_Y = 570;

    // FOR STACKING TILES ON THE GRID
    public static final int NUM_TILES = 144;
    public static final int TILE_IMAGE_OFFSET = 1;
    public static final int TILE_IMAGE_WIDTH = 55;
    public static final int TILE_IMAGE_HEIGHT = 55;
    public static final int Z_TILE_OFFSET = 5;

    // FOR MOVING TILES AROUND
    public static final int MAX_TILE_VELOCITY = 70;
    public static final int SLOW_VELOCITY = 7;
    public static final int MID_VELOCITY = 10;
    
    // UI CONTROLS POSITIONS IN THE GAME SCREEN
    public static final int CONTROLS_MARGIN = 0;
    public static final int PLAYGAME_BUTTON_X = 1100;
    public static final int PLAYGAME_BUTTON_Y = 200;
    public static final int RESETGAME_BUTTON_X = 1100;
    public static final int RESETGAME_BUTTON_Y = 350;
      public static final int QUITGAME_BUTTON_X = 1100;
    public static final int QUITGAME_BUTTON_Y = 500;
      public static final int PLAYLEVEL_BUTTON_X = 660;
    public static final int PLAYLEVEL_BUTTON_Y = 237;
      public static final int QUITLEVEL_BUTTON_X = 1200;
    public static final int QUITLEVEL_BUTTON_Y = 10;
      public static final int SELECTLEVEL_BUTTON_X =300;
    public static final int SELECTLEVEL_BUTTON_Y = 200;
    
      public static final int CLOSESAGA_BUTTON_X = 1200;
    public static final int CLOSESAGA_BUTTON_Y = 10;
    public static final int SCROLLUP_BUTTON_X = 10;
    public static final int SCROLLUP_BUTTON_Y = 510;
       public static final int SCROLLDOWN_BUTTON_X = 10;
    public static final int SCROLLDOWN_BUTTON_Y = 610;
    
      public static final int CLOSELEVEL_BUTTON_X = 1200;
    public static final int CLOSELEVEL_BUTTON_Y = 10;
    public static final int SCORE_X=400;
    public static final int SCORE_Y=1;
    public static final int MOVE_X=700;
    public static final int MOVE_Y=1;
    public static final int MALLET_X=1050;
    public static final int MALLET_Y=0;
    
      
      public static final int PAY_BUTTON_X = 0;
    public static final int PAY_BUTTON_Y = 0;
    public static final int TILE_TEXT_OFFSET = 60;
    public static final int TIME_OFFSET = 130;
    public static final int TIME_TEXT_OFFSET = 55;


    public static final int TILE_STACK_X = 280 + CONTROLS_MARGIN;
    public static final int TILE_STACK_Y = 0;
    public static final int TILE_STACK_OFFSET_X = 30;
    public static final int TILE_STACK_OFFSET_Y = 12;
    public static final int TILE_STACK_2_OFFSET_X = 105;
       
   

    // USED FOR DOING OUR VICTORY ANIMATION
    public static final int WIN_PATH_NODES = 8;
    public static final int WIN_PATH_TOLERANCE = 100;
    public static final int WIN_PATH_COORD = 100;
    public static final int WIN_RAD = 270;
    public static final int WIN_CENTER_X = 640;
    public static final int WIN_CENTER_Y = 350;
     public static final int WIN_RAD_ROOT = 420;

    // COLORS USED FOR RENDERING VARIOUS THINGS, INCLUDING THE
    // COLOR KEY, WHICH REFERS TO THE COLOR TO IGNORE WHEN
    // LOADING ART.
    public static final Color COLOR_KEY = new Color(255, 174, 201);
    public static final Color TRANS_KEY = new Color(237, 28, 36);
    
    public static final Color DEBUG_TEXT_COLOR = Color.RED;
    public static final Color TEXT_DISPLAY_COLOR = new Color (10, 160, 10);
    public static final Color SELECTED_TILE_COLOR = new Color(255,255,0,100);
    public static final Color INCORRECTLY_SELECTED_TILE_COLOR = new Color(255, 50, 50, 100);
    public static final Color STATS_COLOR = new Color(0, 60, 0);

    // FONTS USED DURING FOR TEXTUAL GAME DISPLAYS
    public static final Font TEXT_DISPLAY_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 48);
    public static final Font DEBUG_TEXT_FONT = new Font(Font.MONOSPACED, Font.BOLD, 14);
    public static final Font STATS_FONT = new Font(Font.MONOSPACED, Font.BOLD, 24);
}
