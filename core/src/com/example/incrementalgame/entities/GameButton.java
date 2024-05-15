package com.example.incrementalgame.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class GameButton {
    private Rectangle bounds; //defining position and size of the button
    private String text; //text in the button
    private GlyphLayout glyphLayout; //calcs text width and height

    public GameButton(float x, float y, float width, float height, String text) {
        this.bounds = new Rectangle(x, y, width, height);
        this.text = text;
        this.glyphLayout = new GlyphLayout();
    }

    //used to update text dynamically if necessary
    public void setText(String newText) {
        this.text = newText;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    //drawing the button
    public void draw(SpriteBatch batch, BitmapFont font, Texture texture) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);

        glyphLayout.setText(font, text);
        float textWidth = glyphLayout.width;
        // float textHeight = glyphLayout.height;

        float maxTextWidth = bounds.width * 0.8f; //calc max text width; button width - 20% padding

        //adjusts font scale if text is too wide
        if (textWidth > maxTextWidth) {
            font.getData().setScale(maxTextWidth / textWidth);
            glyphLayout.setText(font, text); //recalcs text  with new scale
        } else {
            font.getData().setScale(1.0f); //reset font scale if text fits
        }

        float textX = bounds.x + (bounds.width - glyphLayout.width) / 2;
        float textY = bounds.y + (bounds.height + glyphLayout.height) / 2;

        font.draw(batch, glyphLayout, textX, textY);
        font.getData().setScale(1.0f); // Reset font scale to default after drawing
    }
}
