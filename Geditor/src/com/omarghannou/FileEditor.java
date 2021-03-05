package com.omarghannou;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.AncestorEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.SliderUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class FileEditor implements ChangeListener{
	
	Geditor geditor;
	
	int searchIndex = 0;
	boolean fileChanged;
	boolean saved;  
	boolean newFileFlag;  
	String fileName;  
	String applicationTitle="Geditor";  
	  
	File fileRef;  
	JFileChooser chooser;  
	/////////////////////////////  
	boolean isSave(){return saved;}  
	void setSave(boolean saved){this.saved=saved;}  
	String getFileName(){return new String(fileName);}  
	void setFileName(String fileName){this.fileName=new String(fileName);}  

	public FileEditor(Geditor editor) {
		
		geditor = editor;
		SetListeners(geditor);
		
		fileChanged = false;
		saved = false;
		newFileFlag = true;
		fileName = new String("Untitled");
		fileRef = new File(fileName);
		geditor.setTitle(fileName + " - " + applicationTitle);
		
		chooser=new JFileChooser();
		FileFilter txtFilter = new FileNameExtensionFilter("text Files","txt");
		FileFilter cppFilter = new FileNameExtensionFilter("C++ Files","cpp");
		FileFilter javaFilter = new FileNameExtensionFilter("Java Files","java");
		FileFilter jpgFilter = new FileNameExtensionFilter("JPG Images","jpg");
		FileFilter jpegFilter = new FileNameExtensionFilter("JPEG Images","jpeg");
		FileFilter pngFilter = new FileNameExtensionFilter("PNG Images","png");
		chooser.addChoosableFileFilter(txtFilter);  
		chooser.addChoosableFileFilter(cppFilter);  
		chooser.addChoosableFileFilter(javaFilter);  
		chooser.addChoosableFileFilter(jpgFilter);  
		chooser.addChoosableFileFilter(jpegFilter);  
		chooser.addChoosableFileFilter(pngFilter);  
		chooser.setCurrentDirectory(new File("."));  
		
		
		geditor.setVisible(true);

	}
	
	void SetListeners(Geditor editor) {

		
		editor.mntmNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newFile();
				
			}
		});
		editor.mntmOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openFile();	
			}
		});
		editor.mntmSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveThisFile();
			}
		});
		editor.mntmSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveAsFile();
			}
		});

		editor.mntmExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				geditor.dispose();
				System.exit(0);
			}
		});


		editor.mntmCut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CutToClipBoard();
				
			}
		});
		
		editor.mntmCopy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			      CopyToClipBoard();
			}
		});
		
		editor.mntmPaste.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PasteFromClipBoard();
			}
		});
		
		editor.mntmDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DeleteFromText();
			}
		});
		
		editor.mntmFont.addActionListener(new ActionListener() {
			JFrame ColorFrame;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ColorFrame = new JFrame();
				ColorFrame.setTitle("Color Selector - Geditor");
				ColorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				ColorFrame.setBounds(150, 150, 400, 240);
				try {
					ColorFrame.setIconImage(ImageIO.read(getClass().getResourceAsStream("/g.png")));
				} catch (IOException expc) {
					// TODO Auto-generated catch block
					expc.printStackTrace();
				}
				JColorChooser cchooser = new JColorChooser();
				cchooser.setColor(geditor.defaultColor);
				ColorFrame.add(cchooser);
				ColorFrame.setVisible(true);
				ColorFrame.addWindowListener(new WindowAdapter() {
				    @Override
				    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				    	geditor.defaultColor = cchooser.getColor();
						geditor.ChangeTextColor(geditor.defaultColor);
						geditor.ColorButton.setBackground(geditor.defaultColor);
						super.windowClosing(windowEvent);
				    }
				} );
				
			}
		});

		editor.mntmZoomIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if((geditor.editorPane.getFont().getSize() + 20) <= 64)
				geditor.ChangeTextSize(geditor.editorPane.getFont().getSize()+20);
				else {
					geditor.ChangeTextSize(64);
				}
			}
		});
		editor.mntmZoomOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if((geditor.editorPane.getFont().getSize() - 20) >=8)
				geditor.ChangeTextSize(geditor.editorPane.getFont().getSize()-20);
				else {
					geditor.ChangeTextSize(8);
				}
				
			}
		});
		
		editor.mntmHelp.addActionListener(new ActionListener() {
			JFrame HelpFrame;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				HelpFrame = new JFrame();
				HelpFrame.setTitle("Help - Geditor");
				HelpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				HelpFrame.setBounds(150, 150, 400, 240);
				try {
					HelpFrame.setIconImage(ImageIO.read(getClass().getResourceAsStream("/g.png")));
				} catch (IOException expc) {
					// TODO Auto-generated catch block
					expc.printStackTrace();
				}
				JLabel label = new JLabel("No help needed");
				HelpFrame.getContentPane().add(label, BorderLayout.NORTH);
				HelpFrame.setVisible(true);	
			}
		});
		
		editor.mntmContact.addActionListener(new ActionListener() {
			JFrame contactFrame;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				contactFrame = new JFrame();
				contactFrame.setTitle("Contact - Geditor");
				contactFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				contactFrame.setBounds(150, 150, 400, 240);
				try {
					contactFrame.setIconImage(ImageIO.read(getClass().getResourceAsStream("/g.png")));
				} catch (IOException expc) {
					// TODO Auto-generated catch block
					expc.printStackTrace();
				}
				JLabel label = new JLabel("omar_ghannou@live.fr");
				contactFrame.getContentPane().add(label, BorderLayout.NORTH);
				contactFrame.setVisible(true);	
				
			}
		});

		editor.mntmAbout.addActionListener(new ActionListener() {
			JFrame aboutFrame;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				aboutFrame = new JFrame();
				aboutFrame.setTitle("About - Geditor");
				aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				aboutFrame.setBounds(150, 150, 400, 240);
				try {
					aboutFrame.setIconImage(ImageIO.read(getClass().getResourceAsStream("/g.png")));
				} catch (IOException expc) {
					// TODO Auto-generated catch block
					expc.printStackTrace();
				}
				JLabel label = new JLabel("this editor is made by omar ghannou as 'Java TP' Project");
				aboutFrame.getContentPane().add(label, BorderLayout.NORTH);
				aboutFrame.setVisible(true);	
				
			}
		});
		
		editor.editorPane.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(fileChanged) return;
				fileChanged = true;
				setSave(false);
				geditor.setTitle("*"+geditor.getTitle());  
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(fileChanged) return;
				fileChanged = true;
				setSave(false);
				geditor.setTitle("*"+geditor.getTitle());  

			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(fileChanged) return;
				fileChanged = true;
				setSave(false);
				geditor.setTitle("*"+geditor.getTitle());  
			}
		});
		
		editor.slider.addChangeListener(this);
		
		editor.ColorButton.addActionListener(new ActionListener() {
			JFrame ColorFrame;
			@Override
			public void actionPerformed(ActionEvent e) {
				ColorFrame = new JFrame();
				ColorFrame.setTitle("Color Selector - Geditor");
				ColorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				ColorFrame.setBounds(150, 150, 400, 240);
				try {
					ColorFrame.setIconImage(ImageIO.read(getClass().getResourceAsStream("/g.png")));
				} catch (IOException expc) {
					// TODO Auto-generated catch block
					expc.printStackTrace();
				}
				JColorChooser cchooser = new JColorChooser();
				cchooser.setColor(geditor.defaultColor);
				ColorFrame.add(cchooser);
				ColorFrame.setVisible(true);
				ColorFrame.addWindowListener(new WindowAdapter() {
				    @Override
				    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				    	geditor.defaultColor = cchooser.getColor();
						geditor.ChangeTextColor(geditor.defaultColor);
						geditor.ColorButton.setBackground(geditor.defaultColor);
						super.windowClosing(windowEvent);
				    }
				} );
				
			}
		}); 
		
		editor.mntmOpenImage.addActionListener(new ActionListener() {
			JFrame ImageFrame;
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageFrame = new JFrame();
				ImageFrame.setTitle("Image - Geditor");
				ImageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				ImageFrame.setBounds(150, 150, 800, 480);
				try {
					ImageFrame.setIconImage(ImageIO.read(getClass().getResourceAsStream("/g.png")));
				} catch (IOException expc) {
					// TODO Auto-generated catch block
					expc.printStackTrace();
				}
				MyCanvas canvas = new MyCanvas(openImage());
				ImageFrame.add(canvas);
				ImageFrame.setVisible(true);
				
			}
		});
		
		editor.btnOpenImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chooser.setDialogTitle("Open Image...");  
				chooser.setApproveButtonText("Open this");   
				chooser.setApproveButtonMnemonic(KeyEvent.VK_O);  
				chooser.setApproveButtonToolTipText("Click me to open the selected file.!");  
				  
				File temp=null;  
				do  
				{  
					if(chooser.showOpenDialog(geditor)!=JFileChooser.APPROVE_OPTION)  
						return;  
					temp=chooser.getSelectedFile();  
				  
					if(temp.exists())   break;  
				  
					JOptionPane.showMessageDialog(geditor,  
				    "<html>"+temp.getName()+"<br>file not found.<br>"+  
				    "Please verify the correct file name was given.<html>",  
				    "Open", JOptionPane.INFORMATION_MESSAGE);  
				  
				} while(true); 
				

				FileEditor.class.getClassLoader();
				String imgsrc = ClassLoader.getSystemResource(temp.getName()).toString();
				
				String textString = geditor.editorPane.getText();
				String before = textString.substring(0, geditor.editorPane.getCaretPosition());
				String after = textString.substring(geditor.editorPane.getCaretPosition()+1);
				
				String res = "<p>" + before + " <img src='"+ imgsrc +"' width=200 height=200/> " + after +  "</p>";
				
	                
				geditor.editorPane.setText(res);

				
			}
		});
		
	}
	
	boolean saveFile(File temp)  
	{  
		FileWriter fout=null;  
		try  
		{  
			fout=new FileWriter(temp);  
			fout.write(geditor.editorPane.getText());  
		}  
		catch(IOException ioe){
			updateStatus(temp,false);
			return false;
			}  
		finally  
		{
			try{fout.close();}
			catch(IOException excp){}
		}  
			updateStatus(temp,true);  
		return true;  
	}  
	

	boolean saveThisFile()  
	{  
		
		if(!newFileFlag)  
		{return saveFile(fileRef);}  
		
		return saveAsFile();  
	}  
		
	boolean saveAsFile()  
	{  
		File temp=null;  
		chooser.setDialogTitle("Save As...");  
		chooser.setApproveButtonText("Save Now");   
		chooser.setApproveButtonMnemonic(KeyEvent.VK_S);  
		chooser.setApproveButtonToolTipText("Click me to save!");  
		
		do  
		{  
			if(chooser.showSaveDialog(this.geditor)!=JFileChooser.APPROVE_OPTION)  
			return false;
			temp=chooser.getSelectedFile();
			if(!temp.exists()) break;  
			if(JOptionPane.showConfirmDialog(  
			this.geditor,"<html>"+temp.getPath()+" already exists.<br>Do you want to replace it?<html>",  
			"Save As",JOptionPane.YES_NO_OPTION  
			)==JOptionPane.YES_OPTION)  
			break;  
		}
		while(true);  
			
		return saveFile(temp);  
			
	}  
	
	
	boolean openFile(File temp)  
	{  
		FileInputStream fin=null;  
		BufferedReader din=null;  
		Document document = this.geditor.editorPane.getDocument();
	try  
	{  
		fin=new FileInputStream(temp);  
		din=new BufferedReader(new InputStreamReader(fin));  
		String str=" ";  
		while(str!=null)  
		{  
		str=din.readLine();  
		if(str==null)  
		break;  
		document.insertString(document.getLength(),(str+"\n"), null); 
		}  
	}
	catch(IOException | BadLocationException ioe)
	{
		updateStatus(temp,false);
		return false;
	}  
	finally  
	{
		try{din.close();fin.close();}
		catch(IOException excp){}
	}  
		updateStatus(temp,true);  
		this.geditor.editorPane.setCaretPosition(0);  
		return true;  
	}  
	
	
	void openFile()  
	{  
		if(!confirmSave()) return;  
		chooser.setDialogTitle("Open File...");  
		chooser.setApproveButtonText("Open this");   
		chooser.setApproveButtonMnemonic(KeyEvent.VK_O);  
		chooser.setApproveButtonToolTipText("Click me to open the selected file.!");  
		  
		File temp=null;  
		do  
		{  
			if(chooser.showOpenDialog(this.geditor)!=JFileChooser.APPROVE_OPTION)  
				return;  
			temp=chooser.getSelectedFile();  
		  
			if(temp.exists())   break;  
		  
			JOptionPane.showMessageDialog(this.geditor,  
		    "<html>"+temp.getName()+"<br>file not found.<br>"+  
		    "Please verify the correct file name was given.<html>",  
		    "Open", JOptionPane.INFORMATION_MESSAGE);  
		  
		} while(true);  
		  
		this.geditor.editorPane.setText("");  
		  
		if(!openFile(temp))  
		{  
		    fileName="Untitled"; saved=true;   
		    this.geditor.setTitle(fileName+" - "+applicationTitle);  
		}  
		if(!temp.canWrite())  
		    newFileFlag=true;  
		  
	}  

	
	boolean confirmSave()  
	{  
	String strMsg="<html>The text in the "+fileName+" file has been changed.<br>"+  
	    "Do you want to save the changes?<html>";  
	if(!saved)  
	{  
		int x=JOptionPane.showConfirmDialog(this.geditor,strMsg,applicationTitle,  
		JOptionPane.YES_NO_CANCEL_OPTION);  
		  
		if(x==JOptionPane.CANCEL_OPTION) return false;  
		if(x==JOptionPane.YES_OPTION && !saveAsFile()) return false;  
	}  
	return true;  
	}  
	
	
	String openImage()  
	{  
		chooser.setDialogTitle("Open Image...");  
		chooser.setApproveButtonText("Open this");   
		chooser.setApproveButtonMnemonic(KeyEvent.VK_O);  
		chooser.setApproveButtonToolTipText("Click me to open the selected Image.!");  
		  
		File temp=null;  
		do  
		{  
			if(chooser.showOpenDialog(this.geditor)!=JFileChooser.APPROVE_OPTION)  
				return null;  
			temp=chooser.getSelectedFile();  
			if(temp.exists())   break;  
			JOptionPane.showMessageDialog(this.geditor,  
		    "<html>"+temp.getName()+"<br>Image not found.<br>"+  
		    "Please verify the correct Image name was given.<html>",  
		    "Open", JOptionPane.INFORMATION_MESSAGE);  
		  
		} while(true);  
		
		
		String pathString = temp.getPath();
		pathString.concat("\\");
		pathString.concat(temp.getName());
		
		String relative = new File(".").toURI().relativize(new File(pathString).toURI()).getPath();
		System.out.println(relative);
		
		return relative;
		  
	}  
	
	
	void updateStatus(File file,Boolean flag) {
		 if(flag)  
		 {   
			 fileChanged = false;
			 this.saved=true;  
			 fileName=new String(file.getName());  
			 if(!file.canWrite())  
			     {fileName+="(Read only)"; newFileFlag=true;}  
			 fileRef=file;
			 geditor.setTitle(fileName + " - "+applicationTitle);  
			 geditor.statusBar.setText("File : "+file.getPath()+" saved/opened successfully.");  
			 newFileFlag=false;  
		 }   
		 else  
		 {   
			 geditor.statusBar.setText("Failed to save/open : "+ file.getPath());
		 }   
	}
	
	void newFile()  
	{  
		if(!confirmSave()) return;  
		  
		this.geditor.editorPane.setText("");  
		fileName=new String("Untitled");  
		fileRef=new File(fileName);  
		saved=true;  
		newFileFlag=true;  
		this.geditor.setTitle(fileName+" - "+applicationTitle);  
	}  
	
	void CopyToClipBoard() {
	    StringSelection data = new StringSelection(geditor.editorPane.getSelectedText());
	    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
	    cb.setContents(data, null);
	}
	
	
	void CutToClipBoard() {
	    StringSelection data = new StringSelection(geditor.editorPane.getSelectedText());
	    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
	    cb.setContents(data, null);
	    
	    String textString = geditor.editorPane.getText();
	    String beforeString = new String();
	    String afterString = new String();
	    if(geditor.editorPane.getSelectionStart() != 0) {
	    beforeString = textString.substring(0,geditor.editorPane.getSelectionStart());
	    }
	    if(geditor.editorPane.getSelectionEnd() != textString.length()) {
	    afterString =  textString.substring(geditor.editorPane.getSelectionEnd(),textString.length()-1);
	    }
	    if(afterString != null && beforeString != null)
	    	geditor.editorPane.setText(beforeString + afterString);
	    else if(afterString != null)
		    geditor.editorPane.setText(afterString);
	    else if(beforeString != null)
		    geditor.editorPane.setText(beforeString);
	}
	
	
	void PasteFromClipBoard() {
	    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

	    try {
	    Transferable t = cb.getContents(null);
        if (t.isDataFlavorSupported(DataFlavor.stringFlavor))
			try {
				geditor.editorPane.getDocument().insertString(geditor.editorPane.getCaretPosition(),t.getTransferData(DataFlavor.stringFlavor).toString(), null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (BadLocationException | UnsupportedFlavorException e) {
			e.printStackTrace();
		}
	}
	
	
	void DeleteFromText() {
	    
	    String textString = geditor.editorPane.getText();
	    String beforeString = new String();
	    String afterString = new String();
	    if(geditor.editorPane.getSelectionStart() != 0) {
	    beforeString = textString.substring(0,geditor.editorPane.getSelectionStart());
	    }
	    if(geditor.editorPane.getSelectionEnd() != textString.length()) {
	    afterString =  textString.substring(geditor.editorPane.getSelectionEnd(),textString.length()-1);
	    }
	    if(afterString != null && beforeString != null)
	    	geditor.editorPane.setText(beforeString + afterString);
	    else if(afterString != null)
		    geditor.editorPane.setText(afterString);
	    else if(beforeString != null)
		    geditor.editorPane.setText(beforeString);
	}
	
	int FindInText(Sting text,String word, int index) {
	    
        String lowerCaseTextString = geditor.editorPane.getText().toLowerCase(); //to be out
        String lowerCaseWord = word.toLowerCase();

        while(index != -1){
            index = lowerCaseTextString.indexOf(lowerCaseWord, index);
            if (index != -1) {
                return index;
            }
        }
        return -1;
	}
	
	void findNext(String word) {
		

	}
	
	
	@Override
	public void stateChanged(ChangeEvent e) {

		if(e.getSource().equals(geditor.slider)) {
			geditor.ChangeTextSize(geditor.slider.getValue());
		}
		
	}

	
	
}


class MyCanvas extends Canvas{ 
	
	String path;
	public MyCanvas(String _path) {
		path = _path;
	}
    
    public void paint(Graphics g) {  
        Image i;
		if(path != null) {
			Toolkit t=Toolkit.getDefaultToolkit();  
			i=t.getImage(path);  
			g.drawImage(i,5,5,this);  
		}
    } 
}

