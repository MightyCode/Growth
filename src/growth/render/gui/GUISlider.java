package growth.render.gui;

import growth.inputs.MouseManager;
import growth.render.shape.ShapeRenderer;
import growth.render.text.FontFace;
import growth.render.text.FontRenderer;
import growth.util.math.Color4;
import growth.util.math.Math;
import growth.util.math.Vec2;

public class GUISlider extends GUIComponent{

    /**
     * The texture use by the check button.
     */
   // private Texture uncheck, uncheck_hover, check, check_hover;

    /**
     * The colors of text of the check box.
     */
    private Color4 textColor;
    private Color4 hoverTextColor;

    /**
     * The colors of the button.
     */
    private Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
    private Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.5f);

    protected int value;

    /**
     * The font renderer use by the check box.
     */
    private FontRenderer fontRenderer;

    protected float slideSizeX, slideSizeY;
    protected float slidePosX, slidePosY;

    protected float minValue, maxValue;

    protected int oldSizeText;

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
        /*uncheck = new Texture("/textures/menu/GUICheckBox-uncheck.png");
        uncheck_hover = new Texture("/textures/menu/GUICheckBox-uncheckHover.png");
        check = new Texture("/textures/menu/GUICheckBox-check.png");
        check_hover = new Texture("/textures/menu/GUICheckBox-checkHover.png");*/

        this.textColor = textColor;
        this.hoverTextColor = hoverTextColor;

        fontRenderer = new FontRenderer(number, font, size.getY()*0.4f, pos, textColor);
        fontRenderer.setPos(new Vec2(this.pos.getX() + size.getX()/2, this.pos.getY()+size.getX()*0.1f));
        fontRenderer.setText(fontRenderer.getText()+value);
        oldSizeText = fontRenderer.getText().length();

        this.minValue = minValue;
        this.maxValue = maxValue;

        this.value = value;
        slideSizeX = slideSizeY = this.size.getY()*0.3f;
        slidePosX = Math.map(value,minValue,maxValue,this.pos.getX()+this.size.getX()*0.1f-slideSizeX/2,this.pos.getX()+ this.size.getX()*0.9f - slideSizeX/2);
        slidePosY = this.pos.getY() +  this.size.getY()*0.7f - slideSizeY/2;
    }

    /**
     * Update the button.
     */
    public void update() {
        if(oldSizeText == fontRenderer.getText().length()){
            fontRenderer.setText(fontRenderer.getText().substring(0,fontRenderer.getText().length() - String.valueOf(value).length()));
            value = (int)Math.map(slidePosX,
                    this.pos.getX()+this.size.getX()*0.1f - slideSizeX/2,
                    this.pos.getX()+ this.size.getX()*0.9f - slideSizeX/2,minValue,maxValue);
        }
        fontRenderer.setText(fontRenderer.getText() + value);

        oldSizeText = fontRenderer.getText().length();
        if (lock) return;

        mouseOver = mouseOver();
        if (mouseOver && MouseManager.button(0)) {
            slidePosX = MouseManager.mouseX() - slideSizeX/2;
            if(MouseManager.mouseX() < pos.getX() + size.getX()*0.1f){
                slidePosX = pos.getX() + size.getX()*0.1f - slideSizeX/2;
            }else if(MouseManager.mouseX() > pos.getX() + size.getX()*0.9f){
                slidePosX = pos.getX() + size.getX()*0.9f - slideSizeX/2;
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
            ShapeRenderer.rectC(pos, size, backgroundColor);
            ShapeRenderer.rectC(new Vec2(pos.getX()+size.getX()*0.1f, pos.getY()+ size.getY()*0.7f), new Vec2(size.getX()*0.8f, 4), backgroundColor);
            ShapeRenderer.rectC(new Vec2(slidePosX,slidePosY),new Vec2(slideSizeX,slideSizeY), hoverColor);
            fontRenderer.render();
        } else {
            ShapeRenderer.rectC(pos, size, hoverColor);
            ShapeRenderer.rectC(new Vec2(pos.getX()+size.getX()*0.1f, pos.getY()+ size.getY()*0.7f), new Vec2(size.getX()*0.8f, 4), hoverColor);
            ShapeRenderer.rectC(new Vec2(slidePosX,slidePosY),new Vec2(slideSizeX,slideSizeY), hoverColor);
            fontRenderer.render();
        }

        fontRenderer.render();
    }

    /**
     * Free the memory.
     */
    public void unload(){
        /*uncheck.unload();
        uncheck_hover.unload();
        check.unload();
        check_hover.unload();*/
        fontRenderer.unload();
    }
}
