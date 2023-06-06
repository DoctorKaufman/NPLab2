import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
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
	public boolean end = false;

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
	 * Fall animation frames
	 */
	private BufferedImage[] framesFall;

	/**
	 * Run frames counter
	 */
	int f = 0;

	/**
	 * Fall frames counter
	 */
	int f2 = 0;

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
	private int currentDeltaY = 0;

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

	Rectangle rect1;

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
	 * Padding regulation (for collision mask)
	 */
	int div = 4;

	double widthMaskSetting = 2.5;

	/**
	 * Padding regulation (for collision mask)
	 */
	int paddingStart = 20;

	/**
	 * Flag for collision
	 */
	private boolean collided = false;

	/**
	 * Flag for finishing fall animation
	 */
	private boolean finishFall = false;

	/**
	 * Contains obstacle
	 */
	private Barrier barrier;

	/**
	 * Image of obstacle
	 */
	private BufferedImage barrierFileName;

	int location;

	private int windowWidth;

	private int windowHeight;

	/**
	 * Filename to load background music
	 */
	private static String filename;

	private int obstaclesLeft;

	private int animationSpeed;

	JFrame frame;

	boolean isPause = false;

	JLabel obstacAmount;

	/**
	 * Describes state of current JPanel
	 */
	boolean isFinished = false;

	/**
	 * Class constructor (starts the GUI and timer animations)
	 * @param t
	 * @throws IOException
	 * @author Krasovskyy Andrii
	 */
	public MovingBackground(int t, int location, int obstacleSpeed, int animationSpeed, String filename, int obstaclesLeft, JFrame frame) throws IOException {

		MovingBackground.filename = filename;

		this.location = location;

		this.obstaclesLeft = obstaclesLeft;

		this.frame = frame;

		// Получаем экземпляр класса Toolkit
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		// Получаем размер экрана в пикселях
		Dimension screenSize = toolkit.getScreenSize();
		windowWidth = screenSize.width;
		windowHeight = (int) (screenSize.height*0.95);

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		GraphicsConfiguration config = device.getDefaultConfiguration();

		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int toolbarHeight = insets.top + insets.bottom;

		windowWidth = screenSize.width;
		windowHeight = screenSize.height - toolbarHeight;

		System.out.println(windowWidth + "-" + windowHeight + "-" + toolbarHeight);


		System.out.println("width: " + windowWidth);
		System.out.println("height: " + windowHeight);

		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(this);

		//Встановлюємо розмір кадру анімації персонажа
		this.frameWidth = ImageIO.read(new File("run1.png")).getWidth()/10;
		this.frameHeight = ImageIO.read(new File("run1.png")).getHeight()/10;
		System.out.println(frameWidth + "-" + frameHeight);

		//Встановлюємо координати маски колізії та її розмір (для персонажа)
		x = paddingStart + frameWidth/div;
		y = (int) (windowHeight - this.frameHeight - currentDeltaY + 30);
		System.out.println(x);
		System.out.println(y);
		width = (int) (frameWidth/widthMaskSetting);
		height = 350;

		barrier = new Barrier(this.location);
		barrierFileName = ImageIO.read(new File(barrier.getFileName()));
		rx = barrier.getX();
		ry = barrier.getY();
		rect1 = new Rectangle(rx, ry, barrier.collisionWidth, barrier.collisionHeight);

		if(location == 1){
			obstacAmount = new JLabel(obstaclesLeft + " / 15");
			obstacAmount.setFont(new Font(Font.SERIF, Font.PLAIN,  35));
			obstacAmount.setBounds(50, 50 , 100, 50);
			add(obstacAmount);
		}else if(location == 2){
			obstacAmount = new JLabel(obstaclesLeft + " / 20");
			obstacAmount.setFont(new Font(Font.SERIF, Font.PLAIN,  35));
			obstacAmount.setBounds(50, 50 , 100, 50);
			add(obstacAmount);
		}else{
			obstacAmount = new JLabel(obstaclesLeft + " / 25");
			obstacAmount.setFont(new Font(Font.SERIF, Font.PLAIN,  35));
			obstacAmount.setBounds(50, 50 , 100, 50);
			add(obstacAmount);
		}

		frames = new BufferedImage[6];

		framesCrouch = new BufferedImage[4];

		framesFall = new BufferedImage[5];

		for (int i = 1; i <= 6; ++i) {
			frames[i-1] = ImageIO.read(new File("run"+i+".png"));
		}

		for (int i = 1; i <= 3; ++i) {
			framesCrouch[i-1] = ImageIO.read(new File("sil"+i+".png"));
		}

		for (int i = 1; i <= 5; ++i) {
			framesFall[i-1] = ImageIO.read(new File("fall"+i+".png"));
		}

		framesCrouch[3] = ImageIO.read(new File("going_sit.png"));

		jump = ImageIO.read(new File("jump.png"));

		playerThread = new Thread(() -> {
			player = new MP3Player(filename);
			player.play();
		});

		playerThread.start();

		//Параметри для стрибка
		currentDeltaY = 0;
		direction = -15;

		switch (this.location) {
		case 1: {
			backgroundImage1 = new ImageIcon("Game_city1_city.png").getImage().getScaledInstance(windowWidth, windowHeight, 0);

			backgroundImage2 = new ImageIcon("Game_city1_back_city.png").getImage().getScaledInstance(windowWidth, windowHeight, 0);

			backgroundImage3 = new ImageIcon("Game_city1_back_sky.png").getImage().getScaledInstance(windowWidth, windowHeight, 0);
			break;
		}
		case 2:{
			backgroundImage1 = new ImageIcon("Game_suburb1_city.png").getImage().getScaledInstance(windowWidth, windowHeight, 0);

			backgroundImage2 = new ImageIcon("Game_suburb1_back_city.png").getImage().getScaledInstance(windowWidth, windowHeight, 0);

			backgroundImage3 = new ImageIcon("Game_suburb1_back_sky.png").getImage().getScaledInstance(windowWidth, windowHeight, 0);
			break;
		}
		case 3:{
			backgroundImage1 = new ImageIcon("Game_village1_village.png").getImage().getScaledInstance(windowWidth, windowHeight, 0);

			backgroundImage2 = new ImageIcon("Game_village1_back_mount.png").getImage().getScaledInstance(windowWidth, windowHeight, 0);

			backgroundImage3 = new ImageIcon("Game_village1_back_sky.png").getImage().getScaledInstance(windowWidth, windowHeight, 0);
			break;
		}
		}


		xCoordinate1 = 0;

		xCoordinate2 = 0;

		xCoordinate3 = 0;

		//Timer for background movement
		Timer timer = new Timer(t, e -> {
			if (end == false) {
				//Тут ми рухаємо фон
				xCoordinate1 -= 3;

				xCoordinate2 -= 2;

				xCoordinate3 -= 1;

				if (xCoordinate1 <= -backgroundImage1.getWidth(null)) {
					xCoordinate1 = 0;
				}

				repaint();
			}
		});


		Timer barrierTimer = new Timer(obstacleSpeed, e -> {
			if (end == false) {
				rx -= 20;
				if (rx <= -250) {
					barrier = new Barrier(this.location);
					try {
						barrierFileName = ImageIO.read(new File(barrier.getFileName()));
						this.obstaclesLeft--;
						if (this.obstaclesLeft <= 0) {
							end = true;
							if(location == 1){
								GoSecond goSecond = new GoSecond();
								this.removeAll();
								frame.dispose();
								cleanup();
							}else if(location == 2){
								GoThird goThird = new GoThird();
								this.removeAll();
								frame.dispose();
								cleanup();
							}else{
								Win win = new Win(this);
								this.removeAll();
								frame.dispose();
								cleanup();
							}
							isFinished = true;
							System.out.println("END OF LEVEL!!!");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					rx = barrier.getX();
					ry = barrier.getY();
					rect1 = new Rectangle(rx, ry, barrier.collisionWidth, barrier.collisionHeight);
				}

				repaint();
			}
		});


		//Timer for running and jumping animations
		Timer timer1 = new Timer(animationSpeed, e -> {
			if(!end) {
				//Here should be your collision check (add checking through masks ArrayList)
				//It's quite possible to move check in a separate method
				Rectangle other = new Rectangle(rx, ry, barrier.getCollisionWidth(), barrier.getCollisionHeight());
				if (getIntersection(other)) {
					if (crouchTimer.isRunning()) {
						crouchTimer.stop();
					}
					repaint();
					player.stop();
					playerThread.interrupt();
					collided  = true;
					if (running == 0) {
						f2 = 2;
					}
					running = 3;
				}

				//Змінюємо кадри бігу
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

				repaint();

				if (running == 3) {
					if (f2 < 4) {
						f2++;
					}
					repaint();
					if (f2 == 4) {
						finishFall = true;
						end = true;
						if (end == true && allowEndSound  == true) {
							allowEndSound = false;

							playerThread = new Thread(() -> {
								player = new MP3Player("e1.mp3");
								player.playOneTime();
							});

							playerThread.start();

							Thread playerThread1 = new Thread(() -> {
								MP3Player player1 = new MP3Player("e2.mp3");
								player1.playOneTime();
								allowEndSound = false;
							});
							playerThread1.start();

							isFinished = true;

							Runtime runtime = Runtime.getRuntime();
							long usedMemory = runtime.totalMemory() - runtime.freeMemory();
							long maxMemory = runtime.maxMemory();

							System.out.println("Використано пам'яті: " + usedMemory / (1024 * 1024) + " МБ");
							System.out.println("Максимально доступна пам'ять: " + maxMemory / (1024 * 1024) + " МБ");

							Lost lost = new Lost(this);

							runtime = Runtime.getRuntime();
							usedMemory = runtime.totalMemory() - runtime.freeMemory();
							maxMemory = runtime.maxMemory();

							System.out.println("Використано пам'яті: " + usedMemory / (1024 * 1024) + " МБ");
							System.out.println("Максимально доступна пам'ять: " + maxMemory / (1024 * 1024) + " МБ");

							this.removeAll();
							frame.dispose();
							frame.removeAll();
							cleanup();

							runtime = Runtime.getRuntime();
							usedMemory = runtime.totalMemory() - runtime.freeMemory();
							maxMemory = runtime.maxMemory();

							System.out.println("Використано пам'яті: " + usedMemory / (1024 * 1024) + " МБ");
							System.out.println("Максимально доступна пам'ять: " + maxMemory / (1024 * 1024) + " МБ");
						}
					}

				}

				if (running == 2 || (running == 3 && finishFall != true)) {
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
						currentDeltaY = 0;
						if (running == 2) {
							enableJump = true;
							enableCrouch = true;
						}

						if(running == 2) {
							running = 1;
						}
						else {
							running = 3;
						}

						currentDeltaY = 0;

						//Change collision mask
						x = paddingStart + frameWidth/div;
						y = (int) (windowHeight - this.frameHeight - currentDeltaY + 30);
						System.out.println(x);
						System.out.println(y);
						width = (int) (frameWidth/widthMaskSetting);
						height = 350;
					}

					currentDeltaY  = currentDeltaY + d;

					if (running == 2 && i != 24) {
						y = (int) (windowHeight - this.frameHeight - currentDeltaY + 30);
					}

					i++;
				}
			}
		});

		//Timer for crouch animations
		crouchTimer = new Timer(animationSpeed, e -> {
			if(standUp == false) {
				if (running == 0 && enableCrouch  == true) {
					if(f1  == 2 || f1 == 3) {
						if (f1 == 3) {
							f1  = 2;
						}
						else if (f1 == 2) {
							f1 = 3;
						}
					}
					else {
						if (f1 == 0) {
							x = paddingStart + frameWidth/div;
							y = (int) (windowHeight - this.frameHeight + 30);
							System.out.println(x);
							System.out.println(y);
							width = (int) (frameWidth/widthMaskSetting);
							height = 350;
						}
						if (f1 == 0) {
							x = paddingStart + frameWidth/div;
							y = (int) (windowHeight - this.frameHeight + 30 + 55);
							System.out.println(x);
							System.out.println(y);
							width = (int) (frameWidth/widthMaskSetting);
							height = 350 - 55;
						}
						if (f1 == 1) {
							x = paddingStart + frameWidth/div;
							y = (int) (windowHeight - this.frameHeight + 30 + 125);
							System.out.println(x);
							System.out.println(y);
							width = (int) (frameWidth/widthMaskSetting);
							height = 350 - 125;
						}
						repaint();
						f1++;
					}
				}
			}
			else {
				if(f1 == 2 || f1== 3) {
					x = paddingStart + frameWidth/div;
					y = (int) (windowHeight - this.frameHeight + 30 + 55);
					System.out.println(x);
					System.out.println(y);
					width = (int) (frameWidth/widthMaskSetting);
					height = 350 - 55;
					f1 = 1;
					repaint();
				}
				else if (f1 == 1) {
					x = paddingStart + frameWidth/div;
					y = (int) (windowHeight - this.frameHeight + 30);
					System.out.println(x);
					System.out.println(y);
					width = (int) (frameWidth/widthMaskSetting);
					height = 350;
					f1--;
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
		});

		timer.start();
		timer1.start();
		barrierTimer.start();
	}

	@Override
	/**
	 * Key listeners for playing game
	 * @author Krasovskyy Andrii
	 */
	public boolean dispatchKeyEvent(KeyEvent e) {
		if(!isFinished) {
			if (collided == false && end == false) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						standUp = false;
						enableJump = false;
						if (enableCrouch == true) {
							running = 0;
							crouchTimer.start();
						}
					}
					else if (e.getKeyCode() == KeyEvent.VK_UP) {
						enableCrouch = false;
						if (enableJump == true) {
							running = 2;
							enableJump = false;
							enableCrouch = false;
							x = paddingStart + frameWidth/div;
							y = windowHeight - this.frameHeight + 30;
							System.out.println(x);
							System.out.println(y);
							width = (int) (frameWidth/widthMaskSetting);
							height = 280;
						}
					}
				}

			}

			if (e.getID() == KeyEvent.KEY_RELEASED) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					standUp = true;
				}
			}


			if (e.getID() == KeyEvent.KEY_PRESSED) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE && !isPause) {
					if(end == true && isPause == true) {
						end = false;
					}
					else {
						end = true;
						isPause = true;
						Pause pause = new Pause(this, frame);
						pause.setVisible(true);
						if(!end){
							try {
								Thread.sleep(1000);
							} catch (InterruptedException ex) {
								throw new RuntimeException(ex);
							}
						}
					}
				}
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
		super.paintComponent(g);

		int panelWidth = getWidth();
		int panelHeight = getHeight();
		startY = panelHeight-this.frameHeight-100;
		Image buffer = createImage(panelWidth, panelHeight);
		Graphics2D g2d = (Graphics2D) buffer.getGraphics();
		g2d.setComposite(AlphaComposite.SrcOver);


		for (int x = xCoordinate3; x < panelWidth; x += backgroundImage3.getWidth(null)) {
			g2d.drawImage(backgroundImage3, x, 0, panelWidth, panelHeight, null);
		}

		for (int x = xCoordinate2; x < panelWidth; x += backgroundImage2.getWidth(null)) {
			g2d.drawImage(backgroundImage2, x, 0, panelWidth, panelHeight, null);
		}

		for (int x = xCoordinate1; x < panelWidth; x += backgroundImage1.getWidth(null)) {
			g2d.drawImage(backgroundImage1, x, 0, panelWidth, panelHeight, null);

			//Малює маску колізії (потім прибрати) (devtools)
			/*g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			g2d.setColor(Color.WHITE);
			g2d.fillRect(this.x, y, width, height);*/

			if (running == 1) {
				g2d.drawImage(frames[f], 10, windowHeight-this.frameHeight, frameWidth, frameHeight, null);
			}
			else if (running == 0) {
				g2d.drawImage(framesCrouch[f1], 10, windowHeight-this.frameHeight, frameWidth, frameHeight, null);
			}
			else if (running == 2) {
				g2d.drawImage(jump , 10, windowHeight-this.frameHeight - currentDeltaY, frameWidth, frameHeight, null);
			}
			else if (running == 3) {
				if (f2 == 0 || f2 == 1) {
					g2d.drawImage(framesFall[f2] , 10, windowHeight-this.frameHeight, (int) (frameWidth*1.27), frameHeight, null);
				}
				if (f2 == 2 || f2 == 3) {
					g2d.drawImage(framesFall[f2] , 10, windowHeight-this.frameHeight, (int) (frameWidth*1.45), frameHeight, null);
				}
				if (f2 == 4) {
					g2d.drawImage(framesFall[f2] , 10, windowHeight-this.frameHeight + 245, (int) (frameHeight/1.1), (int) (frameWidth/(1.7*1.1)), null);
				}
			}


			g2d.drawImage(barrierFileName, rx, ry, rect1.width, rect1.height, null);

			remove(obstacAmount);
			if(location == 1){
				obstacAmount = new JLabel(15 - obstaclesLeft + " / 15");
				obstacAmount.setFont(new Font(Font.SERIF, Font.PLAIN,  35));
				obstacAmount.setBounds(50, 50 , 150, 50);
				add(obstacAmount);
			}else if(location == 2){
				obstacAmount = new JLabel(20 - obstaclesLeft + " / 20");
				obstacAmount.setFont(new Font(Font.SERIF, Font.PLAIN,  35));
				obstacAmount.setBounds(50, 50 , 150, 50);
				add(obstacAmount);
			}else{
				obstacAmount = new JLabel(25 - obstaclesLeft + " / 25");
				obstacAmount.setFont(new Font(Font.SERIF, Font.PLAIN,  35));
				obstacAmount.setBounds(50, 50 , 150, 50);
				add(obstacAmount);
			}

			//Theese two lines are for drawing obstacle collision mask (devtool)
			//g2d.setColor(Color.BLACK);
			//g2d.fillRect(rx, ry, barrier.getCollisionWidth(), barrier.getCollisionHeight());
		}

		g.drawImage(buffer, 0, 0, null);
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
	 * Checks for collision with mask
	 * @param other
	 * @return boolean
	 * @author Krasovskyy Andrii
	 */
	public boolean getIntersection (Rectangle other) {
		int x1 = Math.max(this.x, other.x);
		int y1 = Math.max(this.y, other.y);
		int x2 = Math.min(this.x + this.width, other.x + other.width);
		int y2 = Math.min(this.y + this.height, other.y + other.height);

		if (x2 < x1 || y2 < y1) {
			return false;
		}

		System.out.println("Collision!");
		return true;
	}

	/**
	 * Cleanup method to release resources (up to 1 GB)
	 */
	public void cleanup() {
		player.stop();
		playerThread.interrupt();

		backgroundImage1 = null;
		backgroundImage2 = null;
		backgroundImage3 = null;
		jump = null;

		frames = null;
		framesCrouch = null;
		framesFall = null;

		barrierFileName = null;
		rect1 = null;
		obstacAmount = null;

		frame = null;

		System.gc();
	}
}



