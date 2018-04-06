import java.util.Scanner;

public class Viewer {
    private Scanner scanner = new Scanner(System.in);

    public void print(String line) {
        System.out.println(line);
    }

    public String getInput() {
        return scanner.nextLine();
    }
}
