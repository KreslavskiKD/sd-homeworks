package com.kkdgames.core.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.kkdgames.core.loot.Loot;
import util.FontSizeHandler;

public class Player extends Actor {
    private final float currentStep;

    private final Texture texture;
    private final Array<Loot> inventory;
    private int health;
    private final BitmapFont font;

    private final float halvedTextureWidth;
    private final float halvedTextureHeight;

    public Player(Texture texture, float height, float spawnPointX, float spawnPointY) {
        this.texture = texture;
        float scale = height / texture.getHeight();
        setScale(scale, scale);

        font = FontSizeHandler.INSTANCE.getFont((int) (texture.getWidth() / 2 * getScaleX()), Color.OLIVE);

        halvedTextureWidth = texture.getWidth() / 2f * scale;
        halvedTextureHeight = texture.getHeight() / 2f * scale;
        setX(spawnPointX);
        setY(spawnPointY);

        setX(getCenterX());
        setY(getCenterY());

        inventory = new Array<Loot>();
        health = 100;
        currentStep = STEP_BASE;
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth(int value) {
        this.health -= value;
        if (health < 0) {
            health = 0;
        }
    }

    public Array<Loot> getInventory() {
        return inventory;
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveBy(-currentStep, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveBy(currentStep, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveBy(0, currentStep);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveBy(0, -currentStep);
        }
    }

    public float getCenterX() {
        return getX() + halvedTextureWidth;
    }

    public float getCenterY() {
        return getY() + halvedTextureHeight;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, String.valueOf(health), getX(), getY());
        batch.draw(texture, getX(), getY(), texture.getWidth() * getScaleX(), texture.getHeight() * getScaleY());
    }

    private final static float STEP_BASE = 3.5F;
    private final static float STEP_UP_1 = 4F;
    private final static float STEP_FULL = 8F;
}
