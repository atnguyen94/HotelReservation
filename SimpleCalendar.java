
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleCalendar {

    public static void main(String[] args) throws ParseException {
        Model model = new Model();
        model.loadEvents();
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
