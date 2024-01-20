import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
  public JFrame createFrame(String pageName){
    JFrame f = new JFrame(pageName);
    f.setSize(1300,550);
    f.setLayout(new BorderLayout());
    f.setLocationRelativeTo(null); 
    
    f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      f.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
              int ans = JOptionPane.showConfirmDialog(null, "Do you want to exit the program?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
              if(ans == JOptionPane.YES_OPTION) System.exit(0);
          }
      });
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
        p.setMaximumSize(new Dimension(600,150));
        return p;
    }

    public JPanel createBoxPanel(){
        JPanel p = new JPanel();
        p.removeAll();
        p.validate();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        return p;
    }

    public JScrollPane createScrollPane(JTextArea textArea){
      JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      return scrollPane;
    }
}