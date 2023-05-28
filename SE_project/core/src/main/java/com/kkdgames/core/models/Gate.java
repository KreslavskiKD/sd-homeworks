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
    private final float offset;
    private final float scale;

    private final float width;
    private final float height;

    private float posX;
    private float posY;

    public Gate(Texture texture, Type type, float windowWidth, float windowHeight, float offset) {
        this.texture = texture;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.offset = offset;
        this.scale = windowWidth / 10 / texture.getWidth();
        width = texture.getWidth();
        height = texture.getHeight();
        setPos(type);
    }

    private void setPos(Type type) {
        switch (type) {
            case LEFT: {
                posX = offset;
                posY = windowHeight / 2 - height * scale / 2;
            }
            case RIGHT: {
                posX = windowWidth - width - offset;
                posY = windowHeight / 2 - height * scale / 2;
            }
            case UPPER: {
                posX = windowWidth / 2 - width * scale / 2;
                posY = windowHeight - height;
            }
            case LOWER: {
                posX = windowWidth / 2 - texture.getWidth() * scale / 2;
                posY = 0f;
            }
        }
    }

    public void draw(Batch batch) {
        batch.draw(texture, posX, posY, width * scale, height * scale);
    }

    public void dispose() {
        texture.dispose();
    }
}
