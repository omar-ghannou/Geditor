package com.omarghannou;
import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Geditor frame = new Geditor();
					FileEditor editor = new FileEditor(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
