package frontend;

import backend.Answer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Client extends javax.swing.JFrame {

    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static Socket so;

    public Client() {
        initComponents();
    }

    private void initComponents() {
        txtBotTitle = new javax.swing.JLabel();
        JSeparator jSeparator1 = new JSeparator();
        scrollPaneChat = new javax.swing.JScrollPane();
        labelChat = new javax.swing.JTextArea();
        txtChatUser = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chatbot By Ibrahim Mohammed");

        txtBotTitle.setFont(new java.awt.Font("Lithos Pro Regular", 1, 14));
        txtBotTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtBotTitle.setText("Chat Bot By Ibrahim Mohammed");

        labelChat.setEditable(false);
        labelChat.setColumns(20);
        labelChat.setRows(5);
        labelChat.setText("-------Bot is still learning-------\n");
        scrollPaneChat.setViewportView(labelChat);

        txtChatUser.addActionListener(this::txtChatUserActionPerformed);

        btnSend.setText("Send");
        btnSend.addActionListener(this::btnSendActionPerformed);

        jLabel1.setText("Bot is still in development.");
        jLabel2.setText("To exit, type /quit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtBotTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(scrollPaneChat)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtChatUser, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jLabel2))
                                .addContainerGap())
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtBotTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtChatUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(scrollPaneChat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {
        if (!txtChatUser.getText().trim().isEmpty()) {
            try {
                dos.writeUTF(txtChatUser.getText());
                if (txtChatUser.getText().equalsIgnoreCase("/quit")) {
                    System.out.println("App has been terminated");
                    System.exit(0);
                } else {
                    String jawabanBot = dis.readUTF();
                    labelChat.append("You: " + txtChatUser.getText() + "\nBot: " + jawabanBot + "\n");
                    txtChatUser.setText("");
                    txtChatUser.requestFocus();
                }
            } catch (IOException e) {
                e.printStackTrace();
                labelChat.append("Bot: Error communicating with server.\n");
            }
        } else {
            labelChat.append("You: (empty message)\nBot: You cannot enter an empty message.\n");
        }
    }

    private void txtChatUserActionPerformed(java.awt.event.ActionEvent evt) {
        btnSendActionPerformed(evt);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Client().setVisible(true));

        try {
            so = new Socket("127.0.0.1", 1239);
            dis = new DataInputStream(so.getInputStream());
            dos = new DataOutputStream(so.getOutputStream());
        } catch (IOException e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Connection failed!", e);
            System.out.println("Error: Could not connect to the server.");
        }
    }

    // Variables declaration
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextArea labelChat;
    private javax.swing.JScrollPane scrollPaneChat;
    private javax.swing.JLabel txtBotTitle;
    private javax.swing.JTextField txtChatUser;
}
