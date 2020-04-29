import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

public class PlayerPanel extends JPanel{
    Button check_btn;
    Button fold_btn;
    TextField textField;
    Player player;
    int typedBet = 0;


    public PlayerPanel() {
        check_btn = new Button("Call");
        check_btn.addActionListener((a)->player.setFlag(typedBet));
        fold_btn = new Button(("Fold"));
        fold_btn.addActionListener((a)->player.setFlag(-1));
        textField = new TextField(13);

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    typedBet = Math.min(Integer.parseInt(textField.getText()), player.balance);
                    textField.setText(Integer.toString(typedBet));
                    if (typedBet < player.blind){
                        if (player.bet_now == player.current_bet)
                            check_btn.setLabel("Check");
                        else check_btn.setLabel("Call");
                    }
                    else check_btn.setLabel("Raise");
                } catch (NumberFormatException ex){
                    textField.setText("");
                    if (player.bet_now == player.current_bet)
                        check_btn.setLabel("Check");
                    else check_btn.setLabel("Call");
                }
                textField.setCaretPosition(textField.getText().length());

            }
        });
        add(textField);
        add(check_btn);
        add(fold_btn);

    }

    public void refreshButton(){
       if (typedBet < player.blind){
            if (player.bet_now == player.current_bet)
                check_btn.setLabel("Check");
            else check_btn.setLabel("Call");
        }
        else check_btn.setLabel("Raise");
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
