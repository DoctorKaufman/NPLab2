import javazoom.jl.player.Player;
import java.io.FileInputStream;

/**
 * MP3 player class
 * @author Krasovskyy Andrii
 */
public class MP3Player {
	private static int i = 0;
	private String filename;
	private Player player;
	boolean end = false;

	/**
	 * Class constructor
	 * @param filename
	 */
	public MP3Player(String filename) {
		this.filename = filename;
	}

	/**
	 * Plays mp3 track in cycle
	 */
	public void play() {
		while (!end) {
			try {
				FileInputStream fis = new FileInputStream(filename);
				player = new Player(fis);
				player.play();
			} catch (Exception e) {
				System.out.println("Ошибка при проигрывании файла: " + e);
			}
		}
	}

	/**
	 * Plays mp3 track
	 */
	public void playOneTime() {
		try {
			FileInputStream fis = new FileInputStream(filename);
			player = new Player(fis);
			player.play();
		} catch (Exception e) {
			System.out.println("Ошибка при проигрывании файла: " + e);
		}
	}

	/**
	 * Stops the mp3 track
	 */
	public void stop() {
		if (player != null) {
			end  = true;
			player.close();
		}
	}

	public static void main(String[] args) {
		MP3Player player = new MP3Player("ForceMaker.mp3");

		player.play();
	}
}

