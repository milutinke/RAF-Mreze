package raf.edu.rs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public Server(int port) throws IOException {
		@SuppressWarnings("resource")
		//---------------------------------------------------------------------------------------------
		// Startujemo Server na portu koji se nalazi u promenljivoj port
		//---------------------------------------------------------------------------------------------
		ServerSocket server = new ServerSocket(port);
		
		//---------------------------------------------------------------------------------------------
		// Ispisujemo da je server startovan u konzoli servera
		//---------------------------------------------------------------------------------------------
		System.out.println("Server has started on port: " + port);
		
		//---------------------------------------------------------------------------------------------
		// Beskonacna petlja koja ima zadatak da "osluskuje", tj. prima konekcije od klijentata
		// i pravi Threadove za svaku konekciju da bi smo podrzali vise klijenata na serveru
		// svaki klijent ce imati svoj thread
		//---------------------------------------------------------------------------------------------
		while(true) {
			
			//---------------------------------------------------------------------------------------------
			// Prihvatamo konekciju od klijenta
			//---------------------------------------------------------------------------------------------
			Socket client = server.accept();
			
			//---------------------------------------------------------------------------------------------
			// Inicializujemo/Instanciramo klasu ServerThread kako bi smo je koristili za Thread
			// U konstruktoru prosledjujemo konekciju klijenta
			//---------------------------------------------------------------------------------------------
			ServerThread serverThread = new ServerThread(client);
			
			//---------------------------------------------------------------------------------------------
			// Inicializujemo/Instanciramo thread sa klasom ServerThread koja implementira interfejs Runnable
			//---------------------------------------------------------------------------------------------
			Thread thread = new Thread(serverThread);
			
			//---------------------------------------------------------------------------------------------
			// Pokrecemo rad threada
			//---------------------------------------------------------------------------------------------
			thread.start();
			
			
			//---------------------------------------------------------------------------------------------
			// Kraca varijanta:
			// Gore navedeni kod moze se napisati u ovoj formi
			// PS: Ako koristis kracu varijantu, komentiraj gornji kod
			//---------------------------------------------------------------------------------------------
			
			//new Thread(new ServerThread(server.accept())).start();
		}
	}
	
	public static void main(String[] args) throws IOException {
		new Server(Settings.PORT);
	}

}
