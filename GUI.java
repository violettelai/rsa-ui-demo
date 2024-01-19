import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
  public JFrame createFrame(String pageName){
    JFrame f = new JFrame(pageName);
    f.setSize(500,375);
    f.setLayout(new BorderLayout());
    //Create JFrame at center of screen (Windows)
    f.setLocationRelativeTo(null); 

    // f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    return f; 
  }

  public JPanel createHoriPanel(){
        JPanel p = new JPanel();
        p.removeAll();
        p.validate();
        p.setLayout(new FlowLayout());
        p.setMaximumSize(new Dimension(500,200));
        return p;
    }

    public JPanel createVertPanel(int row, int column, int hgap, int vgap){
        JPanel p = new JPanel();
        p.removeAll();
        p.validate();
        p.setLayout(new GridLayout(row, column, hgap, vgap));
        p.setMaximumSize(new Dimension(250,150));
        //p.setMaximumSize(new Dimension(420,230));
        return p;
    }

    public JPanel createBoxPanel(){
        JPanel p = new JPanel();
        p.removeAll();
        p.validate();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        return p;
    }
}