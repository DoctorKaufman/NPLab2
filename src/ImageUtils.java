import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUtils {
    public static boolean hasBlackPixelOnVerticalLine(String imagePath, int x) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int height = image.getHeight();
            
            for (int y = 0; y < height; y++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                if (pixelColor.equals(Color.BLACK)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        String imagePath = "run1.png";
        int xCoordinate = 1500; // Ваша координата x
        
        boolean hasBlackPixel = hasBlackPixelOnVerticalLine(imagePath, xCoordinate);
        if (hasBlackPixel) {
            System.out.println("На вертикальной линии есть черный пиксель.");
        } else {
            System.out.println("На вертикальной линии нет черных пикселей.");
        }
    }
}
