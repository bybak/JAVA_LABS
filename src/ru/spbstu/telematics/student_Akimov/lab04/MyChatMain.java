
import java.net.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;

public class MyChatMain extends javax.swing.JFrame {
	
    String username;
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    ArrayList<String> userList = new ArrayList();
    private ReentrantLock userListLock = new ReentrantLock();
    Boolean isConnected = false;

    public MyChatMain() {
        initComponents();
    }

    public class IncomingReader implements Runnable{
        public void run() {
            String stream;
            String[] data;
            String done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            try {
                while ((stream = reader.readLine()) != null) {
                    data = stream.split("¥");
                     if (data[2].equals(chat)) {
                        chatTextArea.append(data[0] + ": " + data[1] + "\n");
                    } else if (data[2].equals(connect)){
                        chatTextArea.removeAll();
                        userAdd(data[0]);
                    } else if (data[2].equals("exit")) {
                    	chatTextArea.append("This nic already exist!!!");
                    	disconnectButton.setEnabled(false);
                    	sendButton.setEnabled(false);
                    	connectButton.setEnabled(true);
                    	Disconnect();
                    } else if (data[2].equals(disconnect)) {
                        userRemove(data[0]);
                    } else if (data[2].equals(done)) {
                        usersList.setText("");
                        writeUsers();
                        userListLock.lock();
                        try{
                        	userList.clear();
						}
                        finally{
                        	userListLock.unlock();
                        }
                    }
                }
           }catch(Exception ex) {
           }
        }
    }

    public void ListenThread() {
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }

    public void userAdd(String data) {
    	userListLock.lock();
    	try{
    		userList.add(data);
    	}
    	finally{
    		userListLock.unlock();
    	}
    }

    public void userRemove(String data) {
         chatTextArea.append(data + " has disconnected.\n");
     }

    public void writeUsers() {
    	String[] tempList;
		userListLock.lock();
		try{
	    	tempList = new String[(userList.size())];
		    userList.toArray(tempList);
		}
		finally{
			userListLock.unlock();
		}
	    for (String token:tempList) {
	        usersList.append(token + "\n");
	    }
	 }

    public void sendDisconnect() {
       String bye = (username + "¥ ¥Disconnect");
        try{
            writer.println(bye); 
            writer.flush(); 
        } catch (Exception e) {
            chatTextArea.append("Could not send Disconnect message.\n");
        }
      }

    public void Disconnect() {
        try {
        	chatTextArea.append("Disconnected.\n");
            sock.close();
        } catch(Exception ex) {
        	chatTextArea.append("Failed to disconnect. \n");
        }
        isConnected = false;
        usernameField.setEditable(true);
        hostField.setEditable(true);
        usersList.setText("");
      }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        hostField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        connectButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        usersList = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        inputTextArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyChat");
        setResizable(false);

        disconnectButton.setEnabled(false);
        sendButton.setEnabled(false);
        jLabel1.setText("IP address:");

        hostField.setHorizontalAlignment(javax.swing.JTextField.CENTER);


        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Name:");

        usernameField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usersList.setFont(new java.awt.Font("Courier New", 0, 12));

        chatTextArea.setEditable(false);
        chatTextArea.setColumns(20);
        chatTextArea.setRows(5);
        chatTextArea.setFont(new java.awt.Font("Courier New", 0, 12));
        jScrollPane1.setViewportView(chatTextArea);

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        disconnectButton.setText("Disconnect");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });
        
        this.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
                sendDisconnect();
                Disconnect();
        	}
        });

        usersList.setEditable(false);
        usersList.setColumns(14);
        usersList.setRows(5);
        usersList.setAutoscrolls(false);
        usersList.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane2.setViewportView(usersList);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Users");

        inputTextArea.setEditable(true);
        inputTextArea.setColumns(20);
        inputTextArea.setRows(4);
        inputTextArea.setFont(new java.awt.Font("Courier New", 0, 12));
        jScrollPane3.setViewportView(inputTextArea);

        inputTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputTextKeyReleased(evt);
            }
        });
        
        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hostField)
                            .addComponent(usernameField, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(connectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(disconnectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(connectButton)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(disconnectButton)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameField, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                            .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        pack();
    }
    
    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {
            if (isConnected == false) {
            username = usernameField.getText();
            
            
            try {
            	sock = new Socket(hostField.getText(), 5000);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + "¥" + "has connected" + "¥" + "Connecting");

                writer.flush(); 
                isConnected = true;
                sendButton.setEnabled(true);
                usernameField.setEditable(false);
                hostField.setEditable(false);
                connectButton.setEnabled(false);
            	disconnectButton.setEnabled(true);
            } catch (Exception ex) {
            	connectButton.setEnabled(true);
                chatTextArea.append("Cannot Connect! Try Again. \n");
                usernameField.setEditable(true);
            }
            ListenThread();
        } else if (isConnected == true) {
            chatTextArea.append("You are already connected. \n");
        }
    }

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	disconnectButton.setEnabled(false);
    	sendButton.setEnabled(false);
    	connectButton.setEnabled(true);
        sendDisconnect();
        Disconnect();
    }
    
    
    
    static boolean enter; 
	
    private void inputTextKeyReleased(java.awt.event.KeyEvent evt) {
        if(evt.getKeyCode() == 10){
           	if (enter){
                String nothing = "";
                if ((inputTextArea.getText()).equals(nothing)) {
                    inputTextArea.setText("");
                    inputTextArea.requestFocus();
                } else {
                    try {
                       writer.println(username + "¥" + replace(inputTextArea.getText(),"\n"," ") + "¥" + "Chat");
                       writer.flush();
                    } catch (Exception ex) {
                        chatTextArea.append("Message was not sent. \n");
                    }
                    inputTextArea.setText("");
                    inputTextArea.requestFocus();
                }
                inputTextArea.setText("");
                inputTextArea.requestFocus();
    		}
    		else{
    			enter = true;
    		}
        }
    }
    

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String nothing = "";
        if ((inputTextArea.getText()).equals(nothing)) {
            inputTextArea.setText("");
            inputTextArea.requestFocus();
        } else {
            try {
               writer.println(username + "¥" + replace(inputTextArea.getText(),"\n"," ") + "¥" + "Chat");
               writer.flush();
            } catch (Exception ex) {
                chatTextArea.append("Message was not sent. \n");
            }
            inputTextArea.setText("");
            inputTextArea.requestFocus();
        }
        inputTextArea.setText("");
        inputTextArea.requestFocus();
    }

    static String replace(String str, String pattern, String replace) 
	{
  	  	int s = 0;
  	  	int e = 0;
  	  	StringBuffer result = new StringBuffer();
    	while ((e = str.indexOf(pattern, s)) >= 0) 
    	{
    		result.append(str.substring(s, e));
       	    result.append(replace);
       	    s = e+pattern.length();
    	}
    	result.append(str.substring(s));
    	return result.toString();
    }
    
    public static void main(String args[]) {
    	
    	
    	try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyChatMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyChatMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyChatMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyChatMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }    	
    	
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyChatMain().setVisible(true);
            }
        });
    }

    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JTextField hostField;
    private javax.swing.JTextArea inputTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField usernameField;
    private javax.swing.JTextArea usersList;

}