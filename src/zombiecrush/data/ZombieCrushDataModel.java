/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.data;
import zombiecrush.ui.ZombieCrushTile;
import java.lang.Object;
import javax.swing.Timer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import zombiecrush.ZombieCrush.ZombieCrushPropertyType;
import mini_game.MiniGame;
import mini_game.MiniGameDataModel;
import mini_game.Sprite;
import mini_game.SpriteType;
import properties_manager.PropertiesManager;
import static zombiecrush.ZombieCrushConstants.*;
import zombiecrush.ui.ZombieCrushMiniGame;
import zombiecrush.ui.ZombieCrushPanel;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Jing
 */
public class ZombieCrushDataModel extends MiniGameDataModel{
    // THIS CLASS HAS A REFERERENCE TO THE MINI GAME SO THAT IT
    // CAN NOTIFY IT TO UPDATE THE DISPLAY WHEN THE DATA MODEL CHANGES
    private MiniGame miniGame;
      private Timer timer;
    // THE LEVEL GRID REFERS TO THE LAYOUT FOR A GIVEN LEVEL, MEANING
    // HOW MANY TILES FIT INTO EACH CELL WHEN FIRST STARTING A LEVEL
    private int[][] levelGrid;
    
    // LEVEL GRID DIMENSIONS
    private int gridColumns;
    private int gridRows;
    
    // THIS STORES THE TILES ON THE GRID DURING THE GAME
    private ArrayList<ZombieCrushTile>[][] tileGrid;
    private ArrayList<ZombieCrushTile>[][] templateGrid;
    private ArrayList<ZombieCrushTile>[][] selectGrid;
    
    // THESE ARE THE TILES THE PLAYER HAS MATCHED
    private ArrayList<ZombieCrushTile> stackTiles;
    private ArrayList<ZombieCrushTile> specialTiles;
    private ArrayList<ZombieCrushTile> specialTiles1;
    private ArrayList<ZombieCrushTile> specialTiles2;
    private ArrayList<ZombieCrushTile> specialTiles0;
    // THESE ARE THE TILES THAT ARE MOVING AROUND, AND SO WE HAVE TO UPDATE
    private ArrayList<ZombieCrushTile> movingTiles;
    
    // THIS IS A SELECTED TILE, MEANING THE FIRST OF A PAIR THE PLAYER
    // IS TRYING TO MATCH. THERE CAN ONLY BE ONE OF THESE AT ANY TIME
    private ZombieCrushTile selectedTile;
    private ZombieCrushTile temTile;
    
    // THE INITIAL LOCATION OF TILES BEFORE BEING PLACED IN THE GRID
    private int unassignedTilesX;
    private int unassignedTilesY;
    
    // THESE ARE USED FOR TIMING THE GAME
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    
    // THE REFERENCE TO THE FILE BEING PLAYED
    private String currentLevel;
   //The matches needed for 3,4,5
    private int matchNumber;
    private boolean HMatch1=false;
    private boolean VMatch1=false;
     private boolean HMatch2=false;
    private boolean VMatch2=false;
    private boolean match=false;
    private int totalScore=0;
    private int targetScore=0;
    private int score=0;
    private int stars=0;
    private int playable=0;
    private int levelsPlayed=0;
    private int moves=6;
    private int levelNumber=0;
    private int temScore=0;
    private int flyx;
    private int flyy;
    private int scoreCounter=30;
     int picked=0;
        int tileCounter=1000;
        
        Random r = new Random();
        public void setSmash(){
            
        }
     /**
     * Accessor method for getting the number of tile columns in the game grid.
     * 
     * @return The number of columns (left to right) in the grid for the level
     * currently loaded.
     */
    public int getGridColumns() 
    { 
        return gridColumns; 
    }
    
    /**
     * Accessor method for getting the number of tile rows in the game grid.
     * 
     * @return The number of rows (top to bottom) in the grid for the level
     * currently loaded.
     */
    public int getGridRows() 
    { 
        return gridRows; 
    }
    /**
     * Constructor for initializing this data model, it will create
     * the data structures for storing tiles, but not the tile grid
     * itself, that is dependent of file loading, and so should be
     * subsequently initialized.
     * 
     * @param initMiniGame The Mahjong game UI.
     */
    public ZombieCrushDataModel(MiniGame initMiniGame)
    {
        // KEEP THE GAME FOR LATER
        miniGame = initMiniGame;
        // INIT THESE FOR HOLDING MATCHED AND MOVING TILES
        stackTiles = new ArrayList();
        specialTiles=new ArrayList();
        movingTiles = new ArrayList();
    }
    /**
     * This method loads the tiles, creating an individual sprite for each. Note
     * that tiles may be of various types, which is important during the tile
     * matching tests.
     */
    public void initTiles()
    {
        PropertiesManager props = PropertiesManager.getPropertiesManager();        
        String imgPath = props.getProperty(ZombieCrushPropertyType.IMG_PATH);
        int spriteTypeID = 0;
        SpriteType sT;
        
        // WE'LL RENDER ALL THE TILES ON TOP OF THE BLANK TILE
        String blankTileFileName = props.getProperty(ZombieCrushPropertyType.BLANK_TILE_IMAGE_NAME);
        BufferedImage blankTileImage = miniGame.loadImageWithColorKey(imgPath + blankTileFileName, COLOR_KEY);
        ((ZombieCrushPanel)(miniGame.getCanvas())).setBlankTileImage(blankTileImage);
        
        // THIS IS A HIGHLIGHTED BLANK TILE FOR WHEN THE PLAYER SELECTS ONE
        String blankTileSelectedFileName = props.getProperty(ZombieCrushPropertyType.BLANK_TILE_SELECTED_IMAGE_NAME);
        BufferedImage blankTileSelectedImage = miniGame.loadImageWithColorKey(imgPath + blankTileSelectedFileName, COLOR_KEY);
        ((ZombieCrushPanel)(miniGame.getCanvas())).setBlankTileSelectedImage(blankTileSelectedImage);
        
        
        // FIRST THE BASIC TILES, OF WHICH THERE IS ONLY ONE OF EACH
        // THIS IS ANALOGOUS TO THE SEASON TILES IN FLAVORLESS MAHJONG
        ArrayList<String> basicTiles = props.getPropertyOptionsList(ZombieCrushPropertyType.BASIC_TILES);
        for (int i = 0; i < basicTiles.size(); i++)
        {
            String imgFile = imgPath + basicTiles.get(i);            
            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + spriteTypeID);
            for (int j = 0; j < 300; j++)
            {
                initTile(sT, BASIC_TYPE);
            }

            spriteTypeID++;
        }
        
        // THEN THE SPECIAL TILES, WHICH ALSO ONLY HAVE ONE OF EACH
        // THIS IS ANALOGOUS TO THE FLOWER TILES IN FLAVORLESS MAHJONG
        ArrayList<String> specialTiles = props.getPropertyOptionsList(ZombieCrushPropertyType.SPECIAL_TILES);
        for (int i = 0; i < specialTiles.size(); i++)
        {
            String imgFile = imgPath + specialTiles.get(i);            
            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + spriteTypeID);
            for(int j=0;j<250;j++){
            initSTile(sT, SPECIAL_TYPE);
            }
            spriteTypeID++;
        }
        

           
    
    }
