/**
 * @file        CoffeeCup.java
 * @author      Dean Gaffney 20067423
 * @assignment 	Coffee cup power up 
 * @brief       This class controls all aspects of the Coffee Cup power up.
 *
 * @notes       
 * 				
 */
package ie.wit.cgd.bunnyhop.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ie.wit.cgd.bunnyhop.game.Assets;

public class CoffeeCup extends AbstractGameObject{
	
	private TextureRegion   regCoffeeCup;
	public boolean          collected;

	public CoffeeCup() {
		init();
	}

	private void init() {
		dimension.set(0.8f, 0.8f);
		regCoffeeCup = Assets.instance.coffeeCup.coffeeCup;
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
	}

	public void render(SpriteBatch batch) {
		if (collected) return;
		TextureRegion reg = null;
		reg = regCoffeeCup;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
	}

	public int getScore() {
		return 250;
	}

}
