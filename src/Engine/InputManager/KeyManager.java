package Engine.InputManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    boolean[] Keys;

    boolean
        isRight = false,
        isLeft = false,
        isDown = false,
        isUp = false;
    public KeyManager() {
        Keys = new boolean[256];
    }
    public void tick() {
        isUp = Keys[KeyEvent.VK_W];
        isDown = Keys[KeyEvent.VK_S];
        isLeft = Keys[KeyEvent.VK_A];
        isRight = Keys[KeyEvent.VK_D];
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
    public boolean getKey(int code) {
        return Keys[code];
    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Keys[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        Keys[keyEvent.getKeyCode()] = false;
    }
}
