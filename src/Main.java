import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{

    public Main(Lang lang){
        setSize(450,500);
        setLayout(new BorderLayout());

        JLabel w=new JLabel(" ");w.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(w,BorderLayout.PAGE_START);

        JTextArea text = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(text);
        this.add(jScrollPane,BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        this.add(refresh,BorderLayout.PAGE_END);

        refresh.addActionListener((e)->{
            w.setText(lang.checkText(text.getText()));
        });

        this.setTitle("Siec 1 warstwowa");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) {
        Lang lang= new Lang(3,0.25);
        EventQueue.invokeLater(()->new Main(lang));
    }
}