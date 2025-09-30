package me.vainio.aoc.cache;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

public class AocCache {
    
    private final Path cacheDir;

    public AocCache() {
        this.cacheDir = Path.of(System.getProperty("user.home"), ".aoc_cache");
    }

    public AocCache(Path cacheDir) {
        this.cacheDir = cacheDir;
    }
    
    public String getInput(int year, int day) {
        return read(cachePath(year, day, "input.txt"));
    }

    public void saveInput(int year, int day, String input) {
        write(cachePath(year, day, "input.txt"), input);
    }

    public String getAnswer(int year, int day, int part) {
        return read(cachePath(year, day, "part" + part + ".txt"));
    }

    public void saveAnswer(int year, int day, int part, String answer) {
        write(cachePath(year, day, "part" + part + ".txt"), answer);
    }

    private Path cachePath(int year, int day, String fileName) {
        return this.cacheDir
            .resolve(String.valueOf(year))
            .resolve(String.format("%02d", day))
            .resolve(fileName);
    }

    private String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + path, e);
        }
    }

    private void write(Path path, String contents) {
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
