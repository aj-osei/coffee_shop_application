import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;




public class CoffeeShopGUI extends JFrame {
    private JTextField nameField, emailField, phoneField;
    private JComboBox<String> coffeeTypeComboBox, customerTypeComboBox;
    private JComboBox<String> brewTypeComboBox, shotsComboBox, milkTypeComboBox, addIngredientsComboBox;
    private JTextArea outputArea;
    private JButton orderButton, prepareButton, viewOrderButton, exitButton;
    private List<String> orders;




    public CoffeeShopGUI() {
        super("Coffee Shop Menu");
        initializeComponents();
        setupLayout();
        registerListeners();
        updateCoffeeSelection(); // Initial visibility setup based on default selection
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        orders = new ArrayList<>();
    }




    private void initializeComponents() {
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        coffeeTypeComboBox = new JComboBox<>(new String[]{"Espresso $5", "Filtered Coffee $3"});
        customerTypeComboBox = new JComboBox<>(new String[]{"Regular", "Premium"});
        brewTypeComboBox = new JComboBox<>(new String[]{"Dark", "Medium", "Light"});
        shotsComboBox = new JComboBox<>(new String[]{"None", "1 +$.33", "2 +$.66", "3 +$.99", "4 +$1.32"});
        milkTypeComboBox = new JComboBox<>(new String[]{"Whole milk", "Skim milk", "Almond milk"});
        addIngredientsComboBox = new JComboBox<>(new String[]{"None", "Chocolate syrup +$1", "Whipped cream +$1"});
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        orderButton = new JButton("Order");
        prepareButton = new JButton("Prepare");
        viewOrderButton = new JButton("View Orders");
        exitButton = new JButton("Exit");
    }




    private void setupLayout() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Coffee Type:"));
        formPanel.add(coffeeTypeComboBox);
        formPanel.add(new JLabel("Customer Type:"));
        formPanel.add(customerTypeComboBox);
        formPanel.add(new JLabel("Brew Type:"));
        formPanel.add(brewTypeComboBox);
        formPanel.add(new JLabel("Espresso Shots:"));
        formPanel.add(shotsComboBox);
        formPanel.add(new JLabel("Milk Type:"));
        formPanel.add(milkTypeComboBox);
        formPanel.add(new JLabel("Add. Ingredients:"));
        formPanel.add(addIngredientsComboBox);




        coffeeTypeComboBox.addActionListener(e -> updateCoffeeSelection());




        JPanel controlPanel = new JPanel();
        controlPanel.add(orderButton);
        controlPanel.add(prepareButton);
        controlPanel.add(viewOrderButton);
        controlPanel.add(exitButton);




