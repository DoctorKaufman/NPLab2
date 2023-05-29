import javax.swing.*;
import java.awt.*;

public class MovingBackground extends JPanel {
    private Image backgroundImage1;
    private int xCoordinate1;
    
    private Image backgroundImage2;
    private int xCoordinate2;
    
    private Image backgroundImage3;
    private int xCoordinate3;

    public MovingBackground(int n, int t) {
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

        // Установка начальной координаты по оси x
        //xCoordinate = 0;

        xCoordinate1 = n;
        
        xCoordinate2 = n;
        
        xCoordinate3 = n;
        
        // Запуск таймера для обновления положения фона
        //Timer timer = new Timer(10, e -> {
        Timer timer = new Timer(t, e -> {
            // Обновление координаты
            xCoordinate1 -= 3;
            
            xCoordinate2 -= 2;
            
            xCoordinate3 -= 1;

            // Если фон вышел за пределы панели, сбросить его координату
            if (xCoordinate1 <= -backgroundImage1.getWidth(null)) {
                xCoordinate1 = 0;
            }

            // Перерисовка панели
            repaint();
        });
        
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Создание двойного буфера
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        Image buffer = createImage(panelWidth, panelHeight);
        Graphics2D g2d = (Graphics2D) buffer.getGraphics();
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.SrcOver);
        // Отрисовка фона с непрерывным движением на буфер
        
        for (int x = xCoordinate3; x < panelWidth; x += backgroundImage3.getWidth(null)) {
            g2d.drawImage(backgroundImage3, x, 0, null);
        }
        
        for (int x = xCoordinate2; x < panelWidth; x += backgroundImage2.getWidth(null)) {
            g2d.drawImage(backgroundImage2, x, 0, null);
        }
        
        for (int x = xCoordinate1; x < panelWidth; x += backgroundImage1.getWidth(null)) {
            g2d.drawImage(backgroundImage1, x, 0, null);
        }

        // Отрисовка буфера на панели
        g.drawImage(buffer, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
        // Установка предпочтительного размера панели, основанного на размере изображения фона
        return new Dimension(backgroundImage1.getWidth(null), backgroundImage1.getHeight(null));
    }

    public static void main(String[] args) {
        // Создание окна
        JFrame frame = new JFrame("Moving Background");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**MovingBackground backgroundPanel1 = new MovingBackground(200, 20);
        frame.add(backgroundPanel1);*/
        
        // Создание панели с движущимся фоном
        MovingBackground backgroundPanel = new MovingBackground(0, 20);
        frame.add(backgroundPanel);

        // Установка размера окна
        frame.pack();
        frame.setLocationRelativeTo(null); // Центрирование окна на экране
        frame.setVisible(true);
    }
}




