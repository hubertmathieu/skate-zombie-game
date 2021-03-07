import engine.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class SkateGame extends Game{
    public boolean mapStopped = false;
    private int deadZombies = 0;
    private Player player;
    private PlayerController controller;
    private Map map;
    private ArrayList<Bullet> bullets;
    private ArrayList<Brick> bricks;
    private ArrayList<Zombie> zombies;
    private ArrayList<Heart> hearts;
    ArrayList<GameEntity> killedEntities = new ArrayList<>();

    public void initialize() {
        SoundEffectFactory.makeMusic().play();
        initializeArray();
        super.addKeyListener(controller);
        player = new Player(controller,150, 250);
        map = new Map();
        map.initialize(1);
        addLimit();
        addHearts();
        addZombies();
    }



    public void update() {
        if (controller.isScreenSwitchPressed()) {
            RenderingEngine.getInstance().getScreen().toggleFullScreen();
        }
        updateFire();
        player.update();
        handleCollisionsZombie();
        checkIfLifeHittedByEntity();
        handleCollisionsBullet();
        deleteKilledEntity();
        setMapMouvement();
        if (zombies.size() == 0) {
            addZombies();
        }
        if (player.life <= 0 || map.getX() < -6900) {
            playing = false;
            conclude();
        }
    }

    public void draw(Buffer buffer) {
        map.draw(buffer);
        player.draw(buffer);
        for (Bullet bullet : bullets) {
            bullet.draw(buffer);
        }
        for (Zombie zombie : zombies) {
            zombie.draw(buffer);
        }
        for (Heart heart : hearts) {
            heart.draw(buffer);
        }
        drawHud(buffer);

    }


    public void conclude() {

    }

    private void setMapMouvement() {
        map.setMouvement(mapStopped);
        for (Heart heart : hearts) {
            heart.setMouvement(mapStopped);
        }
        mapStopped = false;
    }

    private void checkIfLifeHittedByEntity() {
        for (Heart heart : hearts) {
            if (player.collisionBoundInterctWidth(heart)) {
                player.setLife(player.getLife() + 20);
                killedEntities.add(heart);
            }
            for (Zombie zombie : zombies) {
                if (zombie.collisionBoundInterctWidth(heart)) {
                    zombie.setX(zombie.getX() - heart.getWidth());
                }
            }
        }
    }

    private void updateFire() {
        if (controller.isFirePressed() && player.canFire()) {
            bullets.add(player.fire());
            if (player.kills < 32 && player.kills > 11) {
                bullets.add(new Bullet(player));
            }
            SoundEffectFactory.makeFire().play();
        }
    }

    private void deleteKilledEntity() {
        for (GameEntity killedEntity : killedEntities) {
            if (killedEntity instanceof Bullet) {
                bullets.remove(killedEntity);
            } else if (killedEntity instanceof Zombie) {
                zombies.remove(killedEntity);
            } else if (killedEntity instanceof Heart) {
                hearts.remove(killedEntity);
            }
            CollidableRepository.getInstance().unregisterEntity(killedEntity);
        }
    }

    private void handleCollisionsBullet() {
        for (Bullet bullet : bullets) {
            bullet.update();
            for (Zombie zombie : zombies) {
                if (bullet.collisionBoundInterctWidth(zombie)) {
                    zombie.setLife(zombie.getLife() - 1);
                    SoundEffectFactory.makePain().play();
                    if (zombie.getLife() == 0) {
                        killedEntities.add(zombie);
                        deadZombies++;
                    }
                    killedEntities.add(bullet);
                }
            }
            for (Heart heart : hearts) {
                if (bullet.collisionBoundInterctWidth(heart)) {
                    bullet.setX(bullet.getX() + heart.getWidth());
                }
            }
        }
        player.setKills(deadZombies);
    }

    private void handleCollisionsZombie() {
        for (Zombie zombie : zombies) {
            zombie.moveAcordingToPlayer(player.getX(), player.getY());
            zombie.update();
            if (zombie.collisionBoundInterctWidth(player)) {
                mapStopped = true;
                if (zombie.cooldown == 0) {
                    player.life--;
                    if (!SoundEffectFactory.makeScary().isRunning()) {
                        SoundEffectFactory.makeScary().play();
                    }
                }

            }
        }
    }

    private void addZombies() {
        Random random = new Random();
        int nb = 0;
        if (player.currentGun.equals("Pistol")) {
            nb = 4;
        }
        if (player.currentGun.equals("Shot gun")) {
            nb = 6;
        }
        if (player.currentGun.equals("Automatic gun")) {
            nb = 10;
        }
        for (int z = 0; z < nb; z++) {
            int x = random.nextInt(200) + 550;
            int y = random.nextInt(170) + 125;
            zombies.add(new Zombie(controller, x, y));
        }
    }

    private void initializeArray() {
        hearts = new ArrayList<>();
        bullets = new ArrayList<>();
        controller = new PlayerController();
        bricks = new ArrayList<>();
        zombies = new ArrayList<>();
    }

    private void addLimit() {
        bricks.add(new Brick(0, 67));
        bricks.add(new Brick(0, 470));
    }

    private void addHearts() {
        hearts.add(new Heart(900, 370));
        hearts.add(new Heart(1200, 120));
        hearts.add(new Heart(1600, 260));
        hearts.add(new Heart(2000, 140));
        hearts.add(new Heart(2400, 400));
        hearts.add(new Heart(2800, 370));
        hearts.add(new Heart(3200, 240));
        hearts.add(new Heart(3800, 280));
        hearts.add(new Heart(4300, 120));
        hearts.add(new Heart(4700, 400));
        hearts.add(new Heart(5100, 370));
        hearts.add(new Heart(5700, 370));
        hearts.add(new Heart(6200, 240));
        hearts.add(new Heart(6600, 370));
        hearts.add(new Heart(900, 370));
    }

    private void drawHud(Buffer buffer) {
        buffer.drawText("Vitality : " + player.life, 30, 20, Color.RED);
        buffer.drawText("Current Gun : " + player.currentGun, 30, 40, Color.RED);
        buffer.drawText("Kills : " + player.kills, 30, 60, Color.RED);
        buffer.drawText("Pistol = 0 kills", 30, 80, Color.RED);
        buffer.drawText("ShotGun = 12 kills", 30, 100, Color.RED);
        buffer.drawText("Automatic gun = 32 kills", 30, 120, Color.RED);
    }
}
