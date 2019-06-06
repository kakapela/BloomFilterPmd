import lombok.Data;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class BloomFilter {

    private int k;
    private int m;
    private BitSet bitArray;
    private long position;
    private ArrayList<Hash> hashesFunctions;
    private HashSet<Integer> set;
    private int r;

    public BloomFilter(int k, int m) {
        this.k = k;
        this.m = m;
        bitArray =new BitSet(m);
        hashesFunctions = new ArrayList<>();
        for (int i=0; i< k; i++){
            Hash hash = new Hash(m);
            hashesFunctions.add(hash);
        }
    }

    public void add(long x){
        System.out.println("x: " + x);
        for (Hash hash: hashesFunctions) {
            position = hash.generate(x);
            bitArray.set(Math.toIntExact(position));
            System.out.println("Dodano liczbe "+ position +" na wartosc " + bitArray.get(Math.toIntExact(position)));
        }
    }
    public void addMultipleRandoms(int n){
        set = new HashSet<>(n);

         r = 10*n;
        int random;
        for(int i=0; i<n; i++){
            random = ThreadLocalRandom.current().nextInt(0, r);
            set.add(random);
            System.out.println("Wylosowana liczba: " + random);
            add(random);
        }

    }

    public Boolean contains(long x){
        System.out.println("x: " + x);
        boolean flag=true;

        for (Hash hash: hashesFunctions) {
            position = hash.generate(x);
            System.out.println("Sprawdzam bit na pozycji: " + position);
            if(!bitArray.get(Math.toIntExact(position))) {
                flag=false;
                break;
            }
        }
        return flag;
    }

}
