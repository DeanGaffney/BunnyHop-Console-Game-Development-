/**
 * @file        WorldController.java
 * @author      Dean Gaffney 20067423
 * @assignment  WorldController for the game.
 * @brief       This class controls all of the game.
 *
 * @notes       
 * 				
 */
package ie.wit.cgd.bunnyhop.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;

import ie.wit.cgd.bunnyhop.game.objects.BunnyHead;
import ie.wit.cgd.bunnyhop.game.objects.BunnyHead.JUMP_STATE;
import ie.wit.cgd.bunnyhop.game.objects.CoffeeCup;
import ie.wit.cgd.bunnyhop.game.objects.Feather;
import ie.wit.cgd.bunnyhop.game.objects.Goal;
import ie.wit.cgd.bunnyhop.game.objects.GoldCoin;
import ie.wit.cgd.bunnyhop.game.objects.Heart;
import ie.wit.cgd.bunnyhop.game.objects.Rock;
import ie.wit.cgd.bunnyhop.util.CameraHelper;
import ie.wit.cgd.bunnyhop.util.Constants;

public class WorldController extends InputAdapter{

	private static final String TAG = WorldController.class.getName();

	public CameraHelper			cameraHelper;
	public Level    level;
	public int		currentLevel = 1;
	public int      lives;
	public float	timer;
	public int      score;
	public int		goals;
	public Rectangle   r1  = new Rectangle();
	public Rectangle   r2  = new Rectangle();
	private float timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;


	public WorldController(){
		init();
	}

	private void init(){
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START;
		initLevel(currentLevel);
	}

