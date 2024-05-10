import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientGUI extends JFrame {
    private JTextArea messageArea;
    private JTextField textField;
    private Client client;
    private JButton exitButton;

    public ClientGUI() {
        super("Chat application");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);
        textField = new JTextField();

        String name = JOptionPane.showInputDialog(this, "Enter your name:",
                "Name Entry", JOptionPane.PLAIN_MESSAGE);
        this.setTitle("Chat Application - " + name);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            String exitMessage = name + " has left the chat.";
            client.sendMessage(exitMessage);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        });
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] "
                        + name + ": " + textField.getText();
                client.sendMessage(message);
                textField.setText("");
            }
        });

        try {
            this.client = new Client("127.0.0.1", 5000, this::onMessageRecieved);
            client.startClient();
        } catch (IOException i) {
            i.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error conntecting to the server",
                    "Connection error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public void onMessageRecieved(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientGUI().setVisible(true));
    }
}