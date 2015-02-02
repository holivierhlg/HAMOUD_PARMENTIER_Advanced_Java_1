package model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.AppController;
import javafx.scene.control.TreeItem;

public class Tree {

	private TreeItem<String> root;
	private AppController controller;
	private boolean Switch = false;
	private String FancyTree =  "strict digraph G {";  
	private final String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	private String GETTINGSTEP_PATTERN = "\\s\\s\\s";
	
	
	public void addFancyTree(String gauche, String droite)
	
	{
		
		///Formatage de l'output pour la creation d'un arbre avec dot.
		FancyTree += "<" + gauche + "> -> <" + droite + ">;";
		
	}

	
	///Explicite
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

	///Contruire l'arbre depuis l'output du terminal
	public TreeItem<String> BuildTree(ArrayList<String> TerminalOutput, String monIP)

	{
		int r = 1;
		
		ArrayList<TreeItem<String>> parents = new ArrayList<TreeItem<String>>();
		ArrayList<TreeItem<String>> childs = new ArrayList<TreeItem<String>>();
		
		
		///Check si c'est le premier lancement, pour ne pas remettre à 0 la liste déjà connue. 
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
		
		///Notre IP est le premier parent
	    parents.add(root);
		

		for (int i = 0; i < TerminalOutput.size(); i++) {

			System.out.println(TerminalOutput.get(i));
			Pattern patternStep = Pattern.compile(GETTINGSTEP_PATTERN);
			Matcher matcherStep = patternStep.matcher(TerminalOutput.get(i));

			///Si nous sommes dans un nouveau "niveau" de profondeur avec vérification via regex. 
			if (!matcherStep.find()) {
				

				///Assign des parents aux enfants
				if (Switch == true) {
					AssignParentsToChild(parents, childs);
					Switch = false;
					parents = new ArrayList<TreeItem<String>>();
				}
				// /Assign des enfants aux parents suivants (afin que 3 enfants puissent avoir 3 enfants, etc...)
				else {
					AssignParentsToChild(childs, parents);
					Switch = true;
					childs = new ArrayList<TreeItem<String>>();
				}
			}

			Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
			Matcher matcher = pattern.matcher(TerminalOutput.get(i));
			/// Obtention de l'IP contenue dans la ligne via la regex
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
				
			///S'il y a des étoiles, on considère que la ligne est quand même une node, mais inconnue. 
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
		///Assign de la dernière adresse.
		if (Switch == true) {
			AssignParentsToChild(parents, childs);
			Switch = false;
		
		}
	
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
