package growth.game.entity.module;

import growth.game.entity.type.Entity;

/**
 * Mother module class.
 * This class is the mother class use by every other child module.
 *
 *  @author MightyCode
 *  @version 1.0
 */
public abstract class Module {

    /**
     * Entity.
     * This variable contains the reference to the entity who use this module.
     */
    protected final Entity entity;

    /**
     * Mother module class constructor.
     * Instance the class and set the reference to the entity.
     *
     * @param entity Entity using the module.
     */
    protected Module(Entity entity) {
        this.entity = entity;
    }

    /**
     * Update the entity with the utility of the module.
     */
    public void update(){}

    /**
     * Display the entity with the utility of the module.
     */
    public void display(){}
}
