import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.event.*;
import java.util.*;



public class Model
{
    private ArrayList<Room> hotelRoomLayout;
    private ArrayList<Room> emptyRooms;
    private ArrayList<Guest> guests;
    private ArrayList<ChangeListener> changeListeners = new ArrayList<ChangeListener>();
    private Guest currentUser;
    private Calendar cal;
    private View view;
    private ManagerView managerView;

    public Model()
    {
        hotelRoomLayout = new ArrayList<>();

        for(int i = 1; i <= 10; i++){
            hotelRoomLayout.add(new Room(i, 200, new ReservationLists() ));
        }

        for(int i = 11; i <= 20; i++){
            hotelRoomLayout.add(new Room(i, 80, new ReservationLists() ));
        }

        guests = new ArrayList<>();
        cal = new GregorianCalendar();
    }

    public void setDay(int day) {
        cal.set(Calendar.DAY_OF_MONTH, day);
        view.repaint();
    }

    public void prevMonth() {
        cal.add(Calendar.MONTH, -1);
        view.repaint();
    }

    public void nextMonth() {
        cal.add(Calendar.MONTH, 1);
        view.repaint();
    }

    public void setManagerDay(int day) {
        cal.set(Calendar.DAY_OF_MONTH, day);
        managerView.repaint();
    }

    public void prevManagerMonth() {
        cal.add(Calendar.MONTH, -1);
        managerView.repaint();
    }

    public void nextManagerMonth() {
        cal.add(Calendar.MONTH, 1);
        managerView.repaint();
    }

    public void prevManageYear() {
        cal.add(Calendar.YEAR, -1);
        managerView.repaint();
    }

    public void nextManagerYear() {
        cal.add(Calendar.YEAR, 1);
        managerView.repaint();
    }

    public Calendar getCal() {
        return cal;
    }

    public void setView(View view) {
        this.view = view;
    }
    ;
    public void setManagerView(ManagerView managerView) { this.managerView = managerView; }

    public Guest getCurrentUser()
    {
        return this.currentUser;
    }

    public ArrayList<Guest> getUsers() {
        return guests;
    }

    public ArrayList<Room> getEmptyRooms()
    {
        return emptyRooms;
    }

    public ArrayList<Room> getRooms()
    {
        return hotelRoomLayout;
    }

    public Iterator<Room> roomIterator()
    {
        return hotelRoomLayout.iterator();
    }

    public Iterator<Guest> userIterator()
    {
        return guests.iterator();
    }

    public void addUser(Guest guest)
    {
        guests.add(guest);
    }

    public void addRoom(Room r)
    {
        hotelRoomLayout.add(r);
    }

    public void setCurrentUser(Guest u)
    {
        this.currentUser = u;
    }


    public Guest locateUser(String name)
    {
        Iterator<Guest> guestIterator = userIterator();

        while(guestIterator.hasNext())
        {
            Guest guest = guestIterator.next();
            if(guest.getName().equals(name)){
                return guest;
            }
        }
        return null;
    }

    public boolean verifyInformation(String name, String userID)
    {
        User user = locateUser(name);
        if (user != null) return user.getUserID().equals(userID);
        return false;
    }


    public void paintRooms(Calendar checkIn, Calendar checkOut, int roomCost)
    {
        Iterator<Room> roomIterator = roomIterator();
        emptyRooms = new ArrayList<>();
        while(roomIterator.hasNext())
        {
            Room room = roomIterator.next();
            if(room.checkClass(roomCost) && room.isEmpty(checkIn, checkOut)) {
                emptyRooms.add(room);
            }
        }

        ChangeEvent e = new ChangeEvent(this);
        for(ChangeListener c : changeListeners)
        {
            c.stateChanged(e);
        }
    }

    public void attach(ChangeListener changeListener)
    {
        changeListeners.add(changeListener);
    }

    public ArrayList<Reservation> getGuestReservations(Guest guest)
    {
        Iterator<Room> roomIterator = roomIterator();
        ArrayList<Reservation> reservationArrayList = new ArrayList<Reservation>();
        while(roomIterator.hasNext())
        {
            Room room = roomIterator.next();
            ReservationLists reservations = room.getReservations();
            Iterator<Reservation> reservationIterator = reservations.getUserReservations(guest.getUserID());
            while(reservationIterator.hasNext())reservationArrayList.add(reservationIterator.next());
        }
        return reservationArrayList;
    }

    public ArrayList<Reservation> getDateReservations(Date date)
    {
        Iterator<Room> roomIterator = roomIterator();
        ArrayList<Reservation> reservationArrayList = new ArrayList<>();
        while(roomIterator.hasNext())
        {
            Room room = roomIterator.next();
            ReservationLists reservations = room.getReservations();
            Iterator<Reservation> reservationIterator = reservations.getDateReservations(date);
            while(reservationIterator.hasNext())reservationArrayList.add(reservationIterator.next());
        }
        return reservationArrayList;
    }

    public void cancelReservation(Reservation res)
    {
        Iterator<Room> roomIterator = roomIterator();
        while(roomIterator.hasNext())
        {
            Room room = roomIterator.next();
            if(room.getRoomNumber() == res.getRoomNumber()) {
                room.cancelReservation(res);
                return;
            }
        }
    }

    public void saveUsers() throws IOException {
        File file = new File("users.txt");
        FileWriter fw = new FileWriter(new File("users.txt"));
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        if (!file.exists()) {
            file.createNewFile();
        }

        PrintWriter pw = new PrintWriter(file);

        for (Guest g : guests) {
            for (Room r : g.getRooms()) {
                ReservationLists reservationLists = r.getReservations();
                Iterator<Reservation> reservationIterator = reservationLists.getReservations();
                while(reservationIterator.hasNext()) {
                    Reservation res = reservationIterator.next();
                    Date startDate = res.getCheckIn();
                    Date endDate = res.getCheckOut();

                    String start = sdf.format(startDate);
                    String end = sdf.format(endDate);

                    pw.print(g.getName() + ", ");
                    pw.print(g.getUserID() + ", ");
                    pw.print(start + ", ");
                    pw.print(end + ", ");
                    pw.print(r.getRoomNumber() + ", ");
                    pw.println(r.getCost());

                }
            }
        }
        pw.close();
    }

    public void loadEvents() throws ParseException {
        String sLine;
        String name;
        String id;
        int roomNumber;
        int roomCost;
        Date startDate;
        Date endDate;
        Room room;
        ReservationLists reservationLists = new ReservationLists();
        Reservation r;
        Guest g;
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        try {
            Scanner reader = new Scanner(new FileInputStream("users.txt"));

            while (reader.hasNext()) {
                sLine = reader.nextLine();
                String[] separated = sLine.split(", ");

                startDate = sdf.parse(separated[2]);
                endDate = sdf.parse(separated[3]);
                roomNumber = Integer.parseInt(separated[4]);
                roomCost = Integer.parseInt(separated[5]);
                id = separated[1];
                name = separated[0];
                g = new Guest(name, id);

                startCal.setTime(startDate);
                endCal.setTime(endDate);

                room = new Room(roomNumber, roomCost, reservationLists);
                r = new Reservation(startCal.getTime(), endCal.getTime(), id, room);
                addUser(g);
                setCurrentUser(g);
                getCurrentUser().addRoom(room);
                room.addReservation(r);
            }
        } catch (FileNotFoundException error) {
            System.out.println("This is the first time running, users.txt does not exist!");
        }
    }
}
