import java.util.*;

public class AsteroidCollision {

    // Stack of survivors. Only "stack top moving right, newcomer moving left"
    // collides; resolve until the newcomer dies or nothing opposes it.
    public static int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int a : asteroids) {
            boolean alive = true;
            while (alive && a < 0 && !stack.isEmpty() && stack.peek() > 0) {
                int top = stack.peek();
                if (top < -a)                // top is smaller — it explodes,
                    stack.pop();             // newcomer keeps flying left
                else if (top == -a) {        // equal — both explode
                    stack.pop();
                    alive = false;
                } else                       // top is bigger — newcomer dies
                    alive = false;
            }
            if (alive) stack.push(a);
        }
        int[] out = new int[stack.size()];   // stack bottom-to-top = left-to-right
        for (int i = out.length - 1; i >= 0; i--)
            out[i] = stack.pop();
        return out;
    }

    public static void main(String[] args) {
        assert Arrays.equals(asteroidCollision(new int[]{5, 10, -5}), new int[]{5, 10});
        assert Arrays.equals(asteroidCollision(new int[]{8, -8}), new int[]{});
        assert Arrays.equals(asteroidCollision(new int[]{10, 2, -5}), new int[]{10});
        assert Arrays.equals(asteroidCollision(new int[]{-2, -1, 1, 2}), new int[]{-2, -1, 1, 2});
        System.out.println("AsteroidCollision: OK");
    }
}
