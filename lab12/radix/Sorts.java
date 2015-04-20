/* Radix.java */

package radix;

/**
 * Sorts is a class that contains an implementation of radix sort.
 * @author
 */
public class Sorts {


    /**
     *  Sorts an array of int keys according to the values of <b>one</b>
     *  of the base-16 digits of each key. Returns a <b>NEW</b> array and
     *  does not modify the input array.
     *  
     *  @param key is an array of ints.  Assume no key is negative.
     *  @param whichDigit is a number in 0...7 specifying which base-16 digit
     *    is the sort key. 0 indicates the least significant digit which
     *    7 indicates the most significant digit
     *  @return an array of type int, having the same length as "keys"
     *    and containing the same keys sorted according to the chosen digit.
     **/
    public static int[] countingSort(int[] keys, int whichDigit) {
        int size = keys.length;
        int[] sortedKeys = new int[size];
        int[] count = new int[16];
        int[] position = new int[16];        
        int mask = 15;
        int index;
        int index2;
        for (int i = 0; i < size; i++) {
            index = ((keys[i] >>> (whichDigit*4)) & mask);
            count[index]++;            
        }
        position[0] = 0;
        for (int j = 1; j < 16; j++) {
            position[j] = position[j-1] + count[j-1];
        }
        for (int k = 0; k < size; k++) {
            index = ((keys[k] >>> (whichDigit*4)) & mask);
            index2 = position[index];
            sortedKeys[index2] = keys[k];
            position[index]++; 
        }
        return sortedKeys;
    }

    /**
     *  radixSort() sorts an array of int keys (using all 32 bits
     *  of each key to determine the ordering). Returns a <b>NEW</b> array
     *  and does not modify the input array
     *  @param key is an array of ints.  Assume no key is negative.
     *  @return an array of type int, having the same length as "keys"
     *    and containing the same keys in sorted order.
     **/
    public static int[] radixSort(int[] keys) {
        int size = keys.length;
        int[] sortedKeys = new int[size];
        for (int i = 0; i < 8; i++) {
            sortedKeys = countingSort(keys, i);
        }
        return sortedKeys;
    }

}
