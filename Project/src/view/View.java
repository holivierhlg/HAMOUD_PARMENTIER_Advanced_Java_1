package view;

import java.io.File;

import java.io.IOException;
import java.net.MalformedURLException;
import controller.AppController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Label;

public final class View {

	private AppController Controller;
	private TreeItem<String> root = new TreeItem<String>();

	public void LancerVue(final Stage primaryStage)
			throws MalformedURLException {

		// /Creation de la boite contenant tout
		final VBox mainBox = new VBox();

		// /Creation de la grid pour l'organisation des composants
		final GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 25, 25, 25));

		// /Creation de la barre de menu
		final Menu menu1 = new Menu("File");
		final Menu menu2 = new Menu("Help");
		final MenuItem save = new MenuItem("Save schema as PNG");
		save.setDisable(true);
		final MenuItem help = new MenuItem("Voir aide");
		menu1.getItems().add(save);
		menu2.getItems().add(help);

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu1, menu2);

		// /Creation des boutons, du textfield ainsi que d'un Label pour
		// d'eventuels messages
		final TextField IpField = new TextField();
		final Label Message = new Label();
		Button btn = new Button("Generate");
		Button btnrandom = new Button("Random IP");

		// /Creation de la barre contenant les boutons et l'ipfield
		final HBox ButtonBox = new HBox();
		HBox.setMargin(IpField, new Insets(5, 5, 5, 5));
		HBox.setMargin(btn, new Insets(5, 5, 5, 5));
		HBox.setMargin(btnrandom, new Insets(5, 5, 0, 0));

		// /Box de la visualisation de la TreeView
		final HBox TreeViewBox = new HBox();

		// /Pane de la visuatlion du FancyTree
		final ScrollPane sp = new ScrollPane();
		sp.setMinWidth(500);

		// /Ajout des différents composant dans la grid
		grid.add(ButtonBox, 0, 0);
		grid.add(TreeViewBox, 0, 2);

		// /Ajout de la barre de menu puis de la grid dans la mainBox
		mainBox.getChildren().add(menuBar);
		mainBox.getChildren().add(grid);

		// /Ajout des buttons, fields et label dans la ButtonBox
		ButtonBox.getChildren().add(IpField);
		ButtonBox.getChildren().add(btn);
		ButtonBox.getChildren().add(btnrandom);
		ButtonBox.getChildren().add(Message);

		// /Display de la fenêtre principale
		Scene scene = new Scene(mainBox, 800, 250);
		primaryStage.setTitle("NetGraph");
		primaryStage.setScene(scene);
		primaryStage.show();

		// /Creation de la page d'aide
		final VBox HelpBox = new VBox();
		HelpBox.setPadding(new Insets(10));
		// Logo Ece
		File file = new File("Logo_ECE_Paris.jpg");
		Image image = new Image(file.toURI().toURL().toExternalForm());
		ImageView logo = new ImageView();
		logo.setImage(image);
		logo.setFitWidth(150);
		logo.setFitHeight(150);
		logo.setPreserveRatio(true);
		logo.setSmooth(true);
		logo.setCache(true);
		// /Texte
		final Label HelpContent = new Label(
				"Welcome in NetGraph.\n\nGenerate your visual Traceroute.\nTo start, enter an IP address or an domain name (or click on random IP if you're not in the mood) and click on generate.\n\nOnce you generated the graph, you can save it by clicking on file / save as png.\n \n \n \n \n");
		// /Button pour clore
		Button closehelp = new Button("Close");

		// /Ajout de tout dans la box
		HelpBox.getChildren().add(logo);
		HelpBox.getChildren().add(HelpContent);
		HelpBox.getChildren().add(closehelp);

		// /Puis de la box dans une fenêtre
		final Stage helpStage = new Stage();
		Scene helpscene = new Scene(HelpBox, 800, 600);
		helpStage.setScene(helpscene);
		
		///Choix du directory pour la sauvegarde
		final DirectoryChooser DirectChooser = new DirectoryChooser();
  	  	DirectChooser.setTitle("Selectionnez votre dossier de destination");
  	  	

  	  	
  	  	///Fermer la fenêtre d'aide
		closehelp.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				helpStage.close();

			}
		});

		
	  	///Afficher la fenêtre d'aide
		help.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				helpStage.show();

			}
		});

	  	///Lancer le traceroute et màj de l'affichage
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				System.out.println(IpField.getText());
				try {

					Controller.launchsearch(IpField.getText());
				} catch (IOException | InterruptedException e1) {
					
					e1.printStackTrace();
				}

				IpField.setText("");
				save.setDisable(false);

				TreeViewBox.getChildren().clear();

				sp.setContent(Controller.schema);
				root = Controller.MyTree;
				TreeView<String> FinalTree = new TreeView<String>(root);
				TreeViewBox.getChildren().add(FinalTree);
				TreeViewBox.getChildren().add(sp);

			}
		});

		save.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				File file = DirectChooser.showDialog(primaryStage);
				if (Controller.moveFile(file)) {
					Message.setText("Votre fichier à été sauvegardé dans : "
							+ file.getAbsolutePath());
				}

			}
		});

		btnrandom.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				IpField.setText(Controller.generateRandomIP());

			}
		});
	}

	public void setController(AppController Controller) {

		this.Controller = Controller;

	}

}
