import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CustomerInfo extends JFrame {
    private JTextField textName;
    private JLabel labelName;
    private JTextField textEmail;
    private JTextField textPhoneNum;
    private JLabel labelEmail;
    private JLabel labelPhoneNum;
    private JButton btnRegCustomer;
    private JButton btnPremCustomer;
    private JLabel labelCT;
    private JPanel CustomerInfoPanel;




    public CustomerInfo() {
        setContentPane(CustomerInfoPanel);
        setTitle("Customer Info");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,400);
        setLocationRelativeTo(null);
        setVisible(true);




        btnRegCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegularCustomer newRegCustomer = new RegularCustomer(textName.getText(), textEmail.getText(), Integer.parseInt(textPhoneNum.getText()));
                newRegCustomer.payCoffee();


                new MainMenu();
            }
        });
        btnPremCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PremiumCustomer newPremCustomer = new PremiumCustomer(textName.getText(), textEmail.getText(), Integer.parseInt(textPhoneNum.getText()));
                newPremCustomer.payCoffee();


                new MainMenu();
            }
        });
    }


    public static void main(String[] args) {
        new CustomerInfo();
    }





   public boolean isValidCharEmail(String email) {
       email = textEmail.getText();
       return email.matches("[a-zA-Z0-9] + @ + [a-zA-Z0-9] + . + [a-zA-Z0-9]");
   }
   public boolean isValidCharPhoneNum(String phoneNum) {
       phoneNum = textPhoneNum.getText();
       return phoneNum.matches("[0-9]");
   }
}


