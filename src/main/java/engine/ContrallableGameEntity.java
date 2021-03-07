package engine;

public abstract class ContrallableGameEntity extends MovableGameEntity {

    protected final MovementController controller;

    public ContrallableGameEntity(MovementController controller) {
        this.controller = controller;
    }

    public void moveAccordingToHandlerPlayer() {
        if (!controller.isMoving()) {
            return;
        }
        if (controller.isDownPressed()) {
            moveDown();
        } else if (controller.isLeftPressed()) {
           // moveLeft();
        } else if (controller.isRightPressed()) {
            //moveRight();
        } else if (controller.isUpPressed()) {
            moveUp();
        } else {

        }
    }


}
