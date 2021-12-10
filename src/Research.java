import java.util.*;

public class Research{
    public static void main(String[] args) {
        runQuestion1();
    }

    public static void runQuestion1() {
        int[] treeSizes = new int[5];
        int power = 1;
        for (int i = 0; i < treeSizes.length; i++) {
            power *= 2;
            treeSizes[i] = 1000 * power;
        }

        //run backwards lists
        for (int treeSize : treeSizes) {
            int[] keys = backwards(treeSize);
            AVLTreeExp t1 = new AVLTreeExp(keys);
            int searchCount = t1.getFingerCounter();
            long pairs = insertionSort(keys);
            System.out.println("Backwards Case");
            System.out.println("Tree Size: " + treeSize);
            System.out.println("Pairs Switch Count: " + pairs);
            System.out.println("Search Cost: " + searchCount);
            System.out.println();
        }
        System.out.println();
        //run random lists
        for (int treeSize : treeSizes) {
            int[] keys = random(treeSize);
            AVLTreeExp t2 = new AVLTreeExp(keys);
            int searchCount = t2.getFingerCounter();
            long pairs = insertionSort(keys);
            System.out.println("Random Case");
            System.out.println("Tree Size: " + treeSize);
            System.out.println("Pairs Switch Count: " + pairs);
            System.out.println("Search Cost: " + searchCount);
            System.out.println();
        }
    }

    public static int[] backwards(int treeSize) {
        int[] keys = new int[treeSize];

        for (int j = 0; j < keys.length; j++) {
            keys[j] = keys.length-1-j;
        }
        return keys;
    }

    public static int[] random(int treeSize) {
        List<Integer> randList = new ArrayList<>();
        for (int i = 0; i < treeSize; i++) {
            randList.add(i);
        }
        Collections.shuffle(randList);
        int[] randArr = randList.stream().mapToInt(i->i).toArray();

        return randArr;
    }

    public static int selectionSort(int[] arr){
        int counter = 0;
        for (int i = 0; i < arr.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.length; j++){
                if (arr[j] < arr[index]){
                    index = j;//searching for lowest index
                }
            }
            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
            counter += 1;
        }
        return counter;
    }

    public static long insertionSort(int[] array) {
        long count = 0;
        for (int i = 0; i < array.length; i++) {
            int j = i;
            while (j > 0 && array[j - 1] > array[j]) {
                swap(array, j - 1, j);
                j = j - 1;
                count++;
            }
        }

        return count;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
