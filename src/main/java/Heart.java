import engine.Buffer;
import engine.CollidableRepository;
import engine.GameEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Heart extends GameEntity {

    private static final String MAP_PATH_PRIMARY = "Wii - Rayman Origins - Heart.png";
    private BufferedImage sprite;

    public Heart(int x, int y) {
        super.setDimension(40, 40);
        super.teleport(x, y);
        CollidableRepository.getInstance().registerEntity(this);
        loadImage();
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(sprite, x, y);
    }

    private void loadImage() {
        try {
            sprite= ImageIO.read(this.getClass().getResource(MAP_PATH_PRIMARY));
        } catch (IOException ex) {}
    }

    public void setMouvement(boolean asStopped) {
        if (!asStopped) {
            x --;
        }
    }
}
