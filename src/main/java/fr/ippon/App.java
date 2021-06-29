package fr.ippon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            Path startingPosPath = Paths.get(args[0]);
            List<String> fileLines = Files.readAllLines(startingPosPath);

            GameLife gameLife = new GameLife(fileLines);

            System.out.println(gameLife.getNextGenerationOutput());
        }
    }
}