        add(formPanel, BorderLayout.CENTER);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.NORTH);
    }




    private void updateCoffeeSelection() {
        String selected = (String) coffeeTypeComboBox.getSelectedItem();
        boolean isEspresso = "Espresso $5".equals(selected);
        brewTypeComboBox.setVisible(!isEspresso);
        shotsComboBox.setVisible(isEspresso);
        milkTypeComboBox.setVisible(isEspresso);
        addIngredientsComboBox.setVisible(isEspresso);
    }




    private void registerListeners() {
        orderButton.addActionListener(this::orderAction);
        prepareButton.addActionListener(this::prepareAction);
        viewOrderButton.addActionListener(this::viewOrderAction);
        exitButton.addActionListener(e -> System.exit(0));
    }




    private void orderAction(ActionEvent e) {
        String name = nameField.getText();
        String email = emailField.getText();
        String phoneText = phoneField.getText();


        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid name.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (phoneText.length() != 10) {
            JOptionPane.showMessageDialog(this, "Invalid phone number. Phone number must be 10 digits long.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }


        long phone = Long.parseLong(phoneText);


        String customerType = (String) customerTypeComboBox.getSelectedItem();
        boolean isPremium = "Premium".equals(customerType);
        Customer customer = isPremium ?
                new PremiumCustomer(name, email, (int) phone) :
                new RegularCustomer(name, email, (int) phone);


        double totalPrice = 0.0;


        String selectedCoffee = (String) coffeeTypeComboBox.getSelectedItem();
        String brewType = (String) brewTypeComboBox.getSelectedItem();
        String selectedShots = (String) shotsComboBox.getSelectedItem();
        String milkType = (String) milkTypeComboBox.getSelectedItem();
        String addIngredients = (String) addIngredientsComboBox.getSelectedItem();


        if ("Espresso $5".equals(selectedCoffee)) {
            if (!selectedShots.equals("None")) {
                int numberOfShots = Integer.parseInt(selectedShots.split(" ")[0]);
                totalPrice += 5;
                totalPrice += numberOfShots * 0.33;
            } else {
                totalPrice += 5;
            }
            String selectedAddIngredients = (String) addIngredientsComboBox.getSelectedItem();
            if (selectedAddIngredients.contains("$1")) {
                totalPrice += 1;
            }
        }else {
            totalPrice += 3;
        }


        if (isPremium) {
            double discount = totalPrice * 0.1;
            totalPrice *= 0.9;
            outputArea.append("(info) Premium customer discount applied: -$" + String.format("%.2f", discount) + "\n");
        }


        customer.payCoffee();
        String orderInfo = ("(Active)Order placed for " + name + " - " + selectedCoffee + ", Total Price: $" + String.format("%.2f", totalPrice) + "\n");
        orders.add(orderInfo);
        outputArea.append(orderInfo + "\n");


        saveOrdersToCSV(orders, new String[]{name, email, phoneText}, selectedCoffee, brewType, selectedShots, milkType, addIngredients);
    }


    private void saveOrdersToCSV(List<String> orders, String[] customerDetails, String selectedCoffee, String brewType, String selectedShots,
                                 String milkType, String addIngredients) {
        String filePath = "orders.csv";


        try {
            FileWriter writer = new FileWriter(filePath, true);


            writer.append(customerDetails[0]);
            writer.append(",");
            writer.append(customerDetails[1]);
            writer.append(",");
            writer.append(customerDetails[2]);
            writer.append(",");
            writer.append(selectedCoffee);
            writer.append(",");
            writer.append((String) customerTypeComboBox.getSelectedItem());
            writer.append(",");


            if ("Espresso $5".equals(selectedCoffee)) {
                writer.append("Default Brew Type");
                writer.append(",");
                if (selectedShots.equals("1 +$.33")) {
                    writer.append("1 shot");
                } else if (selectedShots.equals("2 +$.66")) {
                    writer.append("2 shots");
                } else if (selectedShots.equals("3 +$.99")) {
                    writer.append("3 shots");
                } else if (selectedShots.equals("4 +$1.32")) {
                    writer.append("4 shots");
                } else if (selectedShots.equals("None")) {
                    writer.append("No shots");
                }
                writer.append(",");
                writer.append(milkType);
                writer.append(",");
                // Handling the special cases for additional ingredients
                if (addIngredients.equals("Chocolate syrup +$1")) {
                    writer.append("Chocolate syrup");
                } else if (addIngredients.equals("Whipped cream +$1")) {
                    writer.append("Whipped cream");
                } else {
                    writer.append(addIngredients);
                }
            } else {
                writer.append(brewType);
            }


            writer.append("\n");


            writer.close();


            JOptionPane.showMessageDialog(this, "Orders saved to CSV file successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error occurred while saving orders to CSV file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }


    }
    private void prepareAction(ActionEvent e) {
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No orders to prepare. Please create an order.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        outputArea.append("Preparing coffee...\n");
        for (int i = 0; i < orders.size(); i++) {
            String order = orders.get(i);
            if (order.contains("(Active)")) {
                order = order.replace("(Active)", "(Passive)");
                orders.set(i, order);
            }
        }
        outputArea.setText("");
        for (String order : orders) {
            outputArea.append(order + "\n");
        }
    }


    private void viewOrderAction(ActionEvent e) {
        displayOrders();
    }


    private void displayOrders() {
        outputArea.setText("");


        try {
            BufferedReader reader = new BufferedReader(new FileReader("orders.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                outputArea.append(line + "\n");
            }
            reader.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error occurred while reading orders from CSV file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new CoffeeShopGUI();
    }
}
