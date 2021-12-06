import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class AVLTreePrint extends AVLTree {
    IAVLNode EXTERNAL_NODE = new AVLNode();

    public AVLTreePrint() {};
    public AVLTreePrint(int[] keys) {
        super();

        for (int key : keys) {
            this.insert(key, String.valueOf("num" + key));
            this.bfs_print();

            System.out.println();
        }
    }

//    //prints the tree level by level until the last virtual node
//    // V - virtual node
//    //N - null
//    public void treePrinter(){
//        ArrayList<IAVLNode> currList = new ArrayList<>();
//        IAVLNode root = this.getRoot();
//        currList.add(root);
//        int level = root.getRank();
//
//        while (currList.size() > 0) {
//
//            String space = "  ";
//
//            for (int i = 0; i < level; i++) {
//                space = space + space;
//            }
//            level--;
//            System.out.print(space);
//
//
//            ArrayList<IAVLNode> childrenList = new ArrayList<>();
//
//            for (IAVLNode node: currList) {
//                if (node != null && node.isRealNode()) {
//                    System.out.print(String.valueOf(node.getKey()));
//                    System.out.print("(" + String.valueOf(node.getRank()) + ")" + space);
//                    childrenList.add(node.getLeft());
//                    childrenList.add(node.getRight());
//                }
//                else if (node != null && !node.isRealNode()) {
//                    System.out.print("V");
//                    System.out.print("(" + String.valueOf(node.getRank()) + ")" + space);
//                    childrenList.add(null);
//                    childrenList.add(null);
//
//                }
//                else { //node == null
//                    System.out.print("N");
//                    System.out.print(space);
//
//                    childrenList.add(null);
//                    childrenList.add(null);
//                }
//
//            }
//            boolean onlyNull = true;
//
//            for (int i = 0; i < childrenList.size(); i++) {
//                if (childrenList.get(i) != null) {
//                    onlyNull = false;
//                    break;
//                }
//            }
//            if (onlyNull) {
//                break;
//            }
//            currList = childrenList;
//            childrenList = new ArrayList<>();
//
//            System.out.println();
//            System.out.println();
//        }
//    }
    //main print func is the "bfs_print" one.
//function uses a field called EXTERNAL_NODE for the external node, and uses isRealNode() respectively.
//tree's root field should be called root.

    public void bfs_print(){
        IAVLNode v = this.getRoot();
        int height = v.getHeight();
        IAVLNode[][] table = new IAVLNode[height+1][(int) Math.pow(2,height)];

        Queue<IAVLNode> q = new ArrayDeque<>();


        q.add(v);

        for (int h=0; h <= height; h++){
            int levelsize = q.size();
            for (int i=0; i<levelsize; i++){
                v = q.remove();
                table[h][i] = v;


                if (v.isRealNode() && v.getLeft().isRealNode())
                    q.add(v.getLeft());
                else{
                    q.add(EXTERNAL_NODE);
                }
                if (v.isRealNode() && v.getRight().isRealNode())
                    q.add(v.getRight());
                else{
                    q.add(EXTERNAL_NODE);
                }

            }
        }
        IAVLNode[][] alignedtable = this.aligningPrintTable(table);
        String[][] treetable = this.makeTreeAlike(alignedtable);
        printtreetable(treetable);
    }


    private IAVLNode[][] aligningPrintTable (IAVLNode[][] table){
        int height = this.getRoot().getHeight();
        if (height < 0) {
            return null;
        }
        IAVLNode[][] alignedtable = new IAVLNode[height+1][2*((int) Math.pow(2,height))-1];
        for (int i=0; i<alignedtable.length; i++)
            for (int j=0; j<alignedtable[0].length; j++)
                alignedtable[i][j] = null;


        for (int r=height; r>=0; r--){
            if (r == height){
                for (int i=0; i<table[0].length; i++)
                    alignedtable[r][i*2] = table[r][i];
            } else {

                int firstloc = 0;
                int secondloc = 0;
                boolean firstNodeSeen = false;
                int currnode = 0;

                for (int j=0; j<alignedtable[0].length; j++){
                    if (alignedtable[r+1][j] != null){
                        if (firstNodeSeen){
                            secondloc = j;
                            alignedtable[r][(firstloc+secondloc)/2] = table[r][currnode++];
                            firstNodeSeen = false;
                        } else {
                            firstloc = j;
                            firstNodeSeen = true;
                        }
                    }
                }
            }
        }

        return alignedtable;
    }

    private String[][] makeTreeAlike (IAVLNode[][] alignedtable){
        int height = this.getRoot().getHeight();
        if (height < 0) {
            return null;
        }
        String[][] treetable = new String[(height+1)*3-2][2*((int) Math.pow(2,height))-1];

        for (int r=0; r<treetable.length; r++){
            if (r%3 == 0){
                for (int j=0; j<treetable[0].length; j++) {
                    IAVLNode v = alignedtable[r/3][j];
                    if (v != null && v.isRealNode()) {
                        String k = "" + v.getKey();
                        if (k.length() == 1)
                            k = k + " ";
                        treetable[r][j] = k;
                    } else{
                        if (v != null)
                            treetable[r][j] = "x ";
                        else
                            treetable[r][j] = "  ";
                    }
                }
            }

            else {
                if (r%3 == 1) {
                    for (int j=0; j<treetable[0].length; j++){
                        if (!treetable[r-1][j].equals("  "))
                            treetable[r][j] = "| ";
                        else
                            treetable[r][j] = "  ";
                    }
                } else { //r%3 == 2
                    continue;
                }
            }
        }
        for (int r=0; r<treetable.length; r++){
            if (r%3 == 2){
                boolean write = false;
                for (int j=0; j<treetable[0].length; j++){
                    if (!treetable[r+1][j].equals("  ")){
                        if (write)
                            treetable[r][j] = "__";
                        write = !write;
                    }
                    if (write)
                        treetable[r][j] = "__";
                    else
                        treetable[r][j] = "  ";
                }
            }
        }



        return treetable;
    }

    private void printtreetable (String[][] treetable){
        if (treetable != null) {
            for (int i=0; i< treetable.length; i++){
                for (int j=0; j< treetable[0].length; j++){
                    System.out.print(treetable[i][j]);
                    if (j == treetable[0].length-1)
                        System.out.print("\n");
                }
            }
        }

    }

}
