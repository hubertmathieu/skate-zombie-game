package engine;


import java.awt.event.KeyEvent;

public class MovementController extends Controller {

    private int upKey = KeyEvent.VK_UP;
    private int downKey = KeyEvent.VK_DOWN;
    private int leftKey = KeyEvent.VK_LEFT;
    private int rightKey = KeyEvent.VK_RIGHT;

    public MovementController() {
        register();
    }

    public boolean isLeftPressed() {
        return super.isKeyPressed(leftKey);
    }

    public boolean isRightPressed() {
        return super.isKeyPressed(rightKey);
    }

    public boolean isDownPressed() {
        return super.isKeyPressed(downKey);
    }

    public boolean isUpPressed() {
        return super.isKeyPressed(upKey);
    }

    public boolean isMoving() {
        return isDownPressed() || isLeftPressed() || isRightPressed() || isUpPressed();
    }

    public void setMovementKeys(int upKey, int downKey, int rightKey, int leftKey) {
        this.upKey = upKey;
        this.downKey = downKey;
        this.rightKey = rightKey;
        this.leftKey = leftKey;
        super.resetKeys();
        register();
    }

    public void register() {
        int[] keys = {upKey, downKey, leftKey, rightKey};
        super.registerKeys(keys);
    }

}
