import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BackgroundScrolling {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int SCROLL_SPEED = 10;

    private JFrame frame;
    private JLabel backgroundLabel;
    private Timer scrollTimer;
    private int currentX;

    public BackgroundScrolling(String imagePath) {
        frame = new JFrame("Background Scrolling");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        BufferedImage backgroundImage;
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, FRAME_WIDTH * 3, FRAME_HEIGHT);
        frame.add(backgroundLabel);

        currentX = 0;

        scrollTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentX -= SCROLL_SPEED;
                if (currentX <= -FRAME_WIDTH) {
                    currentX = 0;
                }
                backgroundLabel.setBounds(currentX, 0, FRAME_WIDTH * 3, FRAME_HEIGHT);
            }
        });

        frame.setVisible(true);
    }

    public void startScrolling() {
        scrollTimer.start();
    }

    public static void main(String[] args) {
    	
    	 // Установка начального размера кучи
        long initialHeapSize = 256 * 1024 * 1024; // 256 мегабайт
        System.setProperty("java.lang.management.memory.heap.init", Long.toString(initialHeapSize));

        // Установка максимального размера кучи
        long maxHeapSize = 1024 * 1024 * 1024; // 2 гигабайта
        System.setProperty("java.lang.management.memory.heap.max", Long.toString(maxHeapSize));

        String imagePath = "image.png"; // Замените на путь к вашему изображению
        BackgroundScrolling scrolling = new BackgroundScrolling(imagePath);
        scrolling.startScrolling();
    }
}
