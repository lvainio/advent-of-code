package leo.aoc.year2015.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private final Map<String, HashSet<String>> replacements;

    private final String originalMolecule;

    public Solver(String input) {
        super(input);

        String parts[] = input.split("\r?\n\r?\n");
        this.replacements = parts[0]
                .lines()
                .map(line -> line.split("\\s+"))
                .collect(Collectors.groupingBy(
                        arr -> arr[0],
                        Collectors.mapping(arr -> arr[2], Collectors.toCollection(HashSet::new))));

        this.originalMolecule = parts[1];
    }

    @Override
    public String solvePart1() {
        HashSet<String> molecules = new HashSet<>();
        this.replacements.forEach((key, replacements) -> replacements
                .forEach(replacement -> molecules.addAll(
                        replace(this.originalMolecule, key, replacement))));
        return Integer.toString(molecules.size());
    }

    @Override
    public String solvePart2() {
        HashMap<String, String> reverseReplacements = new HashMap<>();
        for (String key : this.replacements.keySet()) {
            for (String replacement : this.replacements.get(key)) {
                reverseReplacements.put(replacement, key);
            }
        }

        List<Map.Entry<String, String>> sortedReplacements;
        sortedReplacements = new ArrayList<>(reverseReplacements.entrySet());
        sortedReplacements.sort((a, b) -> b.getKey().length() - a.getKey().length());

        int steps = 0;
        String molecule = this.originalMolecule;
        while (!molecule.equals("e")) {
            for (Map.Entry<String, String> entry : sortedReplacements) {
                if (molecule.contains(entry.getKey())) {
                    molecule = molecule.replaceFirst(entry.getKey(), entry.getValue()); 
                    steps++;
                    break;
                }
            }
        }
        return Integer.toString(steps);
    }

    private HashSet<String> replace(String molecule, String key, String replacement) {
        List<Integer> indicesOfKey = findAllIndicesOfKey(molecule, key);
        HashSet<String> newMolecules = new HashSet<>();
        indicesOfKey.forEach(i -> {
            String prefix = molecule.substring(0, i);
            String suffix = molecule.substring(i + key.length(), molecule.length());
            newMolecules.add(prefix + replacement + suffix);
        });
        return newMolecules;
    }

    private List<Integer> findAllIndicesOfKey(String molecule, String key) {
        return IntStream.range(0, molecule.length() - key.length() + 1)
                .filter(i -> molecule.startsWith(key, i))
                .boxed()
                .collect(Collectors.toList());
    }
}
