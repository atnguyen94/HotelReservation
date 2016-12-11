import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * The initial screen where a user indicates whether they are a guest or manager
 *  * GROUP NAME: Warriors
 * @author Milan Mishra, Tuan Nguyen, Nicholas Lacroix
 *
 */
public class GuestOrManagerView extends JFrame {
    /**
     * Constructs the GUI for Guest or Manager View
     */

    public GuestOrManagerView(final Model model)
    {
        JPanel initial = new JPanel(new GridLayout(0,1,10,10));
        initial.setBorder(new EmptyBorder(20,30,20,30));

        Insets margin = new Insets(20,150,20,150);
        JButton guest = new JButton("Guest");
        JButton manager = new JButton("Manager");

        manager.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TODO Auto-generated method stub
                ManagerView man = new ManagerView(model);
                model.setManagerView(man);
                //dispose();
            }
        });

        guest.setMargin(margin);
        manager.setMargin(margin);
        initial.add(guest);
        initial.add(manager);

        guest.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
                try
               {
                  GuestLoginOrSignUpView view = new GuestLoginOrSignUpView(model);
               } catch (IOException e1)
               {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }
                //dispose();

            }
        });
        this.add(initial);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        setLocation(500,300);
        this.pack();
        this.setVisible(true);


    }
}
