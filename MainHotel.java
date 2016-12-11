
/**
 *  * GROUP NAME: Warriors
 * @author Milan Mishra, Tuan Nguyen, Nicholas Lacroix
 */
import java.io.IOException;
import java.text.ParseException;

public class MainHotel {

    public static void main(String[] args) throws IOException, ParseException {

        Model model = new Model();
        model.loadEvents();

        GuestOrManagerView guestOrManagerView = new GuestOrManagerView(model);
    }
}
