package raf.edu.rs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public Client(String ip, int port) throws UnknownHostException, IOException {
		//---------------------------------------------------------------------------------------------
		// Pokrecemo klijenta i povezujemo se na server
		//---------------------------------------------------------------------------------------------
		Socket client = new Socket(ip, port);
		
		//---------------------------------------------------------------------------------------------
		// BufferedReader u kombinaciji InputStreamReader nam omogucava da primamo podatke koje server prosledjuje klijentu
		//---------------------------------------------------------------------------------------------
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
		//---------------------------------------------------------------------------------------------
		// PrintWriter u kombinaciji sa OutputStreamWriter nam omogucava da saljemo podatke serveru
		//---------------------------------------------------------------------------------------------
		PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
		
		//---------------------------------------------------------------------------------------------
		// Klasa Scanner nam omogucava da uzmemo unos iz konzole
		// Inicijalizujemo/Instanciramo klasu Scanner
		//---------------------------------------------------------------------------------------------
		Scanner scanner = new Scanner(System.in);
		
		//---------------------------------------------------------------------------------------------
		// Beskonacna petlja u kojoj primamo i saljemo podatke od servera/serveru
		//---------------------------------------------------------------------------------------------
		while(true) {
			//---------------------------------------------------------------------------------------------
			// Uzimamo unos iz konzole i smestamo ga u promenljivu message tipa String
			// Ovaj deo koda blokira while petlju sve dok ne unesemo tekst i kliknemo enter
			//---------------------------------------------------------------------------------------------
			String message = scanner.nextLine();
			
			//---------------------------------------------------------------------------------------------
			// If ako ukucamo exit, radimo break i izlazimo iz petlje
			// Nakon izlaska iz petlje, konekcija/veza sa serverom se prekida
			//---------------------------------------------------------------------------------------------
			if(message.equalsIgnoreCase("exit"))
				break;
			
			//---------------------------------------------------------------------------------------------
			// Saljemo unetu rec serveru
			//---------------------------------------------------------------------------------------------
			out.println(message);
			
			//---------------------------------------------------------------------------------------------
			// Primamo povratnu rec od servera i ispisujemo je u konzolu
			//---------------------------------------------------------------------------------------------
			System.out.println("[Server]: " + in.readLine());
		}
		
		//---------------------------------------------------------------------------------------------
		// Unistavamo instancu klase Scanner
		//---------------------------------------------------------------------------------------------
		scanner.close();
		
		//---------------------------------------------------------------------------------------------
		// Zatvaramo vezu/konekciju sa serverom
		//---------------------------------------------------------------------------------------------
		client.close();
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Client(Settings.IP, Settings.PORT);
	}

}
