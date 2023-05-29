import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MyPanel extends JPanel implements KeyEventDispatcher {

    private Character man;
    private long previousWorldUpdateTime; // Храним здесь момент времени когда физика мира обновлялась в последний раз

    public MyPanel() throws IOException {
        this.man = new Character(200);
        this.previousWorldUpdateTime = System.currentTimeMillis();

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        man.draw(g, this.getWidth(), this.getHeight());
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) { // Если кнопка была нажата (т.е. сейчас она зажата)
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                man.startRunningLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                man.startRunningRight();
            }
        }

        if (e.getID() == KeyEvent.KEY_RELEASED) {     // Если кнопка была отпущена - мы должны прекратить бег
            if (e.getKeyCode() == KeyEvent.VK_LEFT) { // но только бег в ту сторону, которой соответствует отпущенная кнопка
                man.stopRunningLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                man.stopRunningRight();
            }
        }

        return false;
    }

    void updateWorldPhysics() {
        long currentTime = System.currentTimeMillis();
        long dt = currentTime - previousWorldUpdateTime; // нашли сколько миллисекунд прошло с предыдущего обновления физики мира

        man.update(dt);

        previousWorldUpdateTime = currentTime;
    }
    
    public static void main(String[] args) throws InterruptedException, IOException {
        MyPanel panel = new MyPanel();

        JFrame frame = new JFrame();
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);

        while (true) {
            frame.repaint();
            panel.updateWorldPhysics(); // вызываем чтобы обновить состояние физики мира (движение персонажа)
            Thread.sleep(20);
        }
    }
}