import java.util.*;

public class SimplifyPath {

    // Split on '/', walk the segments with a stack: names push, ".." pops,
    // "." and empties are noise. The stack bottom-to-top IS the canonical path.
    public static String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        for (String seg : path.split("/")) {
            if (seg.isEmpty() || seg.equals("."))
                continue;                        // "//" and "." change nothing
            if (seg.equals("..")) {
                if (!stack.isEmpty())            // ".." above root is a no-op
                    stack.pollLast();
            } else {
                stack.offerLast(seg);            // a real directory name
            }
        }
        return "/" + String.join("/", stack);
    }

    public static void main(String[] args) {
        assert simplifyPath("/home/").equals("/home");
        assert simplifyPath("/../").equals("/");
        assert simplifyPath("/home//foo/").equals("/home/foo");
        assert simplifyPath("/a/./b/../../c/").equals("/c");
        assert simplifyPath("/a/../../b/../c//.//").equals("/c");
        assert simplifyPath("/...").equals("/...");   // "..." is a valid name
        System.out.println("SimplifyPath: OK");
    }
}
