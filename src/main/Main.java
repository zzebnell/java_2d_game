package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("linux")) {
			System.setProperty("sun.java2d.opengl", "true");
		}
		JFrame window = new JFrame();
		GamePanel gamePanel = new GamePanel();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Game");
		
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
	}
}