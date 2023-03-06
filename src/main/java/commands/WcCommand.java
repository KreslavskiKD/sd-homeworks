package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public void run(String params) {
        Path path = Path.of(params);
        if (!Files.exists(path)) {
            System.out.println("File doesn't exist");
            fileStat = null;
            return;
        }
        File file = new File(params);
        long byteCount = file.length();
        long lineCount = 0;
        long wordCount = 0;
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
