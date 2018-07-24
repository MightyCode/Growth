package growth.render.gui;

import growth.inputs.MouseManager;
import growth.render.text.FontFace;
import growth.render.text.FontRenderer;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.util.math.Color4;
import growth.util.math.Math;
import growth.util.math.Vec2;

public class GUISlider extends GUIComponent{

    /**
     * The texture use by the check button.
     */
    private Texture slider, sliderHover, sliderButton, sliderButtonHover;

    /**
     * The colors of text of the check box.
     */
    private Color4 textColor;
    private Color4 hoverTextColor;

    /**
     * The colors of the slider.
     */

    protected float value;

    /**
     * The font renderer use by the check box.
     */
    private FontRenderer fontRenderer;

    private Vec2 cursorSize, cursorPos;

    private float minValue, maxValue;

    private int oldSizeText;


    /**
     * GUICheck box class constructor with the number of the global string.
     *
     * @param pos Position of the button.
     * @param size Size of the button.
     * @param number The number of the global string to display.
     * @param font The font use by its font renderer.
     * @param textColor The normal text color.
     * @param hoverTextColor The color of the text when the button is hovering.
     */
    public GUISlider(Vec2 pos, Vec2 size, int number, FontFace font, Color4 textColor, Color4 hoverTextColor, float minValue, float maxValue, int value){
        super(new Vec2(pos.getX() - (size.getX() / 2), pos.getY() - (size.getY() / 2)),size);
        // Loading texture
        slider = new Texture("/textures/menu/GUISlider.png");
        sliderHover = new Texture("/textures/menu/GUISlider-hover.png");
        sliderButton = new Texture("/textures/menu/GUISliderButton.png");
        sliderButtonHover = new Texture("/textures/menu/GUISliderButton-hover.png");

        this.textColor = textColor;
        this.hoverTextColor = hoverTextColor;

        fontRenderer = new FontRenderer(number, font, size.getY()*0.4f, pos, textColor);
        fontRenderer.setPos(new Vec2(this.pos.getX() + size.getX()/2, this.pos.getY()+size.getX()*0.1f));
        fontRenderer.setText(fontRenderer.getText()+value);
        oldSizeText = fontRenderer.getText().length();

        this.minValue = minValue;
        this.maxValue = maxValue;

        this.value = value;
        cursorSize = new Vec2(this.size.getY()*0.30f);
        cursorPos = new Vec2();
        cursorPos.setX(Math.map(value,minValue,maxValue,
                this.pos.getX()+this.size.getX()*0.1f - cursorSize.getX()/2f,this.pos.getX()+ this.size.getX()*0.9f - cursorSize.getX()/2f));
        cursorPos.setY(this.pos.getY() +  this.size.getY()*0.7f - cursorSize.getY()/2);
    }

    /**
     * Update the button.
     */
    public void update() {
        if(oldSizeText == fontRenderer.getText().length()){
            fontRenderer.setText(fontRenderer.getText().substring(0,fontRenderer.getText().length() - String.valueOf((int)value).length()));
            value = Math.map(cursorPos.getX(),
                    this.pos.getX()+this.size.getX()*0.1f - cursorSize.getX()/2,
                    this.pos.getX()+ this.size.getX()*0.9f - cursorSize.getX()/2,minValue,maxValue);
        }
        fontRenderer.setText(fontRenderer.getText() + (int)value);

        oldSizeText = fontRenderer.getText().length();
        if (lock) return;

        mouseOver = mouseOver();
        if (mouseOver && MouseManager.button(0)) {
            cursorPos.setX(MouseManager.mouseX() - cursorSize.getX()/2);
            if(MouseManager.mouseX() < pos.getX() + size.getX()*0.1f){
                cursorPos.setX(pos.getX() + size.getX()*0.1f - cursorSize.getX()/2);
            }else if(MouseManager.mouseX() > pos.getX() + size.getX()*0.9f){
                cursorPos.setX(pos.getX() + size.getX()*0.9f - cursorSize.getX()/2);
            }
            action();
        }
    }

    /**
     * Display the button.
     */
    public void display() {
        //TextureRenderer.imageC(pos,size,1f);
        if (!mouseOver) {
            slider.bind();
            TextureRenderer.imageC(pos,size);
            sliderButton.bind();
        } else {
            sliderHover.bind();
            TextureRenderer.imageC(pos,size);
            sliderButtonHover.bind();
        }
        TextureRenderer.imageC(cursorPos, cursorSize);
        fontRenderer.render();
    }

    /**
     * Free the memory.
     */
    public void unload(){
        slider.unload();
        sliderHover.unload();
        sliderButton.unload();
        sliderButtonHover.unload();
        fontRenderer.unload();
    }
}
