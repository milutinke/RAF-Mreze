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
				// Obrcemo rec:
				// Duza Varijanta:
				//---------------------------------------------------------------------------------------------
				
				//---------------------------------------------------------------------------------------------
				// Pretvaramo promenljivu message tj. rec koju je klijent prosledio u niz karaktera
				// koji smestamo u promenljivu input
				//---------------------------------------------------------------------------------------------
				char[] input =  message.toCharArray();
				
				//---------------------------------------------------------------------------------------------
				// Ova promeniljiva nam sluzi da pratimo pocetak
				//---------------------------------------------------------------------------------------------
				int begin = 0;
				
				//---------------------------------------------------------------------------------------------
				// Ova promenljiva nam sluzi da odredimo kraj
				//---------------------------------------------------------------------------------------------
				int end = message.length() - 1;
				
				//---------------------------------------------------------------------------------------------
				// Ova promenljiva nam sluzi da privremeno skladistimo karaktere
				//---------------------------------------------------------------------------------------------
				char temporaryChar; 
				
				//---------------------------------------------------------------------------------------------
				// Obrcemo String u petlji
				//---------------------------------------------------------------------------------------------
				while(end > begin){
					//---------------------------------------------------------------------------------------------
					// Smestamo trenutni karakter u temporaryChar
					//---------------------------------------------------------------------------------------------
					temporaryChar = input[begin];
					
					//---------------------------------------------------------------------------------------------
					// Smestamo sledeci karakter na mesto trenutnog
					//---------------------------------------------------------------------------------------------
					input[begin] = input[end];
					
					//---------------------------------------------------------------------------------------------
					// Smestamo trenutni karakter na mesto sledeceg
					//---------------------------------------------------------------------------------------------
					input[end] = temporaryChar;
					
					//---------------------------------------------------------------------------------------------
					// Pomeramo pozicije pocketa i kraja
					//---------------------------------------------------------------------------------------------
			        end --;
			        begin ++;
			    }
				
				//---------------------------------------------------------------------------------------------
				// Pretvaramo niz karaktera nazad u string i smestamo ga u promenljivu message
				//---------------------------------------------------------------------------------------------
				message = new String(input);
				
				//---------------------------------------------------------------------------------------------
				// Kraca varijanta:
				// Prenosimo vrednost promenljive message, tj. rec koju je klijent prosledio klasi StringBuilder
				// Zatim pozivamo metodu reverse() koja obrce rec
				// Nakon toga pozivamo metodu toString() koja "builduje"/"pravi" string i smesta ga u promenljivu message
				// PS: Ako koristis ovo, komentiraj gornji kod za obrtanje reci
				//---------------------------------------------------------------------------------------------
				
				// message = new StringBuilder(message).reverse().toString();
				
				//---------------------------------------------------------------------------------------------
				// Saljemo/Prosledjujemo obrnutu rec klijentu
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
