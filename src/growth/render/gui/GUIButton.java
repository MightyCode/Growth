package growth.render.gui;

import growth.math.Color4;
import growth.math.Vec2;
import growth.render.shape.ShapeRenderer;
import growth.render.text.FontFace;
import growth.render.text.FontRenderer;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.ScreenManager;
import growth.screen.overlay.Overlay;
import growth.screen.screens.Screen;
import growth.inputs.MouseManager;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * GUIButton class.
 * This class is the structure of the click button.
 * The button have two state, one for mouse's hover and one for not.
 *
 * @author MightyCode
 * @version 1.0
 */
public class GUIButton {

    private Vec2 pos;
    private Vec2 size;

    private String text;
    private FontFace font;

    private Color4 backgroundColor;
    private Color4 hoverColor;
    private Color4 textColor;
    private Color4 hoverTextColor;

    private FontRenderer fontRenderer;

    private boolean mouseOver;

    public GUIButton(Vec2 pos, Vec2 size, String text, FontFace font, Color4 backgroundColor, Color4 hoverColor, Color4 textColor, Color4 hoverTextColor) {
        this.pos = new Vec2(pos.getX() - (size.getX() / 2), pos.getY() - (size.getY() / 2));
        this.size = size;
        this.text = text;
        this.font = font;
        this.backgroundColor = backgroundColor;
        this.hoverColor = hoverColor;
        this.textColor = textColor;
        this.hoverTextColor = hoverTextColor;

        fontRenderer = new FontRenderer(text, font, size.getY() - 6, pos, textColor);
        fontRenderer.setPos(new Vec2(this.pos.getX() + (size.getX() / 2) - (fontRenderer.getWidth() / 2), this.pos.getY() + 3));
    }

    /**
     * Test if the mouse is over the button.
     */
    private boolean mouseOver() {
        return  (MouseManager.mouseX() > pos.getX() &&
                MouseManager.mouseX() < pos.getX() + size.getX()) &&
                (MouseManager.mouseY() > pos.getY() &&
                MouseManager.mouseY() < pos.getY() + size.getY());
    }

    /**
     * Update the button.
     */
    public void update() {
        mouseOver = mouseOver();

        if (mouseOver) {
            fontRenderer.setColor(hoverTextColor);
        } else {
            fontRenderer.setColor(textColor);
        }

        if(mouseOver && ScreenManager.mouseManager.mousePressed(GLFW_MOUSE_BUTTON_LEFT)){
            action();
        }
    }

    /**
     * Display the button.
     */
    public void display() {
        if (!mouseOver) {
            ShapeRenderer.rectC(pos, size, backgroundColor);
            fontRenderer.render();
        } else {
            ShapeRenderer.rectC(pos, size, hoverColor);
            fontRenderer.render();
        }
    }

    /**
     * Override class for what does the button.
     */
    protected void action() {}

    /**
     * Free the memory.
     */
    public void unload(){

    }

    public void setPos(Vec2 pos) {
        this.pos = new Vec2(pos.getX() - (size.getX() / 2), pos.getY() - (size.getY() / 2));
        fontRenderer.setPos(new Vec2(this.pos.getX() + (size.getX() / 2) - (fontRenderer.getWidth() / 2), this.pos.getY() + 3));
    }
}