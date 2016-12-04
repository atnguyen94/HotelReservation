import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Model {
   private ArrayList<User> events = new ArrayList<>();
   private Calendar cal;
   private View view;

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
      view.repaint();
   }

   public ArrayList<User> getUsers() {
      return events;
   }

   public void setView(View view) {
      this.view = view;
   }

   public void saveUsers() throws IOException {
      File file = new File("users.txt");
      FileWriter fw = new FileWriter(new File("users.txt"));
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

      if (!file.exists()) {
         file.createNewFile();
      }

      PrintWriter pw = new PrintWriter(file);

      for(User u : events)
      {
         for (Room e : u.getRooms()) {
            String startDate = e.getCheckInDate();
            String endDate = e.getCheckOutDate();

            pw.print(u.getGuest() + ", ");
            pw.print(u.getID() + ", ");
            pw.print(startDate + ", ");
            pw.print(endDate + ", ");
            pw.print(e.getRoomNumber() + ", ");
            pw.println(u.checkManager());
         }
      }

      pw.close();
      System.exit(0);

   }

   public void loadEvents() throws ParseException {
      String sLine;
      String title;
      int month;
      int day;
      int year;
      Date d1;
      Date d2;
      Calendar c1;
      Calendar c2;
      Room e;
      User us;
      
      SimpleDateFormat sdf = new SimpleDateFormat("hh:mmaa");

      try {
         Scanner reader = new Scanner(new FileInputStream("users.txt"));

         while (reader.hasNext()) {
            sLine = reader.nextLine();
            String[] separated = sLine.split(", ");
            String[] separated2 = separated[2].split("/");
            String[] separated3 = separated[3].split("/");
            //System.out.println(sLine);

            title = separated[0];
            month = Integer.parseInt(separated2[0]);
            day = Integer.parseInt(separated2[1]);
            year = Integer.parseInt(separated2[2]);
           // d1 = sdf.parse(separated[2]);
           // d2 = sdf.parse(separated[3]);

           /* c1 = GregorianCalendar.getInstance();
            c1.setTime(d1);
            c1.set(year, month - 1, day);

            c2 = GregorianCalendar.getInstance();
            c2.setTime(d2);
            c2.set(year, month - 1, day);
*/
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            int num = Integer.parseInt(separated[4]);
            Room pp = null;
            
            //Create the rooms from the text file
            if( 0 < num && num <= 10)
               pp = new Luxury(num, separated[2], separated[3]);
            if( 10 < num && num <= 20)
               pp = new Economy(num, separated[2], separated[3]);
            
            //create the user from the text file
            us = new User(separated[0], separated[1], pp.getCost(), false);
            
            if(events.contains(us))
               us.addRoom(pp);
            else
            {
               us.addRoom(pp);
               addUser(us);
            }

         }
      } catch (FileNotFoundException error) {
         System.out.println("This is the first time running, events.txt does not exist!");
      }
   }
}
