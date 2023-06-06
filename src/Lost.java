import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Lose game menu class
 * @author Krasovskyy Andrii
 * @author Kalianova Maria
 * @author Tarasenko Misha
 */
public class Lost extends JFrame {

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
	 * @param movingBackground
	 */
	public Lost(MovingBackground movingBackground) {
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

		System.out.println(screenWidth + "-" + screenHeight + "-" + toolbarHeight);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(screenWidth, screenHeight);
		setLocationRelativeTo(null);
		setTitle("pause menu");
		setLayout(null);

		ImageIcon buttonIcon = new ImageIcon("lose_repeat.png");
		Image button = buttonIcon.getImage();
		Image scaledButton = button.getScaledInstance((int) (screenWidth * 0.4), (int) (screenHeight * 0.14), Image.SCALE_SMOOTH);
		ImageIcon scaledButtonIcon = new ImageIcon(scaledButton);
		JLabel buttonLabel = new JLabel(scaledButtonIcon);

		buttonLabel.setBounds((int) (screenWidth / 2 - screenWidth * 0.19), (int) (screenHeight * (0.2625 + 0.0625)), (int) (screenWidth * 0.4), (int) (screenHeight * 0.14));

		buttonLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				if(movingBackground.location == 1){

					Runtime runtime = Runtime.getRuntime();
					long usedMemory = runtime.totalMemory() - runtime.freeMemory();
					long maxMemory = runtime.maxMemory();

					System.out.println("Використано пам'яті: " + usedMemory / (1024 * 1024) + " МБ");
					System.out.println("Максимально доступна пам'ять: " + maxMemory / (1024 * 1024) + " МБ");

					JFrame frame = new JFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					MovingBackground backgroundPanel;
					try {
						backgroundPanel = new MovingBackground (10, 1, 60, 80, "1.mp3", 15, frame);
						//backgroundPanel = new MovingBackground (10, 1, 60, 80, "1.mp3", 3, frame);
						frame.add(backgroundPanel);

						runtime = Runtime.getRuntime();
						usedMemory = runtime.totalMemory() - runtime.freeMemory();
						maxMemory = runtime.maxMemory();

						System.out.println("Використано пам'яті: " + usedMemory / (1024 * 1024) + " МБ");
						System.out.println("Максимально доступна пам'ять: " + maxMemory / (1024 * 1024) + " МБ");

					} catch (IOException e1) {
						System.out.println("Exception in IO during reload");
						e1.printStackTrace();
					}

					frame.pack();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);

					dispose();

					backgroundPanel = null;                                        
					System.gc();

				} else if(movingBackground.location == 2){
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
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					dispose();
				}else{
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
			}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {
				// ChooseLevel level = new ChooseLevel(finalScreenWidth1, finalScreenHeight1);
				// dispose();
			}
		});
		add(buttonLabel);

		ImageIcon buttonIcon1 = new ImageIcon("lose_exit.png");
		Image button1 = buttonIcon1.getImage();
		Image scaledButton1 = button1.getScaledInstance((int) (screenWidth * 0.4), (int) (screenHeight * 0.14), Image.SCALE_SMOOTH);
		ImageIcon scaledButtonIcon1 = new ImageIcon(scaledButton1);
		JLabel buttonLabel1 = new JLabel(scaledButtonIcon1);

		buttonLabel1.setBounds((int) (screenWidth / 2 - screenWidth * 0.19), (int) (screenHeight * (0.2625 + 0.0625*2 + 0.14)), (int) (screenWidth * 0.4), (int) (screenHeight * 0.14));

		buttonLabel1.addMouseListener(new MouseListener() {

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
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		add(buttonLabel1);

		ImageIcon buttonIcon2 = new ImageIcon("lose_menu.png");
		Image button2 = buttonIcon2.getImage();
		Image scaledButton2 = button2.getScaledInstance((int) (screenWidth * 0.4), (int) (screenHeight * 0.14), Image.SCALE_SMOOTH);
		ImageIcon scaledButtonIcon2 = new ImageIcon(scaledButton2);
		JLabel buttonLabel2 = new JLabel(scaledButtonIcon2);

		buttonLabel2.setBounds((int) (screenWidth / 2 - screenWidth * 0.19), (int) (screenHeight * (0.2625 + 0.0625*3 + 0.14*2)), (int) (screenWidth * 0.4), (int) (screenHeight * 0.14));

		buttonLabel2.addMouseListener(new MouseListener() {

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
			public void mouseClicked(MouseEvent e) {
				// ChooseLevel level = new ChooseLevel(finalScreenWidth1, finalScreenHeight1);
				// dispose();
			}
		});
		add(buttonLabel2);


		ImageIcon imageIcon = new ImageIcon("lost_back.jpg");
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