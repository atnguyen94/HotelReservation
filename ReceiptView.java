import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;



public class ReceiptView
{
   private Model model;

   public ReceiptView(final Model model)
   {
      this.model = model;

      JFrame frame = new JFrame();
      frame.setLayout(new BorderLayout());
      frame.setSize(new Dimension(400,600));


      /*
       * Describes the look and feel of the receipt
       */
      final JTextArea costs = new JTextArea();
      JScrollPane scroll = new JScrollPane(costs);
      scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);



      /**
       * The buttons to choose between simple and comprehensive
       */
      JLabel choose = new JLabel("SELECT A RECEIPT TYPE: ");


      JButton simple = new JButton("Simple");
      simple.addMouseListener(new MouseAdapter(){
         @Override
         public void mouseClicked(MouseEvent e)
         {
            // TODO Auto-generated method stub
            super.mouseClicked(e);
            String test = "";
            SimpleReceipt simp = new SimpleReceipt(model.getCurrentUser());
            ReceiptContext r = new ReceiptContext(simp);
            test += String.format("%10s", model.getCurrentUser().getName());
            test += String.format("\n\n%10s %22s %28s \n", "ROOM TYPE", "ROOM NUMBER", "PRICE");
            test += String.format("%11s %33s %33s\n\n", "------------------", "-----------------------", "----------");
            ArrayList<Room> rooms = model.getCurrentUser().getRooms();
            
            if(!rooms.isEmpty())
            {
               if((rooms.get(rooms.size()-1).getCost() == 200))
               {
                  test += String.format("  %16s %39s    %23s%d\n",
                        "Luxury",
                        model.getCurrentUser().getRooms().get(rooms.size()-1).getRoomNumber(),
                        "$",
                        model.getCurrentUser().getRooms().get(rooms.size()-1).getCost()) + "\n";
               }
               else
               {
                  test += String.format("   %10s %40s   %23s%d\n",
                        "Economy",
                        model.getCurrentUser().getRooms().get(rooms.size()-1).getRoomNumber(),
                        "$",
                        model.getCurrentUser().getRooms().get(rooms.size()-1).getCost()) + "\n";
               }
            }
            test += String.format("\n%11s %33s %33s", "------------------", "-----------------------", "----------");
            test += String.format("\n %s    %74s%d\n", "TOTAL :", "$", simp.getTotalPrice());
            costs.setText(test);
         }
      });
      JButton comp = new JButton("Comprehensive");
      comp.addMouseListener(new MouseAdapter(){
         @Override
         public void mouseClicked(MouseEvent e)
         {
            // TODO Auto-generated method stub
            super.mouseClicked(e);
            String test = "";
            ComprehensiveReceipt simp = new ComprehensiveReceipt(model.getCurrentUser());
            ReceiptContext r = new ReceiptContext(simp);
            test += String.format("%10s", model.getCurrentUser().getName());
            test += String.format("\n\n%10s %22s %28s \n", "ROOM TYPE", "ROOM NUMBER", "PRICE");
            test += String.format("%11s %33s %33s\n\n", "------------------", "-----------------------", "----------");
            for(Room x : model.getCurrentUser().getRooms())
            {
               if((x.getCost() == 200))
               {
                  test += String.format("  %16s %39s    %23s%d\n",
                        "Luxury",
                        x.getRoomNumber(),
                        "$",
                        x.getCost()) + "\n";
               }
               else
               {
                  test += String.format("   %10s %40s   %23s%d\n",
                        "Economy",
                        x.getRoomNumber(),
                        "$",
                        x.getCost()) + "\n";
               }
            }
            test += String.format("\n%11s %33s %33s", "------------------", "-----------------------", "----------");
            test += String.format("\n %s    %74s%d\n", "TOTAL :", "$", simp.getTotalPrice());
            costs.setText(test);
            // String total = String.format("\n  %-10s %48s%d\n", "Total", "$", simpleTotal);
         }
      });

      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout());
      panel.add(choose);
      panel.add(simple);
      panel.add(comp);

      frame.setTitle("Conformation Receipt for " /*+ model.getCurrentUser().getGuest()*/);
      frame.add(panel, BorderLayout.NORTH);
      frame.add(scroll, BorderLayout.CENTER);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setVisible(true);
   }
}