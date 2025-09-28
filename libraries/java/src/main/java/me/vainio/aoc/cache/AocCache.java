package me.vainio.aoc.cache;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

public class AocCache {

    private static final String CACHE_DIR = ".aoc_cache";

    public static String getInput(int year, int day) {
        return read(cachePath(year, day, "input.txt"));
    }

    public static void saveInput(int year, int day, String input) {
        write(cachePath(year, day, "input.txt"), input);
    }

    public static String getAnswer(int year, int day, int part) {
        return read(cachePath(year, day, "part" + part + ".txt"));
    }

    public static void saveAnswer(int year, int day, int part, String answer) {
        write(cachePath(year, day, "part" + part + ".txt"), answer);
    }

    private static Path cachePath(int year, int day, String fileName) {
        return Path.of(
            System.getProperty("user.home"),
            CACHE_DIR,
            String.valueOf(year),
            String.format("%02d", day),
            fileName
        );
    }

    private static String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + path, e);
        }
    }

    private static void write(Path path, String contents) {
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(
                path,
                contents,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + path, e);
        }
    }
}
