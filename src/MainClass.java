import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

// http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Togetallavailablefontsinyoursystem.htm
// https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java

@SuppressWarnings("serial")
public class MainClass extends JComponent implements ActionListener, ListSelectionListener, ChangeListener {
	
	@SuppressWarnings("unused")
	private Image fileIcon, listIcon, searchIcon, aboutIcon, bg;
	private String upp, low, sent, numb, sym;
	
    private JFrame fr = new JFrame();
    
	private JButton search, saveList, loadAll, help;
	private JComboBox <String> version, textSetting;
	private JSlider size;
	private JScrollPane sc, sc_search;
	private JTextField searchBox;
	private JLabel fontNo, fontNoDesc, sentence, sizeDesc, verDesc, settingDesc;
	private JPanel bottomPanel, topPanel,topPanel_i, textPanel, fontListPanel, optionsPanel;
	
	private JList<Font> list = new JList<>();
	private JList<Font> search_list = new JList<>();
	private DefaultListModel<Font> model;
	
	private FontList f = new FontList();
	
	
	Font [] test = new Font[1];
	
	public static void main(String[] args) {
		new MainClass();
	}
	
	public MainClass(){
		loadImages();
		setUpGUI();
		
		//f.printList();		
		fontNo.setText("" + f.getNumber());
		
	}
	
	private void fillPane(Font [] fonts){
		
		fontListPanel = new JPanel();
		
	
		list = new JList<>(fonts);
		// Old Initialization
		//search_list = new JList<>(test);
		
		// Let's use this instead, to enable adding & removing fonts		
		model = new  DefaultListModel<Font>();
		for(Font f: fonts)
			model.addElement(f);
		search_list = new JList<>(model);
		
		sc = new JScrollPane(list);
		sc_search = new JScrollPane(search_list); 
		
		CardLayout cardSetup = new CardLayout(30, 30);
		
		fontListPanel.add(sc);
		fontListPanel.add(sc_search);		
		cardSetup.addLayoutComponent(sc, "Full");
		cardSetup.addLayoutComponent(sc_search, "Search");		
		fontListPanel.setLayout(cardSetup);	
		fontListPanel.setBackground(Color.GRAY);
		
		list.setCellRenderer(new FontRenderer());
		search_list.setCellRenderer(new FontRenderer());
		
		list.setSelectedIndex(0);
		search_list.setSelectedIndex(0);
	    
		list.addListSelectionListener(this);
		search_list.addListSelectionListener(this);
			
	}
	
