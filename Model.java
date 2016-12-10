

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.event.*;

import java.util.*;



public class Model implements Serializable
{
   private ArrayList<Room> hotelRoomLayout;
   private ArrayList<Room> emptyRooms;
   private ArrayList<Guest> guests;
   private ArrayList<ChangeListener> changeListeners = new ArrayList<ChangeListener>();
   private Guest currentUser;
   private Calendar cal;
   private View view;
   private ManagerView managerView;

   public Model()
   {
      hotelRoomLayout = new ArrayList<>();

      for(int i = 1; i <= 10; i++){
         hotelRoomLayout.add(new Room(i, 200, new ReservationLists() ));
      }

      for(int i = 11; i <= 20; i++){
         hotelRoomLayout.add(new Room(i, 80, new ReservationLists() ));
      }

      guests = new ArrayList<>();
      cal = new GregorianCalendar();
   }

   public void setDay(int day) {
      cal.set(Calendar.DAY_OF_MONTH, day);
      view.repaint();
   }

   
   public boolean hasNoReservations(int index)
   {
      return hotelRoomLayout.get(index).isEmpty();
   }
   
   public void prevMonth() {
      cal.add(Calendar.MONTH, -1);
      view.repaint();
   }

   public void nextMonth() {
      cal.add(Calendar.MONTH, 1);
      view.repaint();
   }

   public void setManagerDay(int day) {
      cal.set(Calendar.DAY_OF_MONTH, day);
      managerView.repaint();
   }

   public void prevManagerMonth() {
      cal.add(Calendar.MONTH, -1);
      managerView.repaint();
   }

   public void nextManagerMonth() {
      cal.add(Calendar.MONTH, 1);
      managerView.repaint();
   }

   public void prevManageYear() {
      cal.add(Calendar.YEAR, -1);
      managerView.repaint();
   }

   public void nextManagerYear() {
      cal.add(Calendar.YEAR, 1);
      managerView.repaint();
   }

   public Calendar getCal() {
      return cal;
   }

   public void setView(View view) {
      this.view = view;
   }
   ;
   public void setManagerView(ManagerView managerView) { this.managerView = managerView; }

   public Guest getCurrentUser()
   {
      return this.currentUser;
   }

   public ArrayList<Guest> getUsers() {
      return guests;
   }

   public ArrayList<Room> getEmptyRooms()
   {
      return emptyRooms;
   }

   public ArrayList<Room> getRooms()
   {
      return hotelRoomLayout;
   }

   public Iterator<Room> roomIterator()
   {
      return hotelRoomLayout.iterator();
   }

   public Iterator<Guest> userIterator()
   {
      return guests.iterator();
   }

   public void addUser(Guest guest)
   {
      guests.add(guest);
   }

   public void addRoom(Room r)
   {
      hotelRoomLayout.add(r);
   }

   public void setCurrentUser(Guest u)
   {
      this.currentUser = u;
   }


   public Guest locateUser(String name)
   {
      Iterator<Guest> guestIterator = userIterator();

      while(guestIterator.hasNext())
      {
         Guest guest = guestIterator.next();
         if(guest.getName().equals(name)){
            return guest;
         }
      }
      return null;
   }

   public boolean verifyInformation(String name, String userID)
   {
      User user = locateUser(name);
      if (user != null) return user.getUserID().equals(userID);
      return false;
   }


   public void paintRooms(Calendar checkIn, Calendar checkOut, int roomCost)
   {
      Iterator<Room> roomIterator = roomIterator();
      emptyRooms = new ArrayList<>();
      while(roomIterator.hasNext())
      {
         Room room = roomIterator.next();
         if(room.checkClass(roomCost) && room.isEmpty(checkIn, checkOut)) {
            emptyRooms.add(room);
         }
      }

      ChangeEvent e = new ChangeEvent(this);
      for(ChangeListener c : changeListeners)
      {
         c.stateChanged(e);
      }
   }

   public void attach(ChangeListener changeListener)
   {
      changeListeners.add(changeListener);
   }

   public ArrayList<Reservation> getGuestReservations(Guest guest)
   {
      Iterator<Room> roomIterator = roomIterator();
      ArrayList<Reservation> reservationArrayList = new ArrayList<Reservation>();
      while(roomIterator.hasNext())
      {
         Room room = roomIterator.next();
         ReservationLists reservations = room.getReservations();
         Iterator<Reservation> reservationIterator = reservations.getUserReservations(guest.getUserID());
         while(reservationIterator.hasNext())reservationArrayList.add(reservationIterator.next());
      }
      return reservationArrayList;
   }

   
   public ArrayList<Reservation> getDateReservations(Date date)
   {
      Iterator<Room> roomIterator = roomIterator();
      ArrayList<Reservation> reservationArrayList = new ArrayList<>();
      while(roomIterator.hasNext())
      {
         Room room = roomIterator.next();
         ReservationLists reservations = room.getReservations();
         Iterator<Reservation> reservationIterator = reservations.getDateReservations(date);
         while(reservationIterator.hasNext())reservationArrayList.add(reservationIterator.next());
      }
      return reservationArrayList;
   }

   public void cancelReservation(Reservation res)
   {
      Iterator<Room> roomIterator = currentUser.getRooms().iterator();
      while(roomIterator.hasNext())
      {
         Room room = roomIterator.next();
         if(room.getRoomNumber() == res.getRoomNumber()) {
            room.cancelReservation(res);
            return;
         }
      }
   }

   public void saveUsers() throws IOException {
      File file = new File("users.txt");
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

      if (!file.exists()) {
         file.createNewFile();
      }

      try {
         FileOutputStream fileIn = new FileOutputStream(file);
         ObjectOutputStream in = new ObjectOutputStream(fileIn);
         in.writeObject(guests);
         in.close();
         fileIn.close();
      } catch (IOException i) {
         i.printStackTrace();
         return;
      }
   }

   public void loadEvents() throws IOException {
      String fileName = "users.txt";
      File myFile = new File(fileName);

      if (myFile.exists()) {
         try {
            FileInputStream fileIn = new FileInputStream(myFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            guests =  (ArrayList<Guest>) in.readObject();
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
}
