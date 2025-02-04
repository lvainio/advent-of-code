package com.example.day22_2024;

import java.util.ArrayList;
import java.util.List;

public class Hasher {

    public List<SecretPriceChange> getSecretsPricesChanges(long secret, int n) {
        List<SecretPriceChange> secretsPricesChanges = new ArrayList<>();
        secretsPricesChanges.add(
            new SecretPriceChange(secret, secret%10, Long.MIN_VALUE));

        for (int i = 0; i < n; i++) {
            long previous = secret;

            secret = prune(mix(secret, 64L * secret));
            secret = prune(mix(secret, secret / 32L));
            secret = prune(mix(secret, 2048L * secret));

            secretsPricesChanges.add(
                new SecretPriceChange(secret, secret%10, (secret%10) - (previous%10)));
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
