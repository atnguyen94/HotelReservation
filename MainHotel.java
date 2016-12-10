import java.text.ParseException;

public class MainHotel {

    public static void main(String[] args) throws ParseException {

        Model model = new Model();


        GuestOrManagerView guestOrManagerView = new GuestOrManagerView(model);
    }
}
