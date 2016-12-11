public class ComprehensiveReceipt implements Receipt
{
    private Guest user;
    private double total;

    public ComprehensiveReceipt(Guest user)
    {
        this.user = user;
        total = 0;
    }

    @Override
    public double getTotalPrice()
    {
        // TODO Auto-generated method stub
        for(Room r : user.getRooms())
        {
            total += r.getCost();
        }
        return total;
    }

}