import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageIntersectionExample {
	
	private static BufferedImage loadImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image Intersection Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    
                    

                    // Загрузка изображений из файлов
                    BufferedImage image1 = loadImage("run1.png");
                    BufferedImage image2 = loadImage("run2.png");

                    // Отрисовка изображений
                    g2d.drawImage(image1, 0, 0, null);
                    g2d.drawImage(image2, 0, 0, null);

                    // Создание формы объектов для проверки пересечения
                    Shape shape1 = createShapeFromImage(image1);
                    Shape shape2 = createShapeFromImage(image2);

                    // Проверка пересечения форм
                    boolean intersects = shape1.intersects(shape2.getBounds2D());

                    // Отображение результата пересечения
                    if (intersects) {
                        g2d.setColor(Color.RED);
                        g2d.drawString("Objects intersect", 10, 20);
                    } else {
                        g2d.setColor(Color.GREEN);
                        g2d.drawString("Objects do not intersect", 10, 20);
                    }
                }
            };

            panel.setPreferredSize(new Dimension(800, 600));
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setVisible(true);
        });
    }

    private static Shape createShapeFromImage(BufferedImage image) {
        // Создание формы объекта на основе альфа-канала изображения
        Area area = new Area();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int alpha = (image.getRGB(x, y) >> 24) & 0xFF;
                if (alpha != 0) {
                    Rectangle2D pixelRect = new Rectangle2D.Double(x, y, 1, 1);
                    area.add(new Area(pixelRect));
                }
            }
        }
        return area;
    }
}
