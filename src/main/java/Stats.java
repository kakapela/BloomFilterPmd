import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class Stats {
    public static void checkStats(BloomFilter filter) {
        System.out.println("checking stats...");
        int TP = 0, FP = 0, TN = 0, FN = 0;
        HashSet<Integer> set = filter.getSet();
//        set.forEach(System.out::println);
        long startTime = System.nanoTime();
        for (int i = 0; i < filter.getRange(); i++) {
            Boolean filterContains = filter.contains(i);
            Boolean setContains = set.contains(i);
            if( filterContains && setContains)  TP ++;
            else if (!filterContains && !setContains) TN++;
            else if (!filterContains && setContains) FN++;
            else if (filterContains && !setContains) FP++;
        }
        long estimatedTime = System.nanoTime() - startTime;
        System.out.printf("Czas filtrowania danych: %.6f sec\n", estimatedTime/1_000_000_000.0 );
        System.out.println("TP = " + String.format("%6d", TP) + "\tTPR = "
                    + String.format("%1.4f", (double) TP / (double)((double)TP+(double)FN)));
            System.out.println("TN = " + String.format("%6d", TN) + "\tTNR = "
                    + String.format("%1.4f", (double) TN / (double) ((double)TN+(double)FP) ));
            System.out.println("FN = " + String.format("%6d", FN) + "\tFNR = "
                    + String.format("%1.4f", (double) FN / (double)((double)TP+(double)FN)));
            System.out.println("FP = " + String.format("%6d", FP) + "\tFPR = "
                    + String.format("%1.6f", (double) FP / (double) ((double)TN+(double)FP)));

    }
    public static void calculateProbabilityOfFP(BloomFilter filter, long n){
        double fpr = Math.pow(1 - Math.exp(-filter.getK() * (double) n / filter.getM()), filter.getK());
        System.out.println("Expected FPR = " + String.format("%1.6f", fpr ));
    }
}
