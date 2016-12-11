/**
 * This method describes the main look and feel of the reservation system
 *  * GROUP NAME: Warriors
 * @author Milan Mishra, Tuan Nguyen, Nicholas Lacroix
 */

import java.text.SimpleDateFormat;

import javax.swing.border.EmptyBorder;

import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class View {
   private Model model;
   private Guest g;
   private Calendar cal;
   private JScrollPane scroll;
   private JLabel monthLabel = new JLabel();
   private JPanel monthPanel = new JPanel();
   private JList<Reservation>  reservationJList = new JList<>();
   private DefaultListModel<Reservation> listModel = new DefaultListModel<>();

   public View(final Model model, final Guest g) {
      this.model = model;
      this.cal = model.getCal();
 
      JButton createButton = new JButton("Make Reservations");
      JButton prevButton = new JButton("<");
      JButton nextButton = new JButton(">");
      JButton viewButton = new JButton("View Reservations");
      JButton cancelButton = new JButton("Cancel A Reservation");
      JButton quitButton = new JButton("Save & Quit");

      createButton.setBackground(Color.RED);
      createButton.setForeground(Color.WHITE);
      prevButton.setBackground(Color.LIGHT_GRAY);
      nextButton.setBackground(Color.LIGHT_GRAY);
      viewButton.setBackground(Color.GREEN);
      cancelButton.setBackground(Color.ORANGE);
      quitButton.setBackground(Color.WHITE);

      createButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            new ReservationView(model, model.getCurrentUser());
         }
      });

      prevButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            model.prevMonth();
         }
      });

      nextButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            model.nextMonth();
         }
      });

      viewButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            listModel.removeAllElements();
           
            ArrayList<Room> r = model.getCurrentUser().getRooms();
            
            for(Room room : r)
            {
               ReservationLists res = room.getReservations();
               Iterator<Reservation> y = res.getUserReservations(model.getCurrentUser().getUserID());
               while(y.hasNext())
                  listModel.addElement(y.next());
            }
            reservationJList.setModel(listModel);
         }
      });

      cancelButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            Reservation r = reservationJList.getSelectedValue();
            if(r != null){
               int index = reservationJList.getSelectedIndex();

               listModel.remove(index);
               model.cancelReservation(r);
            }
         }
      });


      JPanel topButtonPanel = new JPanel();
      topButtonPanel.add(createButton);
      topButtonPanel.add(monthLabel);
      topButtonPanel.add(prevButton);
      topButtonPanel.add(nextButton);
      topButtonPanel.add(viewButton);
      topButtonPanel.add(quitButton);

      JPanel bottomButtonPanel = new JPanel(new BorderLayout());
      JPanel bottomButtonPanel2 = new JPanel();
      bottomButtonPanel2.add(cancelButton);
      bottomButtonPanel.add(bottomButtonPanel2, BorderLayout.WEST);

      monthPanel.setLayout(new GridLayout(0, 7, 5, 5));
      monthPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
      JPanel monthWrap = new JPanel();
      monthWrap.add(monthPanel);
      drawMonth(monthPanel);

      scroll = new JScrollPane();
      scroll.getViewport().add(reservationJList);
      scroll.setPreferredSize(new Dimension(340, 150));
      scroll.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED);

      final JFrame frame = new JFrame();
      frame.setTitle("Room Reservation");
      frame.add(topButtonPanel, BorderLayout.NORTH);
      frame.add(bottomButtonPanel, BorderLayout.SOUTH);

      quitButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            try
            {
               model.saveUsers();
            } catch (IOException e1)
            {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            model.setCurrentUser(null);
            frame.dispose();
         }
      });

      frame.add(monthWrap, BorderLayout.WEST);
      frame.add(scroll, BorderLayout.EAST);
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }

   /**
    * Repaint months
    */
   public void repaint() {
      monthPanel.removeAll();
      drawMonth(monthPanel);
      monthPanel.revalidate();
      monthPanel.repaint();
   }

   /**
    * Draw months on calendar for user to specify their check-in and check-out dates
    * @param monthPanel
    */
   private void drawMonth(JPanel monthPanel) {
      monthLabel.setText(new SimpleDateFormat("MMMM yyyy").format(cal.getTime()));
      monthLabel.setForeground(Color.BLUE);

      String[] days = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
      for (int i = 0; i < days.length; i++) {
         final JLabel day = new JLabel(days[i]);
         day.setForeground(Color.MAGENTA);
         monthPanel.add(day);
      }

      int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

      Calendar getStart = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
      int startDay = getStart.get(Calendar.DAY_OF_WEEK);

      for (int i = 1; i < daysInMonth+startDay; i++) {
         if (i < startDay) {
            final JLabel day = new JLabel("");
            monthPanel.add(day);
         } else {
            int dayNumber = i-startDay+1;
            final JLabel day = new JLabel(dayNumber + "");
            day.addMouseListener(new MouseListener() {

               public void mouseClicked(MouseEvent e) {
                  int num = Integer.parseInt(day.getText());
                  model.setDay(num);
               }

               public void mousePressed(MouseEvent e) {}
               public void mouseReleased(MouseEvent e) {}
               public void mouseEntered(MouseEvent e) {}
               public void mouseExited(MouseEvent e) {}

            });
            if (dayNumber == cal.get(Calendar.DAY_OF_MONTH)) {
               day.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
            monthPanel.add(day);
         }
      }
   }
}
