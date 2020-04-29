import sun.plugin2.message.Message;

import javax.swing.*;
import java.io.*;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.Random;


public class Bot extends Person {

    int type;
    public static String [] names = {"Liam", "Noah", "William", "James", "Oliver", "Benjamin", "Elijah", "Lucas", "Mason", "Logan", "Alexander", "Ethan", "Jacob", "Michael", "Daniel", "Henry", "Jackson", "Sebastian", "Aiden", "Matthew", "Samuel", "David", "Joseph", "Carter", "Owen", "Wyatt", "John", "Jack", "Luke", "Jayden", "Dylan", "Grayson", "Levi", "Isaac", "Gabriel", "Julian", "Mateo", "Anthony", "Jaxon", "Lincoln", "Joshua", "Christopher", "Andrew", "Theodore", "Caleb", "Ryan", "Asher", "Nathan", "Thomas", "Leo", "Isaiah", "Charles", "Josiah", "Hudson", "Christian", "Hunter", "Connor", "Eli", "Ezra", "Aaron", "Landon", "Adrian", "Jonathan", "Nolan", "Jeremiah", "Easton", "Elias", "Colton", "Cameron", "Carson", "Robert", "Angel", "Maverick", "Nicholas", "Dominic", "Jaxson", "Greyson", "Adam", "Ian", "Austin", "Santiago", "Jordan", "Cooper", "Brayden", "Roman", "Evan", "Ezekiel", "Xavier", "Jose", "Jace", "Jameson", "Leonardo", "Bryson", "Axel", "Everett", "Parker", "Kayden", "Miles", "Sawyer", "Jason", "Declan", "Weston", "Micah", "Ayden", "Wesley", "Luca", "Vincent", "Damian", "Zachary", "Silas", "Gavin", "Chase", "Kai", "Emmett", "Harrison", "Nathaniel", "Kingston", "Cole", "Tyler", "Bennett", "Bentley", "Ryker", "Tristan", "Brandon", "Kevin", "Luis", "George", "Ashton", "Rowan", "Braxton", "Ryder", "Gael", "Ivan", "Diego", "Maxwell", "Max", "Carlos", "Kaiden", "Juan", "Maddox", "Justin", "Waylon", "Calvin", "Giovanni", "Jonah", "Abel", "Jayce", "Jesus", "Amir", "King", "Beau", "Camden", "Alex", "Jasper", "Malachi", "Brody", "Jude", "Blake", "Emmanuel", "Eric", "Brooks", "Elliot", "Antonio", "Abraham", "Timothy", "Finn", "Rhett", "Elliott", "Edward", "August", "Xander", "Alan", "Dean", "Lorenzo", "Bryce", "Karter", "Victor", "Milo", "Miguel", "Hayden", "Graham", "Grant", "Zion", "Tucker", "Jesse", "Zayden", "Joel", "Richard", "Patrick", "Emiliano", "Avery", "Nicolas", "Brantley", "Dawson", "Myles", "Matteo", "River", "Steven", "Thiago", "Zane", "Matias", "Judah", "Messiah", "Jeremy", "Preston", "Oscar", "Kaleb", "Alejandro", "Marcus", "Mark", "Peter", "Maximus", "Barrett", "Jax", "Andres", "Holden", "Legend", "Charlie", "Knox", "Kaden", "Paxton", "Kyrie", "Kyle", "Griffin", "Josue", "Kenneth", "Beckett", "Enzo", "Adriel", "Arthur", "Felix", "Bryan", "Lukas", "Paul", "Brian", "Colt", "Caden", "Leon", "Archer", "Omar", "Israel", "Aidan", "Theo", "Javier", "Remington", "Jaden", "Bradley", "Emilio", "Colin", "Riley", "Cayden", "Phoenix", "Clayton", "Simon", "Ace", "Nash", "Derek", "Rafael", "Zander", "Brady", "Jorge", "Jake", "Louis", "Damien", "Karson", "Walker", "Maximiliano", "Amari", "Sean", "Chance", "Walter", "Martin", "Finley", "Andre", "Tobias", "Cash", "Corbin", "Arlo", "Iker", "Erick", "Emerson", "Gunner", "Cody", "Stephen", "Francisco", "Killian", "Dallas", "Reid", "Manuel", "Lane", "Atlas", "Rylan", "Jensen", "Ronan", "Beckham", "Daxton", "Anderson", "Kameron", "Raymond", "Orion", "Cristian", "Tanner", "Kyler", "Jett", "Cohen", "Ricardo", "Spencer", "Gideon", "Ali", "Fernando", "Jaiden", "Titus", "Travis", "Bodhi", "Eduardo", "Dante", "Ellis", "Prince", "Kane", "Luka", "Kash", "Hendrix", "Desmond", "Donovan", "Mario", "Atticus", "Cruz", "Garrett", "Hector", "Angelo", "Jeffrey", "Edwin", "Cesar", "Zayn", "Devin", "Conor", "Warren", "Odin", "Jayceon", "Romeo", "Julius", "Jaylen", "Hayes", "Kayson", "Muhammad", "Jaxton", "Joaquin", "Caiden", "Dakota", "Major", "Keegan", "Sergio", "Marshall", "Johnny", "Kade", "Edgar", "Leonel", "Ismael", "Marco", "Tyson", "Wade", "Collin", "Troy", "Nasir", "Conner", "Adonis", "Jared", "Rory", "Andy", "Jase", "Lennox", "Shane", "Malik", "Ari", "Reed", "Seth", "Clark", "Erik", "Lawson", "Trevor", "Gage", "Nico", "Malakai", "Quinn", "Cade", "Johnathan", "Sullivan", "Solomon", "Cyrus", "Fabian", "Pedro", "Frank", "Shawn", "Malcolm", "Khalil", "Nehemiah", "Dalton", "Mathias", "Jay", "Ibrahim", "Peyton", "Winston", "Kason", "Zayne", "Noel", "Princeton", "Matthias", "Gregory", "Sterling", "Dominick", "Elian", "Grady", "Russell", "Finnegan", "Ruben", "Gianni", "Porter", "Kendrick", "Leland", "Pablo", "Allen", "Hugo", "Raiden", "Kolton", "Remy", "Ezequiel", "Damon", "Emanuel", "Zaiden", "Otto", "Bowen", "Marcos", "Abram", "Kasen", "Franklin", "Royce", "Jonas", "Sage", "Philip", "Esteban", "Drake", "Kashton", "Roberto", "Harvey", "Alexis", "Kian", "Jamison", "Maximilian", "Adan", "Milan", "Phillip", "Albert", "Dax", "Mohamed", "Ronin", "Kamden", "Hank", "Memphis", "Oakley", "Augustus", "Drew", "Moises", "Armani", "Rhys", "Benson", "Jayson", "Kyson", "Braylen", "Corey", "Gunnar", "Omari", "Alonzo", "Landen", "Armando", "Derrick", "Dexter", "Enrique", "Bruce", "Nikolai", "Francis", "Rocco", "Kairo", "Royal", "Zachariah", "Arjun", "Deacon", "Skyler", "Eden", "Alijah", "Rowen", "Pierce", "Uriel", "Ronald", "Luciano", "Tate", "Frederick", "Kieran", "Lawrence", "Moses", "Rodrigo", "Brycen", "Leonidas", "Nixon", "Keith", "Chandler", "Case", "Davis", "Asa", "Darius", "Isaias", "Aden", "Jaime", "Landyn", "Raul", "Niko", "Trenton", "Apollo", "Cairo", "Izaiah", "Scott", "Dorian", "Julio", "Wilder", "Santino", "Dustin", "Donald", "Raphael", "Saul", "Taylor", "Ayaan", "Duke", "Ryland", "Tatum", "Ahmed", "Moshe", "Edison", "Emmitt", "Cannon", "Alec", "Danny", "Keaton", "Roy", "Conrad", "Roland", "Quentin", "Lewis", "Samson", "Brock", "Kylan", "Cason", "Ahmad", "Jalen", "Nikolas", "Braylon", "Kamari", "Dennis", "Callum", "Justice", "Soren", "Rayan", "Aarav", "Gerardo", "Ares", "Brendan", "Jamari", "Kaison", "Yusuf", "Issac", "Jasiah", "Callen", "Forrest", "Makai", "Crew", "Kobe", "Bo", "Julien", "Mathew", "Braden", "Johan", "Marvin", "Zaid", "Stetson", "Casey", "Ty", "Ariel", "Tony", "Zain", "Callan", "Cullen", "Sincere", "Uriah", "Dillon", "Kannon", "Colby", "Axton", "Cassius", "Quinton", "Mekhi", "Reece", "Alessandro", "Jerry", "Mauricio", "Sam", "Trey", "Mohammad", "Alberto", "Gustavo", "Arturo", "Fletcher", "Marcelo", "Abdiel", "Hamza", "Alfredo", "Chris", "Finnley", "Curtis", "Kellan", "Quincy", "Kase", "Harry", "Kyree", "Wilson", "Cayson", "Hezekiah", "Kohen", "Neil", "Mohammed", "Raylan", "Kaysen", "Lucca", "Sylas", "Mack", "Leonard", "Lionel", "Ford", "Roger", "Rex", "Alden", "Boston", "Colson", "Briggs", "Zeke", "Dariel", "Kingsley", "Valentino", "Jamir", "Salvador", "Vihaan", "Mitchell", "Lance", "Lucian", "Darren", "Jimmy", "Alvin", "Amos", "Tripp", "Zaire", "Layton", "Reese", "Casen", "Colten", "Brennan", "Korbin", "Sonny", "Bruno", "Orlando", "Devon", "Huxley", "Boone", "Maurice", "Nelson", "Douglas", "Randy", "Gary", "Lennon", "Titan", "Denver", "Jaziel", "Noe", "Jefferson", "Ricky", "Lochlan", "Rayden", "Bryant", "Langston", "Lachlan", "Clay", "Abdullah", "Lee", "Baylor", "Leandro", "Ben", "Kareem", "Layne", "Joe", "Crosby", "Deandre", "Demetrius", "Kellen", "Carl", "Jakob", "Ridge", "Bronson", "Jedidiah", "Rohan", "Larry", "Stanley", "Tomas", "Shiloh", "Thaddeus", "Watson", "Baker", "Vicente", "Koda", "Jagger", "Nathanael", "Carmelo", "Shepherd", "Graysen", "Melvin", "Ernesto", "Jamie", "Yosef", "Clyde", "Eddie", "Tristen", "Gre", "Ray", "Tommy", "Samir", "Ramon", "Santana", "Kristian", "Marcel", "Wells", "Zyaire", "Brecken", "Byron", "Otis", "Reyansh", "Axl", "Joey", "Trace", "Morgan", "Musa", "Harlan", "Enoch", "Henrik", "Kristopher", "Talon", "Rey", "Guillermo", "Houston", "Jon", "Vincenzo", "Dane", "Terry", "Azariah", "Castiel", "Kye", "Augustine", "Zechariah", "Joziah", "Kamryn", "Hassan", "Jamal", "Chaim", "Bodie", "Emery", "Branson", "Jaxtyn", "Kole", "Wayne", "Aryan", "Alonso", "Brixton", "Madden", "Allan", "Flynn", "Jaxen", "Harley", "Magnus", "Sutton", "Dash", "Anders", "Westley", "Brett", "Emory", "Felipe", "Yousef", "Jadiel", "Mordechai", "Dominik", "Junior", "Eliseo", "Fisher", "Harold", "Jaxxon", "Kamdyn", "Maximo", "Caspian", "Kelvin", "Damari", "Fox", "Trent", "Hugh", "Briar", "Franco", "Keanu", "Terrance", "Yahir", "Ameer", "Kaiser", "Thatcher", "Ishaan", "Koa", "Merrick", "Coen", "Rodney", "Brayan", "London", "Rudy", "Gordon", "Bobby", "Aron", "Marc", "Van", "Anakin", "Canaan", "Dario", "Reginald", "Westin", "Darian", "Ledger", "Leighton", "Maxton", "Tadeo", "Valentin", "Aldo", "Khalid", "Nickolas", "Toby", "Dayton", "Jacoby", "Billy", "Gatlin", "Elisha", "Jabari", "Jermaine", "Alvaro", "Marlon", "Mayson", "Blaze", "Jeffery", "Kace", "Braydon", "Achilles", "Brysen", "Saint", "Xzavier", "Aydin", "Eugene", "Adrien", "Cain", "Kylo", "Nova", "Onyx", "Arian", "Bjorn", "Jerome", "Miller", "Alfred", "Kenzo", "Kyng", "Leroy", "Maison", "Jordy", "Stefan", "Wallace", "Benicio", "Kendall", "Zayd", "Blaine", "Tristian", "Anson", "Gannon", "Jeremias", "Marley", "Ronnie", "Dangelo", "Kody", "Will", "Bentlee", "Gerald", "Salvatore", "Turner", "Chad", "Misael", "Mustafa", "Konnor", "Maxim", "Rogelio", "Zakai", "Cory", "Judson", "Brentley", "Darwin", "Louie", "Ulises", "Dakari", "Rocky", "Wesson", "Alfonso", "Payton", "Dwayne", "Juelz", "Duncan", "Keagan", "Deshawn", "Bode", "Bridger", "Skylar", "Brodie", "Landry", "Avi", "Keenan", "Reuben", "Jaxx", "Rene", "Yehuda", "Imran", "Yael", "Alexzander", "Willie", "Cristiano", "Heath", "Lyric", "Davion", "Elon", "Karsyn", "Krew", "Jairo", "Maddux", "Ephraim", "Ignacio", "Vivaan", "Aries", "Vance", "Boden", "Lyle", "Ralph", "Reign", "Camilo", "Draven", "Terrence", "Idris", "Ira", "Javion", "Jericho", "Khari", "Marcellus", "Creed", "Shepard", "Terrell", "Ahmir", "Camdyn", "Cedric", "Howard", "Jad", "Zahir", "Harper", "Justus", "Forest", "Gibson", "Zev", "Alaric", "Decker", "Ernest", "Jesiah", "Torin", "Benedict", "Bowie", "Deangelo", "Genesis", "Harlem", "Kalel", "Kylen", "Bishop", "Immanuel", "Lian", "Zavier", "Archie", "Davian", "Gus", "Kabir", "Korbyn", "Randall", "Benton", "Coleman", "Mark"};
    int special = -1;



