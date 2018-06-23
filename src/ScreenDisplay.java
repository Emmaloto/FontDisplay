

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ScreenDisplay extends JPanel {
	
	private Screen helpScreen;
	private JFrame frame = new JFrame();
	
	//private ;

	public ScreenDisplay(String title, Image bg, String[] helpText){
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  //Close window on exit
		frame.setSize(1150, 850);			
		frame.add(this);
		
		
		helpScreen = new Screen(bg);
		helpScreen.setTitle("Program Information");
		helpScreen.changeTitlePosition((int)(frame.getWidth()/3.5), (int)(frame.getHeight()/20));
		Font f = getFont();
		Font large = new Font(f.getFontName(), Font.BOLD, 20);
		helpScreen.setTextFont(large);
		
		helpScreen.setPhrase(helpText, 40);
		//helpScreen.set
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		
		helpScreen.drawScreen(0, 0, (Graphics2D)g);
		// helpScreen.drawScreen(0, 0, (Graphics2D)info.getGraphics());		
	}
	
	public void displayWindow(){
		frame.setVisible(true);
	}

}
