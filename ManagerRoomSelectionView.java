import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class ManagerRoomSelectionView extends JFrame {
  
   public ManagerRoomSelectionView(Model model)
   {
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      Container pane = getContentPane();
      pane.setLayout(new GridLayout(2, 3));
      for (int i = 0; i < 20; i++) {
         JButton button = new JButton(Integer.toString(i + 1));
         button.setPreferredSize(new Dimension(100, 100));
         pane.add(button);
      }
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
      setVisible(true);
   }
}
