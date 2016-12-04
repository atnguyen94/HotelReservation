
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleCalendar {

    public static void main(String[] args) throws ParseException {
       User user = new User("Bob", "bob", 0, false);
        Model model = new Model();
        View view = new View(model);
        model.setView(view);
        model.loadEvents();

        for(User u : model.getUsers())
        {
           for(Room r : u.getRooms())
           {
              System.out.println(u.getGuest() + " " + r.getCheckInDate() + " " + r.getCheckOutDate());
           }
        }
    }
}
