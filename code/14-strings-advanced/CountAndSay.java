public class CountAndSay {

    // Each term run-length-encodes the previous: "3322251" reads as
    // two 3s, three 2s, one 5, one 1 -> "23321511".
    public static String countAndSay(int n) {
        String curr = "1";
        for (int round = 2; round <= n; round++) {
            StringBuilder next = new StringBuilder();
            int i = 0;
            while (i < curr.length()) {
                char digit = curr.charAt(i);
                int runLen = 0;
                while (i < curr.length() && curr.charAt(i) == digit) {
                    runLen++;                    // measure the run
                    i++;
                }
                next.append(runLen).append(digit);   // say it: count then digit
            }
            curr = next.toString();
        }
        return curr;
    }

    public static void main(String[] args) {
        assert countAndSay(1).equals("1");
        assert countAndSay(2).equals("11");
        assert countAndSay(3).equals("21");
        assert countAndSay(4).equals("1211");
        assert countAndSay(5).equals("111221");
        assert countAndSay(6).equals("312211");
        System.out.println("CountAndSay: OK");
    }
}
