import java.util.*;

public class MultiplyStrings {

    // Schoolbook multiplication on digit arrays: digit i × digit j lands in
    // positions i+j (carry) and i+j+1 (unit) of the result.
    public static String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";
        int m = num1.length(), n = num2.length();
        int[] result = new int[m + n];              // product has at most m+n digits

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int prod = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int posLow = i + j + 1, posHigh = i + j;
                int sum = prod + result[posLow];    // add into the unit slot
                result[posLow] = sum % 10;
                result[posHigh] += sum / 10;        // carry into the next slot
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int d : result)
            if (!(sb.length() == 0 && d == 0))      // skip leading zeros
                sb.append(d);
        return sb.toString();
    }

    public static void main(String[] args) {
        assert multiply("2", "3").equals("6");
        assert multiply("123", "456").equals("56088");
        assert multiply("0", "52").equals("0");
        assert multiply("999", "999").equals("998001");
        Random r = new Random(29);
        for (int t = 0; t < 300; t++) {             // cross-check vs long math
            long a = r.nextInt(1_000_000), b = r.nextInt(1_000_000);
            assert multiply(String.valueOf(a), String.valueOf(b))
                    .equals(String.valueOf(a * b));
        }
        System.out.println("MultiplyStrings: OK");
    }
}
