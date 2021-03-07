import engine.SoundEffect;

public class SoundEffectFactory {

    public static SoundEffect makeExplosion() {
        return new SoundEffect("explode.wav");
    }

    public static SoundEffect makeFire() {
        return new SoundEffect("fire.wav");
    }

    public static SoundEffect makePain() {
        return new SoundEffect("Bite-SoundBible.com-2056759375.wav");
    }

    public static SoundEffect makeScary() {
        return new SoundEffect("Zombie Rising-SoundBible.com-1988883138.wav");
    }

    public static SoundEffect makeMusic() {
        return new SoundEffect("Zapp & Roger - Computer Love.wav");

    }
}