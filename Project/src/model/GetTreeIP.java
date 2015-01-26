package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class GetTreeIP {
	private final String siteTest = "google.com"; // /The site we are
													// tracerouting
	private String line = null;
	private final BufferedReader reader;
	private final BufferedReader IPreader;
	private TreeItem<String> passage;
	private boolean Switch = false;
	private final String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	private String GETTINGSTEP_PATTERN = "\\s\\s\\s";

	public void AssignParentsToChild(ArrayList<TreeItem<String>> parents,
			ArrayList<TreeItem<String>> childs)

	{
		if (childs.size() != 0 && parents.size() != 0)

		{
			for (int i = 0; i < parents.size(); i++)

			{
				for (int j = 0; j < childs.size(); j++) {
					parents.get(i).getChildren().add(childs.get(j));
				}
			}
		}

	}

	public GetTreeIP() throws IOException {

		ProcessBuilder pbIP = new ProcessBuilder("ipconfig", "getifaddr", "en0"); // /Setting
																					// the
																					// command
		Process pIP = pbIP.start(); // /Finding the ip
		IPreader = new BufferedReader(new InputStreamReader(
				pIP.getInputStream())); // /Reading the InputStream
		String monIP = IPreader.readLine();

		TreeItem<String> root = new TreeItem<String>(monIP);
		root.setExpanded(true);

		ProcessBuilder pb = new ProcessBuilder("traceroute", siteTest); // /Setting
																		// the
																		// command
		Process p = pb.start(); // /Launching the traceroute

		reader = new BufferedReader(new InputStreamReader(p.getInputStream())); // /Reading
																				// the
																				// InputStream
		ArrayList<TreeItem<String>> parents = new ArrayList<TreeItem<String>>();
		ArrayList<TreeItem<String>> childs = new ArrayList<TreeItem<String>>();
		parents.add(root);

		passage = root;
		while ((line = reader.readLine()) != null) {

			Pattern patternStep = Pattern.compile(GETTINGSTEP_PATTERN);
			Matcher matcherStep = patternStep.matcher(line);

			// /If we are in a new row
			if (!matcherStep.find()) {

				///Appending parents to childs
				if (Switch == true) {
					AssignParentsToChild(parents, childs);
					Switch = false;
					parents = new ArrayList<TreeItem<String>>();
				} 
				///Appending childs to parents
				else {
					AssignParentsToChild(childs, parents);
					Switch = true;
					childs = new ArrayList<TreeItem<String>>();
				}

				//System.out.println("");
			}

			Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
			Matcher matcher = pattern.matcher(line);
			// /Getting the ip address of the line
			if (matcher.find()) {

				TreeItem<String> Nouveau = new TreeItem<String>(matcher.group());

				if (Switch == true)

				{
					childs.add(Nouveau);
				}

				else {
					parents.add(Nouveau);
				}
			}

		}
		System.out.println(root.getChildren().get(0).getChildren().get(0)
				.getValue());
		reader.close();
	}

}
