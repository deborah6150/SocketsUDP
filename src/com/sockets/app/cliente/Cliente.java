package com.sockets.app.cliente;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


import javax.swing.JFileChooser;
import com.sockets.app.bean.FileMessage;


public class Cliente {
	
	
	private Socket socket;
	private ObjectOutputStream outputStream;
	
	public Cliente() throws UnknownHostException, IOException {
		
		this.socket = new Socket("localhost", 5555);
		this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		
		new Thread(new ListenerSocket(socket)).start();
		
		menu();
	}
	
	private void menu() throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite seu nome: ");
		
		String nome = scanner.nextLine();
		
		this.outputStream.writeObject(new FileMessage(nome));
		
		int option = 0;
		
		while(option != 1) {
			System.out.println("1 - Sair | 2 - Enviar : ");
			option = scanner.nextInt();
			
			if(option == 2) {
				send(nome);
			} else if(option == 1){
				System.exit(0);
			}
			
		}
		
	}

	private void send(String nome) throws IOException {
		
		FileMessage fileMessage = new FileMessage();
		 
		JFileChooser fileChooser = new JFileChooser();
		
		
		int opt = fileChooser.showOpenDialog(null);
		
		if(opt == JFileChooser.APPROVE_OPTION) {
			
			File file = fileChooser.getSelectedFile();
			
			this.outputStream.writeObject(new FileMessage(nome,file));
			
		}
		
		
		
	}
	

}
