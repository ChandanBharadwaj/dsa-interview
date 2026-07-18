// LeetCode 739 — Daily Temperatures
// Monotonic (decreasing) stack of INDICES still waiting for a warmer day.
// Each index is pushed once and popped at most once → O(n) total, despite the nested loop.
public class DailyTemperatures {

    public static int[] dailyTemperatures(int[] temps) {
        int n = temps.length;
        int[] answer = new int[n];             // defaults to 0 = "no warmer day ever comes"
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();  // indices; temps strictly decrease bottom→top
        for (int i = 0; i < n; i++) {
            // Today resolves every colder day still waiting on the stack
            while (!stack.isEmpty() && temps[i] > temps[stack.peek()]) {
                int j = stack.pop();           // day j finally sees a warmer day: today
                answer[j] = i - j;             // the wait was exactly the index distance
            }
            stack.push(i);                     // today now waits for ITS next warmer day
        }
        return answer;                         // indices left on the stack keep answer 0 — correct by default
    }

    public static void main(String[] args) {
        assert java.util.Arrays.equals(
            dailyTemperatures(new int[]{73,74,75,71,69,72,76,73}),
            new int[]{1,1,4,2,1,1,0,0});
        assert java.util.Arrays.equals(
            dailyTemperatures(new int[]{30,40,50,60}), new int[]{1,1,1,0});
        assert java.util.Arrays.equals(
            dailyTemperatures(new int[]{60,50,40,30}), new int[]{0,0,0,0});
        assert java.util.Arrays.equals(
            dailyTemperatures(new int[]{30}), new int[]{0});
        System.out.println("DailyTemperatures: OK");
    }
}
