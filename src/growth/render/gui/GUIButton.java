package growth.render.gui;

import growth.math.Color4;
import growth.math.Vec2;
import growth.render.shape.ShapeRenderer;
import growth.render.text.FontFace;
import growth.render.text.FontRenderer;
import growth.screen.ScreenManager;

import static org.lwjgl.glfw.GLFW.*;

/**
 * GUIButton class.
 * This class is the structure of the click button.
 * The button have two state, one for mouse's hover and one for not.
 *
 * @author MightyCode
 * @version 1.0
 */
public class GUIButton extends GUIComponents{

    private String text;
    private FontFace font;

    private Color4 backgroundColor;
    private Color4 hoverColor;
    private Color4 textColor;
    private Color4 hoverTextColor;

    private FontRenderer fontRenderer;

    private boolean mouseOver;

    public GUIButton(Vec2 pos, Vec2 size, String text, FontFace font, Color4 backgroundColor, Color4 hoverColor, Color4 textColor, Color4 hoverTextColor) {
        super(new Vec2(pos.getX() - (size.getX() / 2), pos.getY() - (size.getY() / 2)),size);
        this.text = text;
        this.font = font;
        this.backgroundColor = backgroundColor;
        this.hoverColor = hoverColor;
        this.textColor = textColor;
        this.hoverTextColor = hoverTextColor;

        fontRenderer = new FontRenderer(text, font, size.getY() - 6, pos, textColor);
        fontRenderer.setPos(new Vec2(this.pos.getX() + (size.getX() / 2) - (fontRenderer.getWidth() / 2f), this.pos.getY()));
    }

    /**
     * Update the button.
     */
    public void update() {
        mouseOver = mouseOver();

        if(mouseOver){
            fontRenderer.setColor(hoverTextColor);
            if(ScreenManager.mouseManager.mousePressed(GLFW_MOUSE_BUTTON_LEFT)) {
                action();
            }
        } else{
            fontRenderer.setColor(textColor);
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
     * Free the memory.
     */
    public void unload(){

    }

    public void setPos(Vec2 pos) {
        this.pos = new Vec2(pos.getX() - (size.getX() / 2), pos.getY() - (size.getY() / 2));
        fontRenderer.setPos(new Vec2(this.pos.getX() + (size.getX() / 2) - (fontRenderer.getWidth() / 2), this.pos.getY() + 3));
    }
}