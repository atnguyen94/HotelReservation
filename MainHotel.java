import java.text.ParseException;

public class MainHotel {

    public static void main(String[] args) throws ParseException {

        Model model = new Model();
        model.loadEvents();

        //model.addUser(new Guest("tempuser", "temppass"));

        GuestOrManagerView guestOrManagerView = new GuestOrManagerView(model);
    }
}
