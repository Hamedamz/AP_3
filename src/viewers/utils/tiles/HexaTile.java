package viewers.utils.tiles;

import javafx.scene.layout.GridPane;

public class HexaTile extends GridPane {
    private int mapX;
    private int mapY;

    public HexaTile(int mapX, int mapY) {
        super();
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                PutTroopsTile putTroopsTile = new PutTroopsTile(i + mapX * 4, j + mapY * 4);
                this.add(putTroopsTile, i, j);
            }
        }
    }

}
