package leo.aoc.year2015.day19;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    private record Node(String molecule, int steps) {
    }

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
        return "WRONG ANSWER";

        // return Integer.toString(bfs());
    }

    public int bfs() {
        HashSet<String> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();

        String startMolecule = "e";
        Node startNode = new Node(startMolecule, 0);

        visited.add(startMolecule);
        queue.offer(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            String currentMolecule = currentNode.molecule();
            int numSteps = currentNode.steps();
  
            if (currentMolecule.equals(this.originalMolecule)) {
                return numSteps;
            }

            for (String key : this.replacements.keySet()) {
                for (String replacement : this.replacements.get(key)) {
                    HashSet<String> newMolecules = replace(currentMolecule, key, replacement);
                    for (String newMolecule : newMolecules) {
                        if (!visited.contains(newMolecule)) {
                            queue.offer(new Node(newMolecule, numSteps + 1));
                            visited.add(newMolecule);
                        }
                    }
                }
            }
        }
        throw new IllegalStateException("Could not make the molecule!");
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
