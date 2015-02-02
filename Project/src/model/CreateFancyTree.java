package model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import controller.AppController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class CreateFancyTree {
	private AppController Controller;

	public void Create_OutputFile() throws IOException, InterruptedException

	{
		///Creation, remplissage et sauvegarde du fichier contenant l'architecture du graph
		PrintWriter writer = new PrintWriter("graph.gv", "UTF-8");
		writer.println(Controller.FancyTreeContent);
		writer.close();
		
		///Execution de dot (graphviz)
		Process p = Runtime.getRuntime().exec("dot -Tpng graph.gv -o graph1.png");
		///Attente de la fin du process
		p.waitFor();
		
		///Obtention de l'image d'output, formatage de l'image et passage au controller. 
		File file = new File("graph1.png");
		Image image = new Image(file.toURI().toURL().toExternalForm());
		ImageView schema = new ImageView();
		schema.setImage(image);
		schema.setFitWidth(500);
        schema.setPreserveRatio(true);
        schema.setSmooth(true);
        schema.setCache(true);
		Controller.schema = schema;

	}

	public void setController(AppController Controller)

	{
		this.Controller = Controller;
	}

}