    public Bot(int type, Random rnd){
        this.type = type;

        name = names[rnd.nextInt(1000)];

    }


    /***
     *  1) Table cards (0 - 5)
     *  2) -1
     *  3) parametres
     *
     *  special is not needed for some bots
     *
     *
     * 3 0
     * 4 0
     * 5 0
     * 6 0
     * 7 0
     * -1
     * current_bet
     * bet_now
     * balance
     * blind
     * special
     *
     */

     @Override
    public int makeTurn(LinkedList<Card> cards, Integer currentBet, Integer blind) {
        int res = 0;


        Process process;
        try {
            process = new ProcessBuilder(System.getProperty("user.dir")+"\\scripts\\bot_0"+type+".exe").start();
            OutputStream os = process.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            StringBuilder s = new StringBuilder("");
            for (Card c : cards){
                s.append(c.value);
                s.append(" ");
                s.append(c.mast);
                s.append('\n');
            }
            s.append(lCard.value);
            s.append(" ");
            s.append(lCard.mast);
            s.append('\n');
            s.append(rCard.value);
            s.append(" ");
            s.append(rCard.mast);
            s.append('\n');

            /// SPECIALS IF IT NEEDED
            if (type == 2){
                if (special == -1) special = new Random(System.currentTimeMillis()).nextInt(4);
            }

            s.append("-1\n"+currentBet+"\n"+bet_now+"\n"+balance+"\n"+blind+"\n"+special+"\n"+hasTurn+"\n");

            osw.write(s.toString());
            osw.flush();


            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            LabelValue lv = new LabelValue();

            res = Integer.parseInt(br.readLine());


            is.close();
            os.close();


        } catch (IOException e) {
            if (JOptionPane.showConfirmDialog(null, "Show error message?", "Major Payne I can't feel my script!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }

            Random rnd = new Random(System.currentTimeMillis());
            if (currentBet != 0)
                if (rnd.nextInt(10) < 8) return 0;
            if (rnd.nextInt(10) < 8) return 0;
            if (rnd.nextInt(10) < 1) return -1;
            res = (rnd.nextInt() % balance) / 3;
        }


        return res;
    }




}
