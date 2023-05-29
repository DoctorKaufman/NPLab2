import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Animation {

    private BufferedImage[] frames;
    private int frameWidth;
    private int frameHeight;

    private long timePerFrame;
    private long playingTime;

    public Animation(String path, int numberOfFrames, long timePerFrame) throws IOException {
        BufferedImage allFrames = ImageIO.read(new File(path));
        int allFramesWidth = allFrames.getWidth();
        if (allFramesWidth % numberOfFrames != 0) {
            throw new RuntimeException("Ширина картинки хранящей анимации=" + allFramesWidth + ", но она должна быть кратна числу кадров " + numberOfFrames + "!");
        }

        //this.frameWidth = allFramesWidth / numberOfFrames;
        //this.frameHeight = allFrames.getHeight();
        
        this.frameWidth = ImageIO.read(new File("run1.png")).getWidth()/30;
        this.frameHeight = ImageIO.read(new File("run1.png")).getHeight()/30;

        // Нам надо нарезать картинку на кадры, как это делать можно найти в гугле по запросу "java swing bufferedimage crop"
        // https://stackoverflow.com/a/4818980
        frames = new BufferedImage[6];
        for (int i = 0; i < numberOfFrames; ++i) {
            //frames[i] = allFrames.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
        
        for (int i = 1; i <= 6; ++i) {
            frames[i-1] = ImageIO.read(new File("run"+i+".png"));
        }
        
        System.out.println("Анимация загружена (разрешение кадра: " + frameWidth + "x" + frameHeight + ", " + numberOfFrames + " кадров, путь: " + path + ")");

        this.timePerFrame = timePerFrame;
        this.playingTime = 0;
    }

    public int getFrameWidth() {
        return frameWidth;
    }
    public int getFrameHeight() {
        return frameHeight;
    }

    public void restart() {
        // Если персонаж начал бежать - мы хотим откатиться к первому кадру, т.е. откатить время проигрывания до нуля
        playingTime = 0;
    }

    public void update(long dt) {
        long totalAnimationTime = timePerFrame * frames.length;
        playingTime = (playingTime + dt) % totalAnimationTime; // взятие по модулю, т.к. анимация зациклена
    }

    public void draw(Graphics g, int x, int y, boolean mirrored) {
        int i = (int) (playingTime / timePerFrame); // вычисляем какой кадр сейчас актуален
        BufferedImage currentFrame = frames[i];
        if (!mirrored) {
            //g.drawImage(currentFrame, x, y, frameWidth, frameHeight, null);
        	g.drawImage(currentFrame, x, y, frameWidth, frameHeight, null);
        } else {
            //g.drawImage(currentFrame, x, y, -frameWidth, frameHeight, null);
        	g.drawImage(currentFrame, x, y, -frameWidth, frameHeight, null);
        }
    }

}