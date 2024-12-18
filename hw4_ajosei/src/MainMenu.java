import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class MainMenu extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JComboBox<String> coffeeComboBox;

    public MainMenu() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Coffee Shop Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        menu.add(exitItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        mainPanel.setPreferredSize(new Dimension(1200, 800)); // Adjust size as needed

        JPanel orderCoffeePanel = createOrderCoffeePanel();
        JPanel viewOrdersPanel = createViewOrdersPanel();
        JPanel preparePanel = createPreparePanel();

        mainPanel.add(orderCoffeePanel, "OrderCoffee");
        mainPanel.add(viewOrdersPanel, "ViewOrders");
        mainPanel.add(preparePanel, "Prepare");

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton orderButton = new JButton("Order Coffee");
        JButton viewButton = new JButton("View Orders");
        JButton prepareButton = new JButton("Prepare Coffee");

        orderButton.addActionListener(e -> cardLayout.show(mainPanel, "OrderCoffee"));
        viewButton.addActionListener(e -> cardLayout.show(mainPanel, "ViewOrders"));
        prepareButton.addActionListener(e -> cardLayout.show(mainPanel, "Prepare"));

        controlPanel.add(orderButton);
        controlPanel.add(viewButton);
        controlPanel.add(prepareButton);

        add(controlPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        setSize(1200, 800); // Set the frame size directly
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private JPanel createOrderCoffeePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel customerInfoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        customerInfoPanel.add(new JLabel("Customer Name:"));
        nameField = new JTextField();
        customerInfoPanel.add(nameField);
        customerInfoPanel.add(new JLabel("Customer Email:"));
        emailField = new JTextField();
        customerInfoPanel.add(emailField);
        customerInfoPanel.add(new JLabel("Customer Phone:"));
        phoneField = new JTextField();
        customerInfoPanel.add(phoneField);

        panel.add(customerInfoPanel, BorderLayout.NORTH);

        JPanel coffeeSelectionPanel = new JPanel();
        JLabel selectLabel = new JLabel("Select Coffee Type:");
        coffeeComboBox = new JComboBox<>(new String[]{"Espresso", "Filtered Coffee"});
        coffeeSelectionPanel.add(selectLabel);
        coffeeSelectionPanel.add(coffeeComboBox);

        panel.add(coffeeSelectionPanel, BorderLayout.CENTER);

        JButton confirmSelectionButton = new JButton("Order Coffee");
        confirmSelectionButton.addActionListener(e -> writeToCSV());
        panel.add(confirmSelectionButton, BorderLayout.SOUTH);

        return panel;
    }

    private void writeToCSV() {
        String selectedCoffeeType = (String) coffeeComboBox.getSelectedItem();
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        String orderInfo = String.format("%s,%s,%s,%s%n", name, email, phone, selectedCoffeeType);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("orders.csv"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(orderInfo);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to CSV file.", "I/O Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createViewOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"Order ID", "Name", "Email", "Phone Number", "Order Status", "Coffee Type", "Order Total", "Customer Type", "Order Date"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (BufferedReader br = new BufferedReader(new FileReader("orders.csv"))) {
            String line;
            br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Split on commas outside quotes
                values = Arrays.stream(values).map(value -> value.replaceAll("\"", "")).toArray(String[]::new); // Remove quotes
                model.addRow(values);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load order data from CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPreparePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Prepare Panel"));
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
