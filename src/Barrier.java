import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Barrier class
 * @author Krasovskyy Andrii
 * @author Tarasenko Misha
 */
public class Barrier {

	/**
	 * Contains current level number
	 */
	int location;

	/**
	 * x position for obstacle
	 */
	int x;

	/**
	 * y position for obstacle
	 */
	int y;

	/**
	 * Collision mask width
	 */
	int collisionWidth;

	/**
	 * Collision mask height
	 */
	int collisionHeight;

	/**
	 * Contains obstacle picture filepath
	 */
	String fileName;

	/**
	 * Generator for randomly choosing obstacle
	 */
	private static Random random = new Random();

	/**
	 * Class constructor
	 * @param location
	 */
	public Barrier(int location) {
		this.location = location;
		this.fileName = randomFileName();
	}

	/**
	 * Returns obstacle x position
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns obstacle y position
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Returns collision mask width
	 * @return collisionWidth
	 */
	public int getCollisionWidth() {
		return collisionWidth;
	}

	/**
	 * Returns collision mask height
	 * @return collisionHeight
	 */
	public int getCollisionHeight() {
		return collisionHeight;
	}

	/**
	 * Returns filepath for obstacle picture
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Randomly chooses obstacle and prepares for drawing in
	 * @return fileName
	 * @author Krasovskyy Andrii
	 * @author Tarasenko Misha
	 */
	private String randomFileName() {
		String fileName = "city_ground1.png";
		int number, groundOrSky;
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		Dimension screenSize = toolkit.getScreenSize();
		int windowWidth = screenSize.width;
		int windowHeight = (int) (screenSize.height*0.95);

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		GraphicsConfiguration config = device.getDefaultConfiguration();

		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);

		int toolbarHeight = insets.top + insets.bottom;

		windowWidth = screenSize.width;
		windowHeight = screenSize.height - toolbarHeight;

		System.out.println(windowWidth + "-" + windowHeight + "-" + toolbarHeight);

		switch (location) {
			case 1: {
				groundOrSky = random.nextInt(2);;
				if(groundOrSky < 1) {
					number = random.nextInt(7) + 1;
					fileName = "city_ground" + number + ".png";
					try {
						this.collisionWidth = ImageIO.read(new File(fileName)).getWidth() / 17 - 10;
						this.collisionHeight = ImageIO.read(new File(fileName)).getHeight() / 15;
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.x = (int) (windowWidth);
					this.y = (int) (windowHeight - 35 - collisionHeight);
				}else {
					number = random.nextInt(3) + 1;
					fileName = "city_sky" + number + ".png";
					try {
						this.collisionWidth = ImageIO.read(new File(fileName)).getWidth() / 25 - 10;
						this.collisionHeight = ImageIO.read(new File(fileName)).getHeight() / 25;
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.x = (int) (windowWidth);
					this.y = (int) (windowHeight * 0.65 - collisionHeight);
				}
				break;
			}
			case 2:{
				groundOrSky = random.nextInt(2);
				if(groundOrSky < 1) {
					number = random.nextInt(7) + 1;
					fileName = "suburb_ground" + number + ".png";
					try {
						this.collisionWidth = ImageIO.read(new File(fileName)).getWidth() / 17 - 10;
						this.collisionHeight = ImageIO.read(new File(fileName)).getHeight() / 15;
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.x = (int) (windowWidth);
					this.y = (int) (windowHeight - 35 - collisionHeight);
				}
				else {
					number = random.nextInt(3) + 1;
					fileName = "suburb_sky" + number + ".png";
					try {
						this.collisionWidth = ImageIO.read(new File(fileName)).getWidth() / 25 - 10;
						this.collisionHeight = ImageIO.read(new File(fileName)).getHeight() / 25;
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.x = (int) (windowWidth);
					this.y = (int) (windowHeight * 0.65 - collisionHeight);
				}
				break;
			}
			case 3:{
				groundOrSky = random.nextInt(2);
				if(groundOrSky < 1) {
					number = random.nextInt(8) + 1;
					fileName = "village_ground" + number + ".png";
					try {
						this.collisionWidth = ImageIO.read(new File(fileName)).getWidth() / 17 - 10;
						this.collisionHeight = ImageIO.read(new File(fileName)).getHeight() / 15;
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.x = (int) (windowWidth);
					this.y = (int) (windowHeight - 35 - collisionHeight);
				}
				else {
					number = random.nextInt(3) + 1;
					fileName = "village_sky" + number + ".png";
					try {
						this.collisionWidth = ImageIO.read(new File(fileName)).getWidth() / 25 - 10;
						this.collisionHeight = ImageIO.read(new File(fileName)).getHeight() / 25;
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.x = (int) (windowWidth);
					this.y = (int) (windowHeight * 0.65 - collisionHeight);
				}
				break;
			}
		}
		System.out.println(fileName);
		return fileName;
	}
}
