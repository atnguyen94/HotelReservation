import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.border.EmptyBorder;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class View {
   private Model model;
   private Calendar cal;
   private JLabel monthLabel = new JLabel();
   private JPanel monthPanel = new JPanel();
   private JPanel dayPanel = new JPanel();

   public View(Model model) {
      this.model = model;
      this.cal = model.getCal();

      JButton createButton = new JButton("Make A Reservation");
      JButton prevButton = new JButton("<");
      JButton nextButton = new JButton(">");
      JButton quitButton = new JButton("Quit");

      createButton.setBackground(Color.RED);
      createButton.setForeground(Color.WHITE);
      prevButton.setBackground(Color.LIGHT_GRAY);
      nextButton.setBackground(Color.LIGHT_GRAY);
      quitButton.setBackground(Color.WHITE);

      createButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            new EventView(model, new User());
         }
      });

      prevButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            model.prevDay();
         }
      });

      nextButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            model.nextDay();
         }
      });

      quitButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            try {
               model.saveUsers();
            } catch (IOException ignored) {}
         }
      });

      JLabel space = new JLabel();
      space.setText("         ");

      JPanel buttonPanel = new JPanel();
      buttonPanel.add(createButton);
      buttonPanel.add(monthLabel);
      buttonPanel.add(prevButton);
      buttonPanel.add(nextButton);
      buttonPanel.add(space);
      buttonPanel.add(quitButton);

      monthPanel.setLayout(new GridLayout(0, 7, 5, 5));
      monthPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
      JPanel monthWrap = new JPanel();
      monthWrap.add(monthPanel);
      drawMonth(monthPanel);

      dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.PAGE_AXIS));
      dayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      paintDay(dayPanel);

      JScrollPane scroll = new JScrollPane();
      scroll.getViewport().add(dayPanel);
      scroll.setPreferredSize(new Dimension(200, 200));
      scroll.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED);

      JFrame frame = new JFrame();
      frame.setTitle("Room Reservation");
      frame.add(buttonPanel, BorderLayout.NORTH);
      frame.add(monthWrap, BorderLayout.WEST);
      frame.add(scroll, BorderLayout.EAST);
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }

   public void repaint() {
      monthPanel.removeAll();
      drawMonth(monthPanel);
      monthPanel.revalidate();
      monthPanel.repaint();

      dayPanel.removeAll();
      paintDay(dayPanel);
      dayPanel.revalidate();
      dayPanel.repaint();
   }

   private void drawMonth(JPanel monthPanel) {
      monthLabel.setText(new SimpleDateFormat("MMMM yyyy").format(cal.getTime()));
      monthLabel.setForeground(Color.BLUE);

      String[] days = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
      for (int i = 0; i < days.length; i++) {
         JLabel day = new JLabel(days[i]);
         day.setForeground(Color.MAGENTA);
         monthPanel.add(day);
      }

      int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

      Calendar getStart = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
      int startDay = getStart.get(Calendar.DAY_OF_WEEK);

      for (int i = 1; i < daysInMonth+startDay; i++) {
         if (i < startDay) {
            JLabel day = new JLabel("");
            monthPanel.add(day);
         } else {
            int dayNumber = i-startDay+1;
            JLabel day = new JLabel(dayNumber + "");
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

   private void paintDay(JPanel dayPanel) {
      ArrayList<User> currentEvents = model.getUsers();

      for(User u : currentEvents)
      {
         for (Room e : u.getRooms()) {
            String[] start = (e.getCheckInDate() + "").split("/");
            String[] end = (e.getCheckOutDate() + "").split("/");
            
           // if (Integer.parseInt(start[0]) == cal.get(Calendar.MONTH)) {
            //   if (Integer.parseInt(end[1]) == cal.get(Calendar.DAY_OF_MONTH)) {

                  String startDate = e.getCheckInDate();
                  String endDate = e.getCheckOutDate();
                  SimpleDateFormat sdf = new SimpleDateFormat("hh:mmaa");

                  JLabel length = new JLabel();
                  String times = (e.getCheckInDate() + " - " + e.getCheckOutDate());
                  length.setText(times);

                  dayPanel.add(new JLabel("Room Number: " + e.getRoomNumber()));
                  dayPanel.add(length);
                  dayPanel.add(new JLabel(" "));
              // }
         //   }
         }
      }
   }
}
