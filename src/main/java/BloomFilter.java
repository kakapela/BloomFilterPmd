import lombok.Data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@Data
public class BloomFilter {
    private int k;
    private int m;
    private BitSet bitArray;
    private int position;
    private ArrayList<Hash> hashesFunctions;
    private HashSet<Integer> set;
    private BigInteger value;
    private int a;
    private int b;
    private int p;
    private int range;
    private int numberOfUsers;



    //for msdc
    private HashSet<Integer> songs;
    private Map<Integer, HashSet<Integer> > usersData;
    private ArrayList<Integer> randomIdSongs;

    public BloomFilter(int k, int m, int range) {
        this.k = k;
        this.m = m;
        this.range= range;
        bitArray =new BitSet(m);
        hashesFunctions = new ArrayList<>();

        Random rand = new Random(0);
        value =new BigInteger(String.valueOf(range));
        p = value.nextProbablePrime().intValue();

        for (int i=0; i< k; i++){
            a = rand.nextInt(p - 1) + 1;
            b = rand.nextInt(p);
            Hash hash = new Hash(a,b,p,m);
            hashesFunctions.add(hash);
        }
    }
    public BloomFilter(int k, int m, int range, int numberOfUsers) {
        this.k = k;
        this.m = m;
        this.range= range;
        this.numberOfUsers = numberOfUsers;
        bitArray =new BitSet(m);
        hashesFunctions = new ArrayList<>();

        Random rand = new Random(0);
        value =new BigInteger(String.valueOf(range));
        p = value.nextProbablePrime().intValue();

        for (int i=0; i< k; i++){
            a = rand.nextInt(p - 1) + 1;
            b = rand.nextInt(p);
            Hash hash = new Hash(a,b,p,m);
            hashesFunctions.add(hash);
        }
    }

    public void add(int x){
        for (Hash hash: hashesFunctions) {
            position = (int) hash.generate(x);
            bitArray.set(position);
        }
    }

    Random random = new Random();

    public void addMultipleRandoms(int n){
        set = new HashSet<>(n);
        long startTime = System.nanoTime();
        for(int i = 0; i <n; i++) {
            int number = random.nextInt(range) + 1;
            set.add(number);
        }
        for (int item : set) {
            add(item);
        }
        long estimatedTime = System.nanoTime() - startTime;
        System.out.printf("Czas potrzebny do utworzenia danych: %.6f sec\n", estimatedTime/1_000_000_000.0 );
    }

    public Boolean contains(int x){
        boolean flag=true;

        for (Hash hash: hashesFunctions) {
            position = (int) hash.generate(x);
            if(!bitArray.get(position)) {
                flag=false;
                break;
            }
        }
        return flag;
    }

    public void prepareMsdcData(){
        System.out.println("started...");
        String csvFile = "facts-nns.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        usersData = new TreeMap<>();
        songs = new HashSet<>();

        int lines = 0;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if (lines == 0) {
                    lines++;
                    continue;
                }

                String[] facts = line.split(cvsSplitBy);


                boolean isKeyInMap = usersData.containsKey(Integer.parseInt(facts[0]));

                songs.add(Integer.parseInt(facts[1]));
                if (!isKeyInMap) {
                    HashSet<Integer> hashSet = new HashSet<>();
                    hashSet.add(Integer.parseInt(facts[1]));
                    usersData.put(Integer.parseInt(facts[0]), hashSet);
                } else {
                    HashSet<Integer> hashSet = usersData.get(Integer.parseInt(facts[0]));
                    hashSet.add(Integer.parseInt(facts[1]));
                    usersData.put(Integer.parseInt(facts[0]), hashSet);
                }
                lines++;
            }

            randomIdSongs = generateRandomIdSongs(songs);
            int tmp = 0;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void filterForMSDC(){

            int tmp = 0;
            for (Map.Entry<Integer, HashSet<Integer>> user: usersData.entrySet()) {
//                if(tmp==numberOfUsers) {
                    bitArray.clear();
//                if (tmp == 0) {
                    HashSet<Integer> set = user.getValue();

                    for (int item : set) {
                        add(item);
                    }

                    long startTime = System.nanoTime();
                    int TP = 0, FP = 0, TN = 0, FN = 0;
                    for (int element : randomIdSongs) {
                        Boolean filterContains = contains(element);
                        Boolean setContains = set.contains(element);
                        if (filterContains && setContains) TP++;
                        else if (!filterContains && !setContains) TN++;
                        else if (!filterContains && setContains) FN++;
                        else if (filterContains && !setContains) FP++;
                    }
                    long estimatedTime = System.nanoTime() - startTime;

                    double tpr = (double) TP / (double) ((double) TP + (double) FN);
                    double tnr = (double) TN / (double) ((double) TN + (double) FP);
                    double fnr = (double) FN / (double) ((double) TP + (double) FN);
                    double fpr = (double) FP / (double) ((double) TN + (double) FP);
                    if (Double.isNaN(tnr)) tnr = 0;
                    if (Double.isNaN(tpr)) tpr = 0;
                    if (Double.isNaN(fnr)) fnr = 0;
                    if (Double.isNaN(fpr)) fpr = 0;

                    if(TP>0) {
                        System.out.println("Zbior usera: " + user.getKey() + " : " + user.getValue());
                        System.out.println("Wylosowane elementy: " + randomIdSongs);
                        System.out.printf("Czas filtrowania danych: %.6f sec\n", estimatedTime / 1_000_000_000.0);
                        System.out.println("DLA USERA NUMER: " + user.getKey());
                        System.out.println("TP = " + String.format("%6d", TP) + "\tTPR = "
                                + String.format("%1.4f", tpr));
                        System.out.println("TN = " + String.format("%6d", TN) + "\tTNR = "
                                + String.format("%1.4f", tnr));
                        System.out.println("FN = " + String.format("%6d", FN) + "\tFNR = "
                                + String.format("%1.4f", fnr));
                        System.out.println("FP = " + String.format("%6d", FP) + "\tFPR = "
                                + String.format("%1.6f", fpr));

                        System.out.println("k: " + k + " m: " + m + " set size: " + set.size());
                        double Expfpr = Math.pow(1 - Math.exp(-k * (double) set.size() / m), k);
                        System.out.println("Expected FPR = " + String.format("%1.6f", Expfpr));
                    }
//                    }
                tmp++;
            }
//                tmp++;
//                if(tmp==9) break;
//            }
    }


     public static ArrayList<Integer> generateRandomIdSongs(HashSet<Integer> songs){
        ArrayList<Integer> randomSongsId = new ArrayList<Integer>();

        Random randomizer = new Random();
        Object[] asArray =  songs.toArray();
        for(int i =0; i<100; i++) {
            Object randomSongId = asArray[randomizer.nextInt(asArray.length)];
            randomSongsId.add((Integer) randomSongId);
        }
        return  randomSongsId;
    }


}
