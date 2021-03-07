import engine.Buffer;
import engine.CollidableRepository;
import engine.GameEntity;

import java.awt.*;

public class Brick extends GameEntity {

    public Brick(int x, int y) {
        super.setDimension(800, 1);
        super.teleport(x, y);
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void draw(Buffer buffer) {

    }
}
