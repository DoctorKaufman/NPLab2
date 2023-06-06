import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Choose level class
 * @author Krasovskyy Andrii
 * @author Kalianova Maria
 * @author Tarasenko Misha
 */
public class ChooseLevel extends JFrame{
	
	/**
	 * Class constructor
	 * @param screenWidth
	 * @param screenHeight
	 */
    public ChooseLevel(int screenWidth, int screenHeight) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();

        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);

        int toolbarHeight = insets.top + insets.bottom;
        
        screenWidth = screenSize.width;
        screenHeight = screenSize.height - toolbarHeight;
        
        System.out.println(screenWidth + "-" + screenHeight + "-" + toolbarHeight);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setTitle("start menu");
        setLayout(null);

        ImageIcon buttonIcon = new ImageIcon("level_choice2.png");
        Image button = buttonIcon.getImage();
        Image scaledButton = button.getScaledInstance((int) (screenWidth * 0.3), (int) (screenHeight * 0.43), Image.SCALE_SMOOTH);
        ImageIcon scaledButtonIcon = new ImageIcon(scaledButton);
        JLabel buttonLabel = new JLabel(scaledButtonIcon);
        buttonLabel.setBounds((int) (screenWidth / 2 - screenWidth * 0.115), (int) (screenHeight / 2 - screenHeight * 0.12), (int) (screenWidth * 0.26), (int) (screenHeight * 0.28));
        buttonLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
            	JFrame frame = new JFrame();
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		MovingBackground backgroundPanel;
				try {
					backgroundPanel = new MovingBackground(10, 2, 40, 70, "2.mp3", 20, frame);
					//backgroundPanel = new MovingBackground(10, 2, 40, 70, "2.mp3", 3, frame);
					frame.add(backgroundPanel);
					frame.pack();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
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

        ImageIcon buttonIcon2 = new ImageIcon("level_choice1.png");
        Image button2 = buttonIcon2.getImage();
        Image scaledButton2 = button2.getScaledInstance((int) (screenWidth * 0.3), (int) (screenHeight * 0.43), Image.SCALE_SMOOTH);
        ImageIcon scaledButtonIcon2 = new ImageIcon(scaledButton2);
        JLabel buttonLabel2 = new JLabel(scaledButtonIcon2);
        
        buttonLabel2.setBounds((int) (screenWidth / 2 - screenWidth * 0.440), (int) (screenHeight / 2 - screenHeight * 0.12), (int) (screenWidth * 0.26), (int) (screenHeight * 0.28));
        
        buttonLabel2.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e){
            	JFrame frame = new JFrame();
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		MovingBackground backgroundPanel;
				try {
					backgroundPanel = new MovingBackground(10, 1, 60, 80, "1.mp3", 15, frame);
					//backgroundPanel = new MovingBackground(10, 1, 60, 80, "1.mp3", 3, frame);
					frame.add(backgroundPanel);
					frame.pack();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
        add(buttonLabel2);

        ImageIcon buttonIcon3 = new ImageIcon("level_choice3.png");
        Image button3 = buttonIcon3.getImage();
        Image scaledButton3 = button3.getScaledInstance((int) (screenWidth * 0.3), (int) (screenHeight * 0.43), Image.SCALE_SMOOTH);
        ImageIcon scaledButtonIcon3 = new ImageIcon(scaledButton3);
        JLabel buttonLabel3 = new JLabel(scaledButtonIcon3);
        buttonLabel3.setBounds((int) (screenWidth / 2 + screenWidth * 0.235), (int) (screenHeight / 2 - screenHeight * 0.12), (int) (screenWidth * 0.26), (int) (screenHeight * 0.28));
        buttonLabel3.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
            	JFrame frame = new JFrame();
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		MovingBackground backgroundPanel;
				try {
					backgroundPanel = new MovingBackground(10, 3, 20, 60, "3.mp3", 25, frame);
					//backgroundPanel = new MovingBackground(10, 3, 20, 60, "3.mp3", 3, frame);
					frame.add(backgroundPanel);
					frame.pack();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				cleanup();
				dispose();
            }

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseClicked(MouseEvent e) {
                //ChooseLevel level = new ChooseLevel(finalScreenWidth, finalScreenHeight);
                //dispose();
            }
        });
        add(buttonLabel3);

        ImageIcon imageIcon = new ImageIcon("main2.jpg");
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
        System.gc();
    }
}
