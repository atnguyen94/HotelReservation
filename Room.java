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

    public int getRoomNumber()
    {
        return number;
    }

    
    
    public int getCost() {return cost;}

    public ReservationLists getReservations()
    {
        return reservations;
    }

    public void addReservation(Reservation res)
    {
        reservations.add(res);
    }

    public void cancelReservation(Reservation res) {
        reservations.cancelReservation(res);
    }

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
    
    public boolean isEmpty()
    {
       return !reservations.getReservations().hasNext();
    }
}
