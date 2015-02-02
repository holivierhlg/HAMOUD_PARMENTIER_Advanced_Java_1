package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.AppController;
import javafx.scene.control.TreeItem;

public class TermOutput {

	private ArrayList<String> Output;
	private AppController Controller;
	public final String MyIP;
	private int loading;

	public TermOutput() throws IOException {
		loading = 0;
		 
		Output = new ArrayList<String>();

		ProcessBuilder pbIP = new ProcessBuilder("ipconfig", "getifaddr", "en0"); // /Setting
																					// the
																					// command
		Process pIP = pbIP.start(); // /Finding the ip
		BufferedReader IPreader = new BufferedReader(new InputStreamReader(
				pIP.getInputStream())); // /Reading the InputStream
		MyIP = IPreader.readLine();
		IPreader.close();

	}

	public ArrayList<String> traceroute(String site) throws IOException

	{	
		String line = null;
		Output = new ArrayList<String>();
		ProcessBuilder pb = new ProcessBuilder("traceroute", site); ///Setting the command
		Process p = pb.start(); // /Launching the traceroute
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream())); // /Reading the InputStream

		while ((line = reader.readLine()) != null) {

			Output.add(line);
			this.loading++;
	
			System.out.println(line);
			

		}
		
		this.loading = 20;

		reader.close();
		return Output;
	}
	
	
	
	public String getMyIP()
	
	{
		
		return MyIP;
	}
	
	public int getLoading()
	{
		return this.loading;
	}
	
	
	
	

}
