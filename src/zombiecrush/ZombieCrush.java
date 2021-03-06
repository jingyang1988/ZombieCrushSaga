/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush;

import properties_manager.PropertiesManager;
import xml_utilities.InvalidXMLFileFormatException;
import zombiecrush.ui.ZombieCrushErrorHandler;
import zombiecrush.ui.ZombieCrushMiniGame;

/**
 *
 * @author Jing
 */
public class ZombieCrush {
 // THIS HAS THE FULL USER INTERFACE AND ONCE IN EVENT
    // HANDLING MODE, BASICALLY IT BECOMES THE FOCAL
    // POINT, RUNNING THE UI AND EVERYTHING ELSE
    static ZombieCrushMiniGame miniGame = new ZombieCrushMiniGame();
    
    // WE'LL LOAD ALL THE UI AND ART PROPERTIES FROM FILES,
    // BUT WE'LL NEED THESE VALUES TO START THE PROCESS
    static String PROPERTY_TYPES_LIST = "property_types.txt";
    static String UI_PROPERTIES_FILE_NAME = "properties.xml";
    static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";    
    static String DATA_PATH = "./data/";

    /**
     * This is where the Mahjong Solitaire game application starts execution. We'll
     * load the application properties and then use them to build our
     * user interface and start the window in event handling mode. Once
     * in that mode, all code execution will happen in response to a 
     * user request.
     */
    public static void main(String[] args)
    {
   
        
        try
        {
            // LOAD THE SETTINGS FOR STARTING THE APP
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(ZombieCrushPropertyType.UI_PROPERTIES_FILE_NAME, UI_PROPERTIES_FILE_NAME);
            props.addProperty(ZombieCrushPropertyType.PROPERTIES_SCHEMA_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
            props.addProperty(ZombieCrushPropertyType.DATA_PATH.toString(), DATA_PATH);
            props.loadProperties(UI_PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
            
            // THEN WE'LL LOAD THE MAHJONG FLAVOR AS SPECIFIED BY THE PROPERTIES FILE
            String gameFlavorFile = props.getProperty(ZombieCrushPropertyType.GAME_FLAVOR_FILE_NAME);
            props.loadProperties(gameFlavorFile, PROPERTIES_SCHEMA_FILE_NAME);
                               
            // NOW WE CAN LOAD THE UI, WHICH WILL USE ALL THE FLAVORED CONTENT
            String appTitle = props.getProperty(ZombieCrushPropertyType.GAME_TITLE_TEXT);
            int fps = Integer.parseInt(props.getProperty(ZombieCrushPropertyType.FPS));
            miniGame.initMiniGame(appTitle, fps);
            miniGame.startGame();
        }
        // THERE WAS A PROBLEM LOADING THE PROPERTIES FILE
        catch(InvalidXMLFileFormatException ixmlffe)
        {
            // LET THE ERROR HANDLER PROVIDE THE RESPONSE
            ZombieCrushErrorHandler errorHandler = miniGame.getErrorHandler();
            errorHandler.processError(ZombieCrushPropertyType.INVALID_XML_FILE_ERROR_TEXT);
        }
    }
  public enum ZombieCrushPropertyType
    {
        /* SETUP FILE NAMES */
        UI_PROPERTIES_FILE_NAME,
        PROPERTIES_SCHEMA_FILE_NAME,
        GAME_FLAVOR_FILE_NAME,
        RECORD_FILE_NAME,
        /* DIRECTORIES FOR FILE LOADING */
        DATA_PATH,
        IMG_PATH,
        
        /* WINDOW DIMENSIONS & FRAME RATE */
        WINDOW_WIDTH,
        WINDOW_HEIGHT,
        FPS,
        GAME_WIDTH,
        GAME_HEIGHT,
        GAME_LEFT_OFFSET,
        GAME_TOP_OFFSET,
        
        /* GAME TEXT */
        GAME_TITLE_TEXT,
        EXIT_REQUEST_TEXT,
        INVALID_XML_FILE_ERROR_TEXT,
        ERROR_DIALOG_TITLE_TEXT,
        
        /* ERROR TYPES */
        AUDIO_FILE_ERROR,
        LOAD_LEVEL_ERROR,
        RECORD_SAVE_ERROR,

        /* IMAGE FILE NAMES */
        WINDOW_ICON,
        SPLASH_SCREEN_IMAGE_NAME,
        SAGA_SCREEN_IMAGE_NAME,
        LEVEL_SCORE_SCREEN_IMAGE_NAME,
        GAME_BACKGROUND_IMAGE_NAME,
        BLANK_TILE_IMAGE_NAME,
        BLANK_TILE_SELECTED_IMAGE_NAME,
        BLANK_TILE_INCORRECTLY_SELECTED_IMAGE_NAME,
        PLAYGAME_BUTTON_IMAGE_NAME,
        PLAYGAME_BUTTON_MOUSE_OVER_IMAGE_NAME,
        RESETGAME_BUTTON_IMAGE_NAME,
       RESETGAME_BUTTON_MOUSE_OVER_IMAGE_NAME,
       QUITGAME_BUTTON_IMAGE_NAME,
        QUITGAME_BUTTON_MOUSE_OVER_IMAGE_NAME,
       PLAYLEVEL_BUTTON_IMAGE_NAME,
      PLAYLEVEL_BUTTON_MOUSE_OVER_IMAGE_NAME,
        QUITLEVEL_BUTTON_IMAGE_NAME,
        QUITLEVEL_BUTTON_MOUSE_OVER_IMAGE_NAME,
     CLOSELEVEL_BUTTON_IMAGE_NAME,
        CLOSELEVEL_BUTTON_MOUSE_OVER_IMAGE_NAME,
       SCROLLUP_BUTTON_IMAGE_NAME,
       SCROLLUP_BUTTON_MOUSE_OVER_IMAGE_NAME,
       SCROLLDOWN_BUTTON_IMAGE_NAME,
       SCROLLDOWN_BUTTON_MOUSE_OVER_IMAGE_NAME,
       CLOSESAGA_BUTTON_IMAGE_NAME,
       CLOSESAGA_BUTTON_MOUSE_OVER_IMAGE_NAME,
       SELECTLEVEL_BUTTON_IMAGE_NAME,
       SELECTLEVEL_BUTTON_MOUSE_OVER_IMAGE_NAME,
       SCORE_IMAGE_NAME,
       MOVE_IMAGE_NAME,
       MALLET_IMAGE_NAME,
       TRANS_IMAGE_NAME,
       FAKE_IMAGE_NAME,
     STARE_IMAGE_NAME,
     STAR1_IMAGE_NAME,
     STAR2_IMAGE_NAME,
     STAR3_IMAGE_NAME,
        
        // AND THE DIALOGS
        STATS_DIALOG_IMAGE_NAME,
        WIN_DIALOG_IMAGE_NAME,
        LOSS_DIALOG_IMAGE_NAME,
        
        /* TILE LOADING STUFF */
        LEVEL_OPTIONS,
        LEVEL_IMAGE_OPTIONS,
        LEVEL_MOUSE_OVER_IMAGE_OPTIONS,
        BASIC_TILES,
        SPECIAL_TILES,
        TRANS_OPTIONS;
       
        
        
        
    }
}
