package view;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Label;


public class tree extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
       final String royalLabelText = "Selected Tree Item From Royal Tree: \n";
       
        //Use HBOX and VBOX layout panes to space out the controls
        //in a single row
        HBox treeBox = new HBox();
        HBox controlBox = new HBox();
        
        //Create labels to highlight the selected items from the TreeViews
        final Label royalLabel = new Label(royalLabelText);

        

        
        //Create TreeItems for the Hierarchy of the TreeView
        TreeItem<String> royalRoot = new TreeItem<String>("Queen Elizabeth - Prince Philip");    
        TreeItem<String> Charlie  = new TreeItem<String>("Prince Charles - Princess Diana");
        TreeItem<String> Annie = new TreeItem<String>("Princess Anne - Mark Phillips");
        TreeItem<String> Andy = new TreeItem<String>("Prince Andrew - Sarah Ferguson");
        TreeItem<String> Eddie = new TreeItem<String>("Prince Edward - Sophie");
        
        //Populate the TreeItem to be used as the root with the other TreeItems
        royalRoot.getChildren().addAll(Charlie, Annie, Andy, Eddie);
        
        //Create a TreeView using the root TreeItem
        TreeView<String> royalTree = new TreeView<String>(royalRoot);
       
        //Populate the other TreeItems with more TreeItems 
        //to build the family tree
        Charlie.getChildren().addAll(
                new TreeItem<String>("Prince William"), 
                new TreeItem<String>("Prince Henry"));
        
        Annie.getChildren().addAll(
                new TreeItem<String>("Peter Phillips"), 
                new TreeItem<String>("Zara Phillips"));
        
        Andy.getChildren().addAll(
                new TreeItem<String>("Princess Beatrice"), 
                new TreeItem<String>("Princess Eugenie"));
        
        Eddie.getChildren().addAll(
                new TreeItem<String>("Lady Louise"), 
                new TreeItem<String>("Viscount Severn"));
        

        
        //Add the TreeViews to the HBox
        treeBox.getChildren().add(royalTree);

        
        //Add the labels to the VBox
        
        //Add the HBox and VBox to another HBox to 
        //position the layout panes
        controlBox.getChildren().add(treeBox);
        
        //Add the main HBOX layout pane to the scene
        Scene scene = new Scene(controlBox, 800, 250);
        
        //Show the form
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}