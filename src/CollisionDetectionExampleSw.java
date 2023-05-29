import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

public class CollisionDetectionExample extends JFrame {
    private Ellipse2D.Double shape1;
    private Rectangle2D.Double shape2;

    private int shape1X = 100;
    private int shape1Y = 100;
    private int shape1Width = 50;
    private int shape1Height = 50;
    private int shape1XSpeed = 2;
    private int shape1YSpeed = 2;

    private int windowWidth;
    private int windowHeight;

    public CollisionDetectionExample() {
        setTitle("Collision Detection Example");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        shape1 = new Ellipse2D.Double(shape1X, shape1Y, shape1Width, shape1Height);
        shape2 = new Rectangle2D.Double(200, 200, 100, 50);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                windowWidth = getWidth();
                windowHeight = getHeight();
            }
        });

        Timer timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shape1X += shape1XSpeed;
                shape1Y += shape1YSpeed;
                shape1.setFrame(shape1X, shape1Y, shape1Width, shape1Height);

                if (shape1.getBounds2D().intersects(shape2.getBounds2D())) {
                    shape1XSpeed *= -1;
                    shape1YSpeed *= -1;
                }

                if (shape1X <= 0 || shape1X + shape1Width >= windowWidth) {
                    shape1XSpeed *= -1;
                }

                if (shape1Y <= 0 || shape1Y + shape1Height >= windowHeight) {
                    shape1YSpeed *= -1;
                }

                repaint();
            }
        });
        timer.start();

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        g2d.fill(shape1);

        g2d.setColor(Color.BLUE);
        g2d.fill(shape2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CollisionDetectionExample());
    }
}
