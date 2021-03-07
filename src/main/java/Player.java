import engine.Buffer;
import engine.CollidableRepository;
import engine.ContrallableGameEntity;
import engine.MovementController;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends ContrallableGameEntity {


    public int life = 100;
    public int kills = 24;
    public String currentGun = "Pistol";
    private final int SHUTGUN = 12;
    private final int AUTO = 32;
    private static final String SPRITE_PATH = "PC Computer - Zombie Land - Player.png";
    private static final int ANIMATION_SPEED = 20;
    private BufferedImage spriteSheet;
    private BufferedImage[] frames;
    private int currentAnimationFrame = 0;
    private int nextFrame = ANIMATION_SPEED;
    private int cooldown = 0;

    public Player(MovementController controller, int x, int y) {
        super(controller);
        this.x = x;
        this.y = y;
        super.setDimension(60,60);
        loadSpriteSheet();
        loadAnimationFrames();
        CollidableRepository.getInstance().registerEntity(this);

    }

    public void update() {
        super.moveAccordingToHandlerPlayer();
        updatCoolDown();
        nextFrame--;
        if (kills == SHUTGUN) {
            currentAnimationFrame = 2;
            currentGun = "Shot gun";
        }
        if (kills == AUTO) {
            currentAnimationFrame = 7;
            currentGun = "Automatic gun";
        }
        if (nextFrame == 0) {
            nextFrame = ANIMATION_SPEED;
            if (currentAnimationFrame == 3) {
                currentAnimationFrame = 2;
            }
            if (currentAnimationFrame == 6) {
                currentAnimationFrame = 7;
            }
        }
    }

    public void draw(Buffer buffer) {
        buffer.drawImage(frames[currentAnimationFrame], x, y);
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(this.getClass().getResource(SPRITE_PATH));
        } catch (IOException ex) {}
    }

    private void loadAnimationFrames() {
        frames = new BufferedImage[8];

        frames[0] = spriteSheet.getSubimage(1, 1, 72, 72);
        frames[1] = spriteSheet.getSubimage(1, 75, 72, 72);
        frames[2] = spriteSheet.getSubimage(1, 149, 72, 72);
        frames[3] = spriteSheet.getSubimage(1, 223, 72, 72);
        frames[4] = spriteSheet.getSubimage(1, 296, 72, 72);
        frames[5] = spriteSheet.getSubimage(1 , 370, 72, 72);
        frames[6] = spriteSheet.getSubimage(1, 445, 72, 72);
        frames[7] = spriteSheet.getSubimage(1, 519, 72, 72);

    }

    public boolean canFire() {
        return cooldown == 0;
    }

    public Bullet fire() {
        nextFrame = 7;
        cooldown = 30;
        if (kills >= SHUTGUN) {
            cooldown = 50;
            currentAnimationFrame = 3;
        }
        if (kills >= AUTO) {
            cooldown = 10;
            currentAnimationFrame = 6;
        }
        return new Bullet(this);
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    private void updatCoolDown() {
        cooldown --;
        if (cooldown <= 0) {
            cooldown = 0;
        }
    }
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
