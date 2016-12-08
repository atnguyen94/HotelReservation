
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleCalendar {

    public static void main(String[] args) throws ParseException, IOException {
        Model model = new Model();
        model.readReservations();
        GuestOrManagerView gOrM = new GuestOrManagerView(model);


        for(User u : model.getUsers())
        {
           for(Room r : u.getRooms())
           {
              System.out.println(u.getGuest() + " " + r.getCheckInDate() + " " + r.getCheckOutDate());
           }
        }
    }
}