	private void setUpGUI(){
        
		// Listener Elements
		search = new JButton("Search");
		resizeTextComp(search, 25);
		search.setIcon(new ImageIcon(searchIcon));
		
		saveList = new JButton("Save Font List"); 
		resizeTextComp(saveList, 20);
		saveList.setIcon(new ImageIcon(fileIcon));
		
		loadAll = new JButton("Show Full List");
		resizeTextComp(loadAll, 20);
		loadAll.setIcon(new ImageIcon(listIcon));
		
		searchBox = new JTextField();
		searchBox.setPreferredSize(new Dimension(120, 30));
		
		sentence = new JLabel(sent);
		fontNoDesc = new JLabel("Number of Fonts Available: ");
		fontNo = new JLabel("N/A");
		resizeTextComp(sentence, 35);
		resizeTextComp(fontNoDesc, 25);
		resizeTextComp(fontNo, 25);
		
		// Middle Panel Stuff
		sizeDesc =  new JLabel("Text Size: " + sentence.getFont().getSize());
		settingDesc =  new JLabel("Text Presets ");
		verDesc =  new JLabel("Version "); 
		resizeTextComp(sizeDesc, 20);
		resizeTextComp(settingDesc, 20);
		resizeTextComp(verDesc, 20);
		
		
		size = new JSlider(10, 50);
		size.setValue(sentence.getFont().getSize());
		version = new JComboBox<String>(new String[]{"Full Version N/A", "Lite Version"}); 
		version.setBackground(Color.WHITE);
		version.setSelectedIndex(1);
		textSetting = new JComboBox<String>(new String[]{"Uppercase", "Lowercase", "Sentence","Numbers", "Symbols"});
		textSetting.setBackground(Color.WHITE);
		textSetting.setSelectedIndex(2);	
		resizeTextComp(version, 17);
		resizeTextComp(textSetting, 17);		
		
		help = new JButton("N/A", new ImageIcon(aboutIcon));

		
		// ALL the Panels!
		// Inner Top
		topPanel_i = new JPanel();
		topPanel_i.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 0));
		//topPanel_i.setLayout(new GridLayout(1, 2));
		//topPanel_i.setLayout(new BoxLayout(topPanel_i, BoxLayout.X_AXIS));
		topPanel_i.add(searchBox);
		topPanel_i.add(search);
		//topPanel_i.add(Box.createHorizontalGlue());
		resizeTextComp(searchBox, 30);
		searchBox.setPreferredSize(new Dimension( 210, 45));
		
		// Top Panel
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		
		topPanel.add(fontNoDesc);
		topPanel.add(fontNo);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(topPanel_i);
		//topPanel.setBackground(Color.YELLOW);
		
		// Middle text panel
		textPanel = new JPanel();
		//textPanel.setBounds(textPanel.getX(), textPanel.getY(), fr.getWidth(), textPanel.getHeight());
		textPanel.add(sentence);
		textPanel.setBackground(Color.LIGHT_GRAY);
		
		// Middle Options Panel
		optionsPanel = new JPanel();
		//optionsPanel.setBackground(Color.WHITE);
		//optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.LINE_AXIS));
		//optionsPanel.setLayout(new GridLayout(1, 3));
		//optionsPanel.setLayout(new FlowLayout(0, 30, FlowLayout.CENTER));
		//optionsPanel.add(Box.createRigidArea(new Dimension(10, 0))); //*
		
		optionsPanel.add(help); 
		
		JPanel sub1 = new JPanel();
		sub1.add(sizeDesc);
		sub1.add(size);
		optionsPanel.add(sub1);
	
		JPanel sub2 = new JPanel();
		sub2.add(settingDesc);
		sub2.add(textSetting);	
		optionsPanel.add(sub2);
		
		JPanel sub3 = new JPanel();
		//sub3.setBackground(Color.CYAN);
		sub3.add(verDesc);
		sub3.add(version);		
		optionsPanel.add(sub3);
		//optionsPanel.add(Box.createRigidArea(new Dimension(10, 0))); //*
		optionsPanel.setBackground(new Color(203,181,158));

		
		// Middle list panel
		fillPane(f.getList());
		
		// Bottom button panel
		bottomPanel = new JPanel();

		bottomPanel.add(saveList);
		bottomPanel.add(loadAll);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
		bottomPanel.setBackground(new Color(177,178,203));
		
		// JFrame
		fr.setTitle("Font Display Program");
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Close window on exit
	 	fr.setSize(1100, 900);			
		fr.getContentPane().add(this);    				  // IMPORTANT to add drawings
		fr.setVisible(true);		
		 
		// Set up of all the panels
		fr.setLayout(new BoxLayout(fr.getContentPane(), BoxLayout.PAGE_AXIS));
		fr.add(Box.createVerticalGlue());
		fr.add(Box.createRigidArea(new Dimension(0, 20)));
		fr.add(topPanel);
		fr.add(Box.createRigidArea(new Dimension(0, 30)));
		fr.add(textPanel);		
		fr.add(Box.createRigidArea(new Dimension(0, 10)));
		fr.add(optionsPanel);
		fr.add(Box.createRigidArea(new Dimension(0, 50)));
		fr.add(fontListPanel);
		//fr.add(Box.createVerticalGlue());
		fr.add(bottomPanel);
		//fr.add(panel2);
				
		/* VERY IMPORTANT with multiple GUI elements*/
		fr.setFocusable(true);
	    fr.requestFocusInWindow();
	    
	    
		search.addActionListener(this);
		saveList.addActionListener(this);	
		loadAll.addActionListener(this);	
		
		size.addChangeListener(this);
		version.addActionListener(this); 
		textSetting.addActionListener(this);
		
	}
	
	/*
    public void paintComponent(Graphics g){
		//super.paintComponent(g);
		g.drawImage(bg, 0, 0, fr.getWidth(),fr.getHeight(), this);
	}
	*/

	public void actionPerformed(ActionEvent a) {
		
		// Buttons
		if(a.getSource() == saveList){
			f.printToFile();
		}else if(a.getSource() == loadAll){
			CardLayout cl = (CardLayout)(fontListPanel.getLayout());
			cl.show(fontListPanel, "Full");	
			list.setSelectedIndex(0);
			
		}else if(a.getSource() == search){
			if(searchBox.getText() != ""){				
				Font [] newList = f.searchFonts(searchBox.getText());
			
				model.removeAllElements();
				for(Font f: newList)
					model.addElement(f);

				CardLayout cl = (CardLayout)(fontListPanel.getLayout());
				cl.show(fontListPanel, "Search");	
				
				search_list.setSelectedIndex(0);
			
			}

		}
		
		//System.out.println(list.isShowing());
		//System.out.println(search_list.isShowing());
		//System.out.println(a.getSource().getClass().toString());
		
		
		// Combo Boxes
		if(a.getSource().getClass().equals(textSetting.getClass())){
			if(a.getSource() == textSetting)
				if(textSetting.getSelectedItem() == "Uppercase")
					sentence.setText(upp);
				else if(textSetting.getSelectedItem() == "Lowercase")
					sentence.setText(low);
				else if(textSetting.getSelectedItem() == "Sentence")
					sentence.setText(sent);
				else if(textSetting.getSelectedItem() == "Numbers")
					sentence.setText(numb);
				else if(textSetting.getSelectedItem() == "Symbols")
					sentence.setText(sym);
			
			else if(a.getSource() == version)
				if(version.getSelectedItem() == "Full Version"){				
					list.setCellRenderer(new FontRenderer());
					search_list.setCellRenderer(new FontRenderer());
				}else{						
					list.setCellRenderer(new FontFullRenderer());
					search_list.setCellRenderer(new FontFullRenderer());
				}
			
		}
	}
	

	

	public void resizeTextComp(JComponent elt, int newSize){
		Font f = elt.getFont();
		Font larger = new Font(f.getFontName(), f.getStyle(), newSize);
		elt.setFont(larger);
	}
	

	
	@SuppressWarnings("unchecked") // To keep Eclipse happy with the generic cast
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()){
			
			JList<Font> source = (JList<Font>)e.getSource();
            Font selected = source.getSelectedValue();           
            
            if(selected != null)
            	sentence.setFont(new Font(selected.getFontName(), selected.getStyle(), size.getValue()));
        }	
		
	}

	@Override
	public void stateChanged(ChangeEvent s) {
		
		resizeTextComp(sentence, size.getValue());
		sizeDesc.setText("Text Size: " + size.getValue());
	}

	
	
	private void loadImages(){
		
		try {
			fileIcon = ImageIO.read(this.getClass().getResource("icons/file.png"));
			listIcon = ImageIO.read(this.getClass().getResource("icons/list.png"));
			searchIcon = ImageIO.read(this.getClass().getResource("icons/search.png"));
			aboutIcon = ImageIO.read(this.getClass().getResource("icons/about.png"));
			
			bg = ImageIO.read(this.getClass().getResource("icons/background.jpg"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
			
		sent = "The quick brown fox jumps under the lazy dog."; 
		upp = sent.toUpperCase(); 
		low = sent.toLowerCase(); 
		
		numb = "1 2 3 4 5 6 7 8 9 0"; 
		sym = "- = + _ \\ / , . ; : ? ! * {} () [] | \" \' ";
	}
	

}


// This renders all items with their respective fonts
class FontRenderer extends JLabel implements ListCellRenderer<Font>{

	JLabel test;
	private Border border;
	
	
	public Component getListCellRendererComponent(JList<? extends Font> l, Font val, int index, boolean isSelected, boolean cellHasFocus) {
		
		setOpaque(true);
		border = BorderFactory.createLineBorder(Color.BLUE, 1);
		
		//Font f = new Font(val.getFontName(), val.getStyle(), 20);
		Font f = new Font(l.getFont().getFontName(), l.getFont().getStyle(), 20);
		setFont(f);

		
		setText(val.getFontName());
		
	    if(isSelected){
	       setBackground(l.getSelectionBackground());
	       setForeground(l.getSelectionForeground());
	    }else{	    	
	        setBackground(l.getBackground());
	        setForeground(l.getForeground());
	     }

	    setEnabled(l.isEnabled());

	    if (isSelected && cellHasFocus)
	    	setBorder(border);
	    else
	       setBorder(null);
	    
	    
		return this;
	}


	
} 

// 	This renderer renders the list items with their respective fonts
//  Not yet in use
class FontFullRenderer extends JLabel implements ListCellRenderer<Font>{

	JLabel test;
	private Border border;
	
	
	public Component getListCellRendererComponent(JList<? extends Font> l, Font val, int index, boolean isSelected, boolean cellHasFocus) {
		
		setOpaque(true);
		border = BorderFactory.createLineBorder(Color.BLUE, 1);
		
		Font f = new Font(val.getFontName(), val.getStyle(), 20);
		//Font f = new Font(l.getFont().getFontName(), l.getFont().getStyle(), 20);
		setFont(f);

		
		setText(val.getFontName());
		
	    if(isSelected){
	       setBackground(l.getSelectionBackground());
	       setForeground(l.getSelectionForeground());
	    }else{	    	
	        setBackground(l.getBackground());
	        setForeground(l.getForeground());
	     }

	    setEnabled(l.isEnabled());

	    if (isSelected && cellHasFocus)
	    	setBorder(border);
	    else
	       setBorder(null);
	    
	    
		return this;
	}


	
} 
