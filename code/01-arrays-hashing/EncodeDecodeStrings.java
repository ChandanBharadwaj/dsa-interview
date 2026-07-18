import java.util.*;

public class EncodeDecodeStrings {

    // Length-prefix framing: "5#hello4#world". No delimiter can appear in
    // arbitrary strings, but a length is unambiguous — the same reason
    // network protocols frame messages with a length header.
    public static String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs)
            sb.append(s.length()).append('#').append(s);
        return sb.toString();
    }

    public static List<String> decode(String data) {
        List<String> out = new ArrayList<>();
        int i = 0;
        while (i < data.length()) {
            int hash = data.indexOf('#', i);            // end of the length field
            int len = Integer.parseInt(data.substring(i, hash));
            out.add(data.substring(hash + 1, hash + 1 + len));  // exactly len chars — '#' inside is data
            i = hash + 1 + len;                         // jump to the next frame
        }
        return out;
    }

    public static void main(String[] args) {
        List<String> in = Arrays.asList("hello", "wor#ld", "", "12#34");
        assert decode(encode(in)).equals(in);           // round-trip, tricky chars included
        assert decode(encode(Arrays.asList())).isEmpty();
        System.out.println("EncodeDecodeStrings: OK");
    }
}
