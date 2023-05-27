package com.kkdgames.core.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Player extends Actor {
    private float posX;
    private float posY;
    private final float scale;

    private final Texture texture;
    private final Array<String> inventory;
    private int health;

    public Player(Texture texture, float height) {
        this.texture = texture;
        this.scale = height / texture.getHeight();

        posX = 0;
        posY = 0;
        inventory = new Array<String>();
        health = 10;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Array<String> getInventory() {
        return inventory;
    }

    @Override
    public void act(float delta) {
        final float step = 2f;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            posX -= step;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            posX += step;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            posY += step;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            posY -= step;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(texture, posX, posY,
                texture.getWidth() * scale,
                texture.getHeight() * scale);
    }
}
