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
			// Ova promeljiva nam sluzi da u njoj uskladistimo prvi broj koji smo primili
			// i za dalje operacije nad tim brojem
			//---------------------------------------------------------------------------------------------
			int number = 0;
			
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
				// Obradjujemo izraz
				//---------------------------------------------------------------------------------------------
				
				//---------------------------------------------------------------------------------------------
				// Izdvajamo znak operacije iz poruke koju smo primili
				// koristimo metodu substring klase String da bi smo uzeli podstring
				// izmedju nultog i prvog mesta u stringu
				//---------------------------------------------------------------------------------------------
				String operation = message.substring(0, 1);
				
				//---------------------------------------------------------------------------------------------
				// Izdvajamo broj iz poruke koju smo primili
				// koristimo metodu substring klase String da bi smo uzeli podstring
				// od prvog do zadnjeg mesta u stringu
				// time iskljucujemo prvo mesto koje je rezervisano za operaciju
				// nakon toga dobijeni string pretvaramo u integer koristeci metodu parseInt klase Integer
				// dobijeni broj smestamo u promenljivu temporaryNumber koju 
				//---------------------------------------------------------------------------------------------
				int temporaryNumber = Integer.parseInt(message.substring(1));
				
				//---------------------------------------------------------------------------------------------
				// Koristimo Switch da bi smo odredili koja operacija treba da se izvrsi nad trenutnim brojem
				// i brojem koji je klijent prosledio
				//---------------------------------------------------------------------------------------------
				switch(operation) {
					//---------------------------------------------------------------------------------------------
					// Vrismo sabiranje trenuntog broja sa novim koji je klijent prosledio
					//---------------------------------------------------------------------------------------------
					case "+":
						number += temporaryNumber;
						break;
						
					//---------------------------------------------------------------------------------------------
					// Vrismo oudzimanje trenuntog broja sa novim koji je klijent prosledio
					//---------------------------------------------------------------------------------------------
					case "-":
						number -= temporaryNumber;
						break;
					
					//---------------------------------------------------------------------------------------------
					// Vrismo deljenje trenuntog broja sa novim koji je klijent prosledio
					//---------------------------------------------------------------------------------------------
					case "/":
						number /= temporaryNumber;
						break;
					
					//---------------------------------------------------------------------------------------------
					// Vrismo mnozenje trenuntog broja sa novim koji je klijent prosledio
					//---------------------------------------------------------------------------------------------
					case "*":
						number *= temporaryNumber;
						break;
						
					//---------------------------------------------------------------------------------------------
					// Ako smo dobili samo broj bez operacije dodeljujemo ga promenljivoj number
					//---------------------------------------------------------------------------------------------
					default:
						number = Integer.parseInt(message);
						break;
				}
				
				//---------------------------------------------------------------------------------------------
				// Saljemo/Prosledjujemo broj klijentu nakon obrade
				//---------------------------------------------------------------------------------------------
				out.println(number);
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
