package CookingClass.Server;

import CookingClass.*;
//import CookingClassServer.Manager.*;
//import CookingClassServer.Teachers.*;
//import CookingClassServer.Student.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

class LogInInfo implements Serializable {
	private String id;
	private String password;
	LogInInfo(String id, String password) {
		this.id=id;
		this.password=password;
	}
	public String getID() { return id; }
	public String getPassword() { return password; }
}

public class ServerMain {
   	private static final int THREAD_CNT = 50; // 최대 50명 접속 가능
   	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_CNT);

	public static void main(String[] args) {
		ServerSocket serversocket=null;
		Socket socket=null;
		OutputStream os=null;
		ObjectOutputStream oos=null;
		InputStream is=null;
		ObjectInputStream ois=null;

		try {
			serversocket=new ServerSocket(5000);
			while(true) {
				socket=serversocket.accept();
				os=socket.getOutputStream();
				oos=new ObjectOutputStream(os);
				is=socket.getInputStream();
				ois=new ObjectInputStream(is);


         				//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         				//BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				String id=(String) ois.readObject();
				System.out.println(id);
				String password=(String) ois.readObject();
				System.out.println(password);
				String type="";
				Database db=new Database();
				type=db.logInCheck(id, password);


				oos.writeObject(type);

				os.close(); is.close(); oos.close(); ois.close();
  				socket.close();
				System.out.println("Conection received from " + socket.getInetAddress().getHostName() + ":"+ socket.getPort());
				switch(type) {
				case "Manager" : 
					threadPool.execute(new ManagerServer(socket));
					break;
				case "Teacher" :
					threadPool.execute(new TeacherServer(socket));
					break;
				case "Student" :
					threadPool.execute(new StudentServer(socket));
					break;
				case "Default" :
					break;
				}
			}
		}	
		catch(IOException e) { System.err.println(e); }
		catch(SQLException e) { System.err.println(e); }
		catch(ClassNotFoundException e) { System.err.println(e); }
		finally {
			try{ serversocket.close(); }
			catch(IOException e) { System.err.println(e); }
		}
	}
}