package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class CatCommand implements Command {
    private final static String NEW_LINE = System.lineSeparator();
    private String result = "";

    @Override
    public void run(String params) {
        Scanner scanner;
        if (params.isEmpty()) {
            scanner = new Scanner(System.in);
        } else {
            try {
                scanner = new Scanner(new File(params));
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                result = "";
                return;
            }
        }

        StringBuilder builder = new StringBuilder();
        String currentLine;
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            if (!Objects.equals(currentLine, "catExit")) {
                if (params.isEmpty()) {
                    System.out.println(currentLine);
                } else {
                    builder.append(currentLine).append(NEW_LINE);
                }
            } else {
                break;
            }
        }

        result = builder.toString();
    }

    @Override
    public Boolean returnsResult() {
        return !Objects.equals(result, "");
    }

    @Override
    public String getResult() {
        return result;
    }
}
