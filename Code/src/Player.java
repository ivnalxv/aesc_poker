import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;



public class Player extends Person {
    private static final Object monitor = new Object();
    Integer flag;       // -2 - none, -1 - fold, 0-bet - check/call, > - raise
    private PlayerPanel playerPanel;
    int current_bet;

    public Player() {
        isBot = false;
        isExist = true;
        inGame = true;
        name = "Ivag";
        flag = -2;
    }

    @Override
    public synchronized int makeTurn(LinkedList<Card> cards, Integer current_bet, Integer blind) {
        this.blind = blind;
        this.current_bet = current_bet;
        playerPanel.refreshButton();
        playerPanel.setVisible(true);
        while (flag == -2){
            try {
                 Thread.sleep(100);
            } catch (InterruptedException e) {
                 e.printStackTrace();
            }
        }
        int tmp = flag;
        playerPanel.setVisible(false);
        playerPanel.typedBet = 0;
        flag = -2;
        return tmp;
    }


    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public void setPlayerPanel(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }
}
