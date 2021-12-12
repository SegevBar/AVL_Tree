import java.util.*;

public class Research{
    public static void main(String[] args) {
        runQuestion2();

        //testTest2();
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

    public static void runQuestion2() {
        int[] treeSizes = new int[10];
        int power = 1;
        for (int i = 0; i < treeSizes.length; i++) {
            power *= 2;
            treeSizes[i] = 1000 * power;
        }

        for (int treeSize : treeSizes) {
            int[] keys = random(treeSize);
            AVLTreeExp t1 = new AVLTreeExp(keys);
            Random random = new Random();
            int randKey = random.nextInt(treeSize);

            AVLTreeExp[] split1 = t1.split(randKey);

            float averageJoinCost1 = (float)(t1.getSumJoin())/(t1.getJoinCounter());
            int maxJoinCost1 = t1.getMaxJoin();

            System.out.println("Random Key");
            System.out.println("Tree Size: " + treeSize);
            System.out.println("Random Key: " + randKey);
            System.out.println("Average Join Cost: " + averageJoinCost1);
            System.out.println("Max Join Cost: " + maxJoinCost1);
            System.out.println();

            AVLTreeExp t2 = new AVLTreeExp(keys);

            AVLTreeExp subLeft = new AVLTreeExp();
            subLeft.setRoot(t2.getRoot().getLeft());
            subLeft.updatemax();
            int maxLeftKey = subLeft.getMax().getKey();

            AVLTreeExp[] split2 = t2.split(maxLeftKey);

            float averageJoinCost2 = (float)(t2.getSumJoin())/(t2.getJoinCounter());
            int maxJoinCost2 = t2.getMaxJoin();

            System.out.println("Maximal left sub tree");
            System.out.println("Tree Size: " + treeSize);
            System.out.println("Maximal Left Key: " + maxLeftKey);
            System.out.println("Average Join Cost: " + averageJoinCost2);
            System.out.println("Max Join Cost: " + maxJoinCost2);
            System.out.println();
        }
    }

    public static void testTest2() {
        int[] keys = random(20);
        System.out.println(Arrays.toString(keys));

        AVLTreeExp t1 = new AVLTreeExp(keys);
        t1.bfs_print();

        Random random = new Random();
        int randKey = random.nextInt(20);
        AVLTreeExp[] split1 = t1.split(randKey);


        float averageJoinCost1 =  (float)(t1.getSumJoin())/(t1.getJoinCounter());
        System.out.println(t1.getSumJoin());
        System.out.println(t1.getJoinCounter());
        int maxJoinCost1 = t1.getMaxJoin();

        System.out.println("Random Key");
        System.out.println("Tree Size: " + 20);
        System.out.println("Random Key: " + randKey);
        System.out.println("smaller");
        split1[0].bfs_print();
        System.out.println("bigger");
        split1[1].bfs_print();
        System.out.println("Average Join Cost: " + averageJoinCost1);
        System.out.println("Max Join Cost: " + maxJoinCost1);
        System.out.println();

        AVLTreeExp t2 = new AVLTreeExp(keys);
        t2.bfs_print();

        AVLTreeExp subLeft = new AVLTreeExp();
        subLeft.setRoot(t2.getRoot().getLeft());
        subLeft.updatemax();
        int maxLeftKey = subLeft.getMax().getKey();
        AVLTreeExp[] split2 = t2.split(maxLeftKey);


        float averageJoinCost2 = t2.getSumJoin()/t2.getJoinCounter();
        int maxJoinCost2 = t2.getMaxJoin();

        System.out.println("Maximal left sub tree");
        System.out.println("Tree Size: " + 20);
        System.out.println("Maximal Left Key: " + maxLeftKey);
        System.out.println("smaller");
        split2[0].bfs_print();
        System.out.println("bigger");
        split2[1].bfs_print();
        System.out.println("Average Join Cost: " + averageJoinCost2);
        System.out.println("Max Join Cost: " + maxJoinCost2);
        System.out.println();
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
