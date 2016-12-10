import java.util.ArrayList;


public class SimpleReceipt implements Receipt
{
   private User user;
   private double total;
   
   public SimpleReceipt(User user)
   {
      this.user = user;
      total = 0;
   }

   @Override
   public double getTotalPrice()
   {
      // TODO Auto-generated method stub
      ArrayList<Room> r = user.getRooms();
      total = r.get(r.size()-1).getCost();
      return total;
   }

}
