package com.ludo.safetynetalerts.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;

/**
 * AgeCalculator to determine person's age.
 *
 * @author Ludovic Allegaert
 */

public class AgeCalculator {

    /**
     * Logger class.
     */
    private static final Logger logger = LogManager.getLogger(AgeCalculator.class);

    /**
     *
     * @param birthDate
     * @param currentDate
     * @return age
     */

    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {

        int age;

        if (birthDate != null && birthDate.isBefore(LocalDate.now())) {
            age= Period.between(birthDate, currentDate).getYears();
        } else {
            logger.error("Invalid birthdate "+ birthDate);
            return 0;
        }

        return  age;
    }

}
