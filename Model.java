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

    public Calendar getCal() {
        return cal;
    }

    public void setView(View view) {
        this.view = view;
    }

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


    public Guest locateUser(String userID)
    {
        Iterator<Guest> guestIterator = userIterator();

        while(guestIterator.hasNext())
        {
            Guest guest = guestIterator.next();
            if(guest.getUserID().equals(userID)){
                return guest;
            }
        }
        return null;
    }

    public boolean verifyInformation(String userID, String name)
    {
        User user = locateUser(userID);
        if (user != null) return user.getName().equals(name);
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

    public void loadEvents() throws ParseException {
        String sLine;
        String title;
        int month;
        int day;
        int year;
        Date d1;
        Date d2;
        Calendar c1;
        Calendar c2;
        Room e;
        User us;

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mmaa");

        try {
            Scanner reader = new Scanner(new FileInputStream("users.txt"));

            while (reader.hasNext()) {
                sLine = reader.nextLine();
                String[] separated = sLine.split(", ");
                String[] separated2;
                String[] separated3;

                if (separated.length == 6) {
                    //start date
                    separated2 = separated[2].split("/");
                    //end date
                    separated3 = separated[3].split("/");
                    month = Integer.parseInt(separated2[0]);
                    day = Integer.parseInt(separated2[1]);
                    year = Integer.parseInt(separated2[2]);
                    title = separated[0];
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    int num = Integer.parseInt(separated[4]);
                    Room pp = null;

                    /*Create the rooms from the text file
                    if( 0 < num && num <= 10)
                        pp = new Luxury(num, separated[2], separated[3]);
                    if( 10 < num && num <= 20)
                        pp = new Economy(num, separated[2], separated[3]);

                    //create the user from the text file
                    us = new User(separated[0], separated[1], false);

                    if(events.contains(us))
                    {
                        us.addRoom(pp);
                        us.setPayment(pp.getCost());
                    }
                    else
                    {
                        us.addRoom(pp);
                        us.setPayment(pp.getCost());
                        addUser(us);
                    }
                }
                if(separated.length == 4)
                {
               /*
                *             pw.print(u.getGuest() + ", ");
            pw.print(u.getID() + ", ");
            pw.print(u.getPayment());
            pw.println(u.checkManager());

                    boolean manager;
                    if(separated[3].equals("True"))
                        manager = true;
                    else
                        manager = false;
                    us = new User(separated[0], separated[1], manager);
                    us.setPayment(Double.parseDouble(separated[2]));
                    for(int i = 0; i < events.size(); i++)
                    {
                        if(us.equals(events.get(i)))
                            break;
                        else
                        {
                            addUser(us);
                            break;
                        }
                    }
                }

                // d1 = sdf.parse(separated[2]);
                // d2 = sdf.parse(separated[3]);

            c1 = GregorianCalendar.getInstance();
            c1.setTime(d1);
            c1.set(year, month - 1, day);

            c2 = GregorianCalendar.getInstance();
            c2.setTime(d2);
            c2.set(year, month - 1, day);*/


                }
            }
        } catch (FileNotFoundException error) {
            System.out.println("This is the first time running, events.txt does not exist!");
        }
    }
}
