package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*/create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        //create the child nodes
        DefaultMutableTreeNode vegetableNode = new DefaultMutableTreeNode("Vegetables");
        DefaultMutableTreeNode fruitNode = new DefaultMutableTreeNode("Fruits");
 
        //add the child nodes to the root node
        root.add(vegetableNode);
        root.add(fruitNode);
         
        //create the tree by passing in the root node
        tree = new JTree(root);
        add(tree);
         
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JTree Example");       
        this.pack();
        this.setVisible(true);
        
        //String ext = line.substring(line.indexOf("(")+1,line.indexOf(")"));
		//line = " 1 80.67.162.30 (80.67.162.30)  0.341 ms  0.300 ms  0.299 ms";
		/*
		int index = line.indexOf("(");
		int index1=line.indexOf(")");
	//	ArrayList<String> resultats = new ArrayList<String>();
		String[][] resultats;
		int row; 
		while(index >=0)
			
		{
			line.substring(index+1, index1);
		}
		
		/*for (int index = line.indexOf("(");index >= 0; index = line.indexOf("(", index + 1))
			
		{
		System.out.println(ext);}*/

public class GetTreeIP {
	private final String siteTest = "google.com"; ///The site we are tracerouting
	private String line = null;
	private final BufferedReader reader;
	
	public GetTreeIP() throws IOException
	{
	
	
	
	
	ProcessBuilder pb = new ProcessBuilder("traceroute", siteTest); ///Setting the command
	Process p = pb.start(); ///Launching the traceroute
	
	reader = new BufferedReader(new InputStreamReader(p.getInputStream())); ///Reading the InputStream
	
	while ((line = reader.readLine()) != null) {
		System.out.println(line); ///For each line, getting the IP addresses of the line
		
	}
		
	
	
	reader.close();

}
}
