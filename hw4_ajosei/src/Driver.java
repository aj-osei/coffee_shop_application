import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;






public class Driver {
    public void start() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new JFrame();


                    frame.setSize(600,600);
                    frame.setTitle("Main Menu");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    frame.add(createMainPanel(), BorderLayout.CENTER);


                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });}


//    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("customerinfo.csv"));
//        String line = reader.readLine();
//        while ((line = reader.readLine()) != null) {
//            String[] data = line.split(",");
//            try {
//                int order = Integer.parseInt(data[0]);
//                String name = data [1];
//                String email = data [2];
//                int phone_num = Integer.parseInt(data [3]);
//                Customer customer = new Customer(order, name, email, phone_num);
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//            }
//    }




    public JPanel createMainPanel() {


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


        JPanel main2Panel = new JPanel();


        JButton order = new JButton("Order");
        JButton prepareOrder = new JButton("Prepare Order");
        JButton viewOrder = new JButton("View Order");
        JButton exit = new JButton("Exit");


        main2Panel.setLayout(new GridLayout(2,2));


        main2Panel.add(order);
        main2Panel.add(prepareOrder);
        main2Panel.add(viewOrder);
        main2Panel.add(exit);


        for (Component c : main2Panel.getComponents()) {
            c.setFont(new Font("Arial", Font.PLAIN, 24));
        }


        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel secondPanel = new JPanel();
                secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.Y_AXIS));




                ;






            }




        });
        mainPanel.add(main2Panel);


        return mainPanel;
    }
}
