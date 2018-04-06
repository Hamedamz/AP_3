import java.util.Scanner;

public class Viewer {
    private Scanner scanner = new Scanner(System.in);

    public void printError(String error) {
        System.out.println(error);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String getInput() {
        return scanner.nextLine();
    }
}
