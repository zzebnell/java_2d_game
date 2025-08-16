package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];
		mapTileNum = new int[gp.MAX_WORLD_ROW][gp.MAX_WORLD_COL];
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String numbers[];
			for (int i = 0; i < gp.MAX_WORLD_ROW; i++) {
				numbers = br.readLine().split(" ");
				for (int j = 0; j < gp.MAX_WORLD_COL; j++) {
					mapTileNum[i][j] = Integer.parseInt(numbers[j]);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int tileX, tileY;
		for (int i = 0; i < gp.MAX_WORLD_ROW; i++) {
			for (int j = 0; j < gp.MAX_WORLD_COL; j++) {
				tileX = gp.TILE_SIZE * j;
				tileY = gp.TILE_SIZE * i;
				if (!(tileX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
							tileX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
							tileY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
							tileY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY)) {
					continue;
				}
				g2.drawImage(tile[mapTileNum[i][j]].image, tileX - gp.player.worldX + gp.player.screenX, tileY - gp.player.worldY + gp.player.screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
			}
		}
	}
}
