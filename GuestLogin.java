import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class GuestLogin extends JFrame {

   public GuestLogin(Model model)
   {
      JButton signup = new JButton("Login");
      JPanel panel = new JPanel();
      JTextField txuser = new JTextField(15);
      JPasswordField pass = new JPasswordField(15);

      setSize(300,200);
      setLocation(500,280);
      panel.setLayout (null); 

      //ID
      JLabel id= new JLabel("Enter your name: ");
      id.setBounds(70,10,150,20);

      //Name
      JLabel name = new JLabel("Enter User ID: ");
      name.setBounds(70, 50, 150, 20);

      txuser.setBounds(70,30,150,20);
      pass.setBounds(70,70,150,20);
      signup.setBounds(110,100,80,20);

      //create a new user
      signup.addMouseListener(new MouseAdapter(){
         @Override
         public void mouseClicked(MouseEvent e)
         {
            // TODO Auto-generated method stub
            super.mouseClicked(e);
            try
            {
               model.loadEvents();
            } catch (ParseException e1)
            {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            String name = txuser.getText();
            @SuppressWarnings("deprecation")
            String pw = pass.getText();

            User u = new User(name, pw, false);
            ArrayList<User> temp = model.getUsers();
            boolean found = false;

            if(temp.isEmpty())
            {
               JOptionPane.showMessageDialog(null,
                     "The username and password do not exist.",
                     "Error",
                     JOptionPane.ERROR_MESSAGE);
            }
            else
            {
               for(int i = 0; i < temp.size(); i++)
               {
                  if(u.equals(temp.get(i)))
                  {
                     model.setCurrentUser(temp.get(i));
                     dispose();
                     View view = new View(model);
                     model.setView(view);
                     found = true;
                     break;
                  }
                  else if(i == temp.size()-1 && found == false)
                  {
                     JOptionPane.showMessageDialog(null,
                           "The username and password do not exist.",
                           "Error",
                           JOptionPane.ERROR_MESSAGE);
                     break;
                  }

               }
            }
         }
      });



      panel.add(signup);
      panel.add(id);
      panel.add(txuser);
      panel.add(pass);
      panel.add(name);

      getContentPane().add(panel);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }
}
