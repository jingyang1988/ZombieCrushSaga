/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.ui;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import zombiecrush.data.ZombieCrushDataModel;
import mini_game.MiniGame;
import static zombiecrush.ZombieCrushConstants.*;
import mini_game.Sprite;
import mini_game.SpriteType;
import properties_manager.PropertiesManager;
import zombiecrush.ZombieCrush.ZombieCrushPropertyType;
import zombiecrush.data.ZombieCrushLevelRecord;
import zombiecrush.file.ZombieCrushFileManager;
import zombiecrush.data.ZombieCrushRecord;


import mini_game.MiniGame;
import zombiecrush.events.BackHandler;
import zombiecrush.events.CloseLevelHandler;
import zombiecrush.events.CloseSagaHandler;
import zombiecrush.events.KeyHandler;
import zombiecrush.events.PayHandler;
import zombiecrush.events.PlayGameHandler;
import zombiecrush.events.PlayLevelHandler;
import zombiecrush.events.QuitGameHandler;
import zombiecrush.events.QuitLevelHandler;
import zombiecrush.events.ResetGameHandler;
import zombiecrush.events.ScrollDownHandler;
import zombiecrush.events.ScrollUpHandler;
import zombiecrush.events.SelectHandler;

/**
 *
 * @author Jing
 */
public class ZombieCrushMiniGame extends MiniGame{
      // THE PLAYER RECORD FOR EACH LEVEL, WHICH LIVES BEYOND ONE SESSION
    private ZombieCrushRecord record;
    
    // HANDLES ERROR CONDITIONS
    private ZombieCrushErrorHandler errorHandler;
    
    // MANAGES LOADING OF LEVELS AND THE PLAYER RECORDS FILES
    private ZombieCrushFileManager fileManager;
    
    // THE SCREEN CURRENTLY BEING PLAYED
    private String currentScreenState;
    BufferedImage img1;
    private boolean Smash=false;
    private int levelsPlayable;
    private String levelname="";
    private int starEarned=0;
    // ACCESSOR METHODS
        // - getPlayerRecord
        // - getErrorHandler
        // - getFileManager
        // - isCurrentScreenState
    
    /**
     * Accessor method for getting the player record object, which
     * summarizes the player's record on all levels.
     * 
     * @return The player's complete record.
     */
    public ZombieCrushRecord getPlayerRecord() 
    { 
        return record; 
    }

    /**
     * Accessor method for getting the application's error handler.
     * 
     * @return The error handler.
     */
    public ZombieCrushErrorHandler getErrorHandler()
    {
        return errorHandler;
    }

    /**
     * Accessor method for getting the app's file manager.
     * 
     * @return The file manager.
     */
    public ZombieCrushFileManager getFileManager()
    {
        return fileManager;
    }

    /**
     * Used for testing to see if the current screen state matches
     * the testScreenState argument. If it mates, true is returned,
     * else false.
     * 
     * @param testScreenState Screen state to test against the 
     * current state.
     * 
     * @return true if the current state is testScreenState, false otherwise.
     */
    public boolean isCurrentScreenState(String testScreenState)
    {
        return testScreenState.equals(currentScreenState);
    }
 
    public void getLevelsPlayable(){
        
    }
        public void setStars(int stars){
            starEarned=stars;
        }
    public String getCurrentScreenState(){
        return currentScreenState;
    }
    
public void setSmash(){
    if(Smash==false){
        Smash=true;
    }
    else{
        Smash=false;
    }
   
}
public boolean getSmash(){
    return Smash;
}
    // SERVICE METHODS
        // - displayStats
        // - savePlayerRecord
        // - switchToGameScreen
        // - switchToSplashScreen
        // - updateBoundaries
   
    /**
     * This method displays makes the stats dialog display visible,
     * which includes the text inside.
     */
    public void displayStats()
    {
        // MAKE SURE ONLY THE PROPER DIALOG IS VISIBLE
        guiDialogs.get(WIN_DIALOG_TYPE).setState(INVISIBLE_STATE);
        guiDialogs.get(STATS_DIALOG_TYPE).setState(VISIBLE_STATE);
        guiDialogs.get(LOSS_DIALOG_TYPE).setState(INVISIBLE_STATE);
        data.pause();
    }

