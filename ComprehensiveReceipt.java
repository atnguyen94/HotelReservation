/**
 * Represents the comprehensive receipt containing all transactions a user
 * makes.
 *  * GROUP NAME: Warriors
 * @author Milan Mishra, Tuan Nguyen, Nicholas Lacroix
 *
 */
public class ComprehensiveReceipt implements Receipt
{
    private Guest user;
    private int total;

    public ComprehensiveReceipt(Guest user)
    {
        this.user = user;
        total = 0;
    }

    @Override
    public int getTotalPrice()
    {
        // TODO Auto-generated method stub
        for(Room r : user.getRooms())
        {
            total += r.getCost();
        }
        return total;
    }

}