import models.World;

public class Controller {

    private static Viewer viewer = new Viewer();
    private static World world = new World();

    public static void main(String[] args) {
        String input = viewer.getInput();
        //unknown end command(?)
        while (true) {
            if (input.equals(IOFormats.NEWGAME_FORMAT)){
                newGame();
            }
            else if (input.equals(IOFormats.LOAD_PATH_FORMAT)) {
                String path = input.split("\\s")[1];
                try {
                    loadGame(path);
                }
                catch (Exception e) {
                    viewer.printError(IOFormats.NO_VALID_FILE_ERROR);
                }
            }
            else if (input.equals(IOFormats.SAVE_FORMAT)) {
                String path = input.split("\\s")[1];
                String name = input.split("\\s")[2];
                try {
                    saveGame(path, name);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (input.equals(IOFormats.TURN_FORMAT)) {
                int turns = Integer.parseInt(input.split("\\s")[1]);
                turn(turns);
            }
            else if (input.equals(IOFormats.SHOW_BUILDINGS_FORMAT)) {
                showBuildings();
            }
            else if (input.equals(IOFormats.SHOW_RESOURCES_FORMAT)) {
                showResources();
            }
            else if (input.equals(IOFormats.SHOW_MENU_FORMAT)) {
                viewer.printMessage(IOFormats.VILLAGE_PRESENCE_FORMAT);
            }
            else if (input.equals(IOFormats.WHERE_AM_I_FORMAT)) {
                whereAmI();
            }
            else if (input.equals(IOFormats.ATTACK_FORMAT)) {
                attack();
            }
        }
    }

    public static void newGame() {}

    public static void loadGame(String path) {
    }

    public static void saveGame(String path, String name) {

    }

    public static void turn(int n) {

    }

    public static void showBuildings() {
        //TODO buildings menus should be handled here
    }

    public static void showResources() {

    }

    public static void showInfo() {

    }

    public static void upgrade() {

    }

    public static void back() {

    }

    public static void showMenu() {

    }

    public static void whereAmI() {

    }

    public static void attack() {

    }


}
