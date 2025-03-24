package com.quodex.JobSpark.utility;

public class Data {
    public static String getMessageBody(String otp, String user) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>JobSpark - Secure OTP</title>\n" +
                "    <style>\n" +
                "        * {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            box-sizing: border-box;\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "        }\n" +
                "        body {\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "            background-color: #f1f8e9;\n" +
                "        }\n" +
                "        .otp-container {\n" +
                "            background: #fff;\n" +
                "            padding: 30px;\n" +
                "            border-radius: 12px;\n" +
                "            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);\n" +
                "            text-align: center;\n" +
                "            width: 420px;\n" +
                "        }\n" +
                "        .otp-header {\n" +
                "            background: #2e7d32;\n" +
                "            color: #fff;\n" +
                "            padding: 18px;\n" +
                "            border-radius: 10px 10px 0 0;\n" +
                "            font-size: 22px;\n" +
                "            font-weight: bold;\n" +
                "            letter-spacing: 1px;\n" +
                "        }\n" +
                "        .otp-body {\n" +
                "            padding: 25px;\n" +
                "        }\n" +
                "        .otp-body p {\n" +
                "            color: #444;\n" +
                "            font-size: 16px;\n" +
                "            margin-bottom: 18px;\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "        .otp-code {\n" +
                "            font-size: 30px;\n" +
                "            font-weight: bold;\n" +
                "            color: #2e7d32;\n" +
                "            letter-spacing: 4px;\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "        .note {\n" +
                "            font-size: 14px;\n" +
                "            color: #555;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            margin-top: 25px;\n" +
                "            font-size: 12px;\n" +
                "            color: #777;\n" +
                "            border-top: 1px solid #ddd;\n" +
                "            padding-top: 12px;\n" +
                "        }\n" +
                "    </style>" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"otp-container\">\n" +
                "        <div class=\"otp-header\">Secure One-Time Password</div>\n" +
                "        <div class=\"otp-body\">\n" +
                "            <p>Dear <strong>"+user+"</strong>,</p>\n" +
                "            <p>Your secure OTP for verification is:</p>\n" +
                "            <div class=\"otp-code\">"+otp+"</div>\n" +
                "            <p class=\"note\">This OTP is valid for 10 minutes. Please do not share it with anyone for security reasons.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">&copy; 2025 JobSpark. All rights reserved. Protect your credentials.</div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }
}
