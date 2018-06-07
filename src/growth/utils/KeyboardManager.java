package growth.utils;

import growth.main.Window;

import static org.lwjgl.glfw.GLFW.glfwGetKey;

/**
 * This class is the keyboard manager.
 *
 * @author MightyCode
 * @version 1.0
 */
public class KeyboardManager {

    /**
     * Input number.
     * This variable contains the number of input in a keyboard.
     */
    private static final int INPUTS = 512;

    /**
     * Number of key use.
     * This variable contains the number of key use on the game.
     */
    private static final int KEYUSE = 10;
    /**
     * Table of key input.
     * This variable contains all of the keys use in the game.
     * This system is use to do a changeable keys system.
     */
    private final int[] keys;

    /**
     * Keys state.
     * This class contains the state of every keys.
     */
    private final boolean[] state = new boolean[INPUTS];

    /**
     * Temp keys state.
     * This class contains the state of every keys in the previous frame.
     */
    private final boolean[] tempState = new boolean[INPUTS];

    /**
     * Keyboard manager class.
     * Instance the class
     */
    public KeyboardManager(){
        for(int i = 0; i < INPUTS; i++) {
            state[i] = false;
            tempState[i] = false;
        }

        keys = new int[KEYUSE];
    }

    /**
     * Set the key use the a action on the game.
     * @param keyNumber The number of the key.
     * @param value The value give per GWJGL_KEY_yourkey.
     */
    public void setKey(int keyNumber, int value){
        keys[keyNumber] = value;
    }

    /**
     * Return the state of key called.
     *
     * @param keyID Key's ID.
     *
     * @return State of the key.
     */
    public boolean key(int keyID){
        return glfwGetKey(Window.WINDOWID, keys[keyID]) == 1;
    }

    /**
     * Test if the key has just been pressed.
     *
     * @param keyID Key's ID.
     * @return boolean
     */
    public boolean keyPressed(int keyID){
        tempState[keys[keyID]] = state[keys[keyID]];
        state[keys[keyID]] = glfwGetKey(Window.WINDOWID, keys[keyID]) == 1;

        return (state[keys[keyID]] && !tempState[keys[keyID]]);
    }

    /**
     * Test if the key has just been released.
     *
     * @param keyID Key's ID.
     * @return boolean
     */
    public boolean keyReleased(int keyID){
        tempState[keys[keyID]] = state[keys[keyID]];
        state[keys[keyID]] = glfwGetKey(Window.WINDOWID, keys[keyID]) == 1;

        return (!state[keys[keyID]] && tempState[keys[keyID]]);
    }
}