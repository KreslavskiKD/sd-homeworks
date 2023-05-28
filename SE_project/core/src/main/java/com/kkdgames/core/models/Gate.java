package com.kkdgames.core.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Gate {
    public enum Type {
        LEFT,
        RIGHT,
        UPPER,
        LOWER
    }

    private final Texture texture;
    private final float windowWidth;
    private final float windowHeight;
    private final float scale;

    private float posX;
    private float posY;

    public Gate(Texture texture, Type type, float windowWidth, float windowHeight) {
        this.texture = texture;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.scale = windowWidth / 10 / texture.getWidth();
        setPos(type);
    }

    private void setPos(Type type) {
        switch (type) {
            case LEFT: {
                posX = 0f;
                posY = windowHeight / 2 - texture.getHeight() * scale / 2;
            }
            case RIGHT: {
                posX = windowWidth - texture.getWidth();
                posY = windowHeight / 2 - texture.getHeight() * scale / 2;
            }
            case UPPER: {
                posX = windowWidth / 2 - texture.getWidth() * scale / 2;
                posY = windowHeight - texture.getHeight();
            }
            case LOWER: {
                posX = windowWidth / 2 - texture.getWidth() * scale / 2;
                posY = 0f;
            }
        }
    }

    public void draw(Batch batch) {
        batch.draw(texture, posX, posY, texture.getWidth() * scale, texture.getHeight() * scale);
    }

    public void dispose() {
        texture.dispose();
    }
}
