import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ClientGUI extends JFrame {
    private JTextArea messageArea;
    private JTextField textField;
    private Client client;

    public ClientGUI() {
        super("Chat application");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        textField = new JTextField();
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                client.sendMessage(textField.getText());
                textField.setText("");
            }
        });
        add(textField, BorderLayout.SOUTH);

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