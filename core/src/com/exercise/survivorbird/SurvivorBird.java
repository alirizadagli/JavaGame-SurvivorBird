package com.exercise.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture bird;
    Texture enemy1;
    Texture enemy2;
    Texture enemy3;
    float birdX = 0;
    float birdY = 0;
    float width = 0;
    float height = 0;
    int velocity = 0;
    GameState gameState = GameState.DID_NOT_START;
    int numberOfEnemies = 4;
    float[] enemyX = new float[numberOfEnemies];
    float distance = 0;
    float enemyVelocity = 6f;
    float[] enemyOffset1 = new float[numberOfEnemies];
    float[] enemyOffset2 = new float[numberOfEnemies];
    float[] enemyOffset3 = new float[numberOfEnemies];
    Random random;

    Circle birdCircle;
    Circle[] enemyCircles1;
    Circle[] enemyCircles2;
    Circle[] enemyCircles3;

    int score = 0;
    int scoredEnemy = 0;
    BitmapFont scoreFont;
    BitmapFont gameOverFont;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        bird = new Texture("bird.png");
        enemy1 = new Texture("enemy.png");
        enemy2 = new Texture("enemy.png");
        enemy3 = new Texture("enemy.png");

        birdX = Gdx.graphics.getWidth() / 4f;
        birdY = Gdx.graphics.getHeight() / 3f;
        width = Gdx.graphics.getWidth() / 15f;
        height = Gdx.graphics.getHeight() / 10f;

        distance = Gdx.graphics.getWidth() / 2f;
        random = new Random();

        birdCircle = new Circle();
        enemyCircles1 = new Circle[numberOfEnemies];
        enemyCircles2 = new Circle[numberOfEnemies];
        enemyCircles3 = new Circle[numberOfEnemies];

        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.WHITE);
        scoreFont.getData().setScale(4);

        gameOverFont = new BitmapFont();
        gameOverFont.setColor(Color.WHITE);
        gameOverFont.getData().setScale(4);

        init();
    }

    private void init() {
        for (int i = 0; i < numberOfEnemies; i++) {
            enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyX[i] = (Gdx.graphics.getWidth() + 150) + (i * distance);

            enemyCircles1[i] = new Circle();
            enemyCircles2[i] = new Circle();
            enemyCircles3[i] = new Circle();
        }
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background,
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        batch.draw(bird,
                birdX,
                birdY,
                width,
                height);

        birdCircle.set(birdX + (width / 2f),
                birdY + (width / 2f) - 12,
                (width / 2) - 12);

        if (gameState == GameState.STARTED) {

            if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 4f) {
                score++;

                if (scoredEnemy < numberOfEnemies - 1) {
                    scoredEnemy++;
                } else {
                    scoredEnemy = 0;
                }
            }

            for (int i = 0; i < numberOfEnemies; i++) {

                if (enemyX[i] <= (Gdx.graphics.getWidth() + 150) + (i * distance)
                        && enemyX[i] > -250) {
                    enemyX[i] -= enemyVelocity;

                    float offset1 = Gdx.graphics.getHeight() / 2f + enemyOffset1[i];
                    float offset2 = Gdx.graphics.getHeight() / 2f + enemyOffset2[i];
                    float offset3 = Gdx.graphics.getHeight() / 2f + enemyOffset3[i];
                    if (!(offset1 > Gdx.graphics.getHeight())
                            && (offset1 - offset2 > 100 || offset2 - offset1 > 100)
                            && (offset1 - offset3 > 100 || offset3 - offset1 > 100)) {
                        batch.draw(enemy1, enemyX[i], offset1, width, height);
                        enemyCircles1[i].set(enemyX[i] + (width / 2f),
                                offset1 + (width / 2f) - 12,
                                (width / 2) - 12);
                        if (Intersector.overlaps(birdCircle, enemyCircles1[i])) {
                            gameState = GameState.ENDED;
                        }
                    }
                    if (!(offset2 > Gdx.graphics.getHeight())
                            && (offset2 - offset1 > 100 || offset1 - offset2 > 100)
                            && (offset2 - offset3 > 100 || offset3 - offset2 > 100)) {
                        batch.draw(enemy2, enemyX[i], offset2, width, height);
                        enemyCircles2[i].set(enemyX[i] + (width / 2f),
                                offset2 + (width / 2f) - 12,
                                (width / 2) - 12);
                        if (Intersector.overlaps(birdCircle, enemyCircles2[i])) {
                            gameState = GameState.ENDED;
                        }
                    }
                    if (!(offset3 > Gdx.graphics.getHeight())
                            && (offset3 - offset1 > 100 || offset1 - offset3 > 100)
                            && (offset2 - offset3 > 100 || offset3 - offset2 > 100)) {
                        batch.draw(enemy3, enemyX[i], offset3, width, height);
                        enemyCircles3[i].set(enemyX[i] + (width / 2f),
                                offset3 + (width / 2f) - 12,
                                (width / 2) - 12);
                        if (Intersector.overlaps(birdCircle, enemyCircles3[i])) {
                            gameState = GameState.ENDED;
                        }
                    }
                } else {
                    enemyX[i] = (Gdx.graphics.getWidth() + 150) + (i * distance);
                }
            }


            if (Gdx.input.justTouched()) {
                velocity -= 15;
            }

            if (birdY > 0 && birdY < Gdx.graphics.getHeight()) {

                velocity++;
                birdY -= velocity;
            } else {
                gameState = GameState.ENDED;
            }
        } else if (Gdx.input.justTouched()
                && gameState != GameState.STARTED) {
            gameState = GameState.STARTED;
        } else if (gameState == GameState.ENDED) {
            gameOverFont.draw(batch,
                    "Game Over! Tap To Play Again!",
                    Gdx.graphics.getWidth() / 3.3f,
                    Gdx.graphics.getHeight() / 1.5f);
            birdX = Gdx.graphics.getWidth() / 4f;
            birdY = Gdx.graphics.getHeight() / 3f;
            velocity = 0;
            score = 0;
            scoredEnemy = 0;
            init();
        }

        scoreFont.draw(batch, "Score: " + score, 100, 200);

        batch.end();
    }

    @Override
    public void dispose() {
    }
}

enum GameState {
    DID_NOT_START,
    STARTED,
    ENDED,
}