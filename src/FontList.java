import java.awt.Font;
import java.awt.GraphicsEnvironment;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.text.SimpleDateFormat;  
import java.util.Date;  

import java.util.ArrayList;

public class FontList {

	private Font [] fontList;	
	
	public FontList(){
	    GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Font[] fonts = e.getAllFonts(); // Get the fonts
	    
	    fontList = fonts;
	    		
	}
	
	public Font [] getList(){
		return fontList;
	}
	
	public int getNumber(){
		return fontList.length;
	}
	
	
	public void printToFile(){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("font-list.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
		
		writer.println("As of " + formatter.format(date) + " you have " + getNumber() +" fonts on your machine accessible to Java.");
		
		for (Font f : fontList) {
		  writer.println(f.getFontName());
		}
		writer.close();
	}
	
	public void printList(){
	    for (Font f : fontList) {
	      System.out.println(f.getFontName());
	    }
	  
	}
	
	
	// Checks if string sequence passed is in any of the font names
	public Font [] searchFonts(String search){
		ArrayList<Font> s = new ArrayList<Font>();
		for(int i = 0; i < fontList.length; i++)
			if(fontList[i].getFontName().toLowerCase().contains(search.toLowerCase()))
				s.add(fontList[i]);
				
		Font selected [] = s.toArray(new Font[s.size()]);		
			
			
		return selected;
	}
	

}
