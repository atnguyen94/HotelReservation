/**
 * Describes a guest using the hotel reservation system
 *  * GROUP NAME: Warriors
 * @author Milan Mishra, Tuan Nguyen, Nicholas Lacroix
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Guest extends User implements Serializable {
    private ArrayList<Room> roomsReserved;

    public Guest(String name, String userID) {
        super(name, userID);
        roomsReserved = new ArrayList<>();
    }

    /**
     * Adds a room to the guest's reserved rooms
     * @param room the room to be added
     */
    public void addRoom(Room room)
    {
        roomsReserved.add(room);
    }

    public ArrayList<Room> getRooms()
    {
        return roomsReserved;
    }

    /**
     * 
     * @param checkIn the check in date
     * @param checkOut the check out date
     * @param userID the user ID
     * @param lists the user's list of reservations
     */
    public void setRoomsReserved(Date checkIn, Date checkOut, String userID, ReservationLists lists) {
        Reservation r = new Reservation(checkIn, checkOut, userID);
        lists.add(r);
    }

    /**
     * 
     * @param userID the user's ID
     * @param lists the user's list of reservations
     */
    public void viewReservation(String userID, ReservationLists lists) {
        lists.getUserReservations(userID);
    }

    /**
     * 
     * @param res the reservation to be cancelled
     * @param lists the list of reservations
     */
    public void cancelReservation(Reservation res, ReservationLists lists)
    {
        lists.cancelReservation(res);
    }
}
