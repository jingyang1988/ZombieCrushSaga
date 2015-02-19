/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import zombiecrush.data.ZombieCrushDataModel;
import zombiecrush.ZombieCrush.ZombieCrushPropertyType;
import static zombiecrush.ZombieCrushConstants.*;
import zombiecrush.data.ZombieCrushRecord;
/**
 *
 * @author Jing
 */
public class ZombieCrushPanel extends JPanel{

    private MiniGame game;
    private ZombieCrushMiniGame xgame;
    
    // AND HERE IS ALL THE GAME DATA THAT WE NEED TO RENDER
    private ZombieCrushDataModel data;
    
    // WE'LL USE THIS TO FORMAT SOME TEXT FOR DISPLAY PURPOSES
    private NumberFormat numberFormatter;
   // WE'LL USE THIS AS THE BASE IMAGE FOR RENDERING UNSELECTED TILES
    private BufferedImage blankTileImage;
private int counter=60;
private int x;
private int y;
private int vy=-10;

    // WE'LL USE THIS AS THE BASE IMAGE FOR RENDERING SELECTED TILES
    private BufferedImage blankTileSelectedImage;
    private ZombieCrushRecord record;
    public ZombieCrushPanel(ZombieCrushMiniGame initGame, ZombieCrushDataModel initData)
    {
        game = initGame;
        data = initData;
        numberFormatter = NumberFormat.getNumberInstance();
        numberFormatter.setMinimumFractionDigits(3);
        numberFormatter.setMaximumFractionDigits(3);
    }
        
    // MUTATOR METHODS
        // -setBlankTileImage
        // -setBlankTileSelectedImage
    
    /**
     * This mutator method sets the base image to use for rendering tiles.
     * 
     * @param initBlankTileImage The image to use as the base for rendering tiles.
     */
    public void setBlankTileImage(BufferedImage initBlankTileImage)
    {
        blankTileImage = initBlankTileImage;
    }
    
