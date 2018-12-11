package hw3.hash;

import java.util.List;
import java.util.TreeMap;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        //K: bucketNum V: item num in this bucket
        int N = oomages.size();
        TreeMap<Integer, Integer> hashMap = new TreeMap<>();
        //calculate hashCode of all Oomage in oomages and count the frequency of each hashCode
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if (!hashMap.containsKey(bucketNum)) {
                hashMap.put(bucketNum, 1);
            } else {
                int count = hashMap.get(bucketNum);
                hashMap.put(bucketNum, count + 1);
            }
        }
        //check each bucket item size
        for (int bucketIndex : hashMap.keySet()) {
            if (hashMap.get(bucketIndex) < N/50 || hashMap.get(bucketIndex) > N/2.5) {
                return false;
            }
        }
        return true;
    }
}
