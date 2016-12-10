

public class MainHotel {

    public static void main(String[] args) {

        Model model = new Model();

        model.addUser(new Guest("temp", "temp"));

        GuestOrManagerView guestOrManagerView = new GuestOrManagerView(model);
    }
}
