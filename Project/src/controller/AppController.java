package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder;

import javafx.concurrent.Task;
/*
 * %  traceroute fr.wikipedia.org
traceroute to rr.knams.wikimedia.org (145.97.39.155), 30 hops max, 38 byte packets
 1  80.67.162.30 (80.67.162.30)  0.341 ms  0.300 ms  0.299 ms
 2  telehouse2-gw.netaktiv.com (80.67.170.1)  5.686 ms  1.656 ms  0.428 ms
 3  giga.gitoyen.net (80.67.168.16)  1.169 ms  0.704 ms  0.563 ms
 4  62.4.73.27 (62.4.73.27)  2.382 ms  1.623 ms  1.297 ms
 5  ge5-2.mpr2.cdg2.fr.above.net (64.125.23.86)  1.196 ms ge9-4.mpr2.cdg2.fr.above.net (64.125.23.102)  1.290 ms ge5-1.mpr2.cdg2.fr.above.net (64.125.23.82)  30.297 ms
 6  so-5-0-0.cr1.lhr3.uk.above.net (64.125.23.13)  41.900 ms  9.658 ms  9.118 ms
 7  so-7-0-0.mpr1.ams5.nl.above.net (64.125.27.178)  23.403 ms  23.209 ms  23.703 ms
 8  64.125.27.221.available.above.net (64.125.27.221)  19.149 ms so-0-0-0.mpr3.ams1.nl.above.net (64.125.27.181)  19.378 ms 64.125.27.221.available.above.net (64.125.27.221)  20.017 ms
 9  PNI.Surfnet.ams1.above.net (82.98.247.2)  16.834 ms  16.384 ms  16.129 ms
10  af-500.xsr01.amsterdam1a.surf.net (145.145.80.9)  21.525 ms 20.645 ms  24.101 ms
11  kncsw001-router.customer.surf.net (145.145.18.158)  20.233 ms 16.868 ms  19.568 ms
12  gi0-24.csw2-knams.wikimedia.org (145.97.32.29)  23.614 ms  23.270 ms  23.574 ms
13  rr.knams.wikimedia.org (145.97.39.155)  23.992 ms  23.050 ms 23.657 ms

*
*
**/








import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import view.View;
import model.CreateFancyTree;
import model.Tree;
import model.TermOutput;

public class AppController {

	private final Tree Tree; 
	private final View View;
	private final TermOutput TermOutput;
	private final CreateFancyTree FancyTree;
	private ArrayList<String> Terminal;
	private String monIP;
	private String site = "google.com"; 
	public String FancyTreeContent; 
	public TreeItem<String> MyTree;
	public ImageView schema; 
	public boolean firsttime = true;
	public AppController(Tree tree, View view, TermOutput termOutput, CreateFancyTree FancyTree, Stage primaryStage) throws IOException
	
	{
	///Passage du controller à la vue et au modèle
	this.Tree= tree;
	this.Tree.setController(this);
	
	this.View = view; 
	this.View.setController(this);
	
	
	
	
	///Obtenir les output du terminal avec les commandes ipconfig et traceroute
	this.TermOutput = termOutput; 
	this.FancyTree = FancyTree;
	this.FancyTree.setController(this);

	
	///Affichage visuel de l'abre
	
	this.View.LancerVue(primaryStage);
	

	}
	
/*	public void launchsearch(String site) throws IOException, InterruptedException
	
	{
		
		this.Terminal = this.TermOutput.traceroute(site); ///Site du traceroute
		this.monIP = TermOutput.getMyIP();
		
		///Construire et obtenir mon arbre depuis les informations du terminal
		this.MyTree = this.Tree.BuildTree(this.Terminal, monIP);
		System.out.println(this.MyTree);
		this.FancyTreeContent = this.Tree.getFancyTree();
		this.FancyTree.Create_OutputFile();
		
	}
	*/
	
	public Task launchsearch(final String site) throws IOException, InterruptedException {
        return new Task() {
        	
			private int loading = 0;
			private Object TermOutput;
			private ArrayList<String> Terminal;
			private String monIP;
			private model.Tree Tree;
			private TreeItem<String> MyTree;
			private String FancyTreeContent;
			private CreateFancyTree FancyTree;
			
			@Override
			protected Object call() throws Exception {

				System.out.println("L'AVANT VOLLAILE");
				this.Terminal = ((model.TermOutput) this.TermOutput).traceroute(site); ///Site du traceroute
				this.monIP = ((model.TermOutput) TermOutput).getMyIP();
				
				System.out.println("L'APRES VOLLAILE");
				
				while(loading != 18)
        		{
        			System.out.println("LE WHILE");
            		loading = ((model.TermOutput) this.TermOutput).getLoading();
            		updateProgress(loading, 19);
        		}
        		System.out.println("L'APRES WHILE");
				
        		
        		///Construire et obtenir mon arbre depuis les informations du terminal
        		this.MyTree = ((model.Tree) this.Tree).BuildTree((ArrayList<String>) this.Terminal,(String) monIP);
        		System.out.println(this.MyTree);
        		this.FancyTreeContent = ((model.Tree) this.Tree).getFancyTree();
        		((CreateFancyTree) this.FancyTree).Create_OutputFile();
        		
        		System.out.println("L'APRES LAUNCH");
        		
        		
                return true;
            }
        };
    }

}