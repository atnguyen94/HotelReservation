
public class ComprehensiveReceipt implements Receipt
{
   private User user;
   private double total;
   
   public ComprehensiveReceipt(User user)
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
