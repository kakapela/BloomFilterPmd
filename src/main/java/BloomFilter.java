import lombok.Data;

import java.util.BitSet;

@Data
public class BloomFilter {

    private int m; //rozmiar tablicy bitowej
    private int k; //ilosc funkcji haszujacych
    private int n; //liczba elementow w zbiorze
    private int maxRange; //przedzial z ktorego beda losowane elementy
    private BitSet set;

    public BloomFilter(int m, int k, int n, int maxRange) {
        this.m = m;
        this.k = k;
        this.n = n;
        this.maxRange = maxRange;
    }

}
