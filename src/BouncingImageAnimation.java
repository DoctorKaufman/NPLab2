import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class BouncingImageAnimation extends JFrame implements KeyListener {

    private ImageIcon imageIcon;
    private JLabel imageLabel;
    private Timer timer;
    private int scaleFactor = 2;
    private int deltaY = -5;

    public BouncingImageAnimation() {
        setTitle("Bouncing Image Animation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        imageIcon = new ImageIcon("run1.png");
        imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        addKeyListener(this);
        setFocusable(true);

        pack();
        setLocationRelativeTo(null);
    }

    private void startAnimation() {
        timer = new Timer(20, e -> {
            int y = imageLabel.getY() + deltaY;
            imageLabel.setLocation(imageLabel.getX(), y);

            if (y <= 0 || y >= (getHeight() - imageLabel.getHeight())) {
                deltaY *= -1;
                scaleFactor /= 100;

                if (scaleFactor < 1) {
                    timer.stop();
                    imageLabel.setIcon(imageIcon);
                    imageLabel.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
                } else {
                    int newWidth = imageIcon.getIconWidth() / scaleFactor;
                    int newHeight = imageIcon.getIconHeight() / scaleFactor;
                    Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                    imageLabel.setSize(newWidth, newHeight);
                }
            }
        });

        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            startAnimation();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BouncingImageAnimation animation = new BouncingImageAnimation();
            animation.setVisible(true);
        });
    }
}
