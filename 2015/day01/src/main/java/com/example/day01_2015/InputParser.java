package com.example.day01_2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class InputParser {

    private List<Token> tokens = null;

    public List<Token> getTokens() {
        return this.tokens;
    }

    public void parseInputFile(String inputFile) {
        InputStream inputStream = InputParser.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream == null) {
            System.err.println("Resource '" + inputFile + "' not found in the classpath.");
            System.exit(1);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            this.tokens =
                    reader.readLine()
                            .chars()
                            .mapToObj(c -> c == '(' ? Token.LPAR : Token.RPAR)
                            .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
