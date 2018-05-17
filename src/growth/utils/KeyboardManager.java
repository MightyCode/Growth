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
     * This class contains the number of input in a keyboard.
     */
    private static final int INPUTS = 512;

    /**
     * Keys state.
     * This class contains the state of every keys.
     */
    private boolean[] state = new boolean[INPUTS];

    /**
     * Temp keys state.
     * This class contains the state of every keys in the previous frame.
     */
    private boolean[] tempState = new boolean[INPUTS];

    /**
     * Keyboard manager class.
     * Instance the class
     */
    public KeyboardManager(){
        for(int i = 0; i < INPUTS; i++) {
            state[i] = false;
            tempState[i] = false;
        }
    }

    /**
     * Return the state of key called.
     *
     * @param keyID Key's ID.
     *
     * @return state of the key.
     */
    public static boolean key(int keyID){
        return glfwGetKey(Window.WINDOWID, keyID) == 1;
    }

    /**
     * Test if the key has just been pressed.
     *
     * @param keyID Key's ID.
     * @return boolean
     */
    public boolean keyPressed(int keyID){
        tempState[keyID] = state[keyID];
        state[keyID] = glfwGetKey(Window.WINDOWID, keyID) == 1;

        return (state[keyID] && !tempState[keyID]);
    }

    /**
     * Test if the key has just been released.
     *
     * @param keyID Key's ID.
     * @return boolean
     */
    public boolean keyReleased(int keyID){
        tempState[keyID] = state[keyID];
        state[keyID] = glfwGetKey(Window.WINDOWID, keyID) == 1;

        return (!state[keyID] && tempState[keyID]);
    }
}
