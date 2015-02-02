package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import javafx.concurrent.Task;

import controller.AppController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;


public final class View {
	
	Task copyLauncher;
	
	private AppController Controller; 
	private TreeItem<String> root = new TreeItem<String>();
    
    public void LancerVue(final Stage primaryStage) throws MalformedURLException {
        
    	final ProgressBar progressBar = new ProgressBar(0);
    	
    	final Menu menu1 = new Menu("File");
    	final MenuItem save = new MenuItem("Save schema as PNG");
    	menu1.getItems().add(save);
    
    	final Menu menu3 = new Menu("Help");
    	final MenuItem help = new MenuItem("Voir aide");
    	
    	menu3.getItems().add(help);
    	
    	final TextField IpField = new TextField();
    	 final Label Message = new Label();
    	 Button btn = new Button("Generate");
    	 Button btnrandom = new Button("Random IP");
    	 
    	  final DirectoryChooser DirectChooser = new DirectoryChooser();
    	  DirectChooser.setTitle("Selectionnez votre dossier de destination");
    	
    
    	 
    	 MenuBar menuBar = new MenuBar();
    	 menuBar.getMenus().addAll(menu1, menu3);
    	 
    	 final GridPane grid = new GridPane();
    	 grid.setAlignment(Pos.TOP_CENTER);
        	grid.setHgap(10);
          	grid.setVgap(10);
    		grid.setPadding(new Insets(0, 25, 25, 25));
    		
    		
    		
    	  
    	
    	  
    	
      
       
        //Use HBOX and VBOX layout panes to space out the controls
        //in a single row
        final VBox mainBox = new VBox();
        final HBox TreeViewBox = new HBox();
        //final HBox FancyViewBox = new HBox();
        final HBox ButtonBox = new HBox();
        final HBox LoadingBox = new HBox();
        
    	final ScrollPane sp = new ScrollPane();
    	sp.setMinWidth(500);
        
     
    	
        grid.add(TreeViewBox, 0, 2);
     
        
        
       final VBox HelpBox = new VBox();
 	   final Label HelpContent = new Label("Welcome in NetGraph.\n\nGenerate your visual Traceroute.\nTo start, enter an IP address or an domain name (or click on random IP if you're not in the mood) and click on generate.\n\nOnce you generated the graph, you can save it by clicking on file / save as png.\n \n \n \n \n");
 	   HelpBox.setPadding(new Insets(10));
 	   File file = new File("Logo_ECE_Paris.jpg");
		Image image = new Image(file.toURI().toURL().toExternalForm());

		ImageView logo = new ImageView();
		logo.setImage(image);
		Button closehelp = new Button("Close");
		
		
		logo.setFitWidth(150);
		logo.setFitHeight(150);
		logo.setPreserveRatio(true);
		logo.setSmooth(true);
        logo.setCache(true);
       HelpBox.getChildren().add(logo);
 	   HelpBox.getChildren().add(HelpContent);
 	   HelpBox.getChildren().add(closehelp);
 	   final Stage helpStage = new Stage(); 
 	   Scene helpscene = new Scene(HelpBox, 800, 200);
 	   helpStage.setScene(helpscene);
 	   
       
        mainBox.getChildren().add(menuBar);
        mainBox.getChildren().add(grid);
        ButtonBox.getChildren().add(IpField);
        ButtonBox.getChildren().add(btn);
        ButtonBox.getChildren().add(btnrandom);
        LoadingBox.getChildren().add(progressBar);
      
    	ButtonBox.setMargin(IpField, new Insets(5,5, 5, 5));
    	ButtonBox.setMargin(btn, new Insets(5, 5, 5, 5));
    	ButtonBox.setMargin(btnrandom, new Insets(5, 5, 0, 0));
    
    	
        
       
        
        save.setDisable(true);
        grid.add(ButtonBox, 0, 0);
        grid.add(LoadingBox, 1, 0);
        ButtonBox.getChildren().add(Message);
        
        //Add the main HBOX layout pane to the scene
        Scene scene = new Scene(mainBox, 800, 250);
        
        //Show the form
        primaryStage.setTitle("NetGraph");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        closehelp.setOnAction(new EventHandler<ActionEvent>() {
        	 
          public void handle(ActionEvent e) {
          	
       	
              helpStage.close();
              
          }
      });
    
        help.setOnAction(new EventHandler<ActionEvent>() {
         	 
           public void handle(ActionEvent e) {
           	
        	
               helpStage.show();
               
           }
       });

        
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
          	 
            public void handle(ActionEvent e) {
            	
            	progressBar.setProgress(0);
            	copyLauncher = Controller.LoadingTask();
            	progressBar.progressProperty().unbind();
            	progressBar.progressProperty().bind(copyLauncher.progressProperty());
                
                    
                new Thread(copyLauncher).start();
                
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
                if (file != null) {
                	
                	try{
                		 
                 	   File afile =new File("graph1.png");
                 		InputStream inStream = null;
                		OutputStream outStream = null;
                 	  inStream = new FileInputStream(afile);
                 	  System.out.println(file.getAbsolutePath());
              	    outStream = new FileOutputStream(file.getAbsolutePath() +"/NetGraph.png");
           
              	    byte[] buffer = new byte[1024];
           
              	    int length;
              	    //copy the file content in bytes 
              	    while ((length = inStream.read(buffer)) > 0){
           
              	    	outStream.write(buffer, 0, length);
           
              	    }
           
              	    inStream.close();
              	    outStream.close();
           
              	    Message.setText("Votre fichier à été sauvegardé dans : " + file.getAbsolutePath() );
                	}catch(IOException e1){
                	    e1.printStackTrace();
                	}
                 	   
                 	  
                }
            	
            }
        });
        
    
        
        
        
    btnrandom.setOnAction(new EventHandler<ActionEvent>() {
     	 
        public void handle(ActionEvent e) {
        	
        	
        	int part; 
        	
        	String output=""; 
        	
        	for(int i = 0; i<3; i++)
        	{part = (int) (Math.random() * (255));
        		output += Integer.toString(part);
        	output += ".";}
        	
        	part = (int) (Math.random() * (255));
    		output += Integer.toString(part);
        	
        	
        	IpField.setText(output);
        	
        }
    });
}
   
    public void setController(AppController Controller)
    {
    	
    	this.Controller = Controller;
    	
    }
       
}
 