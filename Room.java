/**
 * This method represents a room in the hotel
 *  * GROUP NAME: Warriors
 * @author Milan Mishra, Tuan Nguyen, Nicholas Lacroix
 */
import java.io.Serializable;
import java.util.*;


public class Room implements Serializable
{
    private int number;
    private int cost;
    private ReservationLists reservations;

    public Room(int number, int cost, ReservationLists rLists)
    {
        this.number = number;
        this.cost = cost;
        reservations = rLists;
    }

    /**
     * 
     * @return the room number
     */
    public int getRoomNumber()
    {
        return number;
    }

    
    /**
     * 
     * @return the cost of the room
     */
    public int getCost() {return cost;}

    /**
     * 
     * @return the reservations for a room
     */
    public ReservationLists getReservations()
    {
        return reservations;
    }

    /**
     * 
     * @param res the reservation to be added to a room
     */
    public void addReservation(Reservation res)
    {
        reservations.add(res);
    }

    /**
     * 
     * @param res the reservation to be cancelled
     */
    public void cancelReservation(Reservation res) {
        reservations.cancelReservation(res);
    }

    /**
     * 
     * @param lists the list of reservations of a room
     */
    public void setReservation(ReservationLists lists) {
        reservations = lists;
    }

    public boolean checkClass(int roomCost)
    {
        return cost == roomCost;
    }

    public String toString()
    {
        return "Room #" + getRoomNumber();
    }

    public boolean isEmpty(Calendar startDate, Calendar endDate) {
        Iterator<Reservation> reservationIterator = reservations.getReservations();
        while(reservationIterator.hasNext()) {
            Reservation res = reservationIterator.next();
            Date start = res.getCheckIn();
            Date end = res.getCheckOut();

            if (((start.after(startDate.getTime()) || start.equals(startDate.getTime()))
                    && (start.before(endDate.getTime())) || start.equals(endDate.getTime())))
            {
                return false;
            }
            else if ((end.after(startDate.getTime()) || end.equals(startDate.getTime()))
                    && (end.before(endDate.getTime()) || end.equals(endDate.getTime())))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * @return true if a room has reservations, false if no reservations made
     */
    public boolean isEmpty()
    {
       return !reservations.getReservations().hasNext();
    }
}
