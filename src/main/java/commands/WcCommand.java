package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;

class FileStat {
    long byteCount;
    long lineCount;
    long wordCount;

    public FileStat(long byteCount, long lineCount, long wordCount) {
        this.byteCount = byteCount;
        this.lineCount = lineCount;
        this.wordCount = wordCount;
    }

    public String toString() {
        return lineCount + " " + wordCount + " " + byteCount + System.lineSeparator();
    }
}

public class WcCommand implements Command {
    FileStat fileStat;

    @Override
    public void run(String params, boolean piped) {
        long byteCount;
        long lineCount = 0;
        long wordCount = 0;
        if (piped) {
            byteCount = params.getBytes().length;
            lineCount = params.lines().count();
            wordCount = Arrays.stream(params.split("\\s+")).count();
            fileStat = new FileStat(byteCount, lineCount, wordCount);
            return;
        }
        Path path;
        try {
            path = Path.of(params);
        } catch (InvalidPathException e) {
            System.out.println(e.getMessage());
            fileStat = null;
            return;
        }
        if (!Files.exists(path)) {
            System.out.println("File doesn't exist");
            fileStat = null;
            return;
        }
        File file = new File(params);
        byteCount = file.length();
        try (var stream  = Files.lines(path)) {
            lineCount = stream.count();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try (var stream  = Files.lines(path)) {
            wordCount = stream.flatMap(line -> Arrays.stream(line.split("\\s+"))).count();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        fileStat = new FileStat(byteCount, lineCount, wordCount);
    }

    @Override
    public Boolean returnsResult() {
        return fileStat != null;
    }

    @Override
    public String getResult() {
        return fileStat.toString();
    }
}
