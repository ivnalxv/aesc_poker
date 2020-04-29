import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;


public class PreGamePanel extends JPanel implements ActionListener {

    MainFrame frame;
    static String [] botTypes = {"None", "T1 Easy", "T2 Medium", "T3 Hard"};
    LinkedList<BotBlock> botBlocks;
    Integer balance;
    Integer blind;
    JTextField balanceField;
    JTextField blindField;

    class BotBlock extends JPanel{
        public JComboBox comboBox;
        public BotBlock(int number){
            add(new Label("Bot number " + (number+1)));
            comboBox = new JComboBox(botTypes);
            comboBox.setPreferredSize(new Dimension(200, 20));
            comboBox.setEditable(true);
            add(comboBox);
        }
    }

    class BotsPanel extends JPanel {

        public BotsPanel(){
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            for(int i = 0; i < 6; i++){
                add(botBlocks.get(i));
            }
        }
    }

    class ParametresPanel extends JPanel{

        public ParametresPanel(PreGamePanel panel){
            setLayout(new GridLayout(0, 2));
            add(new Label("Balance"));
            balanceField = new JTextField("1000");
            KeyListener kl = new KeyListener() {
                @Override public void keyTyped(KeyEvent e) { }
                @Override public void keyReleased(KeyEvent e) { }
                @Override
                public void keyPressed(KeyEvent e) {

                }

            };

            //balanceField.addKeyListener(kl);
            add(balanceField);
            add(new Label("Big blind"));
            blindField = new JTextField("10");
            //blindField.addKeyListener(kl);
            add(blindField);

            addButton("Start", this);
        }
    }

    class OptionsPanel extends JPanel{
        ParametresPanel parametresPanel;

        public OptionsPanel(PreGamePanel panel){
            JPanel lCont = new JPanel();
            lCont.setLayout(new BoxLayout(lCont, BoxLayout.Y_AXIS));
            lCont.add(new Label("Bots options"));
            BotsPanel botsPanel = new BotsPanel();
            lCont.add(botsPanel);

            JPanel rCont = new JPanel();
            rCont.setLayout(new BoxLayout(rCont, BoxLayout.Y_AXIS));
            rCont.add(new Label("Game settings"));
            rCont.add(new ParametresPanel(panel));
            this.add(lCont);
            this.add(rCont);
        }

    }

    private boolean checkFields(){
        boolean res = true;
        int botQuant = 0;
        for (BotBlock b: botBlocks)
            if (b.comboBox.getSelectedIndex() != 0)
                botQuant++;
        if (botQuant == 0) {
            botBlocks.getFirst().comboBox.setSelectedIndex(1);
            res = false;
        }

        try {
            balance = Integer.parseInt(balanceField.getText());
        } catch (NumberFormatException ex){
            balance = 1000;
            res = false;
        }
        if (balance < 100) {
            balance = 100;
            res = false;
        }
        if (balance > 10000) {
            balance = 10000;
            res = false;
        }
        balanceField.setText(Integer.toString(balance));
        balanceField.setCaretPosition(balanceField.getText().length());

        try {
            blind = Integer.parseInt(blindField.getText());
        } catch (NumberFormatException ex){
            blind = 10;
            res = false;
        }
        if (blind > balance/20){
            blind = balance/20;
            res = false;
        }
        if (blind < 2) {
            blind = 2;
            res = false;
        }
        blindField.setText(Integer.toString(blind));
        blindField.setCaretPosition(blindField.getText().length());


        return res;
    }

    private void addButton(String caption, Container container){
        JButton button = new JButton(caption);
        button.setActionCommand(caption);
        button.addActionListener(this);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Back":
                frame.changePanel("menuPanel");
                break;
            case "Start":
                if (checkFields()) {
                    frame.setGameOpts(makeBots(), balance, blind);
                    frame.changePanel("gamePanel");
                }
                break;
            case "balanceChanged":
                ((JTextField)e.getSource()).setText("LOH!");
                break;
            case "All easy":
                for (BotBlock bb: botBlocks){
                    bb.comboBox.setSelectedIndex(1);
                }
                break;
            case "All Medium":
                for (BotBlock bb: botBlocks){
                    bb.comboBox.setSelectedIndex(2);
                }
                break;
            case "All Hard":
                for (BotBlock bb: botBlocks){
                    bb.comboBox.setSelectedIndex(3);
                }
                break;
            case "Reset":
                for (BotBlock bb: botBlocks){
                    bb.comboBox.setSelectedIndex(0);
                }
                break;
        }
    }

    private List <Bot> makeBots(){
        LinkedList <Bot> bots = new LinkedList<>();
        Random rnd = new Random(System.currentTimeMillis());
        for (int i = 0; i < 6; i++){
            Bot b = new Bot(botBlocks.get(i).comboBox.getSelectedIndex(), rnd);
            if (b.type != 0) {
                b.isExist = true;
                b.inGame = true;
            }
            bots.add(b);
        }
        return bots;
    }

    PreGamePanel(MainFrame frame){
        this.frame = frame;
        botBlocks = new LinkedList<>();

        for (int i = 0; i < 6; i++){
            botBlocks.add(new BotBlock(i));
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addButton("Back", this);
        addButton("Reset", this);
        addButton("All easy", this);
        addButton("All Medium", this);
        addButton("All Hard", this);
        add(new OptionsPanel(this));
    }



}
