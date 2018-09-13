package growth.entity.methods;

import growth.entity.Eobject.Eobject;
import growth.util.math.Vec2;

public class EntityMethods {
    public static boolean isCollid(Vec2 pos, Vec2 size, Eobject entity){
        Vec2 pos2 = entity.getPos();
        if(pos2.getX() > pos.getX() && pos2.getX() < pos.getX()+ size.getX()){
            return pos2.getY() > pos.getY() && pos2.getY() < pos.getY() + size.getY();
        }
        return false;
    }

    public static boolean isCollidc(Vec2 pos, Vec2 size, Eobject entity){
        Vec2 pos2 = entity.getPos();
        Vec2 size2 = entity.getSize();
        if(pos2.getX() + size2.getX()/2 > pos.getX() && pos2.getX() - size2.getX()/2 < pos.getX()+ size.getX()){
            return pos2.getY() > pos.getY() && pos2.getY() < pos.getY() + size.getY();
        }
        return false;
    }
}
