import javax.swing.*;
import java.io.*;
import java.sql.SQLOutput;
import java.util.LinkedList;

public class Comb {

    public LabelValue getComb(LinkedList<Card> table, Card lCard, Card rCard) throws IOException {

        Process process = new ProcessBuilder(System.getProperty("user.dir")+"\\scripts\\comb.exe").start();

        OutputStream os = process.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        StringBuilder s = new StringBuilder("");
        for (Card c : table){
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
        osw.write(s.toString());
        osw.flush();

        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        LabelValue lv = new LabelValue();

        lv.label = br.readLine();
        lv.value = Integer.parseInt(br.readLine());

        is.close();
        os.close();


        return lv;
    }

}

class LabelValue {
    String label;
    int value;

    public LabelValue(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public LabelValue (){
    }


}
