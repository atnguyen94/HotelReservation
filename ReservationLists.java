import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class ReservationLists
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

    public Iterator<Reservation> getReservations()
    {
        return reservationArrayList.iterator();
    }

    public void cancelReservation(Reservation res)
    {
        reservationArrayList.remove(res);
    }

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
