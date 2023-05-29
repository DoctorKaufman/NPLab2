import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Character {
    // ...

	Image woodcutter;
	
	int running;
	double xRunningSpeed;
	double x;
	
    private Animation animationRun;

    public Character(double x) throws IOException {
        //woodcutter = ImageIO.read(new File("Woodcutter_run.png"));
    	woodcutter = ImageIO.read(new File("run1.png"));
        animationRun = new Animation("Woodcutter_run.png", 6, 60);
        this.x = x;
        this.xRunningSpeed = 0.0;
        //this.xRunningSpeed = 0.2; // Скорость в пикселях/миллисекунду
        this.running = 0;         // Изначально мы стоим на месте
    }

    public void startRunningLeft() {
        running = -1;
    }
    public void startRunningRight() {
        running = 1;
    }

    public void stopRunningLeft() {
        if (running == -1) {
            running = 0;
        }
    }
    public void stopRunningRight() {
        if (running == 1) {
            running = 0;
        }
    }

    public void draw(Graphics g, int panelWidth, int panelHeight) {
        int imageX = (int) x;
        //System.out.println(woodcutter.getHeight(null)/30);
        int imageY = panelHeight - woodcutter.getHeight(null)/30;
        if (running == 0) { // если мы не бежим - рисуем старую картинку стоящего дровосека
            g.drawImage(woodcutter, imageX, imageY, null);
        } else {            // иначе - рисуем анимацию бега
            animationRun.draw(g, imageX, imageY, true);
        }
    }

    // ...
    
   public void update(long dt) {
        x += dt * xRunningSpeed * running;

        animationRun.update(dt); // не забываем обновлять анимацию с учетом течения времени
    }

}