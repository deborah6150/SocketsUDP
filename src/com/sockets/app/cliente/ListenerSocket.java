package com.sockets.app.cliente;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.Random;

import com.sockets.app.bean.FileMessage;

public class ListenerSocket implements Runnable {

	private ObjectInputStream inputStream;
	
	
	public ListenerSocket(Socket socket) throws IOException {
	
		this.inputStream = new ObjectInputStream(socket.getInputStream());
	}


	public void run() {
		
		FileMessage message = null;
		
		try {
			
			while((message = (FileMessage) inputStream.readObject()) != null) {
				
				
				System.out.println("\n Voce recebeu um arquivo de " + message.getCliente());
				System.out.println("O arquivo e " + message.getFile().getName());
				
				
//				imprime(message);
				salvar(message);
				
				System.out.println("1 - Sair | 2 - Enviar : ");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		}


	private void salvar(FileMessage message) {
		
		try {
			
			Thread.sleep(new Random().nextInt(1000));
			
			long time = System.currentTimeMillis();
			
			
			
			FileInputStream fileInputStream = new FileInputStream(message.getFile());
			FileOutputStream fileOutputStream = new FileOutputStream("/Users/deborahcaroline/Desktop/teste/" + time +  "_" + message.getFile().getName());
			
			FileChannel fin = fileInputStream.getChannel();
			FileChannel fout = fileOutputStream.getChannel();
			
			long size = fin.size();
			
			fin.transferTo(0, size, fout);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void imprime(FileMessage message) {
		
		try {
			FileReader fileReader = new FileReader(message.getFile());
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String linha;
			
			while((linha = bufferedReader.readLine()) != null) {
				
				System.out.println(linha);
			}
			
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args) {
		try {
			new Cliente();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
		

}
