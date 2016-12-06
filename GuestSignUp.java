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


public class GuestSignUp extends JFrame {

   public GuestSignUp(Model model)
   {
      JButton signup = new JButton("Sign Up");
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
      JLabel name = new JLabel("Enter new User ID: ");
      name.setBounds(70, 50, 150, 20);

      txuser.setBounds(70,30,150,20);
      pass.setBounds(70,70,150,20);
      signup.setBounds(110,100,80,20);


      signup.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e)
         {
            // TODO Auto-generated method stub
            super.mouseClicked(e);
            String name = txuser.getText();
            String pw = pass.getText();
            User u = new User(name, pw, false);
            ArrayList<User> guests = model.getUsers();
            
            if(!guests.isEmpty())
            {
               for(int i = 0; i < guests.size(); i++)
               {
                  if(u.equals(guests.get(i)))
                  {
                     System.out.println(u.equals(guests.get(i)));
                     System.out.println(u.getGuest() + " " + guests.get(i).getGuest());
                     JOptionPane.showMessageDialog(null,
                           "The username entered already exists.",
                           "Error",
                           JOptionPane.ERROR_MESSAGE);
                     break;
                  }
                  else
                  {
                     System.out.println("XD");
                     model.addUser(u);
                     model.setCurrentUser(u);
                     View view = new View(model);
                     model.setView(view);
                     dispose();
                     break;
                  }
               }
            }
            else if(guests.isEmpty())
            {

               model.addUser(u);
               model.setCurrentUser(u);
               View view = new View(model);
               model.setView(view);
               dispose();
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