	private void handleDebugInput(float deltaTime) {

		if (Gdx.app.getType() != ApplicationType.Desktop) return;

		// Camera Controls (move)
		if (!cameraHelper.hasTarget(level.bunnyHead)) { 

			float camMoveSpeed = 5 * deltaTime;
			float camMoveSpeedAccelerationFactor = 5;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
			if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
			if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0, -camMoveSpeed);
			if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) cameraHelper.setPosition(0, 0);
		} 
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

	private void initLevel(int currentLevel) {
		score = 0;
		goals = 0;
		timer = Constants.LEVEL_TIMER;
		switch(currentLevel){
		case 1:
			level = new Level(Constants.LEVEL_01);
			break;
		case 2:
			level = new Level(Constants.LEVEL_02);
			break;
		case 3:
			level = new Level(Constants.LEVEL_03);
			break;
		case 4:
			level = new Level(Constants.LEVEL_04);
			break;
		case 5:
			level = new Level(Constants.LEVEL_05);
			break;
		}
		cameraHelper.setTarget(level.bunnyHead);
	}

	//allows you to reset the game world,toggle camera follow on/off and switch levels according to numberpad.
	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) { // Reset game world
		case Keys.R:
			init();
			//Gdx.app.debug(TAG, "Game world resetted");
			break;
		case Keys.ENTER:
			// Toggle camera follow
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null : level.bunnyHead);
			//Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
			break;
		case Keys.NUM_1:
			currentLevel = 1;
			//Gdx.app.debug(TAG, "Level 1 selected ");
			init();
			break;
		case Keys.NUM_2:
			currentLevel = 2;
			//Gdx.app.debug(TAG, "Level 2 selected ");
			init();
			break;
		case Keys.NUM_3:
			currentLevel = 3;
			//Gdx.app.debug(TAG, "Level 3 selected ");
			init();
			break;
		case Keys.NUM_4:
			currentLevel = 4;
			//Gdx.app.debug(TAG, "Level 4 selected ");
			init();
			break;
		case Keys.NUM_5:
			currentLevel = 5;
			//Gdx.app.debug(TAG, "Level 5 selected ");
			init();
			break;
		}
		return false;
	}

	private void handleInputGame(float deltaTime) {
		if (cameraHelper.hasTarget(level.bunnyHead)) {
			// Player Movement
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				level.bunnyHead.velocity.x = -level.bunnyHead.terminalVelocity.x;
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
			} else {
				// Execute auto-forward movement on non-desktop platform
				if (Gdx.app.getType() != ApplicationType.Desktop) {
					level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
				}
			}

			// Bunny Jump
			if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) 
				level.bunnyHead.setJumping(true);
		} else {
			level.bunnyHead.setJumping(false);
		}
	}

	public void update(float deltaTime){
		handleDebugInput(deltaTime);
		timer -= deltaTime;
		if (isGameOver()) {
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0) init();
		} else {
			handleInputGame(deltaTime);
		}
		level.update(deltaTime);
		testCollisions();
		cameraHelper.update(deltaTime);
		if (!isGameOver() && isPlayerInWater()) {
			lives--;
			if (isGameOver()) timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
			else
				initLevel(currentLevel);
		}

		// game over, just wait until message is finished displaying.
		//then decide what level is next.
		if(isGameWon()) {
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0) {
				currentLevel = (currentLevel + 1 <= Constants.NUM_OF_LEVELS) ? currentLevel + 1:1;
				init();
			}
		}
	}


	private void onCollisionBunnyHeadWithRock(Rock rock) {
		BunnyHead bunnyHead = level.bunnyHead;
		float heightDifference = Math.abs(bunnyHead.position.y - (rock.position.y + rock.bounds.height));
		if (heightDifference > 0.25f) {
			boolean hitLeftEdge = bunnyHead.position.x > (rock.position.x + rock.bounds.width / 2.0f);
			if (hitLeftEdge) {
				bunnyHead.position.x = rock.position.x + rock.bounds.width;
			} else {
				bunnyHead.position.x = rock.position.x - bunnyHead.bounds.width;
			}
			return;
		}

		switch (bunnyHead.jumpState) {
		case GROUNDED:
			break;
		case FALLING:
		case JUMP_FALLING:
			bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height + bunnyHead.origin.y;
			bunnyHead.jumpState = JUMP_STATE.GROUNDED;
			break;
		case JUMP_RISING:
			bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height + bunnyHead.origin.y;
			break;
		}
	};

	private void onCollisionBunnyWithGoldCoin(GoldCoin goldcoin) {
		goldcoin.collected = true;
		score += goldcoin.getScore();
	};

	//allocates extra life if player needs one and collides with a heart game object.
	private void onCollisionBunnyWithHeart(Heart heart){
		if(lives < Constants.LIVES_START){
			heart.collected = true;
			lives++;
		}
	}

	private void onCollisionBunnyWithCoffee(CoffeeCup coffeeCup){
		coffeeCup.collected = true;
		score += coffeeCup.getScore();
		level.bunnyHead.setCoffeePowerup(true);
	}

	private void onCollisionBunnyWithFeather(Feather feather) {
		feather.collected = true;
		score += feather.getScore();
		level.bunnyHead.setFeatherPowerup(true);
	};

	private void onCollisionBunnyWithGoal(Goal goal){
		goal.collected = true;
		goals++;
		timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
	};


	private void testCollisions() {
		r1.set(level.bunnyHead.position.x, level.bunnyHead.position.y, level.bunnyHead.bounds.width,
				level.bunnyHead.bounds.height);

		// Test collision: Bunny Head <-> Rocks
		for (Rock rock : level.rocks) {
			r2.set(rock.position.x, rock.position.y, rock.bounds.width, rock.bounds.height);
			if (!r1.overlaps(r2)) continue;
			onCollisionBunnyHeadWithRock(rock);
			// IMPORTANT: must do all collisions for valid
			// edge testing on rocks.
		}

		// Test collision: Bunny Head <-> Gold Coins
		for (GoldCoin goldCoin : level.goldCoins) {
			if (goldCoin.collected) continue;
			r2.set(goldCoin.position.x, goldCoin.position.y, goldCoin.bounds.width, goldCoin.bounds.height);
			if (!r1.overlaps(r2)) continue;
			onCollisionBunnyWithGoldCoin(goldCoin);
			break;
		}

		// Test collision: Bunny Head <-> Feathers
		for (Feather feather : level.feathers) {
			if (feather.collected) continue;
			r2.set(feather.position.x, feather.position.y, feather.bounds.width, feather.bounds.height);
			if (!r1.overlaps(r2)) continue;
			onCollisionBunnyWithFeather(feather);
			break;
		}

		for(CoffeeCup coffeeCup : level.coffeeCups){
			if(coffeeCup.collected)continue;
			r2.set(coffeeCup.position.x, coffeeCup.position.y, coffeeCup.bounds.width, coffeeCup.bounds.height);
			if(!r1.overlaps(r2))continue;
			onCollisionBunnyWithCoffee(coffeeCup);
			break;
		}

		//test collision with BunnyHead <-> Goal
		for(Goal goal : level.goals){
			if(goal.collected)continue;
			r2.set(goal.position.x,goal.position.y,goal.bounds.width,goal.bounds.height);
			if(!r1.overlaps(r2))continue;
			onCollisionBunnyWithGoal(goal);
		}

		for(Heart heart : level.hearts){
			if(heart.collected)continue;
			r2.set(heart.position.x,heart.position.y,heart.bounds.width,heart.bounds.height);
			if(!r1.overlaps(r2))continue;
			onCollisionBunnyWithHeart(heart);
		}
	}

	public boolean isGameWon(){
		for(Goal goal : level.goals){
			if(!goal.collected)return false;
		}
		if(score < Constants.REQUIRED_SCORE) return false;
		//this code should only be reached when the game is won
		return true;
	}

	public boolean isGameOver() {
		return (lives <=0 || timer <=0) ? true:false;
	}

	public boolean isPlayerInWater() {
		return level.bunnyHead.position.y < -5;
	}

}
