public class NumberComplement {

    // Flip only the SIGNIFICANT bits: build a mask of 1s exactly as long as
    // n's binary form, then XOR (XOR with 1 flips).
    public static int findComplement(int num) {
        int mask = 1;
        while (mask < num)              // grow until mask covers n's top bit
            mask = (mask << 1) | 1;     // 1 -> 11 -> 111 -> ...
        return num ^ mask;              // flip every significant bit
    }

    public static void main(String[] args) {
        assert findComplement(5) == 2;      // 101 -> 010
        assert findComplement(1) == 0;
        assert findComplement(10) == 5;     // 1010 -> 0101
        assert findComplement(7) == 0;      // 111 -> 000
        assert findComplement(8) == 7;      // 1000 -> 0111
        System.out.println("NumberComplement: OK");
    }
}
