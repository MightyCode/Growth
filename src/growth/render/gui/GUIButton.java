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

    private Color4 backgroundColor;
    private Color4 hoverColor;
    private Color4 textColor;
    private Color4 hoverTextColor;

    private FontRenderer fontRenderer;

    private boolean mouseOver;

    public GUIButton(Vec2 pos, Vec2 size, String text, FontFace font, Color4 backgroundColor, Color4 hoverColor, Color4 textColor, Color4 hoverTextColor) {
        super(pos,size);

        this.backgroundColor = backgroundColor;
        this.hoverColor = hoverColor;
        this.textColor = textColor;
        this.hoverTextColor = hoverTextColor;

        fontRenderer = new FontRenderer(text, font, size.getY() - 6, pos, textColor);
        setPos(pos);
    }

    public GUIButton(Vec2 pos, Vec2 size, int number, FontFace font, Color4 backgroundColor, Color4 hoverColor, Color4 textColor, Color4 hoverTextColor) {
        super(pos,size);

        this.backgroundColor = backgroundColor;
        this.hoverColor = hoverColor;
        this.textColor = textColor;
        this.hoverTextColor = hoverTextColor;

        fontRenderer = new FontRenderer(number, font, size.getY() - 6, pos, textColor);
        setPos(pos);
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
    public void setPos(Vec2 pos) {
        super.setPos(pos);
        fontRenderer.setPos(new Vec2(this.pos.getX() + (size.getX() / 2) , this.pos.getY()));
    }


    /**
     * Free the memory.
     */
    public void unload(){
        fontRenderer.unload();
    }
}