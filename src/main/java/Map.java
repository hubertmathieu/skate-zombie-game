import engine.Buffer;
import engine.GameEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Map extends GameEntity {

    private static final String MAP_PATH_PRIMARY = "untitled.png";
    private BufferedImage spriteMapPrimary;
    private boolean desapear = false;

    public void initialize(int speed) {
        loadMap();
        x = 0;
        y = 0;
    }


    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(spriteMapPrimary, x, y - 15);
    }

    public void loadMap() {
        try {
            spriteMapPrimary = ImageIO.read(this.getClass().getResource(MAP_PATH_PRIMARY));
        } catch (IOException ex) {}
    }


    public void setMouvement(boolean asStopped) {
        if (!asStopped) {
            x --;
        }

    }
}
