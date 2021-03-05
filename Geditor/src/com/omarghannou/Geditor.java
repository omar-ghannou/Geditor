package com.omarghannou;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import java.awt.Scrollbar;
import java.awt.List;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.swing.JTextArea;
import java.awt.TextField;
import javax.swing.JSlider;
import java.awt.Label;
import javax.swing.SwingConstants;
import java.awt.Point;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.Box;

public class Geditor extends JFrame {

	int defaultTextSize = 11;
	Color defaultColor = Color.black;
	
	JMenuBar menuBar;
	JMenu mnFile;
	JMenuItem mntmNew;
	JMenuItem mntmOpen;
	JMenuItem mntmSave;
	JMenuItem mntmSaveAs;
	JSeparator separator_5;
	JMenuItem mntmExit;
	JMenu mnEdit;
	JMenuItem mntmUndo;
	JMenuItem mntmRedo;
	JSeparator separator_1;
	JMenuItem mntmCut;
	JMenuItem mntmCopy;
	JMenuItem mntmPaste;
	JMenuItem mntmDelete;
	JSeparator separator_3;
	JMenuItem mntmFind;
	JMenuItem mntmReplace;
	JMenu mnFormat;
	JMenuItem mntmFont;
	JMenu mnView;
	JMenu mnZoom;
	JMenuItem mntmZoomIn;
	JMenuItem mntmZoomOut;
	JMenu mnHelp;
	JMenuItem mntmHelp;
	JMenuItem mntmContact;
	JSeparator separator_2;
	JMenuItem mntmAbout;
	JScrollPane scroll;
	JToolBar toolBar;
	JSlider slider;
	Label label;
	JColorChooser ColorSelector;
	JButton ColorButton;
	JSeparator tool_separator_1;
	JEditorPane editorPane;
	Label statusBar;
	JMenuItem mntmOpenImage;
	JButton btnOpenImage;
	private Component horizontalGlue;
	
	/**
	 * Launch the application.
	 */
	//public static void main(String[] args) {
		//EventQueue.invokeLater(new Runnable() {
			//public void run() {
				//try {
					//Geditor frame = new Geditor();
					//frame.setVisible(true);
				//} catch (Exception e) {
					//e.printStackTrace();
				//}
			//}
		//});
	//}

	/**
	 * Create the frame.
	 */
	public Geditor() {
		setTitle("Untitled - Geditor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 480);
		
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.CYAN);
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		mntmOpen = new JMenuItem("Open...");
		mnFile.add(mntmOpen);
		
		mntmOpenImage = new JMenuItem("Open Image...");
		mnFile.add(mntmOpenImage);
		
		mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		mntmSaveAs = new JMenuItem("Save As...");
		mnFile.add(mntmSaveAs);
		
		separator_5 = new JSeparator();
		mnFile.add(separator_5);
		
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mntmUndo = new JMenuItem("Undo");
		mnEdit.add(mntmUndo);
		
		mntmRedo = new JMenuItem("Redo");
		mnEdit.add(mntmRedo);
		
		separator_1 = new JSeparator();
		mnEdit.add(separator_1);
		
		mntmCut = new JMenuItem("Cut");
		mnEdit.add(mntmCut);
		
		mntmCopy = new JMenuItem("Copy");
		mnEdit.add(mntmCopy);
		
		mntmPaste = new JMenuItem("Paste");
		mnEdit.add(mntmPaste);
		
		mntmDelete = new JMenuItem("Delete");
		mnEdit.add(mntmDelete);
		
		separator_3 = new JSeparator();
		mnEdit.add(separator_3);
		
		mntmFind = new JMenuItem("Find");
		mnEdit.add(mntmFind);
		
		mntmReplace = new JMenuItem("Replace");
		mnEdit.add(mntmReplace);
		
		mnFormat = new JMenu("Format");
		menuBar.add(mnFormat);
		
		mntmFont = new JMenuItem("Font...");
		mnFormat.add(mntmFont);
		
		mnView = new JMenu("View");
		menuBar.add(mnView);
		
		mnZoom = new JMenu("Zoom");
		mnView.add(mnZoom);
		
		mntmZoomIn = new JMenuItem("Zoom In");
		mnZoom.add(mntmZoomIn);
		
		mntmZoomOut = new JMenuItem("Zoom Out");
		mnZoom.add(mntmZoomOut);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmHelp = new JMenuItem("Help...");
		mnHelp.add(mntmHelp);
		
		mntmContact = new JMenuItem("Contact");
		mnHelp.add(mntmContact);
		
		separator_2 = new JSeparator();
		mnHelp.add(separator_2);
		
		mntmAbout = new JMenuItem("About Geditor");
		mnHelp.add(mntmAbout);
		
		Geditor.class.getClassLoader();
		String imgsrc = ClassLoader.getSystemResource("g.png").toString();
		System.out.println(imgsrc);
		editorPane = new JEditorPane("text/html",null);
		editorPane.setContentType("text/html");
		editorPane.setSelectionColor(Color.yellow);
		
		toolBar = new JToolBar();

		
		slider = new JSlider();
		slider.setMaximumSize(new Dimension(80, slider.getMaximumSize().height));
		slider.setMinimum(8);
		slider.setMaximum(64);
		slider.setValue(defaultTextSize);
		
		label = new Label("textSize "+slider.getValue());
		label.setMaximumSize(new Dimension(68, label.getMaximumSize().height));
		
		toolBar.add(label);
		toolBar.add(slider);
		
		ColorSelector = new JColorChooser(Color.black);
		
		tool_separator_1 = new JSeparator();
		tool_separator_1.setLocation(new Point(29, 0));
		tool_separator_1.setOrientation(SwingConstants.VERTICAL);
		tool_separator_1.setMaximumSize(new Dimension(5,tool_separator_1.getMaximumSize().height));
		toolBar.add(tool_separator_1);
		
		ColorButton = new JButton("");
		ColorButton.setMaximumSize(new Dimension(22, 22));
		ColorButton.setBackground(ColorSelector.getColor());
		toolBar.add(ColorButton);		
		
		statusBar = new Label("");
		
		
		scroll = new JScrollPane(editorPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


		this.setMinimumSize(new Dimension(400, 240));

		
		scroll.setViewportView(editorPane);
		scroll.setColumnHeaderView(toolBar);
		
		horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setMaximumSize(new Dimension(8, 0));
		toolBar.add(horizontalGlue);
		
		btnOpenImage = new JButton("Insert Image");
		toolBar.add(btnOpenImage);

		getContentPane().add(scroll);
		
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		try {
			this.setIconImage(ImageIO.read(getClass().getResourceAsStream("/g.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ChangeTextSize(defaultTextSize);
	}
	
	void ChangeTextSize(int TextSize) {
		label.setText("textSize "+Integer.toString(TextSize));
		editorPane.setFont(new Font(editorPane.getFont().getFontName(),editorPane.getFont().getStyle(),TextSize));
	}
	void ChangeTextStyle(int bold) {
		editorPane.setFont(new Font(editorPane.getFont().getFontName(),bold,editorPane.getFont().getSize()));
	}
	void ChangeTextColor(Color color) {
		editorPane.setForeground(color);
	}

}
