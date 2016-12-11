/**
 * This class describes the look and feel of the reservation creation GUI
 *  * GROUP NAME: Warriors
 * @author Milan Mishra, Tuan Nguyen, Nicholas Lacroix
 */
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.text.ParseException;

public class ReservationView {

    private Model model;
    private JTextField roomType = new JTextField("Luxury");
    private JTextField eventText;
    private JTextField beginText;
    private JTextField endText;
    private JLabel header;
    private JList<Room> roomJList = new JList<>();
    private JFrame frame;
    private Calendar startCal = Calendar.getInstance();
    private Calendar endCal = Calendar.getInstance();
    private int total = 0;

    public ReservationView(final Model model, Guest user)  {
        frame = new JFrame();
        JPanel panel = new JPanel();
        this.model = model;
        this.model.setCurrentUser(model.getCurrentUser());

        final Calendar cal = model.getCal();
        SimpleDateFormat time = new SimpleDateFormat("hh:mmaa");
        Calendar tempEnd = new GregorianCalendar();
        tempEnd.setTime(cal.getTime());
        tempEnd.add(Calendar.HOUR, 1);

        eventText = new JTextField(this.model.getCurrentUser().getUserID(), 25);
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

        beginText = new JTextField((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR), 8);
        beginText.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // TODO Auto-generated method stub
                super.mouseClicked(e);
                beginText.setText((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
                header.setText("Available Rooms " + beginText.getText() + " - " + endText.getText());
                paintRooms();
            }
        });

