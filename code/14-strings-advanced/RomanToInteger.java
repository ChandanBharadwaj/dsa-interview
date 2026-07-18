import java.util.*;

public class RomanToInteger {

    // One rule covers all subtractive pairs (IV, IX, XL, ...): a symbol
    // smaller than its right neighbor counts NEGATIVE.
    public static int romanToInt(String s) {
        Map<Character, Integer> val = Map.of(
                'I', 1, 'V', 5, 'X', 10, 'L', 50,
                'C', 100, 'D', 500, 'M', 1000);
        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int v = val.get(s.charAt(i));
            if (i + 1 < s.length() && v < val.get(s.charAt(i + 1)))
                total -= v;                // smaller before bigger: subtract
            else
                total += v;
        }
        return total;
    }

    public static void main(String[] args) {
        assert romanToInt("III") == 3;
        assert romanToInt("LVIII") == 58;
        assert romanToInt("MCMXCIV") == 1994;
        assert romanToInt("IX") == 9;
        assert romanToInt("MMMCMXCIX") == 3999;
        System.out.println("RomanToInteger: OK");
    }
}
