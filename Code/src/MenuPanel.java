import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {

    MainFrame frame;

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
            case "Game with AI":
                frame.changePanel("preGamePanel");
                break;
            case "Controls":
                JOptionPane.showMessageDialog(null, "Покер\nПравила - Техасский Холдем\nКнопки управления:\n" +
                        "    Пробел - Пауза\n    M - вкл/выкл звук\n    H - режим разработчика (видны все карты)\n(Да, в нашей команде не было дизайнера)");
                break;

        }

    }

    public MenuPanel(MainFrame frame){
        this.frame = frame;
        addButton("Game with AI", this);
        addButton("Controls", this);
    }

}
