package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	public String FancyTreeContent;
	public TreeItem<String> MyTree;
	public ImageView schema;
	public boolean firsttime = true;

	public AppController(Tree tree, View view, TermOutput termOutput,
			CreateFancyTree FancyTree, Stage primaryStage) throws IOException

	{
		
		
		
		
		
		
		// /Passage du controller à la vue et au modèle

		// /TermOutput gère le terminal (traceroute) et son output (recupération
		// ligne à ligne, récupération de l'IP de la machine)
		this.TermOutput = termOutput;

		// /Tree implemente toute la création de l'arbre à partir de l'output du
		// terminal (via des TreeItem<String>)
		this.Tree = tree;
		this.Tree.setController(this);

		// /FancyTree implemente la génération d'un arbre "joli" (par rapport à
		// un treeview usuel)
		this.FancyTree = FancyTree;
		this.FancyTree.setController(this);

		// /Affichage de la GUI et passage du controller à la vue
		this.View = view;
		this.View.setController(this);
		this.View.LancerVue(primaryStage);

	}

	// /Lancement d'une recherche
	public void launchsearch(String site) throws IOException,
			InterruptedException

	{

		// /lancement d'un traceroute avec le site précisé dans le field
		this.Terminal = this.TermOutput.traceroute(site);
		this.monIP = TermOutput.getMyIP(); // /Explicite

		// /Construire et obtenir mon arbre depuis les informations du terminal
		this.MyTree = this.Tree.BuildTree(this.Terminal, monIP);
		this.FancyTreeContent = this.Tree.getFancyTree();
		this.FancyTree.Create_OutputFile();

	}

	// /Bouger un fichier (le sauvegarder, dans les faits) // Fonction de
	// sauvegarde
	public boolean moveFile(File file)

	{
		if (file != null) {

			try {

				File infile = new File("graph1.png");
				InputStream inStream = null;
				OutputStream outStream = null;
				inStream = new FileInputStream(infile);
				outStream = new FileOutputStream(file.getAbsolutePath()
						+ "/NetGraph.png");

				byte[] buffer = new byte[1024];

				int length;
				// copy the file content in bytes
				while ((length = inStream.read(buffer)) > 0) {

					outStream.write(buffer, 0, length);

				}

				inStream.close();
				outStream.close();
				return true;

			} catch (IOException e1) {

				e1.printStackTrace();
				return false;
			}

		}
		return false;

	}

	
	///Gérérer une RandomIP (Random 0-255 4 fois, pas de vérification de "l'existence" réelle de l'IP
	public String generateRandomIP()

	{
		int part;

		String output = "";

		for (int i = 0; i < 3; i++) {
			part = (int) (Math.random() * (255));
			output += Integer.toString(part);
			output += ".";
		}

		part = (int) (Math.random() * (255));
		output += Integer.toString(part);

		return output;

	}

}