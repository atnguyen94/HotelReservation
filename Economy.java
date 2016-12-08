import java.io.Serializable;
import java.util.Date;


public class Economy implements Room, Serializable
{
   private int roomNumber;
   private String checkInDate;
   private String checkOutDate;
   private final double cost = 200.00;
   
   
   public Economy(int roomNumber, String checkInDate, String checkOutDate)
   {
      // TODO Auto-generated constructor stub
      this.roomNumber = roomNumber;
      this.checkInDate = checkInDate;
      this.checkOutDate = checkOutDate;
   }
   
   
   
   public double getCost()
   {
      return cost;
   }

   /**
    * Compares two room times
    * @param newRoom the room to be compared
    * @return 1 if check in and check out times do not conflict
    *        -1 if check in and check out times do not conflict
    *         0 if check in and check out times do conflict
    */
   public int compareTo(Room newRoom) {
      if (newRoom.getCheckInDate().compareTo(this.checkInDate) < 0 && newRoom.getCheckOutDate().compareTo(this.checkOutDate) < 0) {
          return 1;
      }
      else if (newRoom.getCheckInDate().compareTo(this.checkOutDate) > 0 && newRoom.getCheckOutDate().compareTo(this.checkOutDate) > 0) {
          return -1;
      }
      else {
          return 0;
      }
  }
   
   public String getCheckInDate()
   {
      return checkInDate;
   }
   
   public String getCheckOutDate()
   {
      return checkOutDate;
   }
   
   public int getRoomNumber()
   {
      return roomNumber;
   }
}
