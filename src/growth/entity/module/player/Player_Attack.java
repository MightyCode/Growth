package growth.entity.module.player;

import growth.entity.Eobject.Emoveable;
import growth.entity.module.Module;
import growth.entity.type.Player;
import growth.screen.GameManager;
import growth.screen.render.shape.ShapeRenderer;
import growth.util.math.Color4;
import growth.util.math.Vec2;

public class Player_Attack extends Module {

    private int attackTime;

    private boolean isAttack;

    private Vec2 attackSize;
    private Vec2 attackPos;

    private int counter;

    public Player_Attack(Emoveable entity, int attackTime, Vec2 attackSize) {
        super(entity);
        this.attackTime = attackTime;
        this.attackSize = attackSize;
        attackPos = new Vec2();
    }

    public void update() {
        super.update();
        if(isAttack){
            if(counter > attackTime){
                isAttack = false;
                counter = 0;
                return;
            }

            entity.setAnimations(Player.ATTACK, Player.ATTACK_P);
            Vec2 speed = entity.getSpeed();
            entity.setSpeed(new Vec2(speed.getX()*0.7f,speed.getY()*0.9f));
            counter++;
        } else {
            if(GameManager.inputsManager.inputPressed(8)) isAttack = true;
        }
        attackPos.setY(entity.getPos().getY() - entity.getSize().getY()*0.5f);
        if(entity.getFacing()){
            attackPos.setX(entity.getPos().getX() - entity.getSize().getX()*0.1f);
        } else{
            attackPos.setX(entity.getPos().getX() - entity.getSize().getX()*0.9f);
        }
    }

    public void display() {
        if(isAttack){
            ShapeRenderer.rect(attackPos, attackSize, new Color4(0.1f,0.1f,0.1f,0.5f));
        }
    }
}
