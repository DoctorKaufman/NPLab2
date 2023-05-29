import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * GUI class (draws all components, moves them and process animations)
 * @author Krasovskyy Andrii
 */
public class MovingBackground extends JPanel implements KeyEventDispatcher {
	/**
	 * Game state
	 */
	boolean end = false;

	/**
	 * Player for background music
	 */
	static MP3Player player;

	/**
	 * Thread for music
	 */
	static Thread playerThread;

	/**
	 * Background image 1
	 */
	private Image backgroundImage1;
	private int xCoordinate1;

	/**
	 * Background image 2
	 */
	private Image backgroundImage2;
	private int xCoordinate2;

	/**
	 * Background image 3
	 */
	private Image backgroundImage3;
	private int xCoordinate3;

	/**
	 * Character frame width
	 */
	private int frameWidth;
	
	/**
	 * Character frame height
	 */
	private int frameHeight;

	/**
	 * Run animation frames
	 */
	private BufferedImage[] frames;

	/**
	 * Crouch animation frames
	 */
	private BufferedImage[] framesCrouch;

	/**
	 * Run frames counter
	 */
	int f = 0;
	
	/**
	 * Movement state
	 */
	private int running = 1;

	Timer crouchTimer;
	Timer timer3;
	
	/**
	 * Crouch frames counter
	 */
	private int f1 = 0;

	/**
	 * Jump counter
	 */
	int i = 0;
	
	/**
	 * Delta for jump move (OY axis)
	 */
	int d = 25;

	private int startY;
	
	/**
	 * Current delta from start position (OY axis)
	 */
	private int currentY = 0;
	
	private int targetY;
	private int direction;

	/**
	 * Enable jump state
	 */
	private boolean enableJump = true;
	
	/**
	 * Enable crouch state
	 */
	private boolean enableCrouch = true;

	/**
	 * Character's collision mask x position
	 */
	int x;
	
	/**
	 * Character's collision mask y position
	 */
	int y;
	
	/**
	 * Character's collision mask width
	 */
	int width;
	
	/**
	 * Character's collision mask height
	 */
	int height;

	int rx = 1700;
	int ry = 1700;

	Rectangle rect1 = new Rectangle(1700, 1700, 50, 50);

	/**
	 * Jump frame
	 */
	private BufferedImage jump;

	private boolean crouch = false;

	/**
	 * End of crouch (stand up) state
	 */
	private boolean standUp = false;

	private int c = 0;

	private boolean allowEndSound = true;

