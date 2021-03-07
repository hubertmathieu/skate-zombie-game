import engine.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Zombie extends ContrallableGameEntity {

    private static final String SPRITE_PATH = "PC Computer - Zombie Land - Zombie.png";
    private static final int ANIMATION_SPEED = 20;
    private BufferedImage spriteSheet;
    private BufferedImage[] frames;
    private int currentAnimationFrame = 0;
    private int nextFrame = ANIMATION_SPEED;
    public int cooldown = 1;
    public int life = 2;



    public Zombie(MovementController controller, int x, int y) {
        super(controller);
        super.setDimension(50, 50);
        super.teleport(x, y);
        CollidableRepository.getInstance().registerEntity(this);
        loadSpriteSheet();
        loadAnimationFrames();
    }

    @Override
    public void update() {
        cooldown--;
        --nextFrame;
        if (nextFrame == 0) {
            currentAnimationFrame = 0;
            nextFrame = 15;
        }
        if (cooldown < 0) {
            cooldown = 1;
        }
        if (currentAnimationFrame == 3) {
            setSpeed(0);
        } else {
            setSpeed(3);
        }
    }

    @Override
    public void draw(Buffer buffer) {
        if (x < -10) {
            x = 800;
        }
        buffer.drawImage(frames[currentAnimationFrame], x, y);
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(this.getClass().getResource(SPRITE_PATH));
        } catch (IOException ex) {}
    }

    private void loadAnimationFrames() {
        frames = new BufferedImage[8];

        frames[0] = spriteSheet.getSubimage(1, 1, 56, 70);
        frames[1] = spriteSheet.getSubimage(0, 72, 57, 71);
        frames[2] = spriteSheet.getSubimage(0, 144, 57, 71);
        frames[3] = spriteSheet.getSubimage(0, 215, 57, 71);
        frames[4] = spriteSheet.getSubimage(0, 286, 57, 71);
    }

    public void moveAcordingToPlayer(int playerX, int playerY) {

         setSpeed(4);
        if (playerY > y ) {
            moveDown();
        }
        if (playerY < y) {
            moveUp();
        }
        moveLeft();
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
        getFrame();
    }

    public void getFrame() {
        currentAnimationFrame = 3;
    }
}
