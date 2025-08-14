package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	// Screen settings
	final int ORIGINAL_TILE_SIZE = 16;
	final int SCALE = 3;
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
	final int MAX_SCREEN_COL = 16;
	final int MAX_SCREEN_ROW = 12;
	final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
	final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
	
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this, keyH);
	
	// Set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setBackground(Color.black);
		setDoubleBuffered(true);
		addKeyListener(keyH);
		setFocusable(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		double drawInterval = 1e9 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		player.draw(g2);
		g2.dispose();
	}
}