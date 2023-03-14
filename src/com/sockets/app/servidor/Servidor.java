package com.sockets.app.servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Servidor {

	private ServerSocket serverSocket;
	private Socket socket;
	private Map<String, ObjectOutputStream> streamMap = new HashMap<String, ObjectOutputStream>();
	
	
	public Servidor() {
		try {
			serverSocket = new ServerSocket(5555);
			System.out.println("Servidor on!");
			
			while(true) {
				
				socket = serverSocket.accept();
				
				new Thread(new ListenerSocket(socket)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
