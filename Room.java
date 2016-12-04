import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public interface Room
{
   static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
   static final int MAX_DAYS_ALLOWED = 60;
   

   /**
    * CODE CITATION: http://stackoverflow.com/questions/7103064/java-calculate-the-number-of-days-between-two-dates/24409106#24409106
    * @param checkInDate the check in date
    * @param checkOutDate the check out date
    * @return the number of days between
    */
   public static long getDayCount(String checkInDate, String checkOutDate) {
     long diff = -1;
     try {
       Date dateStart = simpleDateFormat.parse(checkInDate);
       Date dateEnd = simpleDateFormat.parse(checkOutDate);

       //time is always 00:00:00 so rounding should help to ignore the missing hour when going from winter to summer time as well as the extra hour in the other direction
       diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
     } catch (Exception e) {
       //handle the exception according to your own situation
     }
     return diff;
   }
   
   public static Date getCheckOutDate(String str) throws ParseException
   {
      Date start = simpleDateFormat.parse(str);
      return start;
   }
   
   /**
    * Compares two room times
    * @param newRoom the room to be compared
    * @return 1 if check in and check out times do not conflict
    *        -1 if check in and check out times do not conflict
    *         0 if check in and check out times do conflict
    */
   public int compareTo(Room newRoom);
   
   public String getCheckInDate();
   
   public String getCheckOutDate();
   
   public int getRoomNumber();
   
   public double getCost();
}
