package com.sockets.app.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import com.sockets.app.bean.FileMessage;

public class ListenerSocket implements Runnable {

	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private Map<String, ObjectOutputStream> streamMap = new HashMap<String, ObjectOutputStream>();

	
	
	public ListenerSocket(Socket socket) throws IOException {
		this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		this.inputStream = new ObjectInputStream(socket.getInputStream());
	}
	@Override
	public void run() {
		
		FileMessage message = null;
		
		try {
			while((message = (FileMessage) inputStream.readObject()) != null) {
				
				streamMap.put(message.getCliente(), outputStream);
				
				if(message.getFile() != null) {
					for(Map.Entry<String, ObjectOutputStream> kv : streamMap.entrySet()) {
						
						if(!message.getCliente().equals(kv.getKey())) {
							kv.getValue().writeObject(message);
						}
					}
				}
			}
		} catch (IOException e) {
			
			streamMap.remove(message.getCliente());
			System.out.println(message.getCliente() + " desconectou ");
			
		}catch (ClassNotFoundException e) {
				
				e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Servidor();
	}

}
