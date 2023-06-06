import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Instruction class
 * @author Krasovskyy Andrii
 * @author Kalianova Maria
 * @author Tarasenko Misha
 */
public class Settings extends JFrame {
	
	/**
	 * Screen width
	 */
    private static int screenWidth;
    
    /**
	 * Screen height
	 */
    private static int screenHeight;

    /**
     * Class constructor
     */
    public Settings() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);

        int toolbarHeight = insets.top + insets.bottom;
        
        screenWidth = screenSize.width;
        screenHeight = screenSize.height - toolbarHeight;
        
        screenWidth = screenSize.width;
        screenHeight = screenSize.height - toolbarHeight;
        
        System.out.println(screenWidth + "-" + screenHeight + "-" + toolbarHeight);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setTitle("settings");
        setLayout(null);

        ImageIcon buttonIcon = new ImageIcon("instr_menu.png");
        Image button = buttonIcon.getImage();
        Image scaledButton = button.getScaledInstance((int) (screenWidth * 0.38), (int) (screenHeight * 0.14), Image.SCALE_SMOOTH);
        ImageIcon scaledButtonIcon = new ImageIcon(scaledButton);
        JLabel buttonLabel = new JLabel(scaledButtonIcon);
                
        buttonLabel.setBounds((int) (screenWidth / 2 - screenWidth * 0.19), (int) (screenHeight * (0.03 + 0.115 + 0.067)), (int) (screenWidth * 0.38), (int) (screenHeight * 0.14));
        
        System.out.println((int) (screenHeight * (0.03 + 0.115 + 0.67)));
        
        buttonLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                StartMenu startMenu = new StartMenu();
                cleanup();
                dispose();
            }

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseClicked(MouseEvent e) {}
        });
        add(buttonLabel);

        ImageIcon imageIcon = new ImageIcon("intr.jpg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(scaledImageIcon);
        label.setBounds(0, 0, screenWidth, (int) (screenHeight));
        add(label);

        setVisible(true);
    }
    
    /**
     * Cleanup method to release resources
     */
    public void cleanup() {
        removeAll();

        screenWidth = 0;
        screenHeight = 0;
        
        System.gc();
    }
    
}