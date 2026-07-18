import java.util.*;

public class RestoreIPAddresses {

    // Place 3 dots = choose 4 segments, each 1-3 digits, value <= 255, no
    // leading zeros. Depth = which segment we're building.
    public static List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        if (s.length() >= 4 && s.length() <= 12)     // quick impossibility cut
            backtrack(s, 0, 0, new StringBuilder(), res);
        return res;
    }

    private static void backtrack(String s, int pos, int segment,
                                  StringBuilder path, List<String> res) {
        if (segment == 4) {
            if (pos == s.length())                   // used every digit
                res.add(path.substring(0, path.length() - 1));  // drop last dot
            return;
        }
        for (int len = 1; len <= 3 && pos + len <= s.length(); len++) {
            String part = s.substring(pos, pos + len);
            if (part.length() > 1 && part.charAt(0) == '0')
                break;                               // leading zero: "01" invalid
            if (Integer.parseInt(part) > 255)
                break;                               // longer is only bigger
            int mark = path.length();
            path.append(part).append('.');           // choose
            backtrack(s, pos + len, segment + 1, path, res);
            path.setLength(mark);                    // unchoose
        }
    }

    public static void main(String[] args) {
        assert restoreIpAddresses("25525511135")
                .equals(Arrays.asList("255.255.11.135", "255.255.111.35"));
        assert restoreIpAddresses("0000").equals(List.of("0.0.0.0"));
        assert restoreIpAddresses("101023").size() == 5;
        assert restoreIpAddresses("1111111111111").isEmpty();   // 13 digits
        System.out.println("RestoreIPAddresses: OK");
    }
}
