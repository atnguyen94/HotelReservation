import java.util.ArrayList;


public class SimpleReceipt implements Receipt
{
    private Guest guest;
    private double total;

    public SimpleReceipt(Guest user)
    {
        this.guest = user;
        total = 0;
    }

    @Override
    public double getTotalPrice()
    {
        // TODO Auto-generated method stub
        ArrayList<Room> r = guest.getRooms();
        total = r.get(r.size()-1).getCost();
        return total;
    }

}