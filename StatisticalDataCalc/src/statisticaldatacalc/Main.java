package statisticaldatacalc;

import java.math.*;
import java.util.*;

public class Main {

    static double mean(double data[], boolean biased) {
        double mean = 0;
        for (int i = 0; i < data.length; i++) {
            mean += data[i];
        }
        if (biased) mean /= data.length;
        else mean /= data.length - 1;
        return mean;
    }

    static double mean2(double data[]) {
        double mean = 0;
        for (int i = 0; i < data.length; i++) {
            mean += data[i];
        }
        mean /= data.length - 1;
        return mean;
    }

    static double median(double data[]) {
        Arrays.sort(data);
        int l = data.length;
        double median = 0;
        if (l % 2 == 0) {
            double one = data[data.length / 2 - 1];
            double two = data[data.length / 2];
            median = one + two / 2;
        } else {
            median = data[(int) Math.floor(data.length / 2)];
        }
        return median;
    }

    static double mode(double data[], boolean truemode) throws NoSuchElementException {
        if (truemode) {
            return (3 * median(data)) - (2 * mean(data, true));
        }
        Arrays.sort(data);
        double mode = 0;
        int iterator = 0;
        int maxiterator = 0;
        double prev = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == prev && i != 0) iterator++;
            else {
                if (iterator > maxiterator) {
                    mode = prev;
                    maxiterator = iterator;
                }
                iterator = 0;
            }
            prev = data[i];
        }
        if (mode == 0) throw new NoSuchElementException("No mode");
        else return mode;
    }

    static double range(double data[]) {
        Arrays.sort(data);
        return data[data.length - 1] - data[0];
    }

    static double stdDeviation(double data[], boolean biased) {
        Arrays.sort(data);
        double mean = mean(data, true);
        double deviants[] = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            deviants[i] = (data[i] - mean) * (data[i] - mean);
        }
        return Math.sqrt(mean(deviants, biased));
    }

    public static void main(String args[]) {
        double data[] = {13, 14, 32, 36, 476, 75, 82, 47, 11, 35, 3, 97, 33};
//        for (int i = 0; i < 10; i++) {
//            data[i] = Math.rint(Math.random()*100);
//            System.out.print(data[i]);
//            if (i != 9) System.out.print(", ");
//        }
//        System.out.println();
        System.out.println("Mean: " + mean(data, true));
        System.out.println("Median: " + median(data));
        try {
            System.out.println("Mode: " + mode(data, false));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("True mode: " + mode(data, true));
        System.out.println("Range: " + range(data));
        System.out.println("Standard Deviation (biased): " + stdDeviation(data, true));
        System.out.println("Standard Deviation (unbiased): " + stdDeviation(data, false));
    }
}
