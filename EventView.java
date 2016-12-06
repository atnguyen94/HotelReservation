import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.util.*;

import javax.swing.*;

public class EventView {

   private Model model; 
   
   public EventView(Model model, User user)  {
      JFrame frame = new JFrame();
      JPanel panel = new JPanel();
      this.model = model;
      this.model.setCurrentUser(model.getCurrentUser());
      
      
      Calendar cal = model.getCal();

      SimpleDateFormat time = new SimpleDateFormat("hh:mmaa");
      Calendar tempEnd = new GregorianCalendar();
      tempEnd.setTime(cal.getTime());
      tempEnd.add(Calendar.HOUR, 1);

      JTextField eventText = new JTextField(this.model.getCurrentUser().getGuest(), 25);
      eventText.addMouseListener(new MouseAdapter()
      {
         @Override
         public void mouseClicked(MouseEvent e)
         {
            // TODO Auto-generated method stub
            super.mouseClicked(e);
            eventText.setText("");

         }
      });
      //JTextField dateText = new JTextField((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR), 8);
      JTextField dateText = new JTextField("Room Number", 8);
      dateText.addMouseListener(new MouseAdapter()
      {
         @Override
         public void mouseClicked(MouseEvent e)
         {
            // TODO Auto-generated method stub
            super.mouseClicked(e);
            dateText.setText("");

         }
      });

      
      JTextField beginText = new JTextField((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR), 8);

      JTextField endText = new JTextField("End Date", 5);
      endText.addMouseListener(new MouseAdapter()
      {
         @Override
         public void mouseClicked(MouseEvent e)
         {
            // TODO Auto-generated method stub
            super.mouseClicked(e);
            endText.setText("");

         }
      });

      JButton saveButton = new JButton("Save");
      saveButton.setBackground(Color.CYAN);

      saveButton.addActionListener(new ActionListener()  {

         public void actionPerformed(ActionEvent e) {
            String userID = model.getCurrentUser().getID();
            int roomNumber = Integer.parseInt(dateText.getText());
            String startDate = beginText.getText();
            String endDate = endText.getText();

            /*SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date checkIn = null;
            Date checkOut = null;
            try {
               checkIn = sdf.parse(startDate);
               checkOut = sdf.parse(endDate);
            } catch (ParseException ignored) {}

            Calendar beginCal = new GregorianCalendar();
            beginCal.setTime(checkIn);
            Calendar endCal = new GregorianCalendar();
            endCal.setTime(checkOut);
            
            */
            Room pp = null;
            
            //Create the rooms from the text file
            if( 0 < roomNumber && roomNumber <= 10)
            {
               pp = new Luxury(roomNumber, startDate, endDate);
            }
            if( 10 < roomNumber && roomNumber <= 20)
            {
               pp = new Economy(roomNumber, startDate, endDate);
            }
            
            boolean timeConflict = false;
            
            
            for (Room r : user.getRooms()) {
               if (r.compareTo(pp) == 0) {
                  JOptionPane.showMessageDialog(frame,
                        "Please enter an event without a time conflict.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                  timeConflict = true;
                  break;
               }
            }

            if (!timeConflict) {
               model.getCurrentUser().addRoom(pp);
               model.getCurrentUser().setPayment(pp.getCost());
               model.addUser(user);
               
            }
            frame.dispose();
            frame.repaint();
         }
      });

      panel.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(2, 2, 2, 2);
      gbc.gridx = 0;
      gbc.gridy = 0;
      panel.add(eventText, gbc);

      gbc.gridy++;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      panel.add(dateText,gbc);

      gbc.gridy++;
      gbc.anchor = GridBagConstraints.LINE_START;
      panel.add(beginText, gbc);

      gbc.gridy++;
      gbc.anchor = GridBagConstraints.LINE_START;
      panel.add(endText, gbc);

      gbc.gridy++;
      gbc.anchor = GridBagConstraints.CENTER;
      panel.add(saveButton, gbc);

      frame.add(panel);
      frame.setTitle("Create Event");
      frame.pack();
      frame.setVisible(true);
   }
}
