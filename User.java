import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private String name;
    private String userID;

    public User(String name, String userID) {
        this.name = name;
        this.userID = userID;
    }

    public String getUserID()
    {
        return userID;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return "Name: " + getName();
    }

    public void showReservations(Date checkIn, ReservationLists lists) {
        lists.getDateReservations(checkIn);
    }
}
