import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Guest extends User implements Serializable {
    private ArrayList<Room> roomsReserved;

    public Guest(String name, String userID) {
        super(name, userID);
        roomsReserved = new ArrayList<>();
    }

    public void addRoom(Room room)
    {
        roomsReserved.add(room);
    }

    public ArrayList<Room> getRooms()
    {
        return roomsReserved;
    }

    public void setRoomsReserved(Date checkIn, Date checkOut, String userID, ReservationLists lists) {
        Reservation r = new Reservation(checkIn, checkOut, userID);
        lists.add(r);
    }

    public void viewReservation(String userID, ReservationLists lists) {
        lists.getUserReservations(userID);
    }

    public void cancelReservation(Reservation res, ReservationLists lists)
    {
        lists.cancelReservation(res);
    }
}
