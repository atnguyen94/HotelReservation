/**
 * This method contains the list of reservations for each room
 *  * GROUP NAME: Warriors
 * @author Milan Mishra, Tuan Nguyen, Nicholas Lacroix
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.io.Serializable;



public class ReservationLists implements Serializable
{
    private ArrayList<Reservation> reservationArrayList;

    public ReservationLists()
    {
        reservationArrayList = new ArrayList<>();
    }

    public void add(Reservation res)
    {
        reservationArrayList.add(res);

        if(reservationArrayList.size()>1){
            Collections.sort(reservationArrayList, new Comparator<Reservation>(){
                public int compare(Reservation res1, Reservation res2)
                {
                    if(res1.getCheckIn().after(res2.getCheckIn()))
                        return  -1;
                    else
                        return 1;
                }});
        }
    }

    /**
     * 
     * @return and iterator to traverse the reservation list
     */
    public Iterator<Reservation> getReservations()
    {
        return reservationArrayList.iterator();
    }

    /**
     * 
     * @return an array list of all reservations
     */
    public ArrayList<Reservation> getRes()
    {
       return reservationArrayList;
    }
    
    /**
     * Removes a reservation from the reservation list
     * @param res the reservation to be cancelled
     */
    public void cancelReservation(Reservation res)
    {
        reservationArrayList.remove(res);
    }
    
    /**
     * Returns the reservations of a specific user
     * @param g the user 
     * @return the user's reservations
     */
    public ArrayList<Reservation> getUserRes(Guest g)
    {
       ArrayList<Reservation> r = new ArrayList<>();
       
       for(Reservation res: reservationArrayList)
       {
          if(res.getUserID().equals(g.getUserID()))
             r.add(res);
       }
       
       return r;
    }

    /**
     * Returns an iterator to traverse a user's reservations
     * @param userID the ID of a user
     * @return an iterator
     */
    public Iterator<Reservation> getUserReservations(String userID)
    {
        ArrayList<Reservation> userReservationList = new ArrayList<>();
        for(Reservation res : reservationArrayList)
        {
            if(res.getUserID().equals(userID))
            {
                userReservationList.add(res);
            }
        }
        return userReservationList.iterator();
    }

    /**
     * Returns an iterator to traverse reservations by room
     * @param roomNum the room number
     * @return an iterator
     */
    public Iterator<Reservation> getIteratorByRoom(int roomNum)
    {
        ArrayList<Reservation> roomReservationList = new ArrayList<>();
        for(Reservation res : reservationArrayList)
        {
            if(res.getRoomNumber() == roomNum)
            {
                roomReservationList.add(res);
            }
        }
        return roomReservationList.iterator();
    }

    /**
     * Returns an iterator to traverse reservations by date
     * @param date the specific date
     * @return an iterator
     */
    public Iterator<Reservation> getDateReservations(Date date)
    {
        ArrayList<Reservation> dateReservationList = new ArrayList<>();

        for(Reservation res : reservationArrayList)
        {
            if(date.before(res.getCheckIn()) || date.after(res.getCheckOut())) continue;
            dateReservationList.add(res);
        }
        return dateReservationList.iterator();
    }

}
