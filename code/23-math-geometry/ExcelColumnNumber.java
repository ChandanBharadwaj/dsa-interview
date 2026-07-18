/**
 * LeetCode 171 — Excel Sheet Column Number. Bijective base-26 parsing
 * (digits 1..26, no zero). Mirrors pages/23-math-geometry.html.
 */
public class ExcelColumnNumber {

    public static int titleToNumber(String columnTitle) {
        int result = 0;
        for (char c : columnTitle.toCharArray()) {
            // Standard positional parsing: shift what we have by one "digit"
            // (x26), then add the new digit. A=1 .. Z=26 — no zero digit.
            result = result * 26 + (c - 'A' + 1);
        }
        return result;
    }

    public static void main(String[] args) {
        assert titleToNumber("A") == 1;
        assert titleToNumber("Z") == 26;
        assert titleToNumber("AA") == 27;
        assert titleToNumber("AB") == 28;
        assert titleToNumber("ZY") == 701;
        System.out.println("ExcelColumnNumber: OK");
    }
}
