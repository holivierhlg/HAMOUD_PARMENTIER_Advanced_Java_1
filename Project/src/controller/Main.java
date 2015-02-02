package controller;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import view.View;
import model.CreateFancyTree;
import model.TermOutput;
import model.Tree;
import controller.AppController;

public class Main extends Application {
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		///passage de tous les composants MVC au controller. 
		new AppController(new Tree(), new View(), new TermOutput(), new CreateFancyTree(), primaryStage);
	}
	
	static public void main(String[] args) throws IOException

	{
		///Lancement de l'application
		launch(args);
		
	

	}
}
