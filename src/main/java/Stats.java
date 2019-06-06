import java.util.HashSet;

public class Stats {
    public static void checkStats(BloomFilter filter) {
        int TP = 0, FP = 0, TN = 0, FN = 0;

        HashSet<Integer> set = filter.getSet();
        for (long i = 0; i < filter.getR(); i++) {
            System.out.println("Sprawdzana liczba: " + i);
            if( filter.contains(i) && set.contains(i))  {TP ++;
                System.out.println("i: "+ i+" TP++ ");}
            else if (!filter.contains(i) && !set.contains(i)) {TN++;System.out.println("i: "+ i+" TN++ ");}
            else if (!filter.contains(i) && set.contains(i)) {FN++;System.out.println("i: "+ i+" FN++ ");}
            else if (filter.contains(i) && !set.contains(i)) {FP++;System.out.println("i: "+ i+" FP++ ");}
        }
            System.out.println("TP = " + String.format("%6d", TP) + "\tTPR = "
                    + String.format("%1.4f", (double) TP / (double)((double)TP+(double)FN)));
            System.out.println("TN = " + String.format("%6d", TN) + "\tTNR = "
                    + String.format("%1.4f", (double) TN / (double) ((double)TN+(double)FP) ));
            System.out.println("FN = " + String.format("%6d", FN) + "\tFNR = "
                    + String.format("%1.4f", (double) FN / (double)((double)TP+(double)FN)));
            System.out.println("FP = " + String.format("%6d", FP) + "\tFPR = "
                    + String.format("%1.6f", (double) FP / (double) ((double)TN+(double)FP)));

    }
}
