package engine;

import java.awt.*;

public abstract class MovableGameEntity extends GameEntity {

    private Direction direction = Direction.UP;
    public int speed = 10;
    private final Collision collision;
    private boolean moved;

    public MovableGameEntity() {
        collision = new Collision(this);
    }

    public abstract void update();

    public void move(Direction direction) {
        this.direction = direction;
        int allowedSpeed = collision.getAllowedSpeed(direction);
        x += direction.getVelocityX(allowedSpeed);
        y += direction.getVelocityY(allowedSpeed);

    }


    public void moveUp() {
        move(Direction.UP);
    }

    public void moveDown() {
        move(Direction.DOWN);
    }

    public void moveLeft() {
        move(Direction.LEFT);
    }

    public void moveRight() {
        move(Direction.RIGHT);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean collisionBoundInterctWidth(GameEntity other) {
        if (other == null) {
            return false;
        }
        return getCollisionBound().intersects(other.getBounds());
    }

    public boolean collisionIntersectWith(GameEntity other) {
        if (other == null) {
            return false;
        }
        int oldSpeed = speed;
        boolean collision = getCollisionBound().intersects(other.getBounds());
        speed = oldSpeed;
        return collision;
    }

    protected Rectangle getCollisionBound() {
        switch (direction) {
            case UP:
                return getCollisionUpperBound();
            case DOWN:
                return getCollisionLowerBound();
            case RIGHT:
                return getCollisionRightBound();
            case LEFT:
                return getCollisionLeftBound();
            default:
                return getBounds();
        }
    }

    private Rectangle getCollisionUpperBound() {
        return new Rectangle(x, y - speed, width, speed);
    }

    private Rectangle getCollisionLowerBound() {
        return new Rectangle(x, y + height, width, speed);
    }

    private Rectangle getCollisionLeftBound() {
        return new Rectangle(x - speed, y, speed, height);
    }

    private Rectangle getCollisionRightBound() {
        return new Rectangle(x + width, y, speed, height);
    }

}
