import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CollisionDetectionExample {
    public static void main(String[] args) {
        try {
            // Загрузите изображения объектов
            BufferedImage object1Image = ImageIO.read(new File("run1.png"));
            BufferedImage object2Image = ImageIO.read(new File("run2.png"));

            // Преобразуйте изображения в маски коллизий
            boolean[][] object1CollisionMask = createCollisionMask(object1Image);
            boolean[][] object2CollisionMask = createCollisionMask(object2Image);

            // Проверьте коллизии
            boolean collisionDetected = checkCollision(object1CollisionMask, object2CollisionMask);
            if (collisionDetected) {
                System.out.println("Коллизия обнаружена!");
            } else {
                System.out.println("Коллизий не обнаружено.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean[][] createCollisionMask(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        boolean[][] collisionMask = new boolean[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb, true);
                collisionMask[x][y] = color.getAlpha() > 0;
            }
        }

        return collisionMask;
    }

    private static boolean checkCollision(boolean[][] mask1, boolean[][] mask2) {
        int width = Math.min(mask1.length, mask2.length);
        int height = Math.min(mask1[0].length, mask2[0].length);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (mask1[x][y] && mask2[x][y]) {
                    return true; // Коллизия обнаружена
                }
            }
        }

        return false; // Коллизий не обнаружено
    }
}
