import lombok.Data;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class Hash {
    private int a;
    private int b;
    private int p;
    private int m;
    private long position;
    private BigInteger value;

    public Hash(int m) {
        value =new BigInteger(String.valueOf(m));
        this.m = m;
        p = value.nextProbablePrime().intValue();
        a = ThreadLocalRandom.current().nextInt(1, p);
        b = ThreadLocalRandom.current().nextInt(0, p);
    }

    public long generate(long x){
        position = (long)((a*x+b) % p) % m ;
        return position;
    }
}
