import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MovingRectangle extends JFrame implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int RECTANGLE_WIDTH = 50;
    private static final int RECTANGLE_HEIGHT = 50;
    private static final int DELTA_X = 5;
    private int x = 0;
    private int y = HEIGHT / 2 - RECTANGLE_HEIGHT / 2;
    private Timer timer;
    private BufferedImage buffer;

    public MovingRectangle() {
        setTitle("Движущийся прямоугольник");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = buffer.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x, y, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
        g.drawImage(buffer, 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x -= DELTA_X;

        if (x + RECTANGLE_WIDTH < 0) {
            x = WIDTH;
        }

        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MovingRectangle movingRectangle = new MovingRectangle();
            movingRectangle.setVisible(true);
        });
    }
}
