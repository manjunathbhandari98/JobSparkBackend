package com.quodex.JobSpark.utility;

import java.security.SecureRandom;

import java.security.SecureRandom;

public class OTPGenerator {

    /**
     * Generates a 6-digit One-Time Password (OTP).
     *
     * @return A string representing a randomly generated 6-digit OTP.
     */
    public static String generateOTP() {
        StringBuilder otp = new StringBuilder(); // StringBuilder for efficient string concatenation
        SecureRandom random = new SecureRandom(); // SecureRandom for cryptographic security

        // Generate a 6-digit OTP by appending random digits (0-9)
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10)); // nextInt(10) generates a number between 0 and 9
        }

        return otp.toString(); // Convert StringBuilder to a String and return
    }
}

