package growth.inputs;

import growth.screen.ScreenManager;

public class InputManager {
    private int[] type;
    private int[] inputs;

    public InputManager(int[][] input){
        inputs = new int[input.length];
        type = new int[input.length];
        for(int i = 0; i < input.length; i++){
            if(input[i][0] == -1){
                inputs[i] = input[i][1];
                type[i] = 1;
            } else {
                inputs[i] = input[i][0];
                type[i] = 0;
            }
        }
    }

    /**
     * Return the state of key called.
     *
     * @param inputs Inputs's ID.
     *
     * @return State of the key.
     */
    public boolean input(int inputs){
        if(type[inputs] == 0) return KeyboardManager.key(this.inputs[inputs]);
        else return MouseManager.button(this.inputs[inputs]);
    }

    /**
     * Test if the key has just been pressed.
     *
     * @param inputs Inputs's ID.
     * @return boolean
     */
    public boolean inputPressed(int inputs){
        if(type[inputs] == 0) return ScreenManager.keyboardManager.keyPressed(this.inputs[inputs]);
        else return ScreenManager.mouseManager.mousePressed(this.inputs[inputs]);
    }

    /**
     * Test if the key has just been released.
     *
     * @param inputs Iy's ID.
     * @return boolean
     */
    public boolean inputReleased(int inputs){
        if(type[inputs] == 0) return ScreenManager.keyboardManager.keyReleased(this.inputs[inputs]);
        else return ScreenManager.mouseManager.mouseReleased(this.inputs[inputs]);
    }
}
