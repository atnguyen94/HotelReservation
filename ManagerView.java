import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.border.EmptyBorder;

import java.util.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.*;


public class ManagerView {
   private Model model;
   private Calendar cal;
   private JScrollPane scroll;
   private JTextArea textBox = new JTextArea();
   private JLabel monthLabel = new JLabel();
   private JPanel monthPanel = new JPanel();

   public ManagerView(final Model model) {
      this.model = model;
      this.cal = model.getCal();

      JButton loadButton = new JButton("Load Reservations");
      JButton prevButton = new JButton("<");
      JButton nextButton = new JButton(">");
      JButton viewButton = new JButton("View All Reservations");
      JButton saveButton = new JButton("Save Current Reservations");
      JButton quitButton = new JButton("Save & Quit");

      loadButton.setBackground(Color.RED);
      loadButton.setForeground(Color.WHITE);
      prevButton.setBackground(Color.LIGHT_GRAY);
      nextButton.setBackground(Color.LIGHT_GRAY);
      viewButton.setBackground(Color.GREEN);
      quitButton.setBackground(Color.ORANGE);
      saveButton.setBackground(Color.WHITE);

      loadButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {

            try
            {
              model.loadEvents();
            } catch (IOException e1)
            {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         }
      });

      prevButton.addMouseListener(new MouseAdapter() {

         public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
               model.prevManagerMonth();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
               model.prevManageYear();
            }
         }
      });

      nextButton.addMouseListener(new MouseAdapter() {

         public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
               model.nextManagerMonth();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
               model.nextManagerYear();
            }
         }
      });

      viewButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            setView();
            repaint();
         }
      });

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
            System.exit(0);
         }
      });

      saveButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            try {
               model.saveUsers();
            } catch (IOException ignored) {}
         }
      });

      JPanel topButtonPanel = new JPanel();
      topButtonPanel.add(loadButton);
      topButtonPanel.add(monthLabel);
      topButtonPanel.add(prevButton);
      topButtonPanel.add(nextButton);
      topButtonPanel.add(viewButton);
      topButtonPanel.add(saveButton);
      topButtonPanel.add(quitButton);

      monthPanel.setLayout(new GridLayout(0, 7, 5, 5));
      monthPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
      JPanel monthWrap = new JPanel();
      monthWrap.add(monthPanel);
      drawMonth(monthPanel);

      scroll = new JScrollPane();
      scroll.getViewport().add(textBox);
      textBox.setEditable(false);
      DefaultCaret caret = (DefaultCaret) textBox.getCaret();
      caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
      scroll.setPreferredSize(new Dimension(548, 300));
      scroll.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED);

      final JFrame frame = new JFrame();
      
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
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         }
      });
      
      
      frame.setTitle("Manager View");
      frame.add(topButtonPanel, BorderLayout.NORTH);
      frame.add(monthWrap, BorderLayout.WEST);
      frame.add(scroll, BorderLayout.EAST);
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }

   public void repaint() {
      monthPanel.removeAll();
      drawMonth(monthPanel);
      monthPanel.revalidate();
      monthPanel.repaint();
   }

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
                  model.setManagerDay(num);
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

   private void setView() {
      Calendar date = Calendar.getInstance();
      date.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
      String t = "Room Information: for ";
      t += sdf.format(date.getTime()) + "\n\n";
      ArrayList<Reservation> reservationArrayList = model.getDateReservations(date.getTime());
      t += "Booked Rooms:\n";


      ArrayList<Guest> guests = model.getUsers();

      for(Guest g : guests)
      {
         for(Room r : g.getRooms())
         {
            ReservationLists list = r.getReservations();
            Iterator<Reservation> y = list.getReservations();

            while(y.hasNext())
            {
               Reservation x = y.next();

               t+="Room #" + r.getRoomNumber()
                     + " User ID: " + g.getUserID()
                     + ", Price: $" + x.getPrice()
                     + '\n';
            }
         }
      }
      
      
      
      t+="\nAvailable Rooms: \n";

      for(Room r : model.getRooms())
      {
         if(! r.getReservations().getDateReservations(date.getTime()).hasNext())
            t+="Room #" + r.getRoomNumber() + '\n';
      }
      textBox.setText(t);


   }
}
