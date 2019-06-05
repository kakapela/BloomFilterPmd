import lombok.Data;

import java.util.BitSet;

@Data
public class BloomFilter {

    private int k;
    private int m;
    private BitSet bitArray;
    private long position;

    public BloomFilter(int k, int m) {
        this.k = k;
        this.m = m;
        bitArray =new BitSet(m);
    }

    public void add(long x){
        System.out.println("x: " + x);
        for(int i=0; i<k; i++) {
            position = HashFunctions.universalHash(x, m);
            bitArray.set(Math.toIntExact(position));
            System.out.println("Dodano. Ustawilem bit na pozycji " +
                    position);
        }
    }
    public Boolean contains(long x){
        System.out.println("x: " + x);
        boolean flag=true;
        for(int i=0; i<k; i++) {
            position = HashFunctions.universalHash(x, m);
            System.out.println("Sprawdzam bit na pozycji: " + position);
            if(!bitArray.get(Math.toIntExact(position))) {
                flag=false;
                break;
            }

        }
        return flag;
    }

}
