import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageSlider extends JFrame implements ActionListener {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int IMAGE_WIDTH = 1200;
    private static final int IMAGE_HEIGHT = 600;
    private static final int TIMER_DELAY = 10;

    private ImageIcon[] images;
    private int currentIndex;
    private int xPosition;

    private Timer timer;

    public ImageSlider() {
        setTitle("Image Slider");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Загрузка изображений
        images = new ImageIcon[3];
        images[0] = new ImageIcon("image1.png");
        images[1] = new ImageIcon("image2.png");
        images[2] = new ImageIcon("image3.png");

        currentIndex = 0;
        xPosition = 0;

        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Рисование изображений
        for (int i = -1; i <= 1; i++) {
            int imageIndex = (currentIndex + i + images.length) % images.length;
            Image image = images[imageIndex].getImage();
            g.drawImage(image, xPosition + i * IMAGE_WIDTH, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Обновление позиции
        xPosition--;

        // Проверка, достигла ли левая черта среднего кадра края окна
        if (xPosition <= 0) {
            xPosition = xPosition + IMAGE_WIDTH;
            currentIndex = (currentIndex + 1) % images.length;
            ImageIcon temp = images[0];
            
            for (int i = 0; i < images.length - 1; i++) {
                images[i] = images[i + 1];
            }
            
            //images[0] = images[2];
            
            images[images.length - 1] = temp;
        }

        // Перерисовка окна
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageSlider slider = new ImageSlider();
            slider.setVisible(true);
        });
    }
}
