package com.Mindhubcohort55.Homebanking.utils;

import org.springframework.stereotype.Component;

@Component
public class PaymentRateCalculator {

    public static double createPaymentRate(Integer payment){
         double paymentRate;
         if(payment == 12){
             paymentRate = 0.20;
         }else if(payment > 12){
             paymentRate = 0.25;
         }else {
             paymentRate = 0.15;
         }
         return paymentRate;
    }
}
