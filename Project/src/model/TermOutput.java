package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TermOutput {

	private ArrayList<String> Output;
	public final String MyIP;

	public TermOutput() throws IOException {

		Output = new ArrayList<String>();

		// /Lancement de la commande de GetIP de la machine pour initialiser la
		// première node
		ProcessBuilder pbIP = new ProcessBuilder("ipconfig", "getifaddr", "en0");
		Process pIP = pbIP.start();
		// / Lecture de l'output
		BufferedReader IPreader = new BufferedReader(new InputStreamReader(
				pIP.getInputStream()));
		MyIP = IPreader.readLine();
		IPreader.close();

	}

	public ArrayList<String> traceroute(String site) throws IOException

	{
		
		///Lancement du traceroute
		String line = null;
		Output = new ArrayList<String>();
		ProcessBuilder pb = new ProcessBuilder("traceroute", site); 
		Process p = pb.start(); 
		///Lecture ligne à ligne de l'output et sauvegarde ligne à ligne. 
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream())); 
		while ((line = reader.readLine()) != null) {

			Output.add(line);
			System.out.println(line);

		}

		reader.close();
		return Output;
	}

	public String getMyIP()

	{

		return MyIP;
	}

}
