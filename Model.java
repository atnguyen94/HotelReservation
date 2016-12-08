import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Model implements Serializable {
   /**
    * 
    */
   private static final long serialVersionUID = 3698135386062337013L;
   private ArrayList<User> events = new ArrayList<>();
   private Calendar cal;
   private View view;
   private User currentUser;

   @SuppressWarnings("unchecked")
   public void readReservations() throws IOException
   {
      String fileName = "reservations.txt";
      File myFile = new File(fileName);

      if (myFile.exists()) {
         try {
            FileInputStream fileIn = new FileInputStream(myFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            events =  (ArrayList<User>) in.readObject();
            in.close();
            fileIn.close();
         } catch (IOException i) {
            i.printStackTrace();
            return;
         } catch (ClassNotFoundException c) {
            c.printStackTrace();
         }
      }
   }

   public void writeReservations() throws IOException
   {
      String fileName = "reservations.txt";
      File myFile = new File(fileName);

      if (myFile.exists()) {
         try {
            FileOutputStream fileIn = new FileOutputStream(myFile);
            ObjectOutputStream in = new ObjectOutputStream(fileIn);
            in.writeObject(events);
            in.close();
            fileIn.close();
         } catch (IOException i) {
            i.printStackTrace();
            return;
         }
      }
   }

   public Model() {
      cal = new GregorianCalendar();
   }

   public void setDay(int day) {
      cal.set(Calendar.DAY_OF_MONTH, day);
      view.repaint();
   }

   public void prevDay() {
      cal.add(Calendar.DAY_OF_MONTH, -1);
      view.repaint();
   }

   public void nextDay() {
      cal.add(Calendar.DAY_OF_MONTH, 1);
      view.repaint();
   }

   public Calendar getCal() {
      return cal;
   }

   public void addUser(User e) {
      events.add(e);
      //view.repaint();
   }

   public void addRoom(Room room)
   {
      currentUser.addRoom(room);
      view.repaint();
   }

   public void setCurrentUser(User u)
   {
      this.currentUser = u;
   }

   public User getCurrentUser()
   {
      return this.currentUser;
   }

   public ArrayList<User> getUsers() {
      return events;
   }

   public void setView(View view) {
      this.view = view;
   }  
}
