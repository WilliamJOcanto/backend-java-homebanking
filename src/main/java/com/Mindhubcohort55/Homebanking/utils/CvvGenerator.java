package com.Mindhubcohort55.Homebanking.utils;

import com.Mindhubcohort55.Homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CvvGenerator {

    @Autowired
    private static CardRepository cardRepository;

    public CvvGenerator(CardRepository cardRepository) {
        CvvGenerator.cardRepository = cardRepository;
    }

    public static String cvvNumber(){

        String cvvNumber = String.format("%03d", (int)(Math.random() * (1000 - 1) + 1));


        return cvvNumber;
    }
}