    /**
     * This mutator method sets the base image to use for rendering selected tiles.
     * 
     * @param initBlankTileSelectedImage The image to use as the base for rendering
     * selected tiles.
     */
    public void setBlankTileSelectedImage(BufferedImage initBlankTileSelectedImage)
    {
        blankTileSelectedImage = initBlankTileSelectedImage;
    }

    
    
    
      @Override
    public void paintComponent(Graphics g)
    {
        try
        {
            // MAKE SURE WE HAVE EXCLUSIVE ACCESS TO THE GAME DATA
            game.beginUsingData();
        
            // CLEAR THE PANEL
            super.paintComponent(g);
        
            // RENDER THE BACKGROUND, WHICHEVER SCREEN WE'RE ON
            renderBackground(g);
            // AND THE BUTTONS AND DECOR
            
            renderGUIControls(g);
            renderDialogs(g);
            renderStats(g);
            
            renderTiles(g);
            renderGrid(g);


            //renderScores(g);
        }
        finally
        {
            // RELEASE THE LOCK
            game.endUsingData();    
        }
    }
    public void renderBackground(Graphics g)
    {
        String s=((ZombieCrushMiniGame)game).getCurrentScreenState();

       
            Sprite bg = game.getGUIDecor().get("BACKGROUND_TYPE");
            renderSprite(g, bg);
 
         
    }
        public void renderGUIControls(Graphics g)
    {
        // GET EACH DECOR IMAGE ONE AT A TIME
        Collection<Sprite> decorSprites = game.getGUIDecor().values();
        for (Sprite s : decorSprites)
        {
            renderSprite(g, s);
        }
        
        // AND NOW RENDER THE BUTTONS
        Collection<Sprite> buttonSprites = game.getGUIButtons().values();
        for (Sprite s : buttonSprites)
        {
            renderSprite(g, s);
        }
    }
    /**
     * Renders the game dialog boxes.
     * 
     * @param g This panel's graphics context.
     */
    public void renderDialogs(Graphics g)
    {
        
        // GET EACH DECOR IMAGE ONE AT A TIME
        Collection<Sprite> dialogSprites = game.getGUIDialogs().values();
        for (Sprite s : dialogSprites)
        {
            // RENDER THE DIALOG, NOTE IT WILL ONLY DO IT IF IT'S VISIBLE
            renderSprite(g, s);
        }
        
        
    }
    
    
     public void renderStats(Graphics g){
          if (((ZombieCrushMiniGame)game).isCurrentScreenState(GAME_SCREEN_STATE) 
                )
        {
            
            // RENDER THE Score
            g.setColor(DEBUG_TEXT_COLOR);
             g.setFont(TEXT_DISPLAY_FONT);
            String score =""+ data.getCurrentScore();
            int x = 490;
            int y = 100;
            g.drawString(score, x, y);
            
            //RENDER THE MOVE
            g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            String move=""+data.getCurrentMove();
             int a = 840;
             int b = 100;
             g.drawString(move, a, b);
        }    
         
         
         String s=((ZombieCrushMiniGame)game).getCurrentScreenState();
          record=((ZombieCrushMiniGame)game).getPlayerRecord();
         if(s.equals(LEVEL_SCORE_SCREEN_STATE)){
             g.setColor(DEBUG_TEXT_COLOR);
             g.setFont(TEXT_DISPLAY_FONT);
             String gameName=""; 
            String level=data.getCurrentLevel();
             if(level.equals("./data/./ZombieCrush/level1.zom")){
                gameName="Level 1";
                data.setTargetScore(800);
                data.setCurrentMove(10);
                data.setLevelNumber(1);
                
                g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            String ins="Goal:"+"\n";
            ins+="800 POINTS";
             int e = 400;
             int f = 450;
             g.drawString(ins, e, f);
            }
             else if(level.equals("./data/./ZombieCrush/level2.zom")){
                gameName="Level 2";
                data.setTargetScore(1900);
                data.setCurrentMove(15);
                data.setLevelNumber(2);
                 g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            String ins="Goal:"+"\n";
            ins+="1900 POINTS";
             int e = 400;
             int f = 450;
             g.drawString(ins, e, f);
             }
              else if(level.equals("./data/./ZombieCrush/level3.zom")){
                gameName="Level 3";
                data.setTargetScore(4000);
                data.setCurrentMove(18);
                data.setLevelNumber(3);
                g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            String ins="Goal:"+"\n";
            ins+="4000 POINTS";
             int e = 400;
             int f = 450;
             g.drawString(ins, e, f);
            }
              else if(level.equals("./data/./ZombieCrush/level4.zom")){
                gameName="Level 4";
                data.setTargetScore(4500);
                data.setCurrentMove(15);
                data.setLevelNumber(4);
                g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            String ins="Goal:"+"\n";
            ins+="4500 POINTS";
             int e = 400;
             int f = 450;
             g.drawString(ins, e, f);
            }
              else if(level.equals("./data/./ZombieCrush/level5.zom")){
                gameName="Level 5";
                data.setTargetScore(5000);
                data.setCurrentMove(20);
                data.setLevelNumber(5);
                g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            String ins="Goal:"+"\n";
            ins+="5000 POINTS";
             int e = 400;
             int f = 450;
             g.drawString(ins, e, f);
             
            }
                    else if(level.equals("./data/./ZombieCrush/level6.zom")){
                gameName="Level 6";
                data.setTargetScore(9000);
                data.setCurrentMove(16);
                data.setLevelNumber(6);
                g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            String ins="Goal:"+"\n";
            ins+="9000 POINTS";
             int e = 400;
             int f = 450;
             g.drawString(ins, e, f);
                
             }
              else if(level.equals("./data/./ZombieCrush/level7.zom")){
                gameName="Level 7";
                data.setTargetScore(6000);
                data.setCurrentMove(50);
                data.setLevelNumber(7);
                g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            String ins="Goal:"+"\n";
            ins+="6000 POINTS";
             int e = 400;
             int f = 450;
             g.drawString(ins, e, f);
            }
              else if(level.equals("./data/./ZombieCrush/level8.zom")){
                gameName="Level 8";
                data.setTargetScore(20000);
                data.setCurrentMove(20);
                data.setLevelNumber(8);
                g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            String ins="Goal:"+"\n";
            ins+="20000 POINTS";
             int e = 400;
             int f = 450;
             g.drawString(ins, e, f);
            }
              else if(level.equals("./data/./ZombieCrush/level9.zom")){
                gameName="Level 9";
                data.setTargetScore(22000);
                data.setCurrentMove(25);
                data.setLevelNumber(9);
                g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            String ins="Goal:"+"\n";
            ins+="22000 POINTS";
             int e = 400;
             int f = 450;
             g.drawString(ins, e, f);
            }
                
             
            int x = 1;
            int y = 100;
            g.drawString(gameName, x, y);
            g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            
            String highScore="SCORE:"+record.getScore(level);
             int a = 1;
             int b = 200;
             g.drawString(highScore, a, b);
            
            g.setColor(DEBUG_TEXT_COLOR);
            g.setFont(TEXT_DISPLAY_FONT);
            String star="STARS:"+record.getStars(level);
             int c = 1;
             int d = 300;
             g.drawString(star, c, d);
             
            
       
         }
     }
         
