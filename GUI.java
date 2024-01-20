import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
  public JFrame createFrame(String pageName){
    JFrame f = new JFrame(pageName);
    f.setSize(700,375);
    f.setLayout(new BorderLayout());

    //Create JFrame at center of screen (Windows)
    f.setLocationRelativeTo(null); 
    return f; 
  }

  public JPanel createHoriPanel(){
        JPanel p = new JPanel();
        p.removeAll();
        p.validate();
        p.setLayout(new FlowLayout());
        p.setMaximumSize(new Dimension(1000,200));
        return p;
    }

    public JPanel createVertPanel(int row, int column, int hgap, int vgap){
        JPanel p = new JPanel();
        p.removeAll();
        p.validate();
        p.setLayout(new GridLayout(row, column, hgap, vgap));
        p.setMaximumSize(new Dimension(250,150));
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