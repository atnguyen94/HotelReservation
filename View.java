import java.text.SimpleDateFormat;
import javax.swing.border.EmptyBorder;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class View {
    private Model model;
    private Guest g;
    private Calendar cal;
    private JScrollPane scroll;
    private JLabel monthLabel = new JLabel();
    private JPanel monthPanel = new JPanel();
    private JList<Reservation>  reservationJList = new JList<>();
    private DefaultListModel<Reservation> listModel = new DefaultListModel<>();

    public View(Model model, Guest g) {
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
                new ReservationView(model, g);
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
                ArrayList<Reservation> reservations = model.getGuestReservations(g);
                listModel.removeAllElements();

                for(Reservation r : reservations)
                {
                    listModel.addElement(r);
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

        /*quitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    model.saveUsers();
                } catch (IOException ignored) {}
            }
        });*/

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

        JFrame frame = new JFrame();
        frame.setTitle("Room Reservation");
        frame.add(topButtonPanel, BorderLayout.NORTH);
        frame.add(bottomButtonPanel, BorderLayout.SOUTH);

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
}
