import java.util.*;

public class assignment1 {

    public static String alphaToBi(String data) {
        StringBuilder biData = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            int deci = data.charAt(i);
            StringBuilder charBinary = new StringBuilder();

            for (int j = 0; j < 8; j++) {
                charBinary.append(deci % 2);
                deci /= 2;
            }
            biData.append(charBinary.reverse().toString());
        }
        return biData.toString();
    }

    public static String initialPermutation(String data) {
        return new StringBuilder(data).reverse().toString();
    }

    public static String keyCompression(String key16) {
        StringBuilder key12 = new StringBuilder(key16);
        for (int i = 3; i < key16.length(); i += 3) {
            key12.deleteCharAt(i);
        }
        return key12.toString();
    }

    public static String expandPermutation(String rpt) {
        String expandedRpt = rpt.charAt(7) + rpt.substring(0, 4) + rpt.charAt(3) + rpt.substring(4, 7) + rpt.charAt(0);
        return expandedRpt;
    }

    public static String xor(String s, String rpt) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char bit1 = s.charAt(i);
            char bit2 = rpt.charAt(i % rpt.length());

            if (bit1 == bit2) {
                result.append('0');
            } else {
                result.append('1');
            }
        }

        return result.toString();
    }

    public static String sBoxSubstitution(String input) {
        String[] sBox = {
            "1010", "0110", "1001", "0011",
            "0111", "1011", "1000", "1110",
            "0000", "0001", "1111", "1100",
            "1101", "0100", "0010", "0101"
        };
        StringBuilder substituted = new StringBuilder();
        for (int i = 0; i < input.length(); i += 4) {
            if (i + 4 <= input.length()) {
                String temp = input.substring(i, i + 4);
                int index = Integer.parseInt(temp, 2);
                substituted.append(sBox[index]);
            }
        }
        return substituted.toString();
    }

    public static String pBoxPermutation(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i += 2) {
            result.append(input.charAt(i + 1)).append(input.charAt(i));
        }
        return result.toString();
    }

    public static String encrypt(String message, String key) {
        String permutedMessage = initialPermutation(message);
        String LPT = permutedMessage.substring(0, 8);
        String RPT = permutedMessage.substring(8);

        for (int round = 0; round < 4; round++) {
            String expandedRPT = expandPermutation(RPT);
            String xorResult = xor(expandedRPT, key);
            String substituted = sBoxSubstitution(xorResult);
            String permuted = pBoxPermutation(substituted);
            String xorWithLPT = xor(LPT, permuted);

            LPT = RPT;
            RPT = xorWithLPT;
        }

        String combined = LPT + RPT;
        return finalPermutation(combined);
    }

    public static String finalPermutation(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static String hashFunction(String input) {
        return input.substring(0, input.length() - 2);
    }

    public static String keyFunction(String input) {
        return input.substring(0, input.length() - 3);
    }

    public static String generate12BitKey() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            key.append(random.nextInt(2));
        }
        return key.toString();
    }



    public static void main(String[] args) {

        String message = alphaToBi("Hi");
        String key = generate12BitKey();

        System.out.println("Message in binary: " + message);
        System.out.println("Generated 12-bit Key: " + key);
        String encryptedMessage = encrypt(message, key);

        System.out.println("Encrypted Message in binary: " + encryptedMessage);

        String digitalSignature = hashFunction(encryptedMessage);
        System.out.println("Digital Signature: " + digitalSignature);

        String publicKey = keyFunction(encryptedMessage);
        String privateKey = keyFunction(encryptedMessage);
        System.out.println("Public Key: " + publicKey);
        System.out.println("Private Key: " + privateKey);
    }
}
