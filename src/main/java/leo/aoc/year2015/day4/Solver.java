package leo.aoc.year2015.day4;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

    public Solver(String input) {
        super(input);
    }

    @Override
    public String solvePart1() {
        try {
            int i = 1;
            while (true) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                String input = this.input + i;
                md.update(input.getBytes());
                byte[] hashBytes = md.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }
                String hexString = sb.toString();
                if (hexString.startsWith("00000")) {
                    return Integer.toString(i);
                }
                i++;
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MD5 algorithm not found");
        }
        throw new IllegalStateException("Could not solve part1");
    }

    @Override
    public String solvePart2() {
        try {
            int i = 1;
            while (true) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                String input = this.input + i;
                md.update(input.getBytes());
                byte[] hashBytes = md.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }
                String hexString = sb.toString();
                if (hexString.startsWith("000000")) {
                    return Integer.toString(i);
                }
                i++;
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MD5 algorithm not found");
        }
        throw new IllegalStateException("Could not solve part2");   
    } 
}
