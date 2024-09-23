package com.Mindhubcohort55.Homebanking.utils;

import org.springframework.stereotype.Component;

@Component
public class CardNumberGenerator {

    public static String makeCardNumber() {

        final StringBuilder cardNumberBuilder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            String randomNumbers = String.format("%04d", (int) (Math.random() * (9999 - 1) + 1));
            cardNumberBuilder.append(randomNumbers);
            if (i < 3) {
                cardNumberBuilder.append("-");
            }
        }
        return cardNumberBuilder.toString();
    }
}
