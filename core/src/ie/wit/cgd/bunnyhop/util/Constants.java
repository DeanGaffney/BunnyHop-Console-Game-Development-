/**
 * @file        Constants.java
 * @author      Dean Gaffney 20067423
 * @assignment  All set variables/rules for the game.
 * @brief       This class controls the set variables and rules of the game itself.
 *
 * @notes       
 * 				
 */
package ie.wit.cgd.bunnyhop.util;

public class Constants {

	public static final float VIEWPORT_WIDTH            = 5.0f;         // Visible game world is 5 meters wide
    public static final float VIEWPORT_HEIGHT           = 5.0f;         // Visible game world is 5 meters tall

    public static final float VIEWPORT_GUI_WIDTH        = 800.0f;       // GUI Width
    public static final float VIEWPORT_GUI_HEIGHT       = 480.0f;       // GUI Height

    // Location of description file for texture atlas
    public static final String TEXTURE_ATLAS_OBJECTS    = "images/bunnyhop.atlas";

    //Amount of levels in game
    public static final int NUM_OF_LEVELS 				= 5;
    
    // Location of image file for levels
    public static final String LEVEL_01                 = "levels/level-01.png";    
    public static final String LEVEL_02					= "levels/level-02.png";
    public static final String LEVEL_03					= "levels/level-03.png";
    public static final String LEVEL_04					= "levels/level-04.png";
    public static final String LEVEL_05					= "levels/level-05.png";

    public static final int LIVES_START                 = 3;            // Amount of extra lives at level start
    
	public static final float ITEM_FEATHER_POWERUP_DURATION = 9;
	
	public static final float ITEM_COFFEE_POWERUP_DURATION = 9;
	
	public static final float LEVEL_TIMER 				= 150;
	
	public static final int REQUIRED_SCORE				= 2000;
	
	  // Delay after game over
    public static final float TIME_DELAY_GAME_OVER = 3;
}
