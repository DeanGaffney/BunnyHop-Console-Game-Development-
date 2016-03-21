/**
 * @file        Heart.java
 * @author      Dean Gaffney 20067423
 * @assignment  Heart item for the game.
 * @brief       This class controls all the collection of extra lives.
 *
 * @notes       
 * 				
 */
package ie.wit.cgd.bunnyhop.game.objects;

import ie.wit.cgd.bunnyhop.game.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Heart extends AbstractGameObject{
	private TextureRegion	 regHeart;
	public boolean 			collected;
	
	public Heart(){
		init();
	}
	
	private void init() {
		dimension.set(0.5f, 0.5f);
		regHeart = Assets.instance.heart.heart;
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if (collected) return;
		TextureRegion reg = null;
		reg = regHeart;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
	}

}
