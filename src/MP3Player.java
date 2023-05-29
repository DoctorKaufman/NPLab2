/**import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.Timer;

public class MP3Player {
    private static boolean end;

    public MP3Player() {
        end = false;
    }

    public void play(String filePath) throws IOException {
        while (!end) {
            try {
                FileInputStream fileInputStream = new FileInputStream(filePath);
                AudioDevice audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();
                AdvancedPlayer audioPlayer = new AdvancedPlayer(fileInputStream, audioDevice);
                audioPlayer.play();
                fileInputStream.close();
            } catch (JavaLayerException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        end = true;
    }

    static int i = 0;

    public static void main(String[] args) throws IOException {
        MP3Player player = new MP3Player();
        player.play("obitel-zla.mp3");



        Timer t1 = new Timer(100, e-> {
        	i++;
        	if (i == 5) {
        		player.stop();
        	}
        });

        t1.start();

        // Чтобы остановить проигрывание, вызовите метод stop()
        // player.stop();
    }
}*/

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
	 * Plays mp3 track in cycle
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

		/*Timer t1 = new Timer(1000, e-> {
        	i++;
        	if (i == 5) {
        		player.stop();
        	}
        });

        t1.start();*/

		player.play();
	}
}

