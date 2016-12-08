import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable
{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private final String name;
   private double payment;
   private String userID;
   private ArrayList<Room> roomsReserved;
   private final boolean isManager;
   
   public User(String name, String userID, boolean isManager)
   {
      this.name = name;
      this.payment = payment;
      this.userID = userID;
      this.isManager = isManager;
      roomsReserved = new ArrayList<Room>();
   }
   
   public User()
   {
      // TODO Auto-generated constructor stub
      name = "";
      payment = 0;
      userID = "";
      isManager = false;
      roomsReserved = new ArrayList<Room>();
   }
   
   public boolean equals(User u)
   {

      
      return name.equals(u.name) && userID.equals(u.userID)
            && isManager == u.isManager;
   }
   
   public void addRoom(Room room)
   {
      roomsReserved.add(room);
   }
   
   public String getID()
   {
      return userID;
   }
   
   public ArrayList<Room> getRooms()
   {
      return roomsReserved;
   }
   
   public String getGuest()
   {
      return this.name;
   }
   
   public double getPayment()
   {
      return this.payment;
   }
   
   public void setPayment(double payment)
   {
      this.payment = payment;
   }
   
   public void setID(String id)
   {
      this.userID = id;
   }
   
   public boolean checkManager()
   {
      return this.isManager;
   }
}
