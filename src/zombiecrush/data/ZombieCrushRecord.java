/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiecrush.data;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
/**
 *
 * @author Jing
 */
public class ZombieCrushRecord {
    // HERE ARE ALL THE RECORDS
    private HashMap<String, ZombieCrushLevelRecord> levelRecords;
private int playlevels=0;
    /**
     * Default constructor, it simply creates the hash table for
     * storing all the records stored by level.
     */
    public ZombieCrushRecord()
    {
        levelRecords = new HashMap();
}
    public int getScore(String levelName)
    {
        ZombieCrushLevelRecord rec = levelRecords.get(levelName);
        
        // IF levelName ISN'T IN THE RECORD OBJECT
        // THEN SIMPLY RETURN 0        
        if (rec == null)
            return 0;
        // OTHERWISE RETURN THE WINS
        else
            return rec.score; 
    }
       public int getStars(String levelName)
    {
        ZombieCrushLevelRecord rec = levelRecords.get(levelName);
        
        // IF levelName ISN'T IN THE RECORD OBJECT
        // THEN SIMPLY RETURN 0        
        if (rec == null)
            return 0;
        // OTHERWISE RETURN THE WINS
        else
            return rec.stars; 
    }
    public int getplayable(String levelName){
        ZombieCrushLevelRecord rec = levelRecords.get(levelName);
        if(rec==null){
            return 0;
        }
        return rec.playable;
    }
    public void addLevels(String levelName,int levels){
        ZombieCrushLevelRecord rec = levelRecords.get(levelName);
             rec.playlevels=levels;
    }
      public void setplayedLevels(int playlevels){
           
            this.playlevels=playlevels;
        } 
        public int getplayedLevels(String levelName){
           ZombieCrushLevelRecord rec = levelRecords.get(levelName);
            if (rec == null)
            return 0;
            return rec.playlevels;
        } 

    
/**
     * Adds the record for a level
     * 
     * @param levelName
     * 
     * @param rec 
     */
    public void addZombieCrushRecord(String levelName, ZombieCrushLevelRecord rec)
    {
        levelRecords.put(levelName, rec);
    }
    
        public void addScore(String levelName, int score,int stars,int playable,int playlevels)
    {
        // GET THE RECORD FOR levelName
        ZombieCrushLevelRecord rec = levelRecords.get(levelName);
        
        // IF THE PLAYER HAS NEVER PLAYED A GAME ON levelName
        if (rec == null)
        {
            // MAKE A NEW RECORD FOR THIS LEVEL, SINCE THIS IS
            // THE FIRST TIME WE'VE PLAYED IT
            rec = new ZombieCrushLevelRecord();
            rec.score = score;
            rec.stars = stars ;
            rec.playable=1;
            rec.playlevels=playlevels;
            levelRecords.put(levelName, rec);
        }
        else
        {
            if (score > rec.score){
                rec.score = score;
        }
            if(stars>rec.stars){
                rec.stars=stars;
            }
 this.playlevels=playlevels;
           
    }
    }
    
    
     /**
     * This method constructs and fills in a byte array with all the
     * necessary data stored by this object. We do this because writing
     * a byte array all at once to a file is fast. Certainly much faster
     * than writing to a file across many write operations.
     * 
     * @return A byte array filled in with all the data stored in this
     * object, which means all the player records in all the levels.
     * 
     * @throws IOException Note that this method uses a stream that
     * writes to an internal byte array, not a file. So this exception
     * should never happen.
     */
    public byte[] toByteArray() throws IOException
    {
        Iterator<String> keysIt = levelRecords.keySet().iterator();
        int numLevels = levelRecords.keySet().size();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(numLevels);
        while(keysIt.hasNext())
        {
            String key = keysIt.next();
            dos.writeUTF(key);
            ZombieCrushLevelRecord rec = levelRecords.get(key);
            dos.writeInt(rec.score);
            dos.writeInt(rec.stars);
            dos.writeInt(rec.playable);
            dos.writeInt(rec.playlevels);
         
            
        }
        // AND THEN RETURN IT
        return baos.toByteArray();
    }

}
