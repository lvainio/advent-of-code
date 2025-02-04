package com.example.day22_2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day22 {

    public static void main(String[] args) {
        InputParser parser = new InputParser();
        parser.parseInputFile("input.txt");

        List<Long> numbers = parser.getNumbers();
        Hasher hasher = new Hasher();
        List<List<SecretPriceChange>> mem = new ArrayList<>();

        // PART 1
        long total = 0;
        for (Long secret : numbers) {
            List<SecretPriceChange> secretsPricesChanges = hasher.getSecretsPricesChanges(secret, 2000);
            mem.add(secretsPricesChanges);

            total += secretsPricesChanges.get(secretsPricesChanges.size()-1).secret();
        }
        System.out.println("Part1: " + total);

        // PART 2
        HashMap<Integer, HashMap<ChangeSequence, Long>> priceMap = new HashMap<>();
        for (int buyer = 0; buyer < mem.size(); buyer++) {
            List<SecretPriceChange> secretsPricesChanges = mem.get(buyer);
            priceMap.put(buyer, new HashMap<>());
            for (int i = 0; i <= secretsPricesChanges.size()-4; i++) {
                long c1 = secretsPricesChanges.get(i).change();
                long c2 = secretsPricesChanges.get(i+1).change();
                long c3 = secretsPricesChanges.get(i+2).change();
                long c4 = secretsPricesChanges.get(i+3).change();

                ChangeSequence cs = new ChangeSequence(c1, c2, c3, c4);
                if (!priceMap.get(buyer).containsKey(cs)) {
                    priceMap.get(buyer).put(cs, secretsPricesChanges.get(i+3).price());
                }
            }
        }
        int minChange = -9;
        int maxChange = 9;
        long maxSale = Long.MIN_VALUE;
        for (int a = minChange; a <= maxChange; a++) {
            for (int b = minChange; b <= maxChange; b++) {
                for (int c = minChange; c <= maxChange; c++) {
                    for (int d = minChange; d <= maxChange; d++) {
                        long totalSales = 0;
                        for (int buyer = 0; buyer < mem.size(); buyer++) {
                            ChangeSequence cs = new ChangeSequence(a, b, c, d);
                            if (priceMap.get(buyer).containsKey(cs)) {
                                totalSales += priceMap.get(buyer).get(cs);
                            }
                        }
                        maxSale = Math.max(maxSale, totalSales);
                    }
                }
            }
        }
        System.out.println("Part2: " + maxSale);
    }
}