	/**
	 * Class constructor (starts the GUI and timer animations)
	 * @param n
	 * @param t
	 * @throws IOException
	 * @author Krasovskyy Andrii
	 */
	public MovingBackground(int n, int t) throws IOException {

		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(this);

		this.frameWidth = ImageIO.read(new File("run1.png")).getWidth()/10;
		this.frameHeight = ImageIO.read(new File("run1.png")).getHeight()/10;

		x = 10 + frameWidth/4;
		//int y = this.getHeight()-this.frameHeight - currentY -;
		y = 960 -this.frameHeight + 30 - currentY;
		System.out.println(x);
		System.out.println(y);
		width = frameWidth/2;
		height = 350;

		rx = 1700;
		ry = 850;

		frames = new BufferedImage[6];

		framesCrouch = new BufferedImage[4];

		for (int i = 1; i <= 6; ++i) {
			frames[i-1] = ImageIO.read(new File("run"+i+".png"));
		}

		for (int i = 1; i <= 3; ++i) {
			framesCrouch[i-1] = ImageIO.read(new File("sil"+i+".png"));
		}

		framesCrouch[3] = ImageIO.read(new File("going_sit.png"));

		jump = ImageIO.read(new File("jump.png"));

		int panelHeight = getHeight();
		//startY = panelHeight-this.frameHeight;
		currentY = 0;
		//targetY = 100;
		direction = -15;

		// Загрузка изображения фона
		/**backgroundImage1 = new ImageIcon("city_normal.png").getImage();

        backgroundImage2 = new ImageIcon("city_normal.png").getImage();

        backgroundImage3 = new ImageIcon("city_sky.png").getImage();*/

		/**backgroundImage1 = new ImageIcon("Game_city1_city.png").getImage().getScaledInstance(1800, 960, 0);

        backgroundImage2 = new ImageIcon("Game_city1_back_city.png").getImage().getScaledInstance(1800, 960, 0);

        backgroundImage3 = new ImageIcon("Game_city1_back_sky_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);*/

		/**backgroundImage1 = new ImageIcon("Game_suburb1_city_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

        backgroundImage2 = new ImageIcon("Game_suburb1_back_city_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

        backgroundImage3 = new ImageIcon("Game_suburb1_back_sky_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);*/

		backgroundImage1 = new ImageIcon("Game_village1_village_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

		backgroundImage2 = new ImageIcon("Game_village1_back_mount_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

		backgroundImage3 = new ImageIcon("Game_village1_back_sky_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

		//xCoordinate = 0;

		xCoordinate1 = n;

		xCoordinate2 = n;

		xCoordinate3 = n;

		//Timer timer = new Timer(10, e -> {
		//Timer for background movement
		Timer timer = new Timer(t, e -> {
			xCoordinate1 -= 3;

			xCoordinate2 -= 2;

			xCoordinate3 -= 1;

			if (xCoordinate1 <= -backgroundImage1.getWidth(null)) {
				xCoordinate1 = 0;
			}

			repaint();
		});
		
		//Timer for running and jumping animations
		Timer timer1 = new Timer(80, e -> {

			if (running == 1) {
				if(f == 5) {
					f = 0;
				}
				else {
					f++;
				}
			}
			if (running == 0) {
				f = 0;
			}

			/**if (ImageUtils.hasBlackPixelOnVerticalLine("run"+f+".png", panelHeight)) {
				System.exit(0);
			}*/

			rx -= 20;

			if (rx + 50 <= 0) {
				rx = getWidth();
			}
			
			repaint();
			
			//Here should be your collision check (add checking through masks ArrayList)
			//It's quite possible to move check in a separate method
			if (getIntersection(rect1)) {
				if (crouchTimer.isRunning()) {
					crouchTimer.stop();
				}
				repaint();
				player.stop();
				playerThread.interrupt();
				end = true;
				//timer.stop();
				if (end == true && allowEndSound  == true)
					allowEndSound = false;
					playerThread = new Thread(() -> {
						player = new MP3Player("e1.mp3");
						player.playOneTime();
						
						//player.end = true;
						
						/**player = new MP3Player("e2.mp3");
						player.playOneTime();*/
					});
					playerThread.start();
					
					/*playerThread = new Thread(() -> {
						player = new MP3Player("e2.mp3");
						player.playOneTime();						
						allowEndSound = false;
					});
					playerThread.start();*/
				}	
				
				//Currently disabled music
				//playerThread.start();
				//player.end = true;

			repaint();

			if(running == 2) {
				//if (currentY <= 500 && direction == -1) {
				//  direction = 1;
				//}
				/*else if (currentY >= startY && direction == 1) {
                	running = 0;
                	timer3.stop();       
                }*/
				//else if (currentY >= startY && direction == 1) {
				//running = 1;
				//timer3.stop();   
				//}



				if (i == 3) {
					d = 17;
				}
				else if (i == 6) {
					d = 10;
				}
				else if (i == 9) {
					d = 3;
				}
				else if (i == 12) {
					d = -3;
				}
				else if (i == 15) {
					d = -10;
				}
				else if (i == 18) {
					d = -17;
				}
				else if (i == 21) {
					d = -25;
				}
				else if (i == 24) {
					d = 25;
					i=0;
					enableJump = true;
					enableCrouch = true;
					running = 1;
					currentY = 0;
					x = 10 + frameWidth/4;
					//int y = this.getHeight()-this.frameHeight - currentY -;
					y = 960 -this.frameHeight + 30 - currentY;
					System.out.println(x);
					System.out.println(y);
					width = frameWidth/2;
					height = 350;
				}

				/**if (i < 15) {
					d -= 2;
				}
				else if (i == 15) {
					d = -d;
				}
				else if (i < 30 && i > 15) {
					d -= 2;
				}
				else if (i == 30) {
					d = 25;
					i=0;
					enableJump = true;
					running = 1;
					currentY = 0;
				}*/

				//currentY = currentY + direction;

				currentY  = currentY + d;

				if (running == 2 && i != 24) {

					y = 960 - this.frameHeight + 30 - currentY;
				}

				i++;
				//repaint();
				//}
			}

			/*y = 960 - this.frameHeight + 30 - currentY;*/

			//repaint();
		});

		//Timer for crouch animations
		crouchTimer = new Timer(80, e -> {
			if(standUp == false) {
				if (running == 0 && enableCrouch  == true) {
					if(f1  == 2 || f1 == 3) {
						//f1 = 3;
						//crouch = true;
						if (f1 == 3) {
							f1  = 2;
						}
						else if (f1 == 2) {
							f1 = 3;
						}
						//repaint();
					}
					else {
						if (f1 == 0) {
							x = 10 + frameWidth/4;
							//int y = this.getHeight()-this.frameHeight - currentY -;
							y = 960 - this.frameHeight + 30 - currentY;
							System.out.println(x);
							System.out.println(y);
							width = frameWidth/2;
							height = 350;
						}
						if (f1 == 0) {
							x = 10 + frameWidth/4;
							//int y = this.getHeight()-this.frameHeight - currentY -;
							y = 960 -this.frameHeight + 30 + 55 + 25 - currentY;
							System.out.println(x);
							System.out.println(y);
							width = frameWidth/2;
							height = 350 - 55;
						}
						if (f1 == 1) {
							x = 10 + frameWidth/4;
							//int y = this.getHeight()-this.frameHeight - currentY -;
							y = 960 - this.frameHeight + 30 + 125 + 25 - currentY;
							System.out.println(x);
							System.out.println(y);
							width = frameWidth/2;
							height = 350 - 125;
						}
						repaint();
						f1++;	
					}
				}
			}
			else {
				//f1--;
				if(f1 == 2 || f1== 3) {
					x = 10 + frameWidth/4;
					//int y = this.getHeight()-this.frameHeight - currentY -;
					y = 960 - this.frameHeight + 30 + 55 + 25 - currentY;
					System.out.println(x);
					System.out.println(y);
					width = frameWidth/2;
					height = 350 - 55;
					f1 = 1;
					repaint();
				}
				else if (f1 == 1) {
					x = 10 + frameWidth/4;
					//int y = this.getHeight()-this.frameHeight - currentY -;
					y = 960 - this.frameHeight + 30 + 25 - currentY;
					System.out.println(x);
					System.out.println(y);
					width = frameWidth/2;
					height = 350;
					f1--;
					//sil.stop();
					repaint();
				}
				else if (f1 == 0) {
					running = 1;
					crouchTimer.stop();
					repaint();
					enableJump = true;
					enableCrouch = true;
				}
				
			}

			//repaint();
		});

		timer.start();
		timer1.start();


		//timer3 = new Timer(20, e -> {
		//@Override
		//public void actionPerformed(ActionEvent e) {

		//});

		//timer3.start();


	}

	@Override
	/**
	 * Key listeners for playing game
	 * @author Krasovskyy Andrii
	 */
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			if (e.getKeyCode() == KeyEvent.VK_1) {
				backgroundImage1 = new ImageIcon("Game_city1_city.png").getImage().getScaledInstance(1800, 960, 0);

				backgroundImage2 = new ImageIcon("Game_city1_back_city.png").getImage().getScaledInstance(1800, 960, 0);

				backgroundImage3 = new ImageIcon("Game_city1_back_sky_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

				player.stop();

				/*player = new MP3Player("1.mp3");

				player.play();*/

				playerThread = new Thread(() -> {
					player = new MP3Player("1.mp3");
					player.play();
				});

				playerThread.start();

			} else if (e.getKeyCode() == KeyEvent.VK_2) {
				backgroundImage1 = new ImageIcon("Game_suburb1_city_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

				backgroundImage2 = new ImageIcon("Game_suburb1_back_city_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

				backgroundImage3 = new ImageIcon("Game_suburb1_back_sky_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

				player.stop();

				/*player = new MP3Player("2.mp3");

				player.play();*/

				playerThread = new Thread(() -> {
					player = new MP3Player("2.mp3");
					player.play();
				});

				playerThread.start();
			}
			else if (e.getKeyCode() == KeyEvent.VK_3) {
				backgroundImage1 = new ImageIcon("Game_village1_village_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

				backgroundImage2 = new ImageIcon("Game_village1_back_mount_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

				backgroundImage3 = new ImageIcon("Game_village1_back_sky_Монтажная область 1.png").getImage().getScaledInstance(1800, 960, 0);

				player.stop();

				/*player = new MP3Player("3.mp3");

				player.play();*/

				playerThread = new Thread(() -> {
					player = new MP3Player("3.mp3");
					player.play();
				});

				playerThread.start();
			}
		}

		if (e.getID() == KeyEvent.KEY_PRESSED) { // Если кнопка была нажата (т.е. сейчас она зажата)
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				//man.startRunningLeft();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				//man.startRunningRight();
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				//man.startRunningRight();
				standUp = false;
				enableJump = false;
				//enableCrouch = false;
				if (enableCrouch == true) {
					running = 0;
					crouchTimer.start();
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP) {
				enableCrouch = false;
				//man.startRunningRight();
				if (enableJump == true) {
					running = 2;
					enableJump = false;
					enableCrouch = false;
					x = 10 + frameWidth/4;
					y = 960 -this.frameHeight + 30 - currentY;
					System.out.println(x);
					System.out.println(y);
					width = frameWidth/2;
					height = 280;
				}
				//timer3.start();
			}
		}

		if (e.getID() == KeyEvent.KEY_RELEASED) {     // Если кнопка была отпущена - мы должны прекратить бег
			if (e.getKeyCode() == KeyEvent.VK_LEFT) { // но только бег в ту сторону, которой соответствует отпущенная кнопка
				//man.stopRunningLeft();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				//man.stopRunningRight();
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				//man.stopRunningRight();
				//running = 1;
				//sil.stop();
				//f1=0;
				standUp = true;
				/*x = 10 + frameWidth/4;
				//int y = this.getHeight()-this.frameHeight - currentY -;
				y = 960 -this.frameHeight + 30 - currentY;
				System.out.println(x);
				System.out.println(y);
				width = frameWidth/2;
				height = 350;*/
			}
		}

		return false;

	}

	@Override
	/**
	 * Repaints graphics (updates image)
	 * @author Krasovskyy Andrii
	 */
	protected void paintComponent(Graphics g) {
		if(end == false) {
			super.paintComponent(g);

			int panelWidth = getWidth();
			int panelHeight = getHeight();
			startY = panelHeight-this.frameHeight-100;
			Image buffer = createImage(panelWidth, panelHeight);
			Graphics2D g2d = (Graphics2D) buffer.getGraphics();
			//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setComposite(AlphaComposite.SrcOver);

			//g2d.drawImage(frames[f], 10, panelHeight-this.frameHeight, -frameWidth, frameHeight, null);

			for (int x = xCoordinate3; x < panelWidth; x += backgroundImage3.getWidth(null)) {
				g2d.drawImage(backgroundImage3, x, 0, null);
				//g2d.drawImage(frames[f], 10, panelHeight-this.frameHeight, frameWidth, frameHeight, null);
			}

			for (int x = xCoordinate2; x < panelWidth; x += backgroundImage2.getWidth(null)) {
				g2d.drawImage(backgroundImage2, x, 0, null);
				//g2d.drawImage(frames[f], 10, panelHeight-this.frameHeight, frameWidth, frameHeight, null);
			}

			for (int x = xCoordinate1; x < panelWidth; x += backgroundImage1.getWidth(null)) {
				g2d.drawImage(backgroundImage1, x, 0, null);
				if (running == 1) {
					g2d.drawImage(frames[f], 10, panelHeight-this.frameHeight, frameWidth, frameHeight, null);
				}
				else if (running == 0) {
					g2d.drawImage(framesCrouch[f1], 10, panelHeight-this.frameHeight, frameWidth, frameHeight, null);
				}
				else if (running == 2) {
					//g2d.drawImage(frames[f], 10, panelHeight-this.frameHeight - currentY, frameWidth, frameHeight, null);
					g2d.drawImage(jump , 10, panelHeight-this.frameHeight - currentY, frameWidth, frameHeight, null);
				}

				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, WIDTH, HEIGHT);
				g2d.setColor(Color.BLACK);
				g2d.fillRect(rx, ry, 50, 50);
				getIntersection(rect1);

				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, WIDTH, HEIGHT);
				g2d.setColor(Color.BLACK);
				g2d.fillRect(this.x, y, width, height);
			}

			//g2d.drawImage(frames[f], frameWidth, panelHeight-this.frameHeight, frameWidth, frameHeight, null);

			g.drawImage(buffer, 0, 0, null);
		}
	}

	@Override
	/**
	 * Makes the window fit the background size
	 * @author Krsovskyy Andrii
	 * @return Dimension
	 */
	public Dimension getPreferredSize() {
		return new Dimension(backgroundImage1.getWidth(null), backgroundImage1.getHeight(null));
	}

	/**
	 * @deprecated
	 * @param imagePath
	 * @param x
	 * @return boolean
	 */
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

	/**
	 * Checks for collision with mask
	 * @param other
	 * @return boolean
	 * @author Krasovskyy Andrii
	 */
	public boolean getIntersection(Rectangle other) {
		other = new Rectangle(rx, ry, 50, 50);
		int x1 = Math.max(this.x, other.x);
		int y1 = Math.max(this.y, other.y);
		int x2 = Math.min(this.x + this.width, other.x + other.width);
		int y2 = Math.min(this.y + this.height, other.y + other.height);

		if (x2 < x1 || y2 < y1) {
			// Прямоугольники не пересекаются
			return false;
		}

		//int intersectionWidth = x2 - x1;
		//int intersectionHeight = y2 - y1;
		System.out.println("Collision!");
		return true;

		//return new Rectangle(x1, y1, intersectionWidth, intersectionHeight);
	}

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Moving Background");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		/**MovingBackground backgroundPanel1 = new MovingBackground(200, 20);
        frame.add(backgroundPanel1);*/

		MovingBackground backgroundPanel = new MovingBackground(0, 10);
		frame.add(backgroundPanel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		//player = new MP3Player("ForceMaker.mp3");

		/**player = new MP3Player("3.mp3");

		player.play();*/

		playerThread = new Thread(() -> {
			player = new MP3Player("3.mp3");
			player.play();
		});
		
		//Currently disabled music
		playerThread.start();

	}
}




