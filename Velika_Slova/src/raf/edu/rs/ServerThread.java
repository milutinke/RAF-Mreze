package raf.edu.rs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

//---------------------------------------------------------------------------------------------
// Implementiramo interfejs Runnable kako bi smo koristili ovu klasu da napravimo Thread u Server klasi
//---------------------------------------------------------------------------------------------

public class ServerThread implements Runnable {
	//---------------------------------------------------------------------------------------------
	// Privatni clan (member) klase ServerThread koji ce sadrzati konekciju klijenta
	//---------------------------------------------------------------------------------------------
	private Socket client;

	//---------------------------------------------------------------------------------------------
	// Konstuktor nam omogucava da uzmemo vezu/konekciju trenutnog klijenta koju
	// prosledujemo u klasi Server
	//---------------------------------------------------------------------------------------------
	public ServerThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			//---------------------------------------------------------------------------------------------
			// BufferedReader u kombinaciji InputStreamReader nam omogucava da primamo podatke koje klijent prosledjuje serveru
			//---------------------------------------------------------------------------------------------
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			//---------------------------------------------------------------------------------------------
			// PrintWriter u kombinaciji sa OutputStreamWriter nam omogucava da saljemo podatke klijentu
			//---------------------------------------------------------------------------------------------
			PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
			
			//---------------------------------------------------------------------------------------------
			// Ispisujemo da se klijent povezao/konektovao u konzolu servera
			//---------------------------------------------------------------------------------------------
			System.out.println("[Server] Client with IP: " + client.getInetAddress().getHostAddress() + " has connected!" );
			
			//---------------------------------------------------------------------------------------------
			// Ova promeljiva nam sluzi da u njoj skladistimo poruku koju smo primili
			//---------------------------------------------------------------------------------------------
			String message;
			
			//---------------------------------------------------------------------------------------------
			// Beskonacna petlja koja radi u Threadu sve dok klijent ne posalje zahtev za prekid veze/konekcije
			// U ovoj petlji mi obradujemo rec koju nam klijent prosledi i prosledimo mu je nazad nakon obrade
			// 
			// Ako je klijent poslao poruku sa sadrzajem "exit" izlazimo iz beskonacne petlje
			// Nakon izlaska iz petlje, konekcija/veza sa klijentom se gasi
			//---------------------------------------------------------------------------------------------
			while((message = in.readLine()) != null && !message.equalsIgnoreCase("exit")) {
				//---------------------------------------------------------------------------------------------
				// Ispisujemo rec koju je klijent prosledio u konzoli servera
				//---------------------------------------------------------------------------------------------
				System.out.println( "[Client]: " + message);
				
				//---------------------------------------------------------------------------------------------
				// Povecavamo slova u reci
				//---------------------------------------------------------------------------------------------
				message = message.toUpperCase();
				
				//---------------------------------------------------------------------------------------------
				// Saljemo/Prosledjujemo rec sa velikim slovima klijentu
				//---------------------------------------------------------------------------------------------
				out.println(message);
			}
			
			//---------------------------------------------------------------------------------------------
			// Ispisujemo da je klijent prekinuo konekciju/vezu u konzolu servera
			//---------------------------------------------------------------------------------------------
			System.out.println("[Server] Client with IP: " + client.getInetAddress().getHostAddress() + " has disconnected!" );
						
			//---------------------------------------------------------------------------------------------
			// Prekidamo/Zatvaramo vezu/konekciju sa klijentom
			//---------------------------------------------------------------------------------------------
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
