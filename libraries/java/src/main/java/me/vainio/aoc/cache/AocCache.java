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

    public AocCache(final Path cacheDir) {
        this.cacheDir = cacheDir;
    }
    
    public String getInput(final int year, final int day) {
        return read(cachePath(year, day, "input.txt"));
    }

    public void saveInput(final int year, final int day, final String input) {
        write(cachePath(year, day, "input.txt"), input);
    }

    public String getAnswer(final int year, final int day, final int part) {
        return read(cachePath(year, day, "part" + part + ".txt"));
    }

    public void saveAnswer(final int year, final int day, final int part, final String answer) {
        write(cachePath(year, day, "part" + part + ".txt"), answer);
    }

    private Path cachePath(final int year, final int day, final String fileName) {
        return this.cacheDir
            .resolve(String.valueOf(year))
            .resolve(String.format("%02d", day))
            .resolve(fileName);
    }

    private String read(final Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + path, e);
        }
    }

    private void write(final Path path, final String contents) {
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
