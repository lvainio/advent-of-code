package com.example.day04_2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day04 {
    public static void main(String[] args) {

        String secret = "iwrupvqb";

        try {
            int i = 1;
            while (true) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                String input = secret + i;
                md.update(input.getBytes());
                byte[] hashBytes = md.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }
                String hexString = sb.toString();
                if (hexString.startsWith("000000")) {
                    System.out.println(i);
                    System.out.println(hexString);
                    break;
                }
                i++;
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MD5 algorithm not found");
        }
    }
}
