import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;



public class ReceiptView
{
    private Model model;

    public ReceiptView(Model model)
    {
        this.model = model;

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(400,600));


      /*
       * Describes the look and feel of the receipt
       */
        JTextArea costs = new JTextArea();
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

                if((model.getCurrentUser().getRooms().get(0).getClass()+"").substring(5).equals("Luxury"))
                {
                    test += String.format("  %16s %39s %23s%.2f\n",
                            (model.getCurrentUser().getRooms().get(0).getClass()+"").substring(5),
                            model.getCurrentUser().getRooms().get(0).getRoomNumber(),
                            "$",
                            model.getCurrentUser().getRooms().get(0).getCost()) + "\n";
                }
                else
                {
                    test += String.format("  %10s %41s %23s%.2f\n",
                            (model.getCurrentUser().getRooms().get(0).getClass()+"").substring(5),
                            model.getCurrentUser().getRooms().get(0).getRoomNumber(),
                            "$",
                            model.getCurrentUser().getRooms().get(0).getCost()) + "\n";
                }
                test += String.format("\n%11s %33s %33s", "------------------", "-----------------------", "----------");
                test += String.format("\n %s %74s%.2f\n", "TOTAL:", "$", simp.getTotalPrice());
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
                    if((x.getClass()+"").substring(6).equals("Luxury"))
                    {
                        test += String.format("%16s %40s %23s%.2f\n",
                                (x.getClass()+"").substring(5),
                                x.getRoomNumber(),
                                "$",
                                x.getCost()) + "\n";
                    }
                    else
                    {
                        test += String.format("  %10s %41s %23s%.2f\n",
                                (x.getClass()+"").substring(5),
                                x.getRoomNumber(),
                                "$",
                                x.getCost()) + "\n";
                    }
                }
                test += String.format("\n%11s %33s %33s", "------------------", "-----------------------", "----------");
                test += String.format("\n %s %73s%.2f\n", "TOTAL :", "$", simp.getTotalPrice());
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}