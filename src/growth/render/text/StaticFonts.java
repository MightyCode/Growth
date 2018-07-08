package growth.render.text;

public class StaticFonts {

    public static FontFace IBM = new FontFace("IBM");
    public static FontFace IBM_Stretch = new FontFace("IBM_stretch");
    public static FontFace monofonto = new FontFace("monofonto");

    public static void unload(){
        IBM.unload();
        IBM_Stretch.unload();
        monofonto.unload();
    }
}
