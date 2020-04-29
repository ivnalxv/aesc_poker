import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class MainFrame extends JFrame {

    MenuPanel menuPanel;
    PreGamePanel preGamePanel;
    GamePanel gamePanel;
    List <Bot> bots;
    JPanel curPanel;
    Integer balance;
    Integer blind;


    public MainFrame(){
        super("Poker"); //Заголовок окна
        try {
            Image img = ImageIO.read(getClass().getResource("icon.png"));
            setIconImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setBounds(30, 20, 1200, 600);
        setMinimumSize(new Dimension(1200, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        menuPanel = new MenuPanel(this);
        preGamePanel = new PreGamePanel(this);
        add(menuPanel);
        curPanel = menuPanel;
        setVisible(true);
    }

    protected void changePanel(String key){
        remove(curPanel);
        switch (key){
            case "preGamePanel":
                //remove(menuPanel);
                add(preGamePanel);
                curPanel = preGamePanel;
                preGamePanel.revalidate();
                break;
            case "menuPanel":
                add(menuPanel);
                curPanel = menuPanel;
                menuPanel.revalidate();
                break;
            case "gamePanel":
                gamePanel = new GamePanel(this, bots, balance, blind);
                add(gamePanel);
                curPanel = gamePanel;
                gamePanel.revalidate();
                gamePanel.requestFocus();
                break;
        }
        repaint();
    }

    protected void setGameOpts(List <Bot> bots, Integer balance, Integer blind){
        this.bots = bots;
        this.balance = balance;
        this.blind = blind;
    }




}
