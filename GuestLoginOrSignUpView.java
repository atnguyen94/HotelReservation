import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class GuestLoginOrSignUpView extends JFrame {

   public GuestLoginOrSignUpView(Model model)
   {
      JPanel initial = new JPanel(new GridLayout(0,1,10,10));
      initial.setBorder(new EmptyBorder(20,30,20,30));

      Insets margin = new Insets(20,150,20,150);
      JButton login = new JButton("Login");
      JButton signup = new JButton("Sign Up");

      login.addActionListener(new ActionListener() {


         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            GuestLogin view = new GuestLogin(model);
            dispose();
         }
      });

      signup.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            GuestSignUp view = new GuestSignUp(model);
            dispose();

         }
      });

      login.setMargin(margin);
      signup.setMargin(margin);
      initial.add(login);
      initial.add(signup);


      this.add(initial);
      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      this.setLocationByPlatform(true);
      setLocation(500,300);
      this.pack();
      this.setVisible(true);

   }

}
