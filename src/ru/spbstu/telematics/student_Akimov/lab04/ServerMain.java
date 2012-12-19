import java.io.*;
import java.net.*;
import java.util.*;

public class ServerMain {
	String[] temp;
	ArrayList clientOutputStreams;
    ArrayList<String> onlineUsers = new ArrayList();
    
	public class ClientHandler implements Runnable	{
		BufferedReader reader;
		Socket sock;
        PrintWriter client;
                
		public ClientHandler(Socket clientSocket, PrintWriter user) {
            client = user;
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			}
			catch (Exception ex) {
				System.out.println("error beginning StreamReader");
			}
		}

		public void run() {
			
            String message;
            String[] data;
            String connect = "Connect";
            String disconnect = "Disconnect";
            String chat = "Chat";
            String connecting = "Connecting";

			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("Received: " + message);
					data = message.split("¥");
					temp = data;
                    for (String token:data) {
                    	System.out.println(token);
                    }
                    if (data[2].equals(connecting)) {
                    	boolean flag = false;
	                    for(int i=0;i<onlineUsers.size();i++){
	                    	if(onlineUsers.get(i).equalsIgnoreCase(data[0])){
	                    		System.out.println("[" + data[0] + "] already exist");
	                    		flag = true;
	                    		break;
	                    	}
	                    }
	                    if(!flag){
	                    	tellEveryone((data[0] + "¥" + data[1] + "¥" + chat));
	                    	userAdd(data[0]);
	                    }
	                    else{
	                    	System.out.println("Sending: " + data[0] + "¥" + "This user already exist" + "¥" + chat);
	                    	client.println(data[0] + "¥" + "This user already exist" + "¥" + "exit");
	                    	client.flush();
	                    }
					} 
                    else if (data[2].equals(disconnect)) {
	                    tellEveryone((data[0] + "¥has disconnected." + "¥" + chat));
	                    userRemove(data[0]);
					} 
                    else if (data[2].equals(chat)) {
                        tellEveryone(message);
					} 
                    else {
                        System.out.println("No Conditions were met.");
                    }
			     }
			}
			catch (Exception ex) {
				System.out.println("lost a connection");
			}
		}
	}

	public static void main (String[] args) {
		new ServerMain().go();
	}

	public void go() {
		clientOutputStreams = new ArrayList();

		try {
			ServerSocket serverSock = new ServerSocket(5000);

			while (true) {
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);
				Thread listener = new Thread(new ClientHandler(clientSock, writer));
				listener.start();
				System.out.println("got a connection");
			}
		}
		catch (Exception ex)
		{
			System.out.println("error making a connection");
		} 
	} 

	public void userAdd (String data) {
        String message;
        String add = "¥ ¥Connect", done = "Server¥ ¥Done";
        onlineUsers.add(data);
        String[] tempList = new String[(onlineUsers.size())];
        onlineUsers.toArray(tempList);
        
        for (String token:tempList) {
            
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
	}

	public void userRemove (String data) {
        String message;
        String add = "¥ ¥Connect", done = "Server¥ ¥Done";
        onlineUsers.remove(data);
        String[] tempList = new String[(onlineUsers.size())];
		onlineUsers.toArray(tempList);

        for (String token:tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
	}

        public void tellEveryone(String message) {
		Iterator it = clientOutputStreams.iterator();

		while (it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				System.out.println("Sending" + message);
                                writer.flush();
			}
			catch (Exception ex) {
				System.out.println("error telling everyone");
			}
		}
	}
        
    public void tellOne(String message) {
		Iterator it = clientOutputStreams.iterator();
		while (it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				System.out.println("Sending" + message);
                                writer.flush();
			} 
			catch (Exception ex) {
				System.out.println("error telling everyone");
			} 
		} 
    }       
}