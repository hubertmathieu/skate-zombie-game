import engine.Buffer;
import engine.CollidableRepository;
import engine.Direction;
import engine.MovableGameEntity;
import java.awt.*;

public class Bullet extends MovableGameEntity {

    private Direction playerDirection;

    public Bullet(Player player) {
        playerDirection = player.getDirection();
        setSpeed(20);
        super.teleport(player.getX() + player.getWidth() + 0, player.getY() +40);
        super.setDimension(8, 4);
/*
        if (playerDirection == Direction.RIGHT) {
            super.teleport(player.getX() + player.getWidth() + 1, player.getY() + 15 - 2);
            super.setDimension(8, 4);
        } else if (playerDirection == Direction.LEFT) {
            super.teleport(player.getX() - 9, player.getY() + 15 - 2);
            super.setDimension(8, 4);
        } else if (playerDirection == Direction.DOWN) {
            super.teleport(player.getX() + 15 -2, player.getY() + player.getHeight() + 1);
            super.setDimension(4,8);
        } else if (playerDirection == Direction.UP) {
            super.teleport(player.getX() + 15 -2, player.getY() - 9);
            super.setDimension(4, 8);
        }*/

        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        move(Direction.RIGHT);
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectabgle(x, y, width, height, Color.YELLOW);
    }
}
