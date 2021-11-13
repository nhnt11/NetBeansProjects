package cashface;

import java.util.Scanner;

public class InputTutorial {

    public static void main(String args[]) {
        System.out.println(fact(new Scanner(System.in).nextInt()));
    }

    static long fact(int n) {
        return (n == 0 || n == 1) ? 1 : n * fact(n - 1);
    }
}
