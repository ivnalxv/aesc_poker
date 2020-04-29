import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.IOException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    MainFrame frame;
    Image img;
    volatile LinkedList<Person> persons;
    volatile Integer blind;
    Image [][] cards;
    volatile Player player;
    volatile Integer current_bet = 0;
    volatile Integer bank = 0;
    volatile Integer currPersNumb;
    volatile boolean bankChanged = false;
    LinkedList<Card> tableCards;
    PlayerPanel playerPanel;
    volatile boolean paused = false;
    volatile boolean entered = false;
    boolean hiddenCards = true;
    boolean pauseBetweenGames = false;
    AudioController audioController;
    public boolean mute = true;
    Image muteImg = null;


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            if (paused) paused = false;
            else paused = true;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER){
            entered = true;
        } else if (e.getKeyCode() == KeyEvent.VK_H){
            if (hiddenCards) hiddenCards = false;
            else hiddenCards = true;
        } else if (e.getKeyCode() == KeyEvent.VK_M){
            if (mute) mute = false;
            else mute = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    class GameFlow extends Thread{
        Comb comb;
        LinkedList<Card> deckCards;
        boolean round_done = false;
        Integer mood;
        Integer smallBlindNumb;
        GamePanel panel;

        /***
         * mood
         * 0 - before game (new game)
         * 1 - before flop (preflop)
         * 2 - before tern (flop)
         * 3 - before river (tern)
         * 4 - after river (river)
         * 5 - show cards
         */


        GameFlow(GamePanel panel){
            comb = new Comb();
            deckCards = new LinkedList<>();
            this.panel = panel;
            mood = 0;
            currPersNumb = 0;
            smallBlindNumb = 0;
        }

        @Override
        public void run() {
            goNext();
        }

        private void delay(long time){
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void goNext(){

            while (paused){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            if (mood == 0) {

                getCards();
                shuffleCards();
                mood = 1;
                for (Person pers : persons) {        // Gives cards for all available persons
                    if (pers.inGame) {
                        pers.lCard = deckCards.get(0);
                        pers.rCard = deckCards.get(1);
                        deckCards.remove(0);
                        deckCards.remove(0);
                    }
                }

                while (!persons.get(currPersNumb).inGame) currPersNumb = (currPersNumb+1)%7;

                delay(1500);
                smallBlindNumb = currPersNumb;
                persons.get(currPersNumb).hasTurn = true;
                persons.get(currPersNumb).SB = true;
                persons.get(currPersNumb).bet_now = blind / 2;
                persons.get(currPersNumb).balance -= blind / 2;
                current_bet = blind / 2;

                bank += blind / 2;
                bankChanged = true;
                audioController.play("turn");

                currPersNumb++;
                while (!persons.get(currPersNumb).inGame) currPersNumb = (currPersNumb+1)%7;


                delay(1500);
                persons.get(currPersNumb).hasTurn = true;
                persons.get(currPersNumb).BB = true;
                persons.get(currPersNumb).bet_now = blind;
                persons.get(currPersNumb).balance -= blind;
                current_bet = blind;

                bank += blind;
                bankChanged = true;
                audioController.play("turn");

                currPersNumb++;
                while (!persons.get(currPersNumb).inGame) currPersNumb = (currPersNumb+1)%7;

                goNext();

            } else if (mood == 5) {
                LinkedList<Person> winners = new LinkedList<>();
                hiddenCards = false;
                int max = -1;
                for (Person p : persons) if (p.inGame){
                    try {
                        p.combination = comb.getComb(tableCards, p.lCard, p.rCard);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (max < p.combination.value){
                        max = p.combination.value;
                    }
                }
                for (Person p : persons) if (p.inGame){
                    if (p.combination.value == max) winners.add(p);
                }

                if (winners.size() == 1){
                    JOptionPane.showMessageDialog(null, winners.get(0).name + " win ("+winners.get(0).combination.label+") !");
                    audioController.play("bank");
                    winners.get(0).balance += bank;
                } else {
                    String message = "";
                    for (Person p : winners){
                        message += ", " + p.name;
                    }
                    message = message.substring(2);
                    JOptionPane.showMessageDialog(null, message + " win ("+winners.get(0).combination.label+") !");
                    audioController.play("bank");
                    int tmp = bank/winners.size();
                    for (Person p : winners){
                        p.balance += tmp;
                    }
                }


                bank = 0;
                current_bet = 0;
                startNewGame();

            } else {
                Person p = persons.get(currPersNumb);
                p.hasTurn = true;
                if (p.balance != 0) {
                    if(p.isBot) delay(1500);

                    int action = p.makeTurn(tableCards, current_bet, blind);
                    if (action == -1) {   // FOLD
                        p.inGame = false;
                    } else if (action < blind) {   // CALL
                        if (p.bet_now < current_bet) {
                            int tmp = Math.min(p.balance, current_bet - p.bet_now);
                            p.bet_now += tmp;
                            bank += tmp;
                            bankChanged = true;
                            p.balance -= tmp;
                        }
                    } else {        // RAISE
                        int tmp = Math.min(action + current_bet - p.bet_now, p.balance);
                        p.bet_now += tmp;
                        bank += tmp;
                        bankChanged = true;
                        p.balance -= tmp;
                        current_bet = Math.max(current_bet, p.bet_now);
                    }
                    audioController.play("turn");
                }

                int quant_in_game = 0;
                for (Person prs : persons){
                    if (prs.inGame) quant_in_game++;
                }
                if (quant_in_game == 1){            /// ONLY ONE IS LEFT
                    p = persons.getFirst();
                    for (Person prs : persons) if (prs.inGame) p = prs;

                    JOptionPane.showMessageDialog(null, p.name + " win !");
                    audioController.play("bank");
                    p.balance+= bank;
                    bank = 0;
                    current_bet = 0;

                    startNewGame();
           /* } else {

                boolean zeroBalances = false;
                for (Person p : persons) if (p.inGame && p.balance == 0) zeroBalances = true;
                if (zeroBalances){              /// ONE BALANCE IS NULL
                    while (tableCards.size() < 5) tableCards.add(deckCards.removeFirst()); /// DOES IT WORK?
                    delay(1500);
                    mood = 5;*/

                } else {
                    round_done = true;
                    for (Person prs : persons) {
                        if (!prs.hasTurn && prs.inGame) round_done = false;
                    }

                    if (equal_bets() && round_done) {       /// NEXT MOOD
                        int notNullBalances = 0;
                        for (Person prs : persons) if (prs.inGame && prs.balance != 0) notNullBalances++;
                        if (notNullBalances == 1) {
                            while (tableCards.size() < 5) tableCards.add(deckCards.removeFirst()); /// DOES IT WORK?
                            delay(1500);
                            mood = 5;
                        } else {
                            delay(2000);
                            for (Person prs : persons) prs.hasTurn = false;
                            round_done = false;
                            mood++;
                            if (mood == 2) {
                                for (int i = 0; i < 3; ++i) {
                                    tableCards.add(deckCards.get(0));
                                    deckCards.remove(0);
                                }
                            } else if (mood == 3 || mood == 4) {
                                tableCards.add(deckCards.get(0));
                                deckCards.remove(0);
                            }
                            current_bet = 0;
                            for (Person prs : persons) {
                                if (prs.inGame) prs.bet_now = 0;
                            }
                            currPersNumb = -1;
                            while (!persons.get(++currPersNumb).SB) { }
                            while (!persons.get(currPersNumb).inGame) {
                                currPersNumb++;
                                currPersNumb %= 7;
                            }

                        }
                    } else {
                        currPersNumb++;
                        currPersNumb %= 7;
                        while (!persons.get(currPersNumb).inGame) {
                            currPersNumb++;
                            currPersNumb %= 7;
                        }
                    }
                    goNext();
                }
            }

        }

        private boolean equal_bets(){
            for (Person p : persons){
                if (p.bet_now != current_bet && p.inGame && p.balance > 0) return false;
            }
            return true;
        }


        private void shuffleCards(){
            Collections.shuffle(deckCards, new Random(System.currentTimeMillis()));
        }

        private void getCards(){
            tableCards.clear();
            deckCards.clear();
            for (int i = 0; i < 4; i++){
                for (int j = 2; j < 15; j++){
                    deckCards.add(new Card(i, j));
                }
            }
        }

        private void startNewGame() {
            entered = false;
            pauseBetweenGames = true;
            while (!entered) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            hiddenCards = true;
            pauseBetweenGames = false;
            smallBlindNumb++;
            while (!persons.get(smallBlindNumb).isExist) smallBlindNumb = (smallBlindNumb+1)%7;

            mood = 0;
            int quant = 0;
            for (Person p : persons) {
                if (p.balance <= 0) {
                    p.isExist = false;
                    p.inGame = false;
                }
                if (p.isExist) {
                    p.inGame = true;
                    quant++;
                }
                p.SB = false;
                p.BB = false;
                p.combination = null;
            }
            Person p = null;
            if (quant == 1) {
                for (Person prs : persons) {
                    if (prs.inGame) p = prs;
                }
                audioController.play("win");
                JOptionPane.showMessageDialog(null, "Game over\nyou is only one who left");
                frame.changePanel("preGamePanel");
            } else if (!player.inGame) {
                audioController.play("lost");
                JOptionPane.showMessageDialog(null, "Game over\nyou lost all your money!");
                frame.changePanel("preGamePanel");
            } else {
                goNext();
            }

        }
    }


    public GamePanel(MainFrame frame, List <Bot> bots, Integer balance, Integer blind) {
        this.frame = frame;
        this.blind = blind;
        persons = new LinkedList<>();
        tableCards = new LinkedList<>();
        setFocusable(true);
        setLayout(null);
        playerPanel = new PlayerPanel();
        playerPanel.setVisible(false);
        add(playerPanel);
        requestFocus();
        addKeyListener(this);
        audioController = new AudioController(this);
        loadFiles();

        for (int i = 0; i < 3; i++){
            persons.add(bots.get(i));
        }


        player = new Player();
        playerPanel.setPlayer(player);
        player.setPlayerPanel(playerPanel);
        persons.add(player);

        for (int i = 3; i < 6; i++){
            persons.add(bots.get(i));
        }

        for (Person pers : persons){
            pers.balance = balance;
        }

        new GameFlow(this).start();

        /// LAMBDA IS A SOMETHING
        new Timer(100, e-> repaint()).start();

    }

    private void loadFiles(){
        try {
            img = ImageIO.read(getClass().getResource("table1.jpg"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't get table image");
            e.printStackTrace();
        }
        try {
            muteImg = ImageIO.read(getClass().getResource("mute.png"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't get table image");
            e.printStackTrace();
        }

        cards = new Image [4][15];
        for (int i = 0; i < 4; i++){
            for (int j = 2; j < 15; j++){
                try {
                    String s = "/cards/" +i+"_"+j+".png";
                    cards[i][j] = ImageIO.read(getClass().getResource(s));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            cards[0][0] = ImageIO.read(getClass().getResource("/cards/backSide.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Pause":
                frame.changePanel("menuPanel");
                break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(new Color(0, 26, 9));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(img, this.getWidth()/2-800, this.getHeight()/2-500, 1600, 1000, null); // table

        drawBankBlock(g);
        drawPersons(g);
        drawTableCards(g);

        Integer centerX = this.getWidth() / 2;
        Integer centerY = this.getHeight() / 2;
        if (paused){
            g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 30));
            g.setColor(Color.ORANGE);
            g.drawString("PAUSED", centerX-460, centerY-200);
        }
        if (pauseBetweenGames){
            g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 30));
            g.setColor(Color.ORANGE);
            g.drawString("PRESS ENTER", centerX+300, centerY-200);
        }
        if (mute){
            g.drawImage(muteImg, centerX+490, centerY+200, 50, 50, null);
        }
        playerPanel.setBounds(centerX+90, centerY+130, 150, 100);
    }

    private void drawBankBlock(Graphics g){
        g.setColor(Color.GRAY);
        if (bankChanged){
            bankChanged = false;
            g.setColor(Color.DARK_GRAY);
        }
        g.fillRect(this.getWidth()/2-70, this.getHeight()/2-200, 140, 80);
        g.setColor(Color.WHITE);
        g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 15));
        g.drawString("Bank: " + bank.toString(), this.getWidth()/2-50, this.getHeight()/2-175);
        g.drawString("Bet: " + current_bet.toString(), this.getWidth()/2-50, this.getHeight()/2-140);
    }

    private void drawTableCards(Graphics g){
        Integer centerX = this.getWidth() / 2;
        Integer centerY = this.getHeight() / 2;
        int x = centerX-250;
        for (Card c : tableCards) {
            g.drawImage(cards[c.mast][c.value], x, centerY - 60, 96, 120, null);
            x+= 100;
        }
    }

    private void drawPersons(Graphics g) {
        Integer centerX = this.getWidth() / 2;
        Integer centerY = this.getHeight() / 2;

        for (int i = 0; i < 7; i++){
            if (persons.get(i).inGame){
                switch (i) {
                    case 0:
                        drawPersonBlock(g, centerX+180, centerY-180, persons.get(i), i);
                        break;
                    case 1:
                        drawPersonBlock(g, centerX+450, centerY-100, persons.get(i), i);
                        break;
                    case 2:
                        drawPersonBlock(g, centerX+380, centerY+180, persons.get(i), i);
                        break;
                    case 3:
                        drawPersonBlock(g, centerX, centerY+200, persons.get(i), i);
                        break;
                    case 4:
                        drawPersonBlock(g, centerX-380, centerY+180, persons.get(i), i);
                        break;
                    case 5:
                        drawPersonBlock(g, centerX-450, centerY-100, persons.get(i), i);
                        break;
                    case 6:
                        drawPersonBlock(g, centerX-180, centerY-180, persons.get(i), i);
                        break;

                }
            }
        }

    }

    private void drawPersonBlock(Graphics g, Integer x, Integer y, Person p, int i){
        g.setColor(Color.BLACK);
        g.drawRect(x-75, y-65, 150, 130);
        if (hiddenCards && p.isBot){
            g.drawImage(cards[0][0], x-70, y-60, 96, 120, null);
            g.drawImage(cards[0][0], x-25, y-60, 96, 120, null);
        } else {
            g.drawImage(cards[p.lCard.mast][p.lCard.value], x - 70, y - 60, 96, 120, null);
            g.drawImage(cards[p.rCard.mast][p.rCard.value], x - 25, y - 60, 96, 120, null);
        }
        g.setColor(Color.GRAY);
        if (i == currPersNumb) g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(x-95, y, 190, 75, 10, 10);
        g.setColor(Color.WHITE);
        g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 15));
        g.drawString(p.name + "   Bet: " + p.bet_now, x-70,y+20);
        String bal_text = " Balance: " + p.balance;
        if (p.isBot){
            bal_text = "T"+ ((Bot) p).type + bal_text;
        }
        if (p.SB) bal_text += "  SB";
        if (p.BB) bal_text += "  BB";
        g.drawString(bal_text, x-70,y+40);
        if (p.combination != null){
            String combStr = p.combination.label;
            if (!hiddenCards) combStr+= " " + p.combination.value;
            g.drawString(combStr, x-70,y+60);
        }

    }

}