        endText = new JTextField((cal.get(Calendar.MONTH) + 1) + "/" + (cal.get(Calendar.DAY_OF_MONTH) + 1) + "/" + cal.get(Calendar.YEAR), 8);
        endText.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // TODO Auto-generated method stub
                super.mouseClicked(e);
                endText.setText((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
                header.setText("Available Rooms " + beginText.getText() + " - " + endText.getText());
                paintRooms();
            }
        });

        header = new JLabel();
        header.setText("Available Rooms " + beginText.getText() + " - " + endText.getText());

        JButton luxuryType = new JButton("$200 (Luxury)");

        luxuryType.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                roomType.setText("Luxury");
                paintRooms();
            }
        });

        JButton economyType = new JButton("$100 (Economy)");

        economyType.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                roomType.setText("Economy");
                paintRooms();
            }
        });

        JScrollPane scroll = new JScrollPane();

        scroll.setPreferredSize(new Dimension(300, 200));
        scroll.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS);

        model.attach(new ChangeListener(){
            public void stateChanged(ChangeEvent e){

                DefaultListModel<Room> list = new DefaultListModel<>();
                ArrayList<Room> rooms;
                rooms = model.getEmptyRooms();

                for(Room r : rooms) {
                    {
                        list.addElement(r);
                    }
                }

                roomJList.setModel(list);
                if(list.size() > 0)roomJList.setSelectedIndex(0);
            }
        });

        JButton saveButton = new JButton("Confirm Reservation");
        saveButton.setBackground(Color.CYAN);

        saveButton.addActionListener(new ActionListener()  {

            public void actionPerformed(ActionEvent e) {

                makeReservation();
                paintRooms();
            }
        });

        JButton doneButton = new JButton("Done with bookings");
        doneButton.setBackground(Color.YELLOW);
        doneButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new ReceiptView(model);
                frame.dispose();
            }
        });

        scroll.getViewport().add(roomJList);
        paintRooms();

        JPanel roomPanel = new JPanel(new BorderLayout());
        JPanel roomPanel2 = new JPanel();
        roomPanel2.add(header);
        roomPanel.add(roomPanel2, BorderLayout.NORTH);
        roomPanel.add(scroll, BorderLayout.CENTER);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(eventText, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(roomType, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(luxuryType, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        panel.add(economyType, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(beginText, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(endText, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(doneButton, gbc);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));
        JLabel nameLabel = new JLabel("Guest Name");
        JLabel space1 = new JLabel(" ");
        JLabel roomClass = new JLabel("Room Class");
        JLabel checkIn = new JLabel("Check In");
        JLabel checkOut = new JLabel("Check Out");

        labelPanel.add(Box.createRigidArea(new Dimension(0,8)));
        labelPanel.add(space1);
        labelPanel.add(nameLabel);
        labelPanel.add(Box.createRigidArea(new Dimension(0,7)));
        labelPanel.add(roomClass);
        labelPanel.add(Box.createRigidArea(new Dimension(0,41)));
        labelPanel.add(checkIn);
        labelPanel.add(Box.createRigidArea(new Dimension(0,7)));
        labelPanel.add(checkOut);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));

        frame.add(panel);
        frame.add(labelPanel, BorderLayout.WEST);
        frame.add(roomPanel, BorderLayout.EAST);
        frame.setTitle("Book Reservations");
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Show available rooms on the JScrollPane
     */
    private void paintRooms() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String checkIn = beginText.getText();
        String checkOut = endText.getText();

        if(!checkConstraints())return;

        int cost = 0;

        if (roomType.getText().equals("Economy")) {
             cost = 80;
        }
        else if (roomType.getText().equals("Luxury")) {
            cost = 200;
        }

        try {
            Date startDate = df.parse(checkIn);
            Date endDate = df.parse(checkOut);

            startCal.setTime(startDate);
            endCal.setTime(endDate);

            model.paintRooms(startCal, endCal, cost);

        } catch (ParseException p) {
            p.printStackTrace();
        }
    }

    /**
     * This method creates a reservation from GUI input
     */
    private void makeReservation() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String checkIn = beginText.getText();
        String checkOut = endText.getText();

        try {
            Date startDate = df.parse(checkIn);
            Date endDate = df.parse(checkOut);

            startCal.setTime(startDate);
            endCal.setTime(endDate);

            Room room = roomJList.getSelectedValue();
            Reservation r = new Reservation(startCal.getTime(), endCal.getTime(), eventText.getText(), room);
            total += room.getCost();
            room.addReservation(r);
            model.getCurrentUser().addRoom(room);

        } catch (ParseException p) {
            p.printStackTrace();
        }
    }

    /**
     * Ensure that reservation dates are not before current date, and length of stay is less than or equal to 60 nights
     * @return false if there is an error
     */
    private boolean checkConstraints() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String checkIn = beginText.getText();
        String checkOut = endText.getText();
        Calendar current = Calendar.getInstance();
        boolean b = true;

        try {
            Date startDate = df.parse(checkIn);
            Date endDate = df.parse(checkOut);

            startCal.setTime(startDate);
            endCal.setTime(endDate);

            if (startCal.get(Calendar.YEAR) < current.get(Calendar.YEAR) ||
                    ((startCal.get(Calendar.MONTH) < current.get(Calendar.MONTH)) && startCal.get(Calendar.YEAR) <= current.get(Calendar.YEAR)) ||
                    ((startCal.get(Calendar.DATE) < current.get(Calendar.DATE)) && startCal.get(Calendar.YEAR) <= current.get(Calendar.YEAR))){
                JOptionPane.showMessageDialog(frame,
                        "ERROR: Start date earlier than today.", "Error",
                        JOptionPane.ERROR_MESSAGE); b = false;
            } else if (endCal.get(Calendar.YEAR) < current.get(Calendar.YEAR) ||
                    ((endCal.get(Calendar.MONTH) < current.get(Calendar.MONTH)) && endCal.get(Calendar.YEAR) <= current.get(Calendar.YEAR)) ||
                    ((endCal.get(Calendar.DATE) < current.get(Calendar.DATE)) && endCal.get(Calendar.YEAR) <= current.get(Calendar.YEAR))){
                JOptionPane.showMessageDialog(frame,
                        "ERROR: End date earlier than today.", "Error",
                        JOptionPane.ERROR_MESSAGE); b = false;
            }

            startCal.add(Calendar.DATE, 60);
            if(startCal.before(endCal)){
                JOptionPane.showMessageDialog(frame, "ERROR: Length of stay must be 60 nights or less","Error",
                        JOptionPane.ERROR_MESSAGE); b = false;
            }
            startCal.add(Calendar.DATE, -60);

        } catch (ParseException p) {
            p.printStackTrace();
        }
        return b;
    }
}