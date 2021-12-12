import java.lang.reflect.Array;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int[] k1 = {6,9,12,7,1,3,11,4,18,2,20};
        AVLTreeExp t1 = new AVLTreeExp(k1);
        t1.bfs_print();
        AVLTreeExp[] trees = new AVLTreeExp[2];
        trees = t1.split(7);
        trees[0].bfs_print();
        trees[1].bfs_print();
        System.out.println(trees[1].getRoot().getKey());

    }

    public static void test1() {
        System.out.println("T1 - empty tree");
        AVLTreePrint t1 = new AVLTreePrint();

        System.out.println("Test empty");
        System.out.println(t1.empty());

        System.out.println("Test search");
        System.out.println(t1.search(2));
        System.out.println(t1.search(10));

        System.out.println("Test insert");
        t1.insert(1, "a");
        t1.bfs_print();

        System.out.println("Test delete");
        t1.delete(1);
        t1.bfs_print();

        System.out.println("Test min");
        System.out.println(t1.min());

        System.out.println("Test max");
        System.out.println(t1.max());
        t1.bfs_print();

        System.out.println("Test keysToArray");
        System.out.println(Arrays.toString(t1.keysToArray()));

        System.out.println("Test infoToArray");
        System.out.println(Arrays.toString(t1.infoToArray()));

        System.out.println("Test size");
        System.out.println(t1.size());

        System.out.println("Test split");
        System.out.println("TO COMPLETE");

        System.out.println("Test join");
        System.out.println("TO COMPLETE");

        System.out.println("Test getRoot");
        System.out.println(t1.getRoot());

        System.out.println();
    }

    public static void test2() {
        System.out.println("T2 - rotate left");
        int[] keys2 = {3, 6, 9};
        AVLTreePrint t2 = new AVLTreePrint(keys2);

        System.out.println("Test empty");
        System.out.println(t2.empty());

        System.out.println("Test search");
        System.out.println(t2.search(3)); //in tree
        System.out.println(t2.search(5)); //not in tree
        System.out.println(t2.search(6));

        System.out.println("Test insert");
        t2.insert(2, "num2");
        t2.bfs_print();

        t2.insert(7, "num7");
        t2.bfs_print();

        t2.insert(12, "num12");
        t2.bfs_print();

        System.out.println("Test delete");
        t2.delete(2);
        t2.bfs_print();

        t2.delete(9);
        t2.bfs_print();

        t2.delete(3);
        t2.bfs_print();

        System.out.println("Test min");
        System.out.println(t2.min());

        System.out.println("Test max");
        System.out.println(t2.max());

        System.out.println("Test keysToArray");
        System.out.println(Arrays.toString(t2.keysToArray()));

        System.out.println("Test infoToArray");
        System.out.println(Arrays.toString(t2.infoToArray()));

        System.out.println("Test size");
        System.out.println(t2.size());

        System.out.println("Test split");
        System.out.println("TO COMPLETE");

        System.out.println("Test join");
        System.out.println("TO COMPLETE");

        System.out.println("Test getRoot");
        System.out.println(t2.getRoot());

        System.out.println();
    }

    public static void test3() {

        System.out.println("T3 - double rotate left");
        System.out.println();
        int[] keys3 = {10, 1, 15, 20, 13, 11};
        AVLTreePrint t3 = new AVLTreePrint(keys3);

        System.out.println("Test empty");
        System.out.println(t3.empty());

        System.out.println("Test search");
        System.out.println(t3.search(3)); //in tree
        System.out.println(t3.search(5)); //not in tree

        System.out.println("Test insert");
        t3.insert(2, "nums2");
        t3.bfs_print();
        t3.insert(7, "nums7");
        t3.bfs_print();
        t3.insert(14, "nums14"); //already in the tree
        t3.bfs_print();

        System.out.println("Test delete");
        t3.delete(10); //not in the tree
        t3.bfs_print();
        t3.delete(13);
        t3.bfs_print();

        System.out.println("Test min");
        System.out.println(t3.min());

        System.out.println("Test max");
        System.out.println(t3.max());

        System.out.println("Test keysToArray");
        System.out.println(Arrays.toString(t3.keysToArray()));

        System.out.println("Test infoToArray");
        System.out.println(Arrays.toString(t3.infoToArray()));

        System.out.println("Test size");
        System.out.println(t3.size());

        System.out.println("Test split");
        System.out.println("TO COMPLETE");

        System.out.println("Test join");
        System.out.println("TO COMPLETE");

        System.out.println("Test getRoot");
        System.out.println(t3.getRoot());

        System.out.println();
    }

    public static void test4() {

        System.out.println("T4 - rotate right");
        int[] keys4 = {9, 6, 3};
        AVLTreePrint t4 = new AVLTreePrint(keys4);

        System.out.println("Test empty");
        System.out.println(t4.empty());

        System.out.println("Test search");
        System.out.println(t4.search(3)); //in tree
        System.out.println(t4.search(5)); //not in tree
        System.out.println(t4.search(6));

        System.out.println("Test insert");
        t4.insert(2, "num2");
        t4.bfs_print();

        t4.insert(7, "num7");
        t4.bfs_print();

        t4.insert(12, "num12");
        t4.bfs_print();

        System.out.println("Test delete");
        t4.delete(2);
        t4.bfs_print();

        t4.delete(9);
        t4.bfs_print();

        t4.delete(3);
        t4.bfs_print();

        System.out.println("Test min");
        System.out.println(t4.min());

        System.out.println("Test max");
        System.out.println(t4.max());

        System.out.println("Test keysToArray");
        System.out.println(Arrays.toString(t4.keysToArray()));

        System.out.println("Test infoToArray");
        System.out.println(Arrays.toString(t4.infoToArray()));

        System.out.println("Test size");
        System.out.println(t4.size());

        System.out.println("Test split");
        System.out.println("TO COMPLETE");

        System.out.println("Test join");
        System.out.println("TO COMPLETE");

        System.out.println("Test getRoot");
        System.out.println(t4.getRoot());

        System.out.println();
    }

    public static void test5() {

        System.out.println("T5 - double rotate right");
        System.out.println();
        int[] keys5 = {10, 1, 15, 20, 13, 11};
        AVLTreePrint t5 = new AVLTreePrint(keys5);

        System.out.println("Test empty");
        System.out.println(t5.empty());

        System.out.println("Test search");
        System.out.println(t5.search(20)); //in tree
        System.out.println(t5.search(5)); //not in tree

        System.out.println("Test insert");
        t5.insert(14, "nums14");
        t5.bfs_print();
        t5.insert(25, "nums25");
        t5.bfs_print();
        t5.insert(30, "nums30"); //already in the tree
        t5.bfs_print();

        System.out.println("Test delete");
        t5.delete(15); //not in the tree
        t5.bfs_print();
        t5.delete(13);
        t5.bfs_print();

        System.out.println("Test min");
        System.out.println(t5.min());

        System.out.println("Test max");
        System.out.println(t5.max());

        System.out.println("Test keysToArray");
        System.out.println(Arrays.toString(t5.keysToArray()));

        System.out.println("Test infoToArray");
        System.out.println(Arrays.toString(t5.infoToArray()));

        System.out.println("Test size");
        System.out.println(t5.size());

        System.out.println("Test split");
        System.out.println("TO COMPLETE");

        System.out.println("Test join");
        System.out.println("TO COMPLETE");

        System.out.println("Test getRoot");
        System.out.println(t5.getRoot());

        System.out.println();
    }

    public static void test6() {

        System.out.println("T6 - delete min");
        System.out.println("T5 - double rotate right");
        System.out.println();
        int[] keys5 = {16, 24, 36, 19, 44, 28, 61, 74, 83, 64, 52, 65, 86, 93, 88};
        AVLTreePrint t5 = new AVLTreePrint(keys5);

        System.out.println("Test empty");
        System.out.println(t5.empty());

        System.out.println("Test search");
//        System.out.println(t5.search(20)); //in tree
//        System.out.println(t5.search(5)); //not in tree

        System.out.println("Test insert");
//        t5.insert(14, "nums14");
//        t5.bfs_print();
//        t5.insert(25, "nums25");
//        t5.bfs_print();
//        t5.insert(30, "nums30"); //already in the tree
//        t5.bfs_print();

        System.out.println("Test delete");
        t5.delete(88);
        t5.bfs_print();
        t5.delete(19);
        t5.bfs_print();
        t5.delete(16);
        t5.bfs_print();
        t5.delete(28);
        t5.bfs_print();
        t5.delete(24);
        t5.bfs_print();
        t5.delete(36);
        t5.bfs_print();
        t5.delete(52);
        t5.bfs_print();
        t5.delete(93);
        t5.bfs_print();
        t5.delete(86);
        t5.bfs_print();
        t5.delete(83);
        t5.bfs_print();

        System.out.println("Test min");
        System.out.println(t5.min());

        System.out.println("Test max");
        System.out.println(t5.max());

        System.out.println("Test keysToArray");
        System.out.println(Arrays.toString(t5.keysToArray()));

        System.out.println("Test infoToArray");
        System.out.println(Arrays.toString(t5.infoToArray()));

        System.out.println("Test size");
        System.out.println(t5.size());

        System.out.println("Test split");
        System.out.println("TO COMPLETE");

        System.out.println("Test join");
        System.out.println("TO COMPLETE");

        System.out.println("Test getRoot");
        System.out.println(t5.getRoot());

        System.out.println();
    }

    public static void test7() {
        System.out.println("T7 - delete max");

        int[] keys5 = {1,2};
        AVLTreePrint t1 = new AVLTreePrint(keys5);

        System.out.println(t1.min());
        System.out.println(t1.max());
        t1.delete(2);
        t1.bfs_print();
        System.out.println(t1.min());
        System.out.println(t1.max());
        t1.delete(1);
        t1.bfs_print();
        System.out.println(t1.min());
        System.out.println(t1.max());


        System.out.println("Test empty");
        System.out.println(t1.empty());

        System.out.println("Test search");
        System.out.println(t1.search(2));
        System.out.println(t1.search(10));

        System.out.println("Test insert");
//        t1.insert(1, "a");
//        t1.bfs_print();

        System.out.println("Test delete");
//        t1.delete(1);
//        t1.bfs_print();

        System.out.println("Test min");
        System.out.println(t1.min());

        System.out.println("Test max");
        System.out.println(t1.max());
        t1.bfs_print();

        System.out.println("Test keysToArray");
        System.out.println(Arrays.toString(t1.keysToArray()));

        System.out.println("Test infoToArray");
        System.out.println(Arrays.toString(t1.infoToArray()));

        System.out.println("Test size");
        System.out.println(t1.size());

        System.out.println("Test split");
        System.out.println("TO COMPLETE");

        System.out.println("Test join");
        System.out.println("TO COMPLETE");

        System.out.println("Test getRoot");
        System.out.println(t1.getRoot());

        System.out.println();

    }

//        System.out.println("Test empty");
//        System.out.println("Test search");
//        System.out.println("Test insert");
//        System.out.println("Test delete");
//        System.out.println("Test min");
//        System.out.println("Test max");
//        System.out.println("Test keysToArray");
//        System.out.println("Test infoToArray");
//        System.out.println("Test size");
//        System.out.println("Test split");
//        System.out.println("Test join");
//        System.out.println("Test getRoot");
}