    /**
     * Renders all the game tiles, doing so carefully such
     * that they are rendered in the proper order.
     * 
     * @param g the Graphics context of this panel.
     */
    public void renderTiles(Graphics g)
    {
       // THEN DRAW THE GRID TILES BOTTOM TO TOP USING
        // THE TILE'S Z TO STAGGER THEM AND GIVE THE ILLUSION
        // OF DEPTH
        ArrayList<ZombieCrushTile>[][] tileGrid = data.getTileGrid();
        boolean noneOnLevel = false;
        int zIndex = 0;

            for (int i = 0; i < data.getGridColumns(); i++)
            {
                for (int j = 0; j < data.getGridRows(); j++)
                {
                    if (tileGrid[i][j].size() > zIndex)
                    {
                        ZombieCrushTile tile = tileGrid[i][j].get(zIndex);
                        renderTile(g, tile);
                    }
                }
            }

        //}
        // THEN DRAW ALL THE MOVING TILES
        Iterator<ZombieCrushTile> movingTiles = data.getMovingTiles();
        while (movingTiles.hasNext())
        {
           ZombieCrushTile tile = movingTiles.next();
            renderTile(g, tile);
        }
     
   
    }

    /**
     * Helper method for rendering the tiles that are currently moving.
     * 
     * @param g Rendering context for this panel.
     * 
     * @param tileToRender Tile to render to this panel.
     */
    public void renderTile(Graphics g, ZombieCrushTile tileToRender)
    {
        // ONLY RENDER VISIBLE TILES
        if (!tileToRender.getState().equals(INVISIBLE_STATE))
        {
            // FIRST DRAW THE BLANK TILE IMAGE
          
        if (tileToRender.getState().equals(VISIBLE_STATE)){
                g.drawImage(blankTileImage, (int)tileToRender.getX(), (int)tileToRender.getY(), null);
 }
            
            // THEN THE TILE IMAGE
            SpriteType bgST = tileToRender.getSpriteType();
            Image img = bgST.getStateImage(tileToRender.getState());
            g.drawImage(img, (int)tileToRender.getX()+TILE_IMAGE_OFFSET, (int)tileToRender.getY()+TILE_IMAGE_OFFSET, bgST.getWidth(), bgST.getHeight(), null); 
            
         
        }        
    }
    

    /**
     * This method renders grid lines in the game tile grid to help
     * during debugging.
     * 
     * @param g Graphics context for this panel.
     */
    public void renderGrid(Graphics g)
    {
        // ONLY RENDER THE GRID IF WE'RE DEBUGGING
        if (false)
        {
            for (int i = 0; i < data.getGridColumns(); i++)
            {
                for (int j = 0; j < data.getGridRows(); j++)
                {
                    int x = data.calculateTileXInGrid(i, 0);
                    int y = data.calculateTileYInGrid(j, 0);
                    g.drawRect(x, y, TILE_IMAGE_WIDTH, TILE_IMAGE_HEIGHT);
                }
            }
        }
        
        
        
            if (((ZombieCrushMiniGame)game).isCurrentScreenState(GAME_SCREEN_STATE)){
            if(data.getTemScore()!=0){
            String score=""+data.getTemScore();
            if(counter>0){
                
            g.setColor(DEBUG_TEXT_COLOR);
                g.setFont(TEXT_DISPLAY_FONT);
                 data.update(data.getFlyX(),data.getFlyY());
                g.drawString(score,data.getFlyX(),data.getFlyY());
            }

         }
            }

        }
    
    
        
    
    public void renderSprite(Graphics g, Sprite s)
    {
        // ONLY RENDER THE VISIBLE ONES
        if (!s.getState().equals("INVISIBLE_STATE"))
        {
            SpriteType bgST = s.getSpriteType();
            Image img = bgST.getStateImage(s.getState());
            g.drawImage(img, (int)s.getX(), (int)s.getY(), bgST.getWidth(), bgST.getHeight(), null); 
        }
    }
    /**
     * Renders the debugging text to the panel. Note
     * that the rendering will only actually be done
     * if data has activated debug text rendering.
     * 
     * @param g the Graphics context for this panel
     */
    public void renderDebuggingText(Graphics g)
    {
        // IF IT'S ACTIVATED
        if (data.isDebugTextRenderingActive())
        {
            // ENABLE PROPER RENDER SETTINGS
            g.setFont(DEBUG_TEXT_FONT);
            g.setColor(DEBUG_TEXT_COLOR);
            
            // GO THROUGH ALL THE DEBUG TEXT
            Iterator<String> it = data.getDebugText().iterator();
            int x = data.getDebugTextX();
            int y = data.getDebugTextY();
            while (it.hasNext())
            {
                // RENDER THE TEXT
                String text = it.next();
                g.drawString(text, x, y);
                y += 20;
            }   
        } 
    }
    
}
