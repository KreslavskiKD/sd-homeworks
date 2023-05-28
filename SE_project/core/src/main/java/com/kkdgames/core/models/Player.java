package com.kkdgames.core.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.kkdgames.core.loot.Loot;

public class Player extends Actor {
    private float posX;
    private float posY;
    private final float scale;

    private float currentStep;      // not final because later some loot from inventory can increase the speed

    private final Texture texture;
    private final Array<Loot> inventory;
    private int health;

    public Player(Texture texture, float height, float spawnPointX, float spawnPointY) {
        this.texture = texture;
        this.scale = height / texture.getHeight();

        posX = spawnPointX;
        posY = spawnPointY;
        inventory = new Array<Loot>();
        health = 10;
        currentStep = 2F;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Array<Loot> getInventory() {
        return inventory;
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            posX -= currentStep;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            posX += currentStep;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            posY += currentStep;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            posY -= currentStep;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(texture, posX, posY,
                texture.getWidth() * scale,
                texture.getHeight() * scale);
    }
}
