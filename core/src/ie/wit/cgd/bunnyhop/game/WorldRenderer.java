package ie.wit.cgd.bunnyhop.game;

import ie.wit.cgd.bunnyhop.game.objects.GoldCoin;
import ie.wit.cgd.bunnyhop.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

public class WorldRenderer implements Disposable{

	private OrthographicCamera camera;
	private OrthographicCamera cameraGUI;
	private SpriteBatch batch;
	private WorldController worldController;

	public WorldRenderer(WorldController worldController){
		this.worldController = worldController;
		init();
	}

	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // flip y-axis
		cameraGUI.update();
	}

	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float) height) * (float) width;
		camera.update();
		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float) width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}

	private void renderGuiScore(SpriteBatch batch) {
		float x = -15;
		float y = -15;
		batch.draw(Assets.instance.goldCoin.goldCoin, x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig.draw(batch, "" + worldController.score, x + 75, y + 37);
	}

	public void render(){
		renderWorld(batch);
		renderGui(batch);	
	}

	private void renderWorld(SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
	}

	private void renderGuiExtraLive(SpriteBatch batch) {
		float x = cameraGUI.viewportWidth - 50 - Constants.LIVES_START * 50;
		float y = -15;
		for (int i = 0; i < Constants.LIVES_START; i++) {
			if (worldController.lives <= i) batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			batch.draw(Assets.instance.bunny.head, x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
		}
	}


	private void renderGuiFpsCounter(SpriteBatch batch) {
		float x = cameraGUI.viewportWidth - 55;
		float y = cameraGUI.viewportHeight - 15;
		int fps = Gdx.graphics.getFramesPerSecond();
		BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
		if (fps >= 45) {
			// 45 or more FPS show up in green
			fpsFont.setColor(0, 1, 0, 1);
		} else if (fps >= 30) {
			// 30 or more FPS show up in yellow
			fpsFont.setColor(1, 1, 0, 1);
		} else {
			// less than 30 FPS show up in red
			fpsFont.setColor(1, 0, 0, 1);
		}
		fpsFont.draw(batch, "FPS: " + fps, x, y);
		fpsFont.setColor(1, 1, 1, 1); // white
	}

	private void renderGui(SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();

		// draw collected gold coins icon + text
		// (anchored to top left edge)
		renderGuiScore(batch);

		// draw extra lives icon + text (anchored to top right edge)
		renderGuiExtraLive(batch);

		// draw FPS text (anchored to bottom right edge)
		renderGuiFpsCounter(batch);

		//draw remaining goals and icon next to score.
		renderRemainingGoals(batch);

		//renderGuiCoinCollectedMessage(batch);
		renderGuiGameOverMessage(batch);
		renderGuiLevelTimer(batch);
		renderGuiFeatherPowerup(batch);

		renderGuiGameWonMessage(batch);
		batch.end();
	}

	private void renderGuiExtraLifeMessage(SpriteBatch batch){
		//TO DO display message when you pick up a life.

	}


	/*private void renderGuiCoinCollectedMessage(SpriteBatch batch){
		//TO DO display message when gold coin is collected.
		float x = cameraGUI.viewportWidth / 2;
		float y = cameraGUI.viewportHeight / 2;
		float displayTime = TimeUtils.nanoTime();
		boolean display = false;
		for(GoldCoin goldCoin: worldController.level.goldCoins){
			if(goldCoin.collected && displayTime > 100000000){
				BitmapFont fontGameOver = Assets.instance.fonts.defaultNormal;
				fontGameOver.setColor(1, 0.75f, 0.25f, 1);
				fontGameOver.draw(batch, "+100", x, y, 0, Align.center,true);
				fontGameOver.setColor(1, 1, 1, 1);
				displayTime -= Gdx.graphics.getDeltaTime();
			}
		}
	}*/

	private void renderGuiGameWonMessage(SpriteBatch batch){
		float x = cameraGUI.viewportWidth / 2;
		float y = cameraGUI.viewportHeight / 2;
		if (worldController.isGameWon()) {
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			fontGameOver.draw(batch, "YOU WIN!", x, y, 0, Align.center,true);
			fontGameOver.setColor(1, 1, 1, 1);
		}
	}

	private void renderRemainingGoals(SpriteBatch batch){
		float x = -15;
		float y = 30;
		int remainingGoals = worldController.level.goals.size - worldController.goals;
		batch.draw(Assets.instance.goal.goal, x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig.draw(batch, "" + remainingGoals, x + 75, y + 37);
	}

	private void renderGuiLevelTimer(SpriteBatch batch){
		float x = 80;
		float y = 465;
		
		float timer = worldController.timer;
		int mins = (int)(timer/60);
		int secs = (int)(timer%60);
		int milli = (int)(timer * 100) % 100;
		BitmapFont fontTimer = Assets.instance.fonts.defaultNormal;
		fontTimer.setColor(1, 0.75f, 0.25f, 1);
		fontTimer.draw(batch, "Time remaining: "+mins+":"+secs+":"+milli, x, y, 0, Align.center,true);
		fontTimer.setColor(0, 0, 1, 1);
		
		if(worldController.timer <= 0){
			BitmapFont fontOutOfTime = Assets.instance.fonts.defaultBig;
			fontOutOfTime.setColor(1, 0.75f, 0.25f, 1);
			fontOutOfTime.draw(batch, "OUT OF TIME!", x, y, 0, Align.center,true);
			fontOutOfTime.setColor(1, 1, 1, 1);
		}
	}


	private void renderGuiGameOverMessage(SpriteBatch batch) {
		float x = cameraGUI.viewportWidth / 2;
		float y = cameraGUI.viewportHeight / 2;
		if (worldController.isGameOver()) {
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			fontGameOver.draw(batch, "GAME OVER", x, y, 0, Align.center,true);
			fontGameOver.setColor(1, 1, 1, 1);
		}
	}

	private void renderGuiFeatherPowerup(SpriteBatch batch) {
		float x = -15;
		float y = 30;
		float timeLeftFeatherPowerup = worldController.level.bunnyHead.timeLeftFeatherPowerup;
		if (timeLeftFeatherPowerup > 0) {
			// Start icon fade in/out if the left power-up time
			// is less than 4 seconds. The fade interval is set
			// to 5 changes per second.
			if (timeLeftFeatherPowerup < 4) {
				if (((int) (timeLeftFeatherPowerup * 5) % 2) != 0) {
					batch.setColor(1, 1, 1, 0.5f);
				}
			}
			batch.draw(Assets.instance.feather.feather, x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
			Assets.instance.fonts.defaultSmall.draw(batch, "" + (int) timeLeftFeatherPowerup, x + 60, y + 57);
		}
	}
	@Override
	public void dispose() {
		batch.dispose();
	}
}
