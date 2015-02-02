package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.AppController;
import javafx.scene.control.TreeItem;

public class Tree {

	private TreeItem<String> root;
	private AppController controller;
	private boolean Switch = false;
	private String FancyTree =  "digraph G {";  
	private final String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	private String GETTINGSTEP_PATTERN = "\\s\\s\\s";
	
	
	public void addFancyTree(String gauche, String droite)
	
	{
		
		FancyTree += "<" + gauche + "> -> <" + droite + ">;";
		
	}

	public void AssignParentsToChild(ArrayList<TreeItem<String>> parents,
			ArrayList<TreeItem<String>> childs)

	{
		if (childs.size() != 0 && parents.size() != 0)

		{
			for (int i = 0; i < parents.size(); i++)

			{
				for (int j = 0; j < childs.size(); j++) {
					parents.get(i).getChildren().add(childs.get(j));
					addFancyTree(parents.get(i).getValue(), childs.get(j).getValue());
				}
			}
		}

	}

	// /Build the tree from the terminal output
	public TreeItem<String> BuildTree(ArrayList<String> TerminalOutput, String monIP)

	{
		int r = 1;
		
		ArrayList<TreeItem<String>> parents = new ArrayList<TreeItem<String>>();
		ArrayList<TreeItem<String>> childs = new ArrayList<TreeItem<String>>();
		
		
		if(controller.firsttime == false)
			
		{
			  root = controller.MyTree;
		      FancyTree = FancyTree.substring(0, FancyTree.length()-1);
		  
	    }
		else {
		
		controller.firsttime = false;
		root = new TreeItem<String>(monIP);
		root.setExpanded(true);
		
		}
	    parents.add(root);
		

		for (int i = 0; i < TerminalOutput.size(); i++) {

			System.out.println(TerminalOutput.get(i));
			Pattern patternStep = Pattern.compile(GETTINGSTEP_PATTERN);
			Matcher matcherStep = patternStep.matcher(TerminalOutput.get(i));

			// /If we are in a new row
			if (!matcherStep.find()) {
				

				// /Appending parents to childs
				if (Switch == true) {
					AssignParentsToChild(parents, childs);
					Switch = false;
					parents = new ArrayList<TreeItem<String>>();
				}
				// /Appending childs to parents
				else {
					AssignParentsToChild(childs, parents);
					Switch = true;
					childs = new ArrayList<TreeItem<String>>();
				}
			}

			Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
			Matcher matcher = pattern.matcher(TerminalOutput.get(i));
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
			else {
				
				TreeItem<String> Nouveau = new TreeItem<String>("Hidden adresse " + r);
				r++;

				if (Switch == true)

				{
					childs.add(Nouveau);
				}

				else {
					parents.add(Nouveau);
				}
			
			}

		}
		
		if (Switch == true) {
			AssignParentsToChild(parents, childs);
			Switch = false;
		
		}
		// /Appending childs to parents
		else {
			AssignParentsToChild(childs, parents);
			Switch = true;
		}
		
		FancyTree += " }";
		System.out.println(FancyTree);
		return root;

	}

	public void setController(AppController controller)

	{
		this.controller = controller;
	}
	public String getFancyTree() 
	
	{
		return this.FancyTree;	
	}
	

}
