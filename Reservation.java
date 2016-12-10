/**
 * This class describes a reservation for a room
 *  * GROUP NAME: Warriors
 * @author Milan Mishra, Tuan Nguyen, Nicholas Lacroix
 */
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Reservation implements Serializable {
    private Date checkIn;
    private Date checkOut;
    private String userID;
    private Room room;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public Reservation(Date checkIn, Date checkOut, String userID) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.userID = userID;
    }

    public Reservation(Date checkIn, Date checkOut, String userID, Room r) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.userID = userID;
        room = r;
    }

    public String getUserID()
    {
        return userID;
    }

    public int getRoomNumber()
    {
        return room.getRoomNumber();
    }

    public Date getCheckIn()
    {
        return checkIn;
    }

    public Date getCheckOut()
    {
        return checkOut;
    }

    public int getPrice()
    {
        return room.getCost();
    }

    public String toString() {
        return "Room #" + getRoomNumber() +  "...................From " + sdf.format(checkIn) + " to " + sdf.format(checkOut);
    }

}

