package leo.aoc.year2024.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import leo.aoc.AbstractSolver;

public class Solver extends AbstractSolver {

  private record SecretPriceChange(long secret, long price, long change) {}

  public record ChangeSequence(long c1, long c2, long c3, long c4) {}

  private List<Long> numbers = null;

  public Solver(String input) {
    super(input);

    this.numbers = input.lines().map(line -> Long.parseLong(line)).collect(Collectors.toList());
  }

  @Override
  public String solvePart1() {
    Hasher hasher = new Hasher();
    List<List<SecretPriceChange>> mem = new ArrayList<>();
    long total = 0;
    for (Long secret : numbers) {
      List<SecretPriceChange> secretsPricesChanges = hasher.getSecretsPricesChanges(secret, 2000);
      mem.add(secretsPricesChanges);

      total += secretsPricesChanges.get(secretsPricesChanges.size() - 1).secret();
    }
    return Long.toString(total);
  }

  @Override
  public String solvePart2() {
    Hasher hasher = new Hasher();
    List<List<SecretPriceChange>> mem = new ArrayList<>();
    for (Long secret : numbers) {
      List<SecretPriceChange> secretsPricesChanges = hasher.getSecretsPricesChanges(secret, 2000);
      mem.add(secretsPricesChanges);
    }
    HashMap<Integer, HashMap<ChangeSequence, Long>> priceMap = new HashMap<>();
    for (int buyer = 0; buyer < mem.size(); buyer++) {
      List<SecretPriceChange> secretsPricesChanges = mem.get(buyer);
      priceMap.put(buyer, new HashMap<>());
      for (int i = 0; i <= secretsPricesChanges.size() - 4; i++) {
        long c1 = secretsPricesChanges.get(i).change();
        long c2 = secretsPricesChanges.get(i + 1).change();
        long c3 = secretsPricesChanges.get(i + 2).change();
        long c4 = secretsPricesChanges.get(i + 3).change();
        ChangeSequence cs = new ChangeSequence(c1, c2, c3, c4);
        if (!priceMap.get(buyer).containsKey(cs)) {
          priceMap.get(buyer).put(cs, secretsPricesChanges.get(i + 3).price());
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
    return Long.toString(maxSale);
  }

  private class Hasher {
    public List<SecretPriceChange> getSecretsPricesChanges(long secret, int n) {
      List<SecretPriceChange> secretsPricesChanges = new ArrayList<>();
      secretsPricesChanges.add(new SecretPriceChange(secret, secret % 10, Long.MIN_VALUE));

      for (int i = 0; i < n; i++) {
        long previous = secret;

        secret = prune(mix(secret, 64L * secret));
        secret = prune(mix(secret, secret / 32L));
        secret = prune(mix(secret, 2048L * secret));

        secretsPricesChanges.add(
            new SecretPriceChange(secret, secret % 10, (secret % 10) - (previous % 10)));
      }
      return secretsPricesChanges;
    }

    private long mix(long secret, long value) {
      return secret ^ value;
    }

    private long prune(long secret) {
      return secret % 16777216L;
    }
  }
}
