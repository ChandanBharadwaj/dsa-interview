import java.util.*;

public class HappyNumber {

    private static int next(int n) {
        int sum = 0;
        while (n > 0) {
            int d = n % 10;         // peel the last digit
            sum += d * d;
            n /= 10;
        }
        return sum;
    }

    // Variation 1: HashSet cycle detection — the sequence either reaches 1
    // or enters a loop; a repeat proves the loop.
    public static boolean isHappySet(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && seen.add(n))   // add() returns false on a repeat
            n = next(n);
        return n == 1;
    }

    // Variation 2: Floyd's tortoise-and-hare — O(1) space. The "linked list"
    // is implicit: each number points at next(number).
    public static boolean isHappy(int n) {
        int slow = n, fast = next(n);
        while (fast != 1 && slow != fast) {
            slow = next(slow);          // one step
            fast = next(next(fast));    // two steps
        }
        return fast == 1;               // met at 1 = happy; met elsewhere = cycle
    }

    public static void main(String[] args) {
        assert isHappy(19) && isHappySet(19);      // 19→82→68→100→1
        assert !isHappy(2) && !isHappySet(2);
        assert isHappy(1) && isHappy(7);
        for (int i = 1; i <= 1000; i++)            // both variants must agree
            assert isHappy(i) == isHappySet(i);
        System.out.println("HappyNumber: OK");
    }
}
