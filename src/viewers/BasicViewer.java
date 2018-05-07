package viewers;

import controllers.Exceptions.InvalidInputException;

import java.util.Scanner;

public class BasicViewer {
    protected Scanner scanner = new Scanner(System.in);

    public void printErrorMessage(String error) {
        System.out.println(error);
    }

    public void printInformation(String info) {
        System.out.println(info);
    }

    public void printPropertyValue(String property, Number value) {
        System.out.format("%s: %s\n", property, value);
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public void requestForInput(String request) {
        System.out.println(request);
    }

    public boolean getConfirmation() throws InvalidInputException {
        String input = getInput();
        if (input.matches("[yY]")) {
            return true;
        } else if (input.matches("[nN]")) {
            return false;
        }
        throw new InvalidInputException("invalid input");
    }
}
