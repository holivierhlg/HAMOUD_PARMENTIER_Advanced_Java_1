package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder;


public class AppController {

	static public void main(String[] args) throws IOException

	{
		String siteTest = "google.com";
		ProcessBuilder pb = new ProcessBuilder("traceroute", siteTest);
		Process p = pb.start();
		final BufferedReader reader = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
   
	}

}