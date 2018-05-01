package viewers;

import java.util.Scanner;

public class BasicViewer {
    protected Scanner scanner = new Scanner(System.in);

    public void printErrorMessage(String error) {
        System.out.println(error);
    }

    public void printInformation(String info) {
        System.out.println(info);
    }

    public String getInput() {
        return scanner.nextLine();
    }
}
