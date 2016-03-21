/**
 * @file        GoldCoin.java
 * @author      Dean Gaffney 20067423
 * @assignment  Gold Coin items for the game.
 * @brief       This class controls all aspects of gold coins for the game.
 *
 * @notes       
 * 				
 */
package ie.wit.cgd.bunnyhop.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ie.wit.cgd.bunnyhop.game.Assets;

public class GoldCoin extends AbstractGameObject {
	private TextureRegion   regGoldCoin;
	public boolean          collected;

	public GoldCoin() {
		init();
	}

	private void init() {
		dimension.set(0.5f, 0.5f);
		regGoldCoin = Assets.instance.goldCoin.goldCoin;
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
	}

	public void render(SpriteBatch batch) {
		if (collected) return;

		TextureRegion reg = null;
		reg = regGoldCoin;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
	}

	public int getScore() {
		return 100;
	}
}