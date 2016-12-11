/**
 * Using the strategy pattern, this class creates a receipt for the most recent
 * transaction of a user
 *  * GROUP NAME: Warriors
 * @author Milan Mishra, Tuan Nguyen, Nicholas Lacroix
 */
import java.util.ArrayList;


public class SimpleReceipt implements Receipt
{
    private Guest guest;
    private int total;

    public SimpleReceipt(Guest user)
    {
        this.guest = user;
        total = 0;
    }

    @Override
    public int getTotalPrice()
    {
        // TODO Auto-generated method stub
        ArrayList<Room> r = guest.getRooms();
        if(r.isEmpty())
           total = 0;
        else
           total = r.get(r.size()-1).getCost();
        return total;
    }

}