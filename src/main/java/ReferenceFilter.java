import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

@Data
@AllArgsConstructor
public class ReferenceFilter {

    private int n;
    private Hashtable<Integer, Integer> hashtable;
    private HashSet<Integer> set;

    public ReferenceFilter(int n, HashSet<Integer> set) {
        this.n = n;
        this.hashtable = new Hashtable<>();
        this.set = set;
    }
    public void initAdding(){
        long startTime = System.nanoTime();
        for (int item:set) {
        hashtable.put(item,item);
        }
        long estimatedTime = System.nanoTime() - startTime;
        System.out.printf("Czas potrzebny do utworzenia danych: %.6f sec\n", estimatedTime/1_000_000_000.0 );
    }
    public void checkStats(int range){
        System.out.println("checking stats...");
        int TP = 0, FP = 0, TN = 0, FN = 0;
//        set.forEach(System.out::println);
        long startTime = System.nanoTime();
        for (int i = 0; i < range; i++) {
            Boolean filterContains = hashtable.contains(i);
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
        System.out.println("Pamiêæ filtra referencyjnego: " + n);
    }

}
