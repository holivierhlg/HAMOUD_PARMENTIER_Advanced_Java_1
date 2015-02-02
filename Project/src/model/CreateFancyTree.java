package model;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import controller.AppController;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;

public class CreateFancyTree {
	private AppController Controller;

	public void Create_OutputFile() throws IOException, InterruptedException

	{
		PrintWriter writer = new PrintWriter("graph.gv", "UTF-8");
		writer.println(Controller.FancyTreeContent);
		writer.close();
		Process p = Runtime.getRuntime().exec("dot -Tpng graph.gv -o graph1.png");
		///We have to wait for the 
		p.waitFor();
		File file = new File("graph1.png");
		Image image = new Image(file.toURI().toURL().toExternalForm());

		// simple displays ImageView the image as is
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
