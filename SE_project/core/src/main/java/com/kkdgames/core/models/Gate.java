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

    private final Type type;
    private final Texture texture;
    private final int windowWidth;
    private final int windowHeight;

    private final float width;
    private final float height;

    private float posX;
    private float posY;

    public Gate(Texture texture, Type type, int windowWidth, int windowHeight) {
        this.texture = texture;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        float scale = windowWidth / 7f / texture.getWidth();
        width = texture.getWidth() * scale;
        height = texture.getHeight() * scale;
        this.type = type;
        setupPos();
    }

    public Type getType() {
        return type;
    }

    public float top() {
        return height + posY;
    }

    public float bottom() {
        return posY;
    }

    public float left() {
        return posX;
    }

    public float right() {
        return posX + width;
    }

    private void setupPos() {
        switch (type) {
            case LEFT: {
                posX = 0f;
                posY = windowHeight / 2f - height / 2;
                break;
            }
            case RIGHT: {
                posX = windowWidth - width;
                posY = windowHeight / 2f - height / 2;
                break;
            }
            case UPPER: {
                posX = windowWidth / 2f - width / 2;
                posY = windowHeight - height;
                break;
            }
            case LOWER: {
                posX = windowWidth / 2f - width / 2f;
                posY = 0f;
                break;
            }
        }
    }

    public void draw(Batch batch) {
        batch.draw(texture, posX, posY, width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}
