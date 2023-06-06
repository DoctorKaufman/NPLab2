import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Start menu class
 * @author Krasovskyy Andrii
 * @author Kalianova Maria
 * @author Tarasenko Misha
 */
public class StartMenu extends JFrame {

	/**
	 * Screen width
	 */
    private static int screenWidth;
    
    /**
	 * Screen height
	 */
    private static int screenHeight;
    
    /**
     * Class constructor (set up the GUI)
     */
    public StartMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height - 50;
        
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);

        int toolbarHeight = insets.top + insets.bottom;
        
        System.out.println(insets.top + "-" + insets.bottom);
        
        screenWidth = screenSize.width;
        screenHeight = screenSize.height - toolbarHeight;
        
        System.out.println(screenWidth + "-" + screenHeight + "-" + toolbarHeight);
        
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setTitle("Start menu");
        setLayout(null);
        
        ImageIcon buttonIcon = new ImageIcon("prev_start.png");
        Image button = buttonIcon.getImage();
        Image scaledButton = button.getScaledInstance((int) (screenWidth*0.26), (int) (screenHeight * 0.13), Image.SCALE_SMOOTH);
        ImageIcon scaledButtonIcon = new ImageIcon(scaledButton);
        JLabel buttonLabel = new JLabel(scaledButtonIcon);
        buttonLabel.setBounds((int) (screenWidth/2 - screenWidth*0.13), (int) (screenHeight/4*3 + screenHeight *0.034), (int) (screenWidth*0.26), (int) (screenHeight*0.13));
        buttonLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				ChooseLevel level = new ChooseLevel(screenWidth, screenHeight);
				cleanup();
				dispose();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
        add(buttonLabel);
        
        ImageIcon buttonIcon2 = new ImageIcon("prev_exit.png");
        Image button2 = buttonIcon2.getImage();
        Image scaledButton2 = button2.getScaledInstance((int) (screenWidth*0.14), (int) (screenHeight * 0.1), Image.SCALE_SMOOTH);
        ImageIcon scaledButtonIcon2 = new ImageIcon(scaledButton2);
        JLabel buttonLabel2 = new JLabel(scaledButtonIcon2);
        buttonLabel2.setBounds((int) (screenWidth/2 - screenWidth*0.34), (int) (screenHeight/4*3 + screenHeight*0.05), (int) (screenWidth*0.14), (int) (screenHeight*0.1));
        buttonLabel2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				cleanup();
                dispose();
                System.exit(0);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
        add(buttonLabel2);
        
        ImageIcon buttonIcon3 = new ImageIcon("prev_settings.png");
        Image button3 = buttonIcon3.getImage();
        Image scaledButton3 = button3.getScaledInstance((int) (screenWidth*0.14), (int) (screenHeight * 0.1), Image.SCALE_SMOOTH);
        ImageIcon scaledButtonIcon3 = new ImageIcon(scaledButton3);
        JLabel buttonLabel3 = new JLabel(scaledButtonIcon3);
        buttonLabel3.setBounds((int) (screenWidth/2 + screenWidth*0.2), (int) (screenHeight/4*3 + screenHeight * 0.05), (int) (screenWidth*0.14), (int) (screenHeight*0.1));
        buttonLabel3.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Settings settings = new Settings();
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
        add(buttonLabel3);
        
        ImageIcon imageIcon = new ImageIcon("pre.jpg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(scaledImageIcon);
        label.setBounds(0, 0, screenWidth, screenHeight);
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

    public static void main(String[] args) {        
        StartMenu frame = new StartMenu();
        frame.setVisible(true);
    }
}