    /**
     * This method forces the file manager to save the current player record.
     */
    public void savePlayerRecord()
    { // THIS CURRENTLY DOES NOTHING, INSTEAD, IT MUST SAVE ALL THE
        // PLAYER RECORDS IN THE SAME FORMAT IT IS BEING LOADED
        try{
          PropertiesManager props = PropertiesManager.getPropertiesManager();
            String dataPath = props.getProperty(ZombieCrushPropertyType.DATA_PATH);
            String recordPath = dataPath + props.getProperty(ZombieCrushPropertyType.RECORD_FILE_NAME);
            File fileToSave = new File(recordPath);

            // LET'S USE A FAST LOADING TECHNIQUE. WE'LL LOAD ALL OF THE
            // BYTES AT ONCE INTO A BYTE ARRAY, AND THEN PICK THAT APART.
            // THIS IS FAST BECAUSE IT ONLY HAS TO DO FILE READING ONCE
            byte[] bytes = record.toByteArray();
            ByteArrayOutputStream bais = new ByteArrayOutputStream();
            FileOutputStream fis = new FileOutputStream(fileToSave);
            BufferedOutputStream bis = new BufferedOutputStream(fis);
            
            // HERE IT IS, THE ONLY READY REQUEST WE NEED
            bis.write(bytes);
            bis.flush();
            bis.close();
            
            
            // NOTE THAT WE NEED TO LOAD THE DATA IN THE SAME
            // ORDER AND FORMAT AS WE SAVED IT
            // FIRST READ THE NUMBER OF LEVELS
          

            //for (int i = 0; i < numLevels; i++)
            //{
                //String levelName = dis.writeUTF(name)
                        //readUTF();
               // MahjongLevelRecord rec = new MahjongLevelRecord();
                //rec.gamesPlayed = dis.readInt();
                //rec.wins = dis.readInt();
                //rec.losses = dis.readInt();
                //rec.fastestTime = dis.readLong();
                
            //}
            
        }
    
    catch(Exception e)
        {
            // THERE WAS NO RECORD TO LOAD, SO WE'LL JUST RETURN AN
            // EMPTY ONE AND SQUELCH THIS EXCEPTION
        }        
        
        
    }
    
    /**
     * This method switches the application to the game screen, making
     * all the appropriate UI controls visible & invisible.
     */
    public void switchToGameScreen() throws InterruptedException
    {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
       // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(GAME_SCREEN_STATE);
        guiDecor.get(BACKGROUND_TYPE).setY(0);
        // ACTIVATE THE TOOLBAR AND ITS CONTROLS
         guiButtons.get(PLAYGAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(PLAYGAME_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(RESETGAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(RESETGAME_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(QUITGAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(QUITGAME_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(PLAYLEVEL_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(PLAYLEVEL_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(CLOSELEVEL_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(CLOSELEVEL_BUTTON_TYPE).setEnabled(false);
         guiButtons.get(CLOSESAGA_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(CLOSESAGA_BUTTON_TYPE).setEnabled(false);
         guiButtons.get(SCROLLUP_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(SCROLLUP_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SCROLLDOWN_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(SCROLLDOWN_BUTTON_TYPE).setEnabled(false);
           guiButtons.get(QUITLEVEL_BUTTON_TYPE).setState(VISIBLE_STATE);
            guiButtons.get(QUITLEVEL_BUTTON_TYPE).setEnabled(true);
      guiDecor.get(SCORE_TYPE).setState(VISIBLE_STATE);
        guiDecor.get(MOVE_TYPE).setState(VISIBLE_STATE);
          guiButtons.get(MALLET_TYPE).setState(VISIBLE_STATE); 
guiDecor.get(STAR_TYPE).setState(STAR_EMPTY_STATE);
        
        // DEACTIVATE THE LEVEL SELECT BUTTONS
        ArrayList<String> levels = props.getPropertyOptionsList(ZombieCrushPropertyType.LEVEL_OPTIONS);
        for (String level : levels)
        {
            guiButtons.get(level).setState(INVISIBLE_STATE);
            guiButtons.get(level).setEnabled(false);
        }

        // MOVE THE TILES TO THE STACK AND MAKE THEM VISIBLE
        ((ZombieCrushDataModel)data).enableTiles(true);
        data.reset(this);
       
        /**while(possibeMoves)
         * data.checkformoves
        
        */
        // AND CHANGE THE SCREEN STATE
        currentScreenState = GAME_SCREEN_STATE;
        
        guiDialogs.get(WIN_DIALOG_TYPE).setState(INVISIBLE_STATE);
        guiDialogs.get(LOSS_DIALOG_TYPE).setState(INVISIBLE_STATE);
             ((ZombieCrushDataModel)data).checkAlls();
        ((ZombieCrushDataModel)data).setCurrentScore();
        
    }
    public void switchToSagaScreen(){
         PropertiesManager props = PropertiesManager.getPropertiesManager();
         // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(SAGA_SCREEN_STATE);
        //guiButtons.get(SELECTLEVEL_BUTTON_TYPE).setY(200);
        
        // ACTIVATE THE TOOLBAR AND ITS CONTROLS
         guiButtons.get(PLAYGAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(PLAYGAME_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(RESETGAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(RESETGAME_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(QUITGAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(QUITGAME_BUTTON_TYPE).setEnabled(false);
         guiButtons.get(PLAYLEVEL_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(PLAYLEVEL_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(CLOSELEVEL_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(CLOSELEVEL_BUTTON_TYPE).setEnabled(false);
         guiButtons.get(SCROLLUP_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(SCROLLUP_BUTTON_TYPE).setEnabled(true);
            guiButtons.get(SCROLLDOWN_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(SCROLLDOWN_BUTTON_TYPE).setEnabled(true);
           guiButtons.get(QUITLEVEL_BUTTON_TYPE).setState(INVISIBLE_STATE);
            guiButtons.get(QUITLEVEL_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(CLOSESAGA_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(CLOSESAGA_BUTTON_TYPE).setEnabled(true);
        currentScreenState = SAGA_SCREEN_STATE;
         guiDecor.get(SCORE_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(MOVE_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(MALLET_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(STAR_TYPE).setState(NOTSEEN_STATE);
           ArrayList<String> levels = props.getPropertyOptionsList(ZombieCrushPropertyType.LEVEL_OPTIONS);
          levelsPlayable=getPlayerRecord().getplayedLevels("./data/./ZombieCrush/level1.zom");
          int playable1=0;
        for (String level : levels)
        {
            if(playable1<=levelsPlayable){
            guiButtons.get(level).setState(VISIBLE_STATE);
            guiButtons.get(level).setEnabled(true);}
            else{
                guiButtons.get(level).setState(NOTPLAYED_STATE);
            guiButtons.get(level).setEnabled(false);
            }
            playable1++;
        }  
        ((ZombieCrushDataModel)data).enableTiles(false);
        currentScreenState = SAGA_SCREEN_STATE;
    }
    public void switchToScoreScreen(){
         PropertiesManager props = PropertiesManager.getPropertiesManager();
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(LEVEL_SCORE_SCREEN_STATE);
        guiDecor.get(BACKGROUND_TYPE).setY(0);
        // ACTIVATE THE TOOLBAR AND ITS CONTROLS
         guiButtons.get(PLAYGAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(PLAYGAME_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(RESETGAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(RESETGAME_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(QUITGAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(QUITGAME_BUTTON_TYPE).setEnabled(false);
         guiButtons.get(PLAYLEVEL_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(PLAYLEVEL_BUTTON_TYPE).setEnabled(true);
            guiButtons.get(CLOSELEVEL_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(CLOSELEVEL_BUTTON_TYPE).setEnabled(true);
         guiButtons.get(CLOSESAGA_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(CLOSESAGA_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SCROLLUP_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(SCROLLUP_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SCROLLDOWN_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(SCROLLDOWN_BUTTON_TYPE).setEnabled(false);
           guiButtons.get(QUITLEVEL_BUTTON_TYPE).setState(INVISIBLE_STATE);
            guiButtons.get(QUITLEVEL_BUTTON_TYPE).setEnabled(false);
           currentScreenState = LEVEL_SCORE_SCREEN_STATE;
            guiDecor.get(SCORE_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(MOVE_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(MALLET_TYPE).setState(INVISIBLE_STATE);
        //guiButtons.get(TRANS_TYPE).setState(INVISIBLE_STATE); 
        guiDecor.get(STAR_TYPE).setState(INVISIBLE_STATE);
        ((ZombieCrushDataModel)data).enableTiles(false);
        data.reset(this);
       
        
         ArrayList<String> levels = props.getPropertyOptionsList(ZombieCrushPropertyType.LEVEL_OPTIONS);
        for (String level : levels)
        {
            guiButtons.get(level).setState(INVISIBLE_STATE);
            guiButtons.get(level).setEnabled(false);
        }  
         currentScreenState = LEVEL_SCORE_SCREEN_STATE;
         guiDialogs.get(WIN_DIALOG_TYPE).setState(INVISIBLE_STATE);
        guiDialogs.get(LOSS_DIALOG_TYPE).setState(INVISIBLE_STATE);
    }
    
    /**
     * This method switches the application to the splash screen, making
     * all the appropriate UI controls visible & invisible.
     */    
    public void switchToSplashScreen()
    {
         PropertiesManager props = PropertiesManager.getPropertiesManager();
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(SPLASH_SCREEN_STATE);
        guiDecor.get(BACKGROUND_TYPE).setY(0);
        // DEACTIVATE THE TOOLBAR CONTROLS
           guiButtons.get(PLAYGAME_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(PLAYGAME_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(RESETGAME_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(RESETGAME_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(QUITGAME_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(QUITGAME_BUTTON_TYPE).setEnabled(true);
        //guiButtons.get(SELECTLEVEL_BUTTON_TYPE).setState(INVISIBLE_STATE);
         //guiButtons.get(SELECTLEVEL_BUTTON_TYPE).setEnabled(false);
         guiButtons.get(PLAYLEVEL_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(PLAYLEVEL_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(CLOSELEVEL_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(CLOSELEVEL_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(CLOSESAGA_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(CLOSESAGA_BUTTON_TYPE).setEnabled(false);
         guiButtons.get(CLOSESAGA_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(CLOSESAGA_BUTTON_TYPE).setEnabled(false);
         guiButtons.get(SCROLLUP_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(SCROLLUP_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SCROLLDOWN_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(SCROLLDOWN_BUTTON_TYPE).setEnabled(false);
           guiButtons.get(QUITLEVEL_BUTTON_TYPE).setState(INVISIBLE_STATE);
            guiButtons.get(QUITLEVEL_BUTTON_TYPE).setEnabled(false);
            guiDecor.get(SCORE_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(MOVE_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(MALLET_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(TRANS_TYPE).setState(INVISIBLE_STATE); 
        guiDecor.get(STAR_TYPE).setState(INVISIBLE_STATE);
        // ACTIVATE THE LEVEL SELECT BUTTONS
        // DEACTIVATE THE LEVEL SELECT BUTTONS   
        ArrayList<String> levels = props.getPropertyOptionsList(ZombieCrushPropertyType.LEVEL_OPTIONS);
        for (String level : levels)
        {
            guiButtons.get(level).setState(INVISIBLE_STATE);
            guiButtons.get(level).setEnabled(false);
        }  
      

        // HIDE THE TILES
        ((ZombieCrushDataModel)data).enableTiles(false);

        // MAKE THE CURRENT SCREEN THE SPLASH SCREEN
        currentScreenState = SPLASH_SCREEN_STATE;
        guiDialogs.get(WIN_DIALOG_TYPE).setState(INVISIBLE_STATE);
        guiDialogs.get(LOSS_DIALOG_TYPE).setState(INVISIBLE_STATE);
       
    }
    
    /**
     * This method updates the game grid boundaries, which will depend
     * on the level loaded.
     */    
    public void updateBoundaries()
    {
        
// NOTE THAT THE ONLY ONES WE CARE ABOUT ARE THE LEFT & TOP BOUNDARIES
        float totalWidth = ((ZombieCrushDataModel)data).getGridColumns() * TILE_IMAGE_WIDTH;
        float halfTotalWidth = totalWidth/2.0f;
        float halfViewportWidth = data.getGameWidth()/2.0f;
        boundaryLeft = halfViewportWidth - halfTotalWidth;

        // THE LEFT & TOP BOUNDARIES ARE WHERE WE START RENDERING TILES IN THE GRID
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        float topOffset = Integer.parseInt(props.getProperty(ZombieCrushPropertyType.GAME_TOP_OFFSET.toString()));
        float totalHeight = ((ZombieCrushDataModel)data).getGridRows() * TILE_IMAGE_HEIGHT;
        float halfTotalHeight = totalHeight/2.0f;
        float halfViewportHeight = (data.getGameHeight() - topOffset)/2.0f;
        boundaryTop = topOffset + halfViewportHeight - halfTotalHeight;

    }
    
    // METHODS OVERRIDDEN FROM MiniGame
   
        // - initData
        // - initGUIControls
        // - initGUIHandlers
        // - reset
        // - updateGUI

   
    /**
     * Initializes the game data used by the application. Note
     * that it is this method's obligation to construct and set
     * this Game's custom GameDataModel object as well as any
     * other needed game objects.
     */
    @Override
    public void initData()
    {        
        // INIT OUR ERROR HANDLER
        errorHandler = new ZombieCrushErrorHandler(window);
        
        // INIT OUR FILE MANAGER
        fileManager = new ZombieCrushFileManager(this);

        // LOAD THE PLAYER'S RECORD FROM A FILE
        record = fileManager.loadRecord();
        
        // INIT OUR DATA MANAGER
        data = new ZombieCrushDataModel(this);

        // LOAD THE GAME DIMENSIONS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        int gameWidth = Integer.parseInt(props.getProperty(ZombieCrushPropertyType.GAME_WIDTH.toString()));
        int gameHeight = Integer.parseInt(props.getProperty(ZombieCrushPropertyType.GAME_HEIGHT.toString()));
        data.setGameDimensions(gameWidth, gameHeight);

        // THIS WILL CHANGE WHEN WE LOAD A LEVEL
        boundaryLeft = Integer.parseInt(props.getProperty(ZombieCrushPropertyType.GAME_LEFT_OFFSET.toString()));
        boundaryTop = Integer.parseInt(props.getProperty(ZombieCrushPropertyType.GAME_TOP_OFFSET.toString()));
        boundaryRight = gameWidth - boundaryLeft;
        boundaryBottom = gameHeight;
    }
    
    /**
     * Initializes the game controls, like buttons, used by
     * the game application. Note that this includes the tiles,
     * which serve as buttons of sorts.
     */
    @Override
    public void initGUIControls()
    {
        // WE'LL USE AND REUSE THESE FOR LOADING STUFF
        BufferedImage img;
        
        
        float x, y;
        SpriteType sT;
        SpriteType sS;
        Sprite s;
        Sprite s1;
 
        // FIRST PUT THE ICON IN THE WINDOW
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(ZombieCrushPropertyType.IMG_PATH);      
        String windowIconFile = props.getProperty(ZombieCrushPropertyType.WINDOW_ICON);
        img = loadImage(imgPath + windowIconFile);
        window.setIconImage(img);

        // CONSTRUCT THE PANEL WHERE WE'LL DRAW EVERYTHING
        canvas = new ZombieCrushPanel(this, (ZombieCrushDataModel)data);
        
        // LOAD THE BACKGROUNDS, WHICH ARE GUI DECOR
        currentScreenState = SPLASH_SCREEN_STATE;
        sT = new SpriteType(BACKGROUND_TYPE);
        img = loadImage(imgPath + props.getProperty(ZombieCrushPropertyType.SPLASH_SCREEN_IMAGE_NAME));  
        sT.addState(SPLASH_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(ZombieCrushPropertyType.GAME_BACKGROUND_IMAGE_NAME));
        sT.addState(GAME_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(ZombieCrushPropertyType.LEVEL_SCORE_SCREEN_IMAGE_NAME));
        sT.addState(LEVEL_SCORE_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(ZombieCrushPropertyType.SAGA_SCREEN_IMAGE_NAME));
        sT.addState(SAGA_SCREEN_STATE, img);
        
        s = new Sprite(sT, 0, 0, 0, 0, SPLASH_SCREEN_STATE);
        guiDecor.put(BACKGROUND_TYPE, s);

        sT = new SpriteType(STAR_TYPE);
        img = loadImage(imgPath + props.getProperty(ZombieCrushPropertyType.STARE_IMAGE_NAME));  
        sT.addState(STAR_EMPTY_STATE, img);
        img = loadImage(imgPath + props.getProperty(ZombieCrushPropertyType.STAR1_IMAGE_NAME));
        sT.addState(STAR1_STATE, img);
        img = loadImage(imgPath + props.getProperty(ZombieCrushPropertyType.STAR2_IMAGE_NAME));
        sT.addState(STAR2_STATE, img);
        img = loadImage(imgPath + props.getProperty(ZombieCrushPropertyType.STAR3_IMAGE_NAME));
        sT.addState(STAR3_STATE, img);
        img = loadImage(imgPath + props.getProperty(ZombieCrushPropertyType.FAKE_IMAGE_NAME));
        sT.addState(NOTSEEN_STATE, img);
        s = new Sprite(sT, 0, 0, 0, 0, INVISIBLE_STATE);
        guiDecor.put(STAR_TYPE, s);
        
        // ADD A BUTTON FOR EACH LEVEL AVAILABLE
        ArrayList<String> levels = props.getPropertyOptionsList(ZombieCrushPropertyType.LEVEL_OPTIONS);
        ArrayList<String> trans = props.getPropertyOptionsList(ZombieCrushPropertyType.TRANS_OPTIONS);
        ArrayList<String> levelImageNames = props.getPropertyOptionsList(ZombieCrushPropertyType.LEVEL_IMAGE_OPTIONS);
        ArrayList<String> levelMouseOverImageNames = props.getPropertyOptionsList(ZombieCrushPropertyType.LEVEL_MOUSE_OVER_IMAGE_OPTIONS);
        //float totalWidth = levels.size() * (LEVEL_BUTTON_WIDTH + LEVEL_BUTTON_MARGIN) - LEVEL_BUTTON_MARGIN;
        //float gameWidth = Integer.parseInt(props.getProperty(ZombieCrushPropertyType.GAME_WIDTH));
        //x = (gameWidth - totalWidth)/2.0f;
        x=200;
        y = 200;
        levelsPlayable=getPlayerRecord().getplayedLevels("./data/./ZombieCrush/level1.zom");
        System.out.println("levesplayable"+levelsPlayable);
        for (int i = 0; i <levels.size(); i++)
        {
            if(i==5){
                x=200;
                y=500;
            }
            sT = new SpriteType(SELECTLEVEL_BUTTON_TYPE);
            img = loadImageWithColorKey(imgPath + levelImageNames.get(i), COLOR_KEY);
            sT.addState(VISIBLE_STATE, img);
           
            img = loadImageWithColorKey(imgPath + levelImageNames.get(i), TRANS_KEY);
            sT.addState(NOTPLAYED_STATE, img);
            
            img = loadImageWithColorKey(imgPath + levelMouseOverImageNames.get(i), COLOR_KEY);
            sT.addState(MOUSE_OVER_STATE, img);
            s = new Sprite(sT, x, y, 0, 0, INVISIBLE_STATE);
            guiButtons.put(levels.get(i), s);
            
     //if the levels are not playable,render a jelly like img on there.
  
            x += LEVEL_BUTTON_WIDTH + LEVEL_BUTTON_MARGIN;
            y+=10;
        }
        
        // ADD THE CONTROLS ALONG THE NORTH OF THE GAME SCREEN
                
         // THEN THE PLAYGAME BUTTON
        String newButton = props.getProperty(ZombieCrushPropertyType.PLAYGAME_BUTTON_IMAGE_NAME);
        sT = new SpriteType(PLAYGAME_BUTTON_TYPE);
	img = loadImage(imgPath + newButton);
        sT.addState(VISIBLE_STATE, img);
        
        String newMouseOverButton = props.getProperty(ZombieCrushPropertyType.PLAYGAME_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImage(imgPath + newMouseOverButton);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, PLAYGAME_BUTTON_X, PLAYGAME_BUTTON_Y, 0, 0, VISIBLE_STATE);
        guiButtons.put(PLAYGAME_BUTTON_TYPE, s);
        
        //add mallet
        String malletButton = props.getProperty(ZombieCrushPropertyType.MALLET_IMAGE_NAME);
        sT = new SpriteType(MALLET_TYPE);
	img = loadImage(imgPath + malletButton);
        sT.addState(VISIBLE_STATE, img);
        
        String malletOverButton = props.getProperty(ZombieCrushPropertyType.MALLET_IMAGE_NAME);
        img = loadImage(imgPath + malletOverButton);
        img1=loadImage(imgPath + malletOverButton);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, MALLET_X, MALLET_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(MALLET_TYPE, s);
        
        //Add the BacktoSplash/CLOSE SAGA Button
         String BacktoSplash= props.getProperty(ZombieCrushPropertyType.CLOSESAGA_BUTTON_IMAGE_NAME);
        sT = new SpriteType(CLOSESAGA_BUTTON_TYPE);
	img = loadImage(imgPath + BacktoSplash);
        sT.addState(VISIBLE_STATE, img);
 
        String backMouseOverButton = props.getProperty(ZombieCrushPropertyType.CLOSELEVEL_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImage(imgPath + backMouseOverButton);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT,CLOSESAGA_BUTTON_X, CLOSESAGA_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(CLOSESAGA_BUTTON_TYPE, s);
        

         //ADD RESETGAME BUTTON
        String resetGame = props.getProperty(ZombieCrushPropertyType.RESETGAME_BUTTON_IMAGE_NAME);
        sT = new SpriteType(RESETGAME_BUTTON_TYPE);
	img = loadImage(imgPath + resetGame);
        sT.addState(VISIBLE_STATE, img);
        String resetMouseOverButton = props.getProperty(ZombieCrushPropertyType.RESETGAME_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImage(imgPath + resetMouseOverButton);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, RESETGAME_BUTTON_X, RESETGAME_BUTTON_Y, 0, 0, VISIBLE_STATE);
        guiButtons.put(RESETGAME_BUTTON_TYPE, s);
        
         //ADD QUITGAME BUTTON
        String  quitGame= props.getProperty(ZombieCrushPropertyType.QUITGAME_BUTTON_IMAGE_NAME);
        sT = new SpriteType(QUITGAME_BUTTON_TYPE);
	img = loadImage(imgPath +quitGame );
        sT.addState(VISIBLE_STATE, img);
        String QGMouseOverButton = props.getProperty(ZombieCrushPropertyType.QUITGAME_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImage(imgPath + QGMouseOverButton);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, QUITGAME_BUTTON_X, QUITGAME_BUTTON_Y, 0, 0, VISIBLE_STATE);
        guiButtons.put(QUITGAME_BUTTON_TYPE, s);
        
        //Add selectlevel button
        //String selectLevel = props.getProperty(ZombieCrushPropertyType.SELECTLEVEL_BUTTON_IMAGE_NAME);
        //sT = new SpriteType(SELECTLEVEL_BUTTON_TYPE);
	//img = loadImage(imgPath +selectLevel);
        //sT.addState(VISIBLE_STATE, img);
        //String SLMouseOverButton = props.getProperty(ZombieCrushPropertyType.SELECTLEVEL_BUTTON_MOUSE_OVER_IMAGE_NAME);
        //img = loadImage(imgPath + SLMouseOverButton);
        //sT.addState(MOUSE_OVER_STATE, img);
        //s = new Sprite(sT, SELECTLEVEL_BUTTON_X, SELECTLEVEL_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        //guiButtons.put(SELECTLEVEL_BUTTON_TYPE, s);
  
        
          //ADD PLAYLEVEL BUTTON
        String playLevel = props.getProperty(ZombieCrushPropertyType.PLAYLEVEL_BUTTON_IMAGE_NAME);
        sT = new SpriteType(PLAYLEVEL_BUTTON_TYPE);
	img = loadImage(imgPath +playLevel);
        sT.addState(VISIBLE_STATE, img);
        String PLMouseOverButton = props.getProperty(ZombieCrushPropertyType.PLAYLEVEL_BUTTON_MOUSE_OVER_IMAGE_NAME);
       img = loadImage(imgPath + PLMouseOverButton);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, PLAYLEVEL_BUTTON_X, PLAYLEVEL_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(PLAYLEVEL_BUTTON_TYPE, s);
     
        
        
        
               //ADD QUITLEVEL BUTTON
        String quitLevel = props.getProperty(ZombieCrushPropertyType.QUITLEVEL_BUTTON_IMAGE_NAME);
        sT = new SpriteType(QUITLEVEL_BUTTON_TYPE);
	img = loadImage(imgPath +quitLevel );
        sT.addState(VISIBLE_STATE, img);
        String QLMouseOverButton = props.getProperty(ZombieCrushPropertyType.QUITLEVEL_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImage(imgPath + QLMouseOverButton);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT,QUITLEVEL_BUTTON_X, QUITLEVEL_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(QUITLEVEL_BUTTON_TYPE, s);
        
               //ADD CLOSELEVEL BUTTON
        String closeLevel = props.getProperty(ZombieCrushPropertyType.CLOSELEVEL_BUTTON_IMAGE_NAME);
        sT = new SpriteType(CLOSELEVEL_BUTTON_TYPE);
	img = loadImage(imgPath +closeLevel);
        sT.addState(VISIBLE_STATE, img);
        String CLMouseOverButton = props.getProperty(ZombieCrushPropertyType.CLOSELEVEL_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImage(imgPath + CLMouseOverButton);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, CLOSELEVEL_BUTTON_X, CLOSELEVEL_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(CLOSELEVEL_BUTTON_TYPE, s);
        
        //ADD SCROLL BUTTON
        String scrollup = props.getProperty(ZombieCrushPropertyType.SCROLLUP_BUTTON_IMAGE_NAME);
        sT = new SpriteType(SCROLLUP_BUTTON_TYPE);
	img = loadImage(imgPath +scrollup );
        sT.addState(VISIBLE_STATE, img);
        String SCUMouseOverButton = props.getProperty(ZombieCrushPropertyType.SCROLLUP_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImage(imgPath + SCUMouseOverButton);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT,SCROLLUP_BUTTON_X,SCROLLUP_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(SCROLLUP_BUTTON_TYPE, s);
        
          //ADD SCROLL BUTTON
        String scrolldown = props.getProperty(ZombieCrushPropertyType.SCROLLDOWN_BUTTON_IMAGE_NAME);
        sT = new SpriteType(SCROLLDOWN_BUTTON_TYPE);
	img = loadImage(imgPath +scrolldown );
        sT.addState(VISIBLE_STATE, img);
        String SCMouseOverButton = props.getProperty(ZombieCrushPropertyType.SCROLLDOWN_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImage(imgPath + SCMouseOverButton);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT,SCROLLDOWN_BUTTON_X,SCROLLDOWN_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(SCROLLDOWN_BUTTON_TYPE, s);
        
        //ADD PAY BUTTON
  
        String scoreContainer = props.getProperty(ZombieCrushPropertyType.SCORE_IMAGE_NAME);
        sT = new SpriteType(SCORE_TYPE);
        img = loadImage(imgPath + scoreContainer);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, SCORE_X, SCORE_Y, 0, 0, INVISIBLE_STATE);
        guiDecor.put(SCORE_TYPE, s);
        
        // AND THE TIME DISPLAY
        String moveContainer = props.getProperty(ZombieCrushPropertyType.MOVE_IMAGE_NAME);
        sT = new SpriteType(MOVE_TYPE);
        img = loadImage(imgPath + moveContainer);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, MOVE_X, MOVE_Y, 0, 0, INVISIBLE_STATE);
        guiDecor.put(MOVE_TYPE, s);

        // AND THE WIN CONDITION DISPLAY
        String winDisplay = props.getProperty(ZombieCrushPropertyType.WIN_DIALOG_IMAGE_NAME);
        sT = new SpriteType(WIN_DIALOG_TYPE);
        img = loadImageWithColorKey(imgPath + winDisplay, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        x = (data.getGameWidth()/2) - (img.getWidth(null)/2);
        y = (data.getGameHeight()/2) - (img.getHeight(null)/2);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE_STATE);
        guiDialogs.put(WIN_DIALOG_TYPE, s);
        
	 // AND THE LOSS CONDITION DISPLAY
        String lossDisplay = props.getProperty(ZombieCrushPropertyType.LOSS_DIALOG_IMAGE_NAME);
        sT = new SpriteType(LOSS_DIALOG_TYPE);
        img = loadImageWithColorKey(imgPath + lossDisplay, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        x = (data.getGameWidth()/2) - (img.getWidth(null)/2);
        y = (data.getGameHeight()/2) - (img.getHeight(null)/2);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE_STATE);
        guiDialogs.put(LOSS_DIALOG_TYPE, s);	
        
        // THEN THE TILES STACKED TO THE TOP LEFT
        ((ZombieCrushDataModel)data).initTiles();
    }		
    
    /**
     * Initializes the game event handlers for things like
     * game gui buttons.
     */
    @Override
    public void initGUIHandlers()
    {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String dataPath = props.getProperty(ZombieCrushPropertyType.DATA_PATH);
        
        // WE'LL HAVE A CUSTOM RESPONSE FOR WHEN THE USER CLOSES THE WINDOW
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        QuitGameHandler qg = new QuitGameHandler(this);
        window.addWindowListener(qg);
        
        // LEVEL BUTTON EVENT HANDLERS
        ArrayList<String> levels = props.getPropertyOptionsList(ZombieCrushPropertyType.LEVEL_OPTIONS);
        for (String levelFile : levels)
        {        
            SelectHandler slh = new SelectHandler(this, dataPath + levelFile);
            guiButtons.get(levelFile).setActionListener(slh);
             System.out.println(levelFile);
        }   
       //System.out.println("xy");
        // GAME EVENT HANDLER
       PlayGameHandler pgh = new PlayGameHandler(this);
        guiButtons.get(PLAYGAME_BUTTON_TYPE).setActionListener(pgh);
        
       ResetGameHandler rgh = new ResetGameHandler(this);
        guiButtons.get(RESETGAME_BUTTON_TYPE).setActionListener(rgh);


        // SAGA SCREEN HANDLER
        //SelectLevelHandler slh = new SelectHandler(this);
        //guiButtons.get(PLAYLEVEL_BUTTON_TYPE).setActionListener(slh);
 
        ScrollUpHandler suh = new ScrollUpHandler(this);
        guiButtons.get(SCROLLUP_BUTTON_TYPE).setActionListener(suh);
        
         ScrollDownHandler sdh = new ScrollDownHandler(this);
        guiButtons.get(SCROLLDOWN_BUTTON_TYPE).setActionListener(sdh);
        
        //go back to splash screen
        BackHandler bh = new BackHandler(this);
        guiButtons.get(CLOSELEVEL_BUTTON_TYPE).setActionListener(bh);
        
        //SCORE SCREEN HANDLER
          for (String levelFile : levels)
        { 
         PlayLevelHandler plh = new PlayLevelHandler(this, dataPath + levelFile);
        guiButtons.get(PLAYLEVEL_BUTTON_TYPE).setActionListener(plh);
        }
       
 
        //SelectHandler slh = new SelectHandler(this);
         //guiButtons.get(SELECTLEVEL_BUTTON_TYPE).setActionListener(slh);
     
        CloseLevelHandler clh = new CloseLevelHandler(this);
        guiButtons.get(CLOSELEVEL_BUTTON_TYPE).setActionListener(clh);
  
        //RETURN SAGA SAME AS PLAYGAME
        
        // GAME SCREEN HANDLERS
        QuitLevelHandler qlh = new QuitLevelHandler(this);
        guiButtons.get(QUITLEVEL_BUTTON_TYPE).setActionListener(qlh);
        
        PayHandler ph=new PayHandler(this);
        guiButtons.get(MALLET_TYPE).setActionListener(ph);
        
        
         KeyHandler mkh = new KeyHandler(this);
        this.setKeyListener(mkh);
        
        CloseSagaHandler csh = new CloseSagaHandler(this);
        guiButtons.get(CLOSESAGA_BUTTON_TYPE).setActionListener(csh);
        guiButtons.get(QUITGAME_BUTTON_TYPE).setActionListener(csh);
       
        
      
    }
    
    /**
     * Invoked when a new game is started, it resets all relevant
     * game data and gui control states. 
     */
    @Override
    public void reset()
    {
        data.reset(this);
    }
    
    /**
     * Updates the state of all gui controls according to the 
     * current game conditions.
     */
    public int move=100;
    @Override
    public void updateGUI()
    {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
     ArrayList<String> levels = props.getPropertyOptionsList(ZombieCrushPropertyType.LEVEL_OPTIONS);
        if(starEarned==0&&getCurrentScreenState().equals(GAME_SCREEN_STATE)){
            guiDecor.get(STAR_TYPE).setState(STAR_EMPTY_STATE);
        }
        
         if(starEarned==1&&getCurrentScreenState().equals(GAME_SCREEN_STATE)){
            guiDecor.get(STAR_TYPE).setState(STAR1_STATE);
        }
         
         if(starEarned==2&&getCurrentScreenState().equals(GAME_SCREEN_STATE)){
            guiDecor.get(STAR_TYPE).setState(STAR2_STATE);
        }
          if(starEarned==3&&getCurrentScreenState().equals(GAME_SCREEN_STATE)){
            guiDecor.get(STAR_TYPE).setState(STAR3_STATE);
        }
        
        if(move<50){
            guiDecor.get(BACKGROUND_TYPE).update(this);
            //guiButtons.get(SELECTLEVEL_BUTTON_TYPE).update(this);
            for (int i = 0; i <levels.size(); i++)
        {
            guiButtons.get(levels.get(i)).update(this);
            move++;
        }
        }
        // GO THROUGH THE VISIBLE BUTTONS TO TRIGGER MOUSE OVERS
        Iterator<Sprite> buttonsIt = guiButtons.values().iterator();
        while (buttonsIt.hasNext())
        {
            Sprite button = buttonsIt.next();
            
             if(getCurrentScreenState().equals(GAME_SCREEN_STATE)&&getSmash()==true&&button.getSpriteType().getSpriteTypeID().equals(MALLET_TYPE)){
                 Toolkit toolkit = Toolkit.getDefaultToolkit();
                 Image image = toolkit.getImage("./ZombieCrush/mallet.png");
                 Cursor c;
               Point mallet=new Point(10,10);
                c = toolkit.createCustomCursor(img1,mallet,"img");
                canvas.setCursor(c);
                 //button.setX(data.getLastMouseX());
               // button.setY(data.getLastMouseY());
               
            }
              if(getSmash()==false){
                  Cursor c = null;
                  canvas.setCursor(c.getDefaultCursor());

              }
            
            
            // ARE WE ENTERING A BUTTON?
            if (button.getState().equals(VISIBLE_STATE))
            {
                if (button.containsPoint(data.getLastMouseX(), data.getLastMouseY()))
                {
                    button.setState(MOUSE_OVER_STATE);
                }
            }
            // ARE WE EXITING A BUTTON?
            else if (button.getState().equals(MOUSE_OVER_STATE))
            {
                 if (!button.containsPoint(data.getLastMouseX(), data.getLastMouseY()))
                {
                    button.setState(VISIBLE_STATE);
                }
            }
        }
    }  
    
    @Override
    public void initAudioContent() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
