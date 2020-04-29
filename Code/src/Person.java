import java.util.LinkedList;

abstract public class Person {
    public Card lCard;
    public Card rCard;
    boolean inGame = false;
    boolean isExist = false;
    boolean active = false;
    boolean SB = false;
    boolean BB = false;
    int bet_now = 0;
    int balance;
    boolean isBot = true;
    String name;
    boolean hasTurn = false;
    int blind;
    public LabelValue combination = null;

    public abstract int makeTurn(LinkedList<Card> cards, Integer currentBet, Integer blind);
    /***
     * -1 - fold
     *
     * 0 - check
     *
     * x - add x
     *
     */


}