/**
     * Helper method for loading the tiles, it constructs the prescribed
     * tile type using the provided sprite type.
     * 
     * @param sT The sprite type to use to represent this tile during rendering.
     * 
     * @param tileType The type of tile. Note that there are 3 broad categories.
     */
    private void initTile(SpriteType sT, String tileType)
    {
        // CONSTRUCT THE TILE
        ZombieCrushTile newTile = new ZombieCrushTile(sT, unassignedTilesX, unassignedTilesY, 0, 0, INVISIBLE_STATE, tileType);
        
        // AND ADD IT TO THE STACK
        stackTiles.add(newTile);        
    }
    private void initSTile(SpriteType sT, String tileType)
    {
        // CONSTRUCT THE TILE
        ZombieCrushTile newTile = new ZombieCrushTile(sT, unassignedTilesX, unassignedTilesY, 0, 0, INVISIBLE_STATE, tileType);
        
        // AND ADD IT TO THE STACK
        specialTiles.add(newTile);        
    }
    
    /**
     * Called after a level has been selected, it initializes the grid
     * so that it is the proper dimensions.
     * 
     * @param initGrid The grid distribution of tiles, where each cell 
     * specifies the number of tiles to be stacked in that cell.
     * 
     * @param initGridColumns The columns in the grid for the level selected.
     * 
     * @param initGridRows The rows in the grid for the level selected.
     */
    public void initLevelGrid(int[][] initGrid, int initGridColumns, int initGridRows)
    {
        // KEEP ALL THE GRID INFO
        levelGrid = initGrid;
        gridColumns = initGridColumns;
        gridRows = initGridRows;

        // AND BUILD THE TILE GRID FOR STORING THE TILES
        // SINCE WE NOW KNOW ITS DIMENSIONS
        tileGrid = new ArrayList[gridColumns][gridRows];
        for (int i = 0; i < gridColumns; i++)
        {
            for (int j = 0; j < gridRows; j++)
            {
                // EACH CELL HAS A STACK OF TILES, WE'LL USE
                // AN ARRAY LIST FOR THE STACK
                tileGrid[i][j] = new ArrayList();
            }
        }
        // MAKE ALL THE TILES VISIBLE
        enableTiles(true);
    }
    
    /**
     * This helper method initializes a sprite type for a tile or set of
     * similar tiles to be created.
     */
    private SpriteType initTileSpriteType(String imgFile, String spriteTypeID)
    {
        // WE'LL MAKE A NEW SPRITE TYPE FOR EACH GROUP OF SIMILAR LOOKING TILES
        SpriteType sT = new SpriteType(spriteTypeID);
        addSpriteType(sT);
        
        // LOAD THE ART
        BufferedImage img = miniGame.loadImageWithColorKey(imgFile, COLOR_KEY);
        Image tempImage = img.getScaledInstance(TILE_IMAGE_WIDTH, TILE_IMAGE_HEIGHT, BufferedImage.SCALE_SMOOTH);
        img = new BufferedImage(TILE_IMAGE_WIDTH, TILE_IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().drawImage(tempImage, 0, 0, null);
        
        // WE'LL USE THE SAME IMAGE FOR ALL STATES
        sT.addState(INVISIBLE_STATE, img);
        sT.addState(VISIBLE_STATE, img);
        sT.addState(SELECTED_STATE, img);
        sT.addState(INCORRECTLY_SELECTED_STATE, img);
        return sT;
    }
        
    // ACCESSOR METHODS

    /**
     * Accessor method for getting the level currently being played.
     * 
     * @return The level name used currently for the game screen.
     */
    public String getCurrentLevel() 
    { 
        return currentLevel; 
    }
    public int getTemScore(){
        return temScore;
    }
    public void setTemScore(int score){
        temScore=score;
    }
      public int getCurrentScore() 
    { 
        return totalScore; 
    }
      public int getCurrentstar(){
          return stars;
      }
        public int getCurrentMove() 
    { 
        return moves; 
    }
        public void setCurrentScore() 
    { 
        totalScore=0; 
    }
              public void setCurrentScoreH() 
    { 
        totalScore=30000; 
    }
          public void setCurrentMove() 
    { 
       moves=1; 
    }
        public void setLevelNumber(int levelNumber){
            this.levelNumber=levelNumber;
        }
        public void setCurrentMove(int moves) 
    { 
       this.moves=moves;
    }
        public void setTargetScore(int targetScore){
            this.targetScore=targetScore;
        }
        public int getFlyX(){
          return flyx;
        }
      public int getFlyY(){
          return flyy;
      }
      public int getCounter(){
          return scoreCounter;
      }
/**
     * Accessor method for getting the tile grid, which has all the
     * tiles the user may select from.
     * 
     * @return The main 2D grid of tiles the user selects tiles from.
     */
    public ArrayList<ZombieCrushTile>[][] getTileGrid() 
    { 
        return tileGrid; 
    }
    
    /**
     * Accessor method for getting the stack tiles.
     * 
     * @return The stack tiles, which are the tiles the matched tiles
     * are placed in.
     */
    public ArrayList<ZombieCrushTile> getStackTiles()
    {
        return stackTiles;
    }

    /**
     * Accessor method for getting the moving tiles.
     * 
     * @return The moving tiles, which are the tiles currently being
     * animated as they move around the game. 
     */
    public Iterator<ZombieCrushTile> getMovingTiles()
    {
        return movingTiles.iterator();
    }
    
    /**
     * Mutator method for setting the currently loaded level.
     * 
     * @param initCurrentLevel The level name currently being used
     * to play the game.
     */
    public void setCurrentLevel(String initCurrentLevel)
    {
        currentLevel = initCurrentLevel;
    }

    /**
     * Used to calculate the x-axis pixel location in the game grid for a tile
     * placed at column with stack position z.
     * 
     * @param column The column in the grid the tile is located.
     * 
     * @param z The level of the tile in the stack at the given grid location.
     * 
     * @return The x-axis pixel location of the tile 
     */
    public int calculateTileXInGrid(int column, int z)
    {
        int cellWidth = TILE_IMAGE_WIDTH;
        float leftEdge = miniGame.getBoundaryLeft();
        return (int)(leftEdge + (cellWidth * column) - (Z_TILE_OFFSET * z));
    }
    
    /**
     * Used to calculate the y-axis pixel location in the game grid for a tile
     * placed at row with stack position z.
     * 
     * @param row The row in the grid the tile is located.
     * 
     * @param z The level of the tile in the stack at the given grid location.
     * 
     * @return The y-axis pixel location of the tile 
     */
    public int calculateTileYInGrid(int row, int z)
    {
        int cellHeight = TILE_IMAGE_HEIGHT;
        float topEdge = miniGame.getBoundaryTop();
        return (int)(topEdge + (cellHeight * row) - (Z_TILE_OFFSET * z));
    }

    /**
     * Used to calculate the grid column for the x-axis pixel location.
     * 
     * @param x The x-axis pixel location for the request.
     * 
     * @return The column that corresponds to the x-axis location x.
     */
    public int calculateGridCellColumn(int x)
    {
        float leftEdge = miniGame.getBoundaryLeft();
        x = (int)(x - leftEdge);
        return x / TILE_IMAGE_WIDTH;
    }

    /**
     * Used to calculate the grid row for the y-axis pixel location.
     * 
     * @param y The y-axis pixel location for the request.
     * 
     * @return The row that corresponds to the y-axis location y.
     */
    public int calculateGridCellRow(int y)
    {
        float topEdge = miniGame.getBoundaryTop();
        y = (int)(y - topEdge);
        return y / TILE_IMAGE_HEIGHT;
    }

    // GAME DATA SERVICE METHODS
        // -enableTiles
        // -findMove
        // -moveAllTilesToStack
        // -moveTiles
        // -playWinAnimation
        // -processMove
        // -selectTile
        // -undoLastMove

    /**
     * This method can be used to make all of the tiles either visible (true)
     * or invisible (false). This should be used when switching between the
     * splash and game screens.
     * 
     * @param enable Specifies whether the tiles should be made visible or not.
     */
    public void enableTiles(boolean enable)
    {
        // PUT ALL THE TILES IN ONE PLACE WHERE WE CAN PROCESS THEM TOGETHER
        moveAllTilesToStack();
        
        // GO THROUGH ALL OF THEM 
        for (ZombieCrushTile tile : stackTiles)
        {
            // AND SET THEM PROPERLY
            if (enable)
                tile.setState(VISIBLE_STATE);
            else
                tile.setState(INVISIBLE_STATE);
        }        
    }

    /**
     * This method examines the current game grid and finds and returns
     * a valid move that is available.
     * 
     * @return A move that can be made, or null if none exist.
     */
    public boolean findMove() throws InterruptedException{
           HMatch1=false;
           VMatch1=false;
    ;
        boolean havemove=false;
        int counter1=0;
        int counter2=0;
        int counter3=0;
        boolean fourTL=false;
        
         timer = new Timer(0, new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent evt) {
          addRandom();  
  }
});
timer.setDelay(3000); //wait one second
timer.setRepeats(false); //only once
timer.start();
        

        for (int i = 0; i < gridColumns; i++)
        {
            for (int j = 0; j < gridRows; j++)
            {
                if(tileGrid[i][j].size()!=0){
                ZombieCrushTile tempTile;
                tempTile=tileGrid[i][j].get(0);
                 checkHorizontal1(3,tempTile);
                 checkVertical1(3,tempTile);
                 fourTL=check4TL(tempTile);
                 if(HMatch1){
                     counter1++;
                }
                  if(VMatch1){
                     counter2++;
                }
                   if(fourTL){
                     counter3++;
                }
            }
        }
        }
        //System.out.println(counter1+""+counter2+""+counter3);
        
                 if(counter1!=0||counter2!=0||counter3!=0){
          for (int i = 0; i < gridColumns; i++){
            for (int j = 0; j < gridRows; j++)
            {
                if(tileGrid[i][j].size()!=0){
                    havemove=true;
                    ZombieCrushTile tempTile;
                    tempTile=tileGrid[i][j].get(0);
                 removeT(tempTile);
                 removespecialTL(tempTile);
                remove4(tempTile);
                removeHorizontal(3,tempTile);
                removeVertical(3,tempTile);
                 }
            }
        }
   timer = new Timer(0, new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent evt) {
      try { 
          fulFill();
      } catch (InterruptedException ex) {
          Logger.getLogger(ZombieCrushDataModel.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
});
timer.setInitialDelay(1000); //wait one second
timer.setRepeats(false); //only once
timer.start();
          
          
                 }     
                 
                 else if(counter1==0||counter2==0||counter3==0){
                     havemove=false;
                 }
 
        return havemove;
    } 
        // WE'VE SEARCHED THE ENTIRE GRID AND THERE
        // ARE NO POSSIBLE MOVES REMAINING
  
     //pretend  two selected tiles are swap,  check if there are any horizonal matches
     //3,4,5 matches are decided by int match
     //if there are special zombies,then they can be consider as any unspecial one and there's a match if there are two same zombies
     //4-special: elimate the whole column
     //5-special:elimate any zombie type that swap with it
     //T/L special: elimate all nine tiles around
     public void checkHorizontal1(int match,ZombieCrushTile testTile){
         HMatch1=false;
		int counter=0;
                int col=testTile.getGridColumn();
                int row= testTile.getGridRow();
                
                String testID=testTile.getSpriteType().getSpriteTypeID();
                for (int i=col+1;i<=9&&tileGrid[i][row].size()!=0&&tileGrid[i][row].get(0).getSpriteType().getSpriteTypeID().equals(testID);i++){
                     counter++;
     }
     for (int j=col-1;j>=0&&tileGrid[j][row].size()!=0&&tileGrid[j][row].get(0).getSpriteType().getSpriteTypeID().equals(testID);j--){
                    counter++;
     }
     counter++;
    
     if(counter>=3){
		HMatch1=true;}
            //System.out.println("H1"+HMatch1);
	}
     public void checkHorizontal2(int match,ZombieCrushTile testTile){
		int counter=0;
                int col=testTile.getGridColumn();
                int row= testTile.getGridRow();
                String testID=testTile.getSpriteType().getSpriteTypeID();
                for (int i=col+1;i<=9&&tileGrid[i][row].size()!=0&&tileGrid[i][row].get(0).getSpriteType().getSpriteTypeID().equals(testID);i++){
                     counter++;
     }
     for (int j=col-1;j>=0&&tileGrid[j][row].size()!=0&&tileGrid[j][row].get(0).getSpriteType().getSpriteTypeID().equals(testID);j--){
                    counter++;
     }
     counter++;
    
     if(counter>=3){
		HMatch2=true;}
     //System.out.println("H2"+HMatch2);
	}
     //pretend  two selected tiles are swap,  check if there are any vertical matches
     //3,4,5 matches are decided by int match
        public void checkVertical1(int match,ZombieCrushTile testTile){
            VMatch1=false;
            int col=testTile.getGridColumn();
                int row= testTile.getGridRow();
            String testID=testTile.getSpriteType().getSpriteTypeID();
          int counter=0;

for (int i=row+1;i<gridRows&&levelGrid[col][i]!=0&&tileGrid[col][i].size()!=0&&tileGrid[col][i].get(0).getSpriteType().getSpriteTypeID().equals(testID);i++){

                  counter++;
     }
     for (int j=row-1;j>=0&&levelGrid[col][j]!=0&&tileGrid[col][j].size()!=0&&tileGrid[col][j].get(0).getSpriteType().getSpriteTypeID().equals(testID);j--){
		  
                       counter++;
     }       
     counter++;
		if(counter>=3){
			VMatch1=true;
                } 
	//System.out.println("V1"+VMatch1);	
	}
        public void checkVertical2(int match,ZombieCrushTile testTile){
            int col=testTile.getGridColumn();
                int row= testTile.getGridRow();
            String testID=testTile.getSpriteType().getSpriteTypeID();
              int counter=0;

for (int i=row+1;i<gridRows&&levelGrid[col][i]!=0&&tileGrid[col][i].size()!=0&&tileGrid[col][i].get(0).getSpriteType().getSpriteTypeID().equals(testID);i++){
		  //really need to decide if that's get() or removez()
                    
                  counter++;
     }
     for (int j=row-1;j>=0&&levelGrid[col][j]!=0&&tileGrid[col][j].size()!=0&&tileGrid[col][j].get(0).getSpriteType().getSpriteTypeID().equals(testID);j--){
		  
                       counter++;
     }       
     counter++;
		if(counter>=3){
			VMatch2=true;
                } 
		//System.out.println("V2"+VMatch2);
	}
        
             public boolean check4TL(ZombieCrushTile testTile){
         int counter=0;
        boolean HL=false;
        boolean VU=false;
        boolean HR=false;
        boolean VD=false;
        boolean all=false;
         String testID=testTile.getSpriteType().getSpriteTypeID();
      boolean match=false;
     int col=testTile.getGridColumn();
     int row= testTile.getGridRow();
     if(specialTiles.get(5).getSpriteType().getSpriteTypeID().equals(testID)||specialTiles.get(508).getSpriteType().getSpriteTypeID().equals(testID)){
        // System.out.println(testID);
     if(row+2<=9&&tileGrid[col][row+1].size()!=0&&tileGrid[col][row+2].size()!=0){
     VD=tileGrid[col][row+1].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col][row+2].get(0).getSpriteType().getSpriteTypeID());
     //System.out.println("VD"+VD);
     }
     if(row-2>=0&&tileGrid[col][row-1].size()!=0&&tileGrid[col][row-2].size()!=0){
     VU=tileGrid[col][row-1].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col][row-2].get(0).getSpriteType().getSpriteTypeID());
     //System.out.println("VU"+VU);
     }
     if(col+2<=9&&tileGrid[col+1][row].size()!=0&&tileGrid[col+2][row].size()!=0){
     HR=tileGrid[col+1][row].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col+2][row].get(0).getSpriteType().getSpriteTypeID());
     //System.out.println("HR"+HR);
     }
     if(col-2>=0&&tileGrid[col-1][row].size()!=0&&tileGrid[col-2][row].size()!=0){
     HL=tileGrid[col-1][row].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col-2][row].get(0).getSpriteType().getSpriteTypeID());
     System.out.println("HL"+HL);
     }
     }
     if(HL||HR||VU||VD){
        all=true;
     }
     return all;
             }
        
        
         public void removeHorizontal(int match,ZombieCrushTile testTile) throws InterruptedException{
               ArrayList<ZombieCrushTile>horizontal;
               horizontal=new ArrayList();
               int a=0;
		int counter=0;
                int col=testTile.getGridColumn();
                int row= testTile.getGridRow();
                String testID=testTile.getSpriteType().getSpriteTypeID();
       
                 for (int i=col+1;i<gridColumns&&levelGrid[i][row]!=0&&tileGrid[i][row].size()!=0&&tileGrid[i][row].get(0).getSpriteType().getSpriteTypeID().equals(testID);i++){
                 temTile=tileGrid[i][row].get(0);
                   horizontal.add(temTile);
                       a++;
     }
     for (int j=col-1;j>=0&&levelGrid[j][row]!=0&&tileGrid[j][row].size()!=0&&tileGrid[j][row].get(0).getSpriteType().getSpriteTypeID().equals(testID);j--){
                      temTile=tileGrid[j][row].get(0);
                      horizontal.add(temTile);
                      a++;
     }
     a++;
 
     if(a>=3&&tileGrid[col][row].size()!=0){
         calculateScore(a,testTile);
         calculateStars();
          
                         tileGrid[col][row].get(0).setTarget(0,0);
                         tileGrid[col][row].get(0).startMovingToTarget(MID_VELOCITY);
                        movingTiles.add(tileGrid[col][row].get(0));
                        //tileGrid[col][row].get(0).setState(INVISIBLE_STATE);
                        tileGrid[col][row].remove(0);
         //System.out.println("A"+a);
      for(int k=0;k<horizontal.size();k++){
                     int colv=horizontal.get(k).getGridColumn();
                            int rowv=horizontal.get(k).getGridRow();
                        
                        horizontal.get(k).setTarget(0,0);
                        horizontal.get(k).startMovingToTarget(MID_VELOCITY);
                    
                        movingTiles.add(tileGrid[colv][rowv].get(0));
                           // horizontal.get(k).setState(INVISIBLE_STATE);
                        tileGrid[colv][rowv].remove(0);
      }
                     if(a==4){
                         generateSpecial4(col,row);
                     }
                     else if(a==5){
                         generateSpecial5(col,row);
                     }
                     
                        
      //horizontal.clear();
              //TimeUnit.SECONDS.sleep(2);
     }               
		
	}
        public void removeVertical(int match,ZombieCrushTile testTile) throws InterruptedException{
            ArrayList<ZombieCrushTile>vertical;
            int b=0;
            vertical=new ArrayList();
            String testID=testTile.getSpriteType().getSpriteTypeID();
            int col=testTile.getGridColumn();
                int row= testTile.getGridRow();
		int counter=0;
                //System.out.println(testID);
                
		for (int i=row+1;i<gridRows&&levelGrid[col][i]!=0&&tileGrid[col][i].size()!=0&&tileGrid[col][i].get(0).getSpriteType().getSpriteTypeID().equals(testID);i++){
		  //really need to decide if that's get() or removez()
                    
                    temTile=tileGrid[col][i].get(0);
                  vertical.add(temTile);
                  b++;
     }
     for (int j=row-1;j>=0&&levelGrid[col][j]!=0&&tileGrid[col][j].size()!=0&&tileGrid[col][j].get(0).getSpriteType().getSpriteTypeID().equals(testID);j--){
		  temTile=tileGrid[col][j].get(0);
                       vertical.add(temTile);
                       b++;
     }       
     b++;
		if(b>=3&&tileGrid[col][row].size()!=0){
                    calculateScore(b,testTile);
                    calculateStars();
			 //System.out.println("B"+b);
                        
                         //tileGrid[col][row].get(0).setState(INVISIBLE_STATE);
                         tileGrid[col][row].get(0).setTarget(0,0);
                         tileGrid[col][row].get(0).startMovingToTarget(MID_VELOCITY);
                          movingTiles.add(tileGrid[col][row].get(0));
                        tileGrid[col][row].remove(0);
                        
                        for(int k=0;k<vertical.size();k++){
                            int colv=vertical.get(k).getGridColumn();
                            int rowv=vertical.get(k).getGridRow();
                        //vertical.get(k).setState(INVISIBLE_STATE);
                        vertical.get(k).setTarget(0,0);
                        vertical.get(k).startMovingToTarget(MID_VELOCITY);
                        movingTiles.add(vertical.get(k));
                        tileGrid[colv][rowv].remove(0);
                       
                        }
                        
                        if(b==4){
                         generateSpecial4(col,row);
                     }
                        else if(b==5){
                         generateSpecial5(col,row);
                     }
                        //vertical.clear();
                        //testTile.setState(INVISIBLE_STATE);
                      //TimeUnit.SECONDS.sleep(2);   
		}

	}
        //remove all t and l figure
     public void removeT(ZombieCrushTile testTile) throws InterruptedException{
         int H=0;
         int V=0;
         
         int totalCounter=0;
         boolean L=false;
         boolean T=false;
         String testID=testTile.getSpriteType().getSpriteTypeID();
     int col=testTile.getGridColumn();
     int row= testTile.getGridRow();
     ArrayList<ZombieCrushTile>t;
     t=new ArrayList();
     for (int i=row+1;i<gridRows&&levelGrid[col][i]!=0&&tileGrid[col][i].size()!=0&&tileGrid[col][i].get(0).getSpriteType().getSpriteTypeID().equals(testID);i++){
		  temTile=tileGrid[col][i].get(0);
                  t.add(temTile);
                 V++;
     }
     for (int j=row-1;j>=0&&tileGrid[col][j].size()!=0&&tileGrid[col][j].get(0).getSpriteType().getSpriteTypeID().equals(testID);j--){
		  temTile=tileGrid[col][j].get(0);
                       t.add(temTile);
                       V++;
     }             
     for (int i=col+1;i<gridColumns&&levelGrid[i][row]!=0&&tileGrid[i][row].size()!=0&&tileGrid[i][row].get(0).getSpriteType().getSpriteTypeID().equals(testID);i++){
          temTile=tileGrid[i][row].get(0);
           t.add(temTile);
                       H++;
     }
     for (int j=col-1;j>=0&&levelGrid[j][row]!=0&&tileGrid[j][row].size()!=0&&tileGrid[j][row].get(0).getSpriteType().getSpriteTypeID().equals(testID);j--){
                      temTile=tileGrid[j][row].get(0);
                      t.add(temTile);
                      H++;
     }
     H++;
     V++;
    
     if(H>=3&&V>=3){
         calculateScore(t.size(),testTile);
         calculateStars();
         //tileGrid[col][row].get(0).setState(INVISIBLE_STATE);
                         tileGrid[col][row].get(0).setTarget(0,0);
                         tileGrid[col][row].get(0).startMovingToTarget(MID_VELOCITY);
                         movingTiles.add(tileGrid[col][row].get(0));
                        tileGrid[col][row].remove(0);
      for(int k=0;k<t.size();k++){
                      int colv=t.get(k).getGridColumn();
                       int rowv=t.get(k).getGridRow();
                        //t.get(k).setState(INVISIBLE_STATE);
                        t.get(k).setTarget(0,0);
                        t.get(k).startMovingToTarget(MID_VELOCITY);
                         movingTiles.add(t.get(k));
                        tileGrid[colv][rowv].remove(0);
                        }
                        //t.clear();
      generateSpecialTL(col,row);
     }
     }
     
     public void remove4(ZombieCrushTile testTile) throws InterruptedException{
         int counter=0;
        boolean HL=false;
        boolean VU=false;
        boolean HR=false;
        boolean VD=false;
         String testID=testTile.getSpriteType().getSpriteTypeID();
      boolean match=false;
     int col=testTile.getGridColumn();
     int row= testTile.getGridRow();
     if(specialTiles.get(5).getSpriteType().getSpriteTypeID().equals(testID)){
         System.out.println(testID);
     if(row+2<=9&&tileGrid[col][row+1].size()!=0&&tileGrid[col][row+2].size()!=0){
     VD=tileGrid[col][row+1].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col][row+2].get(0).getSpriteType().getSpriteTypeID());
     System.out.println("VD"+VD);
     }
     if(row-2>=0&&tileGrid[col][row-1].size()!=0&&tileGrid[col][row-2].size()!=0){
     VU=tileGrid[col][row-1].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col][row-2].get(0).getSpriteType().getSpriteTypeID());
     System.out.println("VU"+VU);
     }
     if(col+2<=9&&tileGrid[col+1][row].size()!=0&&tileGrid[col+2][row].size()!=0){
     HR=tileGrid[col+1][row].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col+2][row].get(0).getSpriteType().getSpriteTypeID());
     System.out.println("HR"+HR);
     }
     if(col-2>=0&&tileGrid[col-1][row].size()!=0&&tileGrid[col-2][row].size()!=0){
     HL=tileGrid[col-1][row].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col-2][row].get(0).getSpriteType().getSpriteTypeID());
     System.out.println("HL"+HL);
     }
     }

     //IF there's more than two same tiles combine with special4,remove the whole row
     if(HL||HR){
         
       
         System.out.println("REMOVED ROW");
         for(int i=0;i<gridColumns;i++){
             if(tileGrid[i][row].size()!=0){
             //tileGrid[i][row].get(0).setState(INVISIBLE_STATE);
            tileGrid[i][row].get(0).setTarget(0,0);
             tileGrid[i][row].get(0).startMovingToTarget(MID_VELOCITY);
              movingTiles.add(tileGrid[i][row].get(0));
              tileGrid[i][row].remove(0);
              counter++;
             }
         }
         if(tileGrid[col][row].size()!=0){
             //tileGrid[col][row].get(0).setState(INVISIBLE_STATE);
            tileGrid[col][row].get(0).setTarget(0,0);
             tileGrid[col][row].get(0).startMovingToTarget(MID_VELOCITY);
              movingTiles.add(tileGrid[col][row].get(0));
              tileGrid[col][row].remove(0);
         }
         
     }
     //IF there's more than two same tiles combine with special4,remove the whole column
     else if(VU||VD){
         System.out.println("REMOVED COLUMN");
          for(int i=0;i<gridRows;i++){
             if(tileGrid[col][i].size()!=0){
             //tileGrid[col][i].get(0).setState(INVISIBLE_STATE);
            tileGrid[col][i].get(0).setTarget(0,0);
             tileGrid[col][i].get(0).startMovingToTarget(MID_VELOCITY);
              movingTiles.add(tileGrid[col][i].get(0));
              tileGrid[col][i].remove(0);
              counter++;
             }
         }
if(tileGrid[col][row].size()!=0){
             //tileGrid[col][row].get(0).setState(INVISIBLE_STATE);
            tileGrid[col][row].get(0).setTarget(0,0);
             tileGrid[col][row].get(0).startMovingToTarget(MID_VELOCITY);
              movingTiles.add(tileGrid[col][row].get(0));
              tileGrid[col][row].remove(0);
         }
          calculateScore(counter,testTile);
          calculateStars();
     }
   
     }
     //remove all the tiles same as special-5 zombie
     public void remove5(ZombieCrushTile testTile) throws InterruptedException{
         int counter=0;
         String testID=testTile.getSpriteType().getSpriteTypeID();
     int col=testTile.getGridColumn();
     int row= testTile.getGridRow();
       
      for (int i = 0; i < gridColumns; i++)
        {
            for (int j = 0; j < gridRows; j++)
            {
                if(tileGrid[i][j].size()!=0&&tileGrid[i][j].get(0).getSpriteType().getSpriteTypeID().equals(testID)){
                //tileGrid[i][j].get(0).setState(INVISIBLE_STATE);
                tileGrid[i][j].get(0).setTarget(0,0);
             tileGrid[i][j].get(0).startMovingToTarget(MID_VELOCITY);
              movingTiles.add(tileGrid[i][j].get(0));
              tileGrid[i][j].remove(0);
               counter++;
                }
            }
        }
     calculateScore(counter,testTile);
     calculateStars();
     }
     
     public void removespecialTL(ZombieCrushTile testTile) throws InterruptedException{
         int counter=0;
          boolean HL=false;
        boolean VU=false;
        boolean HR=false;
        boolean VD=false;
         String testID=testTile.getSpriteType().getSpriteTypeID();
      boolean match=false;
     int col=testTile.getGridColumn();
     int row= testTile.getGridRow();
     if(specialTiles.get(508).getSpriteType().getSpriteTypeID().equals(testID)){
         System.out.println(testID);
     if(row+2<=9&&tileGrid[col][row+1].size()!=0&&tileGrid[col][row+2].size()!=0){
     VD=tileGrid[col][row+1].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col][row+2].get(0).getSpriteType().getSpriteTypeID());
     System.out.println("VD"+VD);
     }
     if(row-2>=0&&tileGrid[col][row-1].size()!=0&&tileGrid[col][row-2].size()!=0){
     VU=tileGrid[col][row-1].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col][row-2].get(0).getSpriteType().getSpriteTypeID());
     System.out.println("VU"+VU);
     }
     if(col+2<=9&&tileGrid[col+1][row].size()!=0&&tileGrid[col+2][row].size()!=0){
     HR=tileGrid[col+1][row].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col+2][row].get(0).getSpriteType().getSpriteTypeID());
     System.out.println("HR"+HR);
     }
     if(col-2>=0&&tileGrid[col-1][row].size()!=0&&tileGrid[col-2][row].size()!=0){
     HL=tileGrid[col-1][row].get(0).getSpriteType().getSpriteTypeID().equals(tileGrid[col-2][row].get(0).getSpriteType().getSpriteTypeID());
     System.out.println("HL"+HL);
     }
     }
     
     //IF there's more than two same tiles combine with specialTL,just remove all the nine tiles arount it.
     if(HL||HR||VU||VD){
         match=true;
         System.out.println("REMOVED 9");
          for(int i=col-1;i<=col+1&&col-1>=0&&col+1<=9;i++){
              for(int j=row-1;j<=row+1&&row-1>=0&&row+1<=9;j++){
             if(tileGrid[i][j].size()!=0){
             
            tileGrid[i][j].get(0).setTarget(0,0);
             tileGrid[i][j].get(0).startMovingToTarget(MID_VELOCITY);
            // tileGrid[i][j].get(0).setState(INVISIBLE_STATE);
              movingTiles.add(tileGrid[i][j].get(0));
              tileGrid[i][j].remove(0);
              counter++;
             }
              }
         }

         calculateScore(counter,testTile);
         calculateStars();
     }
     }
 
     
        //check the whole grid for already matched tiles when the grid was reset at the beginning
        public void checkAll() throws InterruptedException{

   boolean checkResult=true;
   boolean check=false;
     while(checkResult){

          timer = new Timer(0, new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent evt) {
      try {
          findMove();
      } catch (InterruptedException ex) {
          Logger.getLogger(ZombieCrushDataModel.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
});
timer.setDelay(2000); //wait one second
timer.setRepeats(false); //only once
timer.start();
 

checkResult=findMove();

        }
        }
   //automatically fulfill the empty grid after match
         public void checkAlls() throws InterruptedException{

    boolean checkResult=true;

     while(checkResult){

        addRandom();
     checkResult=findMove();

}
     //addRandom();
         }
         
         
        public void fulFill() throws InterruptedException{
            
int dropped=0;
for (int i = gridRows-1; i>=0; i--)
        {
            for (int j = 0;j<gridColumns;j++)
            {
                     if(levelGrid[j][i]!=0){
                        
                //if the tileGrid is empty and there's some tiles above it
                    if(tileGrid[j][i].size()==0){
                        int counter=0;
                        for(int k=i-1;k>=0&&counter==0;k--){
                            if(tileGrid[j][k].size()!=0){
                            ZombieCrushTile fallTile;
                            fallTile=tileGrid[j][k].remove(0);
                            fallTile.setGridCell(j, i);
                        float x = calculateTileXInGrid(j, 0);
                        float y = calculateTileYInGrid(i, 0);
                       fallTile.setTarget(x, y);
                        fallTile.startMovingToTarget(SLOW_VELOCITY);
                         tileGrid[j][i].add(fallTile);
                        movingTiles.add(fallTile);
                        counter++;
                        dropped++;

                            }
                        }
                        
                    }
                }
            }
            }
//give the dropped tiles some times to drop
        }
        
        public void addRandom(){
            int z=2000000000;
            for(int x=0;x<=10;x++){
             while(z>-200000000){
             z--;
         }
            }
               //just fill in the rest empty grid,all possible tiles fall down
for (int i = gridRows-1; i>=0; i--)
        {
            for (int j = 0;j<gridColumns;j++)
            {
                     if(levelGrid[j][i]!=0){
                        
                    if(tileGrid[j][i].size()==0){
                        picked=r.nextInt(tileCounter);
                        tileCounter--;
                    // TAKE THE TILE OUT OF THE STACK
                    ZombieCrushTile tile = stackTiles.remove(picked);
                     // PUT IT IN THE GRID
                    tile.setGridCell(j, i);
                        float x = calculateTileXInGrid(j, 0);
                        float y = calculateTileYInGrid(i, 0);
                       tile.setTarget(x, y);
                        tile.startMovingToTarget(MAX_TILE_VELOCITY);
                         tileGrid[j][i].add(tile);
                       
                        movingTiles.add(tile);
                        //System.out.println("random tile added");
 }
                }
        }
            }

        }
     //detect if there are any matches 3,4,5,T or L after switch two tiles.
     //If yes,enable switch/no, no switch
     
     public void swap(ZombieCrushMove move){
         ArrayList<ZombieCrushTile> stack1 = tileGrid[move.col1][move.row1];
        ArrayList<ZombieCrushTile> stack2 = tileGrid[move.col2][move.row2];        
        ZombieCrushTile tile1 = stack1.remove(stack1.size()-1);
        ZombieCrushTile tile2 = stack2.remove(stack2.size()-1);
       //ZombieCrushTile tile1=tileGrid[move.col1][move.row1].remove(0);
        //ZombieCrushTile tile2=tileGrid[move.col2][move.row2].remove(0);
         tile1.setState(VISIBLE_STATE);
         tile2.setState(VISIBLE_STATE);
         int x1=0;
         int y1=0;
         int x2=0;
         int y2=0;
         //selecttile col,row
        int col1=tile1.getGridColumn();
        int row1= tile1.getGridRow();
        //selectedtile col,row
        int col2=tile2.getGridColumn();
        int row2 = tile2.getGridRow();
       
             x1=calculateTileXInGrid(col1,0);
             y1=calculateTileYInGrid(row1,0);
             x2=calculateTileXInGrid(col2,0);
             y2=calculateTileYInGrid(row2,0);
             tile1.setGridCell(col2,row2);
             tile2.setGridCell(col1,row1);

             tile1.setTarget(x2,y2);
             tile2.setTarget(x1,y1);
             tile1.startMovingToTarget(MID_VELOCITY);
             
             tile2.startMovingToTarget(MID_VELOCITY);
            
             tileGrid[move.col1][move.row1].add(tile2);
             tileGrid[move.col2][move.row2].add(tile1);
            
             movingTiles.add(tile1);
             movingTiles.add(tile2);
             moves--;
     }
    

    /**
     * This method moves all the tiles not currently in the stack 
     * to the stack.
     */
    public void moveAllTilesToStack()
    {
        for (int i = 0; i < gridColumns; i++)
        {
            for (int j = 0; j < gridRows; j++)
            {
                ArrayList<ZombieCrushTile> cellStack = tileGrid[i][j];
                moveTiles(cellStack, stackTiles);
            }
        }        
    }

    /**
     * This method removes all the tiles in from argument and moves them
     * to argument.
     * 
     * @param from The source data structure of tiles.
     * 
     * @param to The destination data structure of tiles.
     */
    private void moveTiles(ArrayList<ZombieCrushTile> from, ArrayList<ZombieCrushTile> to)
    {
        // GO THROUGH ALL THE TILES, TOP TO BOTTOM
        for (int i = from.size()-1; i >= 0; i--)
        {
            ZombieCrushTile tile = from.remove(i);
            
            // ONLY ADD IT IF IT'S NOT THERE ALREADY
            if (!to.contains(tile))
                to.add(tile);
        }        
    }
  
/**
     * This method updates all the necessary state information
     * to process the move argument.
     * 
     * @param move The move to make. Note that a move specifies
     * the cell locations for a match.
     */
    public void processMove(ZombieCrushMove move)
    {
        
        // REMOVE THE MOVE TILES FROM THE GRID
        ArrayList<ZombieCrushTile> stack1 = tileGrid[move.col1][move.row1];
        ArrayList<ZombieCrushTile> stack2 = tileGrid[move.col2][move.row2];        
        ZombieCrushTile tile1 = stack1.remove(stack1.size()-1);
        ZombieCrushTile tile2 = stack2.remove(stack2.size()-1);
        
        // MAKE SURE BOTH ARE UNSELECTED
        tile1.setState(VISIBLE_STATE);
        tile2.setState(VISIBLE_STATE);
        
        // SEND THEM TO THE STACK
        //tile1.setTarget(tile1.getX(), tile1.getY());
        //tile1.startMovingToTarget(MAX_TILE_VELOCITY);
        //tile2.setTarget(tile2.getX(), tile2.getY());
        //tile2.startMovingToTarget(MAX_TILE_VELOCITY);
        //stackTiles.add(tile1);
        //stackTiles.add(tile2);  
        
        // MAKE SURE THEY MOVE
        movingTiles.add(tile1);
        movingTiles.add(tile2);
    
    }
    
    /**
     * This method attempts to select the selectTile argument. Note that
     * this may be the first or second selected tile. If a tile is already
     * selected, it will attempt to process a match/move.
     * 
     * @param selectTile The tile to select.
     */
    public void selectTile(ZombieCrushTile selectTile) throws InterruptedException
    {
        
        float x1=0;
        float y1=0;
        float x2=0;
        float y2=0;
        boolean above=false;
        boolean under=false;
        boolean left=false;
        boolean right=false;
        boolean all=false;
        boolean fourTL1=false;
                boolean fourTL2=false;
        int col5=0;
        int row5=0;
       
       // IF IT'S ALREADY THE SELECTED TILE, DESELECT IT
        if (selectTile == selectedTile)
        {
            //movingTiles.clear();
            selectedTile = null;
            selectTile.setState(VISIBLE_STATE);
            return;
        }
       
        
        // IF THE TILE IS NOT AT THE TOP OF ITS STACK, DO NOTHING
        int col = selectTile.getGridColumn();
        int row = selectTile.getGridRow();
        int index = tileGrid[col][row].indexOf(selectTile);
     
                
        // IT'S FREE
        if (selectedTile == null)
        {
            //movingTiles.clear();
            selectedTile = selectTile;
            selectedTile.setState(SELECTED_STATE);
            
        }

        //IT'S NOT FREE,MAKE A MATCH AND IT WORKS,
        //suppose they are match
        else if(selectedTile!=null)
        {
            //movingTiles.clear();
            selectTile.setState(SELECTED_STATE);
            
            ZombieCrushDataModel data = (ZombieCrushDataModel)miniGame.getDataModel();
            ZombieCrushMove move = new ZombieCrushMove();
            ZombieCrushMove moveR=new ZombieCrushMove();
             move.col1 = col;
             move.row1 = row;
             move.col2 = selectedTile.getGridColumn();
             move.row2 = selectedTile.getGridRow();
             int cols=selectedTile.getGridColumn();
             int rows=selectedTile.getGridRow();
              boolean T=selectedTile.getSpriteType().getSpriteTypeID().equals(specialTiles.get(508).getSpriteType().getSpriteTypeID());
             boolean L=selectTile.getSpriteType().getSpriteTypeID().equals(specialTiles.get(509).getSpriteType().getSpriteTypeID());
             moveR.col1 = selectedTile.getGridColumn();
             moveR.row1 = selectedTile.getGridRow();
             moveR.col2 = col;
             moveR.row2 = row;
             if(move.col2==move.col1&&move.row1==move.row2-1){
                 above=true;
             }
             else if(move.col2==move.col1&&move.row1==move.row2+1){
                 under=true;
             } 
             else if(move.row2==move.row1&&move.col1==move.col2-1){
                 left=true;
             }
             else if(move.row2==move.row1&&move.col1==move.col2+1){
                 right=true;
             }
             boolean nextTo=(above||under||left||right);
             //if one of the tile is 5-combo special, just swap and remove all the tiles same as the other tile.
             if(nextTo&&selectedTile.getSpriteType().getSpriteTypeID().equals(specialTiles.get(252).getSpriteType().getSpriteTypeID())){
       
               remove5(selectTile);
              //tileGrid[cols][rows].get(0).setState(INVISIBLE_STATE);
               if(tileGrid[cols][rows].size()!=0){
                         tileGrid[cols][rows].get(0).setTarget(0,0);
                         tileGrid[cols][rows].get(0).startMovingToTarget(MAX_TILE_VELOCITY);
                         movingTiles.add(tileGrid[cols][rows].get(0));
                        tileGrid[cols][rows].remove(0);
               }
               
   timer = new Timer(0, new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent evt) {
      try {
          fulFill();
      } catch (InterruptedException ex) {
          Logger.getLogger(ZombieCrushDataModel.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
});
timer.setInitialDelay(1500); //wait one second
timer.setRepeats(false); //only once
timer.start();

timer = new Timer(0, new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent evt) {
      try {
          checkAll();
      } catch (InterruptedException ex) {
          Logger.getLogger(ZombieCrushDataModel.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
});
timer.setInitialDelay(3000); //wait one second
timer.setRepeats(false); //only once
timer.start();
 
               
             selectedTile=null;
             selectTile=null;
             }
             
             else if(nextTo&&selectTile.getSpriteType().getSpriteTypeID().equals(specialTiles.get(252).getSpriteType().getSpriteTypeID())){
   
                 remove5(selectedTile);              
                       // tileGrid[col][row].get(0).setState(INVISIBLE_STATE);
                 if(tileGrid[col][row].size()!=0){
                         tileGrid[col][row].get(0).setTarget(0,0);
                         tileGrid[col][row].get(0).startMovingToTarget(MAX_TILE_VELOCITY);
                          movingTiles.add(tileGrid[cols][rows].get(0));
                        tileGrid[col][row].remove(0);
                 }
   timer = new Timer(0, new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent evt) {
      try {
          fulFill();
      } catch (InterruptedException ex) {
          Logger.getLogger(ZombieCrushDataModel.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
});
timer.setInitialDelay(1500); //wait one second
timer.setRepeats(false); //only once
timer.start();

timer = new Timer(0, new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent evt) {
      try {
          checkAll();
      } catch (InterruptedException ex) {
          Logger.getLogger(ZombieCrushDataModel.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
});
timer.setInitialDelay(3000); //wait one second
timer.setRepeats(false); //only once
timer.start();
               
                selectedTile=null;
             selectTile=null;
             }
             
          
             else if(nextTo){
                 swap(move);
             matchNumber=3;
              checkHorizontal1(3,selectedTile);
              checkVertical1(3,selectedTile);
              checkHorizontal2(3,selectTile);
              checkVertical2(3,selectTile);
              fourTL1=check4TL(selectTile);
              fourTL2=check4TL(selectedTile);
              System.out.println("check done");
               if(HMatch1||VMatch1||HMatch2||VMatch2||fourTL1||fourTL2){
                 removeT(selectTile);
                 removeT(selectedTile);
                 removespecialTL(selectTile);
                 removespecialTL(selectedTile);
                remove4(selectTile);
                 remove4(selectedTile);
                removeHorizontal(3,selectTile);
                removeHorizontal(3,selectedTile);
                removeVertical(3,selectTile);
               
                removeVertical(3,selectedTile);
                //make a delayed fulfill
      timer = new Timer(0, new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent evt) {
      try {  
          fulFill();
      } catch (InterruptedException ex) {
          Logger.getLogger(ZombieCrushDataModel.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
});
timer.setInitialDelay(1500); //wait one second
timer.setRepeats(false); //only once
timer.start();
    
//make a delayed check
timer = new Timer(0, new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent evt) {
      try {  
          checkAll();
      } catch (InterruptedException ex) {
          Logger.getLogger(ZombieCrushDataModel.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
});
timer.setInitialDelay(2000); //wait one second
timer.setRepeats(false); //only once
timer.start();
           
                
                 
             selectedTile=null;
             selectTile=null;
             HMatch1=false;
             VMatch1=false;
             HMatch2=false;
             VMatch2=false;
             }
        
             else{
                   
                 swap(moveR);
              selectedTile=null;
             selectTile=null;
             HMatch1=false;
             VMatch1=false;
             HMatch2=false;
             VMatch2=false;   
             } 
             }
             if(totalScore>=targetScore&&moves==0){
                  calculateStars();
                 endGameAsWin();
                 
             }
             else if(totalScore<targetScore&&moves<=0){
                 calculateStars();
                 endGameAsLoss();
  
             }
              above=false;
              under=false;
              left=false;
              right=false;
             
             selectedTile=null;
             selectTile=null;     
        }
        
    }
     public void smashTile(ZombieCrushTile selectTile) throws InterruptedException{
        int col = selectTile.getGridColumn();
        int row = selectTile.getGridRow();
      ZombieCrushTile smashTile;
        smashTile=tileGrid[col][row].remove(0);
       smashTile.setState(INVISIBLE_STATE);
        setTemScore(20);
        scoreCounter=30;
        flyx=(int)selectTile.getX();
        flyy=(int)selectTile.getY();
   
       fulFill();
        checkAll();
        selectTile=null;
        selectedTile=null;
     }
     public void generateSpecial4(int col,int row){
        ZombieCrushTile newTile;
        int pick;
        pick=(int)(Math.random()*200); 
             newTile=specialTiles.get(pick);
             newTile.setState(VISIBLE_STATE);
             int x;
             int y;
             x=calculateTileXInGrid(col,0);
             y=calculateTileYInGrid(row,0);
             
             newTile.setGridCell(col,row);
             newTile.setTarget(x,y);
             newTile.startMovingToTarget(MAX_TILE_VELOCITY);
             tileGrid[col][row].add(newTile);
             movingTiles.add(newTile);

     }
          public void generateSpecial5(int col,int row){
        ZombieCrushTile newTile;
        int pick;
        pick=255+(int)(Math.random()*200);
             newTile=specialTiles.get(pick);
             newTile.setState(VISIBLE_STATE);
             int x;
             int y;
             x=calculateTileXInGrid(col,0);
             y=calculateTileYInGrid(row,0);
             
             newTile.setGridCell(col,row);
             newTile.setTarget(x,y);
             newTile.startMovingToTarget(MAX_TILE_VELOCITY);
             tileGrid[col][row].add(newTile);
             movingTiles.add(newTile);
    
     }
        public void generateSpecialTL(int col,int row){

        ZombieCrushTile newTile;
        int pick;
        pick=510+(int)(Math.random()*200);
              newTile=specialTiles.get(pick);
             newTile.setState(VISIBLE_STATE);
             int x;
             int y;
             x=calculateTileXInGrid(col,0);
             y=calculateTileYInGrid(row,0);
             
             newTile.setGridCell(col,row);
             newTile.setTarget(x,y);
             newTile.startMovingToTarget(MAX_TILE_VELOCITY);
             tileGrid[col][row].add(newTile);
             movingTiles.add(newTile);

    
     }
        
public void calculateSpecial(int count,ZombieCrushTile tile){
    temScore=count*(20+10*(levelNumber-1));
    totalScore=totalScore+temScore;
    setTemScore(temScore);
    scoreCounter=30;
        flyx=(int)tile.getX();
        flyy=(int)tile.getY();
   
}
    public void calculateScore(int matchNumber,ZombieCrushTile tile) throws InterruptedException{
        if(matchNumber==3){
            temScore=60+10*(levelNumber-1);
            totalScore=totalScore+temScore;
            
        }
            if(matchNumber==4){
                temScore=120+10*(levelNumber-1);
                totalScore=totalScore+temScore;
          
            }
         if(matchNumber==5){
             temScore=200+10*(levelNumber-1);
                totalScore=totalScore+temScore;
               
            }
         if(matchNumber>5){
             temScore=matchNumber*(20+10*(levelNumber-1));
    totalScore=totalScore+temScore;
         }
        setTemScore(temScore);
        scoreCounter=30;
        flyx=(int)tile.getX();
        flyy=(int)tile.getY();
   
        //TimeUnit.SECONDS.sleep(1);
           //setTemScore(0);
    }
    public void update(int x,int y){
        int vy=-20;
        if(scoreCounter>0){
            x=x;
            y=y+vy;
            scoreCounter--;
        }
        flyx=x;
        flyy=y;
        if(flyy<=0){
            setTemScore(0);
        }
    }
    public void calculateStars(){
         if(totalScore<(int)targetScore*0.33){
            stars=0;
           ((ZombieCrushMiniGame)miniGame).setStars(stars);
        }
        if(totalScore>=(int)targetScore*0.33){
            stars=1;
           ((ZombieCrushMiniGame)miniGame).setStars(stars);
        }
        if(totalScore>=(int)targetScore*0.66){
            stars=2;
            ((ZombieCrushMiniGame)miniGame).setStars(stars);
        }
      if(totalScore>=(int)targetScore){
            stars=3;
            ((ZombieCrushMiniGame)miniGame).setStars(stars);
        }
       
    }
    /**
     * Called when the game is won, it will record the ending game time, update
     * the player record, display the win dialog, and play the win animation.
     */
    @Override
    public void endGameAsWin()
    {
        // UPDATE THE GAME STATE USING THE INHERITED FUNCTIONALITY
        super.endGameAsWin();
   
        // RECORD IT AS A WIN
        String level=getCurrentLevel();
       System.out.println(level);
          System.out.println(playable);
          System.out.println(levelsPlayed);

        if(((ZombieCrushMiniGame)miniGame).getPlayerRecord().getplayable(level)==0){
            playable=1;
            levelsPlayed=((ZombieCrushMiniGame)miniGame).getPlayerRecord().getplayedLevels("./data/./ZombieCrush/level1.zom")+1;
          }
        else{
            playable=1;
            levelsPlayed=((ZombieCrushMiniGame)miniGame).getPlayerRecord().getplayedLevels("./data/./ZombieCrush/level1.zom");
        }
         System.out.println(level);
          System.out.println(playable);
          System.out.println(levelsPlayed);
          
        ((ZombieCrushMiniGame)miniGame).getPlayerRecord().setplayedLevels(levelsPlayed);
        ((ZombieCrushMiniGame)miniGame).getPlayerRecord().addScore(currentLevel, totalScore,stars,playable,levelsPlayed);
        ((ZombieCrushMiniGame)miniGame).getPlayerRecord().addLevels("./data/./ZombieCrush/level1.zom",levelsPlayed);
        ((ZombieCrushMiniGame)miniGame).savePlayerRecord();
        
        System.out.println(((ZombieCrushMiniGame)miniGame).getPlayerRecord().getplayable(level));
        System.out.println(((ZombieCrushMiniGame)miniGame).getPlayerRecord().getplayedLevels(level));
        playable=0;
        // DISPLAY THE WIN DIALOG
        miniGame.getGUIDialogs().get(WIN_DIALOG_TYPE).setState(VISIBLE_STATE);     
         for (int i = 0; i < gridColumns; i++)
        {
            for (int j = 0; j < gridRows; j++)
            {
                if(tileGrid[i][j].size()!=0)
                tileGrid[i][j].get(0).setState(INVISIBLE_STATE);
            }
        
        }
         for(int i=0;i<movingTiles.size();i++){
             movingTiles.get(i).setState(INVISIBLE_STATE);
             
         }
         
    }
    @Override
    public void endGameAsLoss(){
     super.endGameAsLoss();
         stars=0;
        // DISPLAY THE LOSS DIALOG
        miniGame.getGUIDialogs().get(LOSS_DIALOG_TYPE).setState(VISIBLE_STATE); 
         for (int i = 0; i < gridColumns; i++)
        {
            for (int j = 0; j < gridRows; j++)
            {
                if(tileGrid[i][j].size()!=0)
                tileGrid[i][j].get(0).setState(INVISIBLE_STATE);
            }
        
        }
           for(int i=0;i<movingTiles.size();i++){
             movingTiles.get(i).setState(INVISIBLE_STATE);
             
         }
    }
    
    @Override
    public void checkMousePressOnSprites(MiniGame game, int x, int y) {
          // FIGURE OUT THE CELL IN THE GRID
        int col = calculateGridCellColumn(x);
        int row = calculateGridCellRow(y);
        
        // DISABLE THE STATS DIALOG IF IT IS OPEN
        //if (game.getGUIDialogs().get(STATS_DIALOG_TYPE).getState().equals(VISIBLE_STATE))
        //{
            //game.getGUIDialogs().get(STATS_DIALOG_TYPE).setState(INVISIBLE_STATE);
           // unpause();
            //return;
        //}
        
        // CHECK THE TOP OF THE STACK AT col, row
        ArrayList<ZombieCrushTile> tileStack = tileGrid[col][row];
        if (tileStack.size() > 0)
        {
            // GET AND TRY TO SELECT THE TOP TILE IN THAT CELL, IF THERE IS ONE
            ZombieCrushTile testTile = tileStack.get(tileStack.size()-1);
           if(miniGame.getCanvas().getCursor().getName().equals("img")){    
                try {
                    smashTile(testTile);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ZombieCrushDataModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
            if (testTile.containsPoint(x, y))
                try {
                selectTile(testTile);
            } catch (InterruptedException ex) {
                Logger.getLogger(ZombieCrushDataModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //add the smash fuction here
    }

        /**
     * Called when a game is started, the game grid is reset.
     * 
     * @param game 
     */
    @Override
    public void reset(MiniGame game)
    {
        ((ZombieCrushMiniGame)miniGame).setStars(0);
         // PUT ALL THE TILES IN ONE PLACE AND MAKE THEM VISIBLE
        //moveAllTilesToStack();
       

        // RANDOMLY ORDER THEM
        //Collections.shuffle(stackTiles);
        
       
        // NOW LET'S REMOVE THEM FROM THE STACK
        // AND PUT THE TILES IN THE GRID        
        for (int i = 0; i < gridColumns; i++)
        {
            for (int j = 0; j < gridRows; j++)
            {
                for (int k = 0; k < levelGrid[i][j]; k++)
                {
                              picked=r.nextInt(tileCounter);
                              tileCounter--;
                    // TAKE THE TILE OUT OF THE STACK
                    ZombieCrushTile tile = stackTiles.remove(picked);
                    
                    // PUT IT IN THE GRID
                    tileGrid[i][j].add(tile);
                  
                    //System.out.println(tileGrid[i][j].get(0).getSpriteType().getSpriteTypeID());
                    tile.setGridCell(i, j);
                    
                    // WE'LL ANIMATE IT GOING TO THE GRID, SO FIGURE
                    // OUT WHERE IT'S GOING AND GET IT MOVING
                    float x = calculateTileXInGrid(i, k);
                    float y = calculateTileYInGrid(j, k);
                    tile.setTarget(x, y);
                    tile.startMovingToTarget(MAX_TILE_VELOCITY);
                    movingTiles.add(tile);
                }
            }
        }  
         System.out.println(tileCounter);
        // AND START ALL UPDATES
      
         beginGame();
        setCurrentScore();
         miniGame.getGUIDialogs().get(WIN_DIALOG_TYPE).setState(INVISIBLE_STATE);
         miniGame.getGUIDialogs().get(LOSS_DIALOG_TYPE).setState(INVISIBLE_STATE);
    }    


    @Override
    public void updateAll(MiniGame game) {
        //System.out.println("movingtiles"+movingTiles.size());
        //System.out.println("movingtiles size"+movingTiles.size());
       // MAKE SURE THIS THREAD HAS EXCLUSIVE ACCESS TO THE DATA
        try
        {
            game.beginUsingData();
        
            // WE ONLY NEED TO UPDATE AND MOVE THE MOVING TILES
            for (int i = 0; i < movingTiles.size(); i++)
            {
                // GET THE NEXT TILE
                ZombieCrushTile tile = movingTiles.get(i);
            
                // THIS WILL UPDATE IT'S POSITION USING ITS VELOCITY
                tile.update(game);
            
                // IF IT'S REACHED ITS DESTINATION, REMOVE IT
                // FROM THE LIST OF MOVING TILES
                if (!tile.isMovingToTarget())
                {
                    movingTiles.remove(tile);
                }
            }
        
            // IF THE GAME IS STILL ON, THE TIMER SHOULD CONTINUE
            if (inProgress())
            {
                // KEEP THE GAME TIMER GOING IF THE GAME STILL IS
                endTime = new GregorianCalendar();
            }
        }
        finally
        {
            // MAKE SURE WE RELEASE THE LOCK WHETHER THERE IS
            // AN EXCEPTION THROWN OR NOT
            game.endUsingData();
        }
     
    }

    @Override
    public void updateDebugText(MiniGame mg) {
         //To change body of generated methods, choose Tools | Templates.
    }

}
