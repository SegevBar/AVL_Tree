import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTreeExp {

	private IAVLNode virtualLeaf = new AVLNode();
	private IAVLNode root = virtualLeaf;
	private IAVLNode min = virtualLeaf;
	private IAVLNode max = virtualLeaf;
	private int fingerSearchCounter = 0;

	public AVLTreeExp() {};
	public AVLTreeExp(int[] keys) {

		for (int key : keys) {
			this.insertFinger(key, "num" + key);
//			System.out.println("added key:" + key);
//			this.bfs_print();
//			System.out.println("count search steps: " + this.getFingerCounter());

		}
	}

	//finger tree stuff
	public int getFingerCounter() {
		return this.fingerSearchCounter;
	}

	public int insertFinger(int k, String i) {
		int rebalanceActions = 0;

		//if the tree is empty - add the first node as the root
		if (this.empty()) {
			AVLNode firstNode = new AVLNode(k, i);
			this.root = firstNode;
			this.min = firstNode;
			this.max = firstNode;

			//updating the node variables:
			firstNode.setRight(this.virtualLeaf);
			firstNode.setLeft(this.virtualLeaf);
			firstNode.setParent(this.virtualLeaf);
			firstNode.setHeight(0);
			firstNode.setSize();

			this.fingerSearchCounter++;
		}
		//otherwise - find the position to add the new node, add and rebalance
		else {
			IAVLNode parent = this.treePosition(k);
			if (parent.getKey() == k) {  //if key is already in the tree - return (-1)
				return -1;
			}
			IAVLNode child = new AVLNode(k, i);

			//updating the node variables:
			child.setRight(this.virtualLeaf);
			child.setLeft(this.virtualLeaf);
			child.setParent(parent);
			child.setHeight(0);
			child.setSize();

			//add the new node as left child if k is smaller, or right child if k is bigger than parent's key
			if (parent.getKey() < k) {
				parent.setRight(child);
			} else {
				parent.setLeft(child);
			}

			//check if k is smaller then min key
			if (this.min.getKey() > k) {
				this.min = child;
			}
			//check if k is bigger then max key
			if (this.max.getKey() < k) {
				this.max = child;
			}

			this.fingerSearchCounter += searchFinger(k);

			//rebalance the tree
			rebalanceActions += this.rebalance(parent, rebalanceActions);
		}
		return rebalanceActions;
	}

	private int searchFinger(int k) {
		IAVLNode p = this.max;
		if (p.getKey() == k) {
			return 1;
		}
		IAVLNode parent = p.getParent();
		int count = 1;

		// k < max.key
		while (parent.isRealNode() && p.getKey() > k) {
			if (parent.getKey() < k) {
				return treePositionFinger(k, p, count);
			}
			p = parent;
			parent = parent.getParent();
			count++;
		}
		return treePositionFinger(k, p, count);
	}

	private int treePositionFinger(int k, IAVLNode curRoot, int count) {
		IAVLNode p = curRoot;

		while (p.isRealNode()) {
			if (p.getKey() == k) {
				return count;
			}
			//if node.key < k continue with smaller keys on the lest child
			else if (p.getKey() > k) {
				if (p.getLeft().isRealNode()) {
					p = p.getLeft();
					count++;
				}
				//if there is no child - return the parent
				else {
					count++;
					return count;
				}
			}
			//if node.key > k continue with bigger keys on the right child
			else {
				if (p.getRight().isRealNode()) {
					p = p.getRight();
					count++;
				}
				//if there is no child - return the parent
				else {
					count++;
					return count;
				}
			}
		}
		count++;
		return count;
	}

	public void bfs_print(){
		AVLTreeExp.IAVLNode v = this.getRoot();
		int height = v.getHeight();
		AVLTreeExp.IAVLNode[][] table = new AVLTreeExp.IAVLNode[height+1][(int) Math.pow(2,height)];

		Queue<AVLTreeExp.IAVLNode> q = new ArrayDeque<>();


		q.add(v);

		for (int h=0; h <= height; h++){
			int levelsize = q.size();
			for (int i=0; i<levelsize; i++){
				v = q.remove();
				table[h][i] = v;


				if (v.isRealNode() && v.getLeft().isRealNode())
					q.add(v.getLeft());
				else{
					q.add(this.virtualLeaf);
				}
				if (v.isRealNode() && v.getRight().isRealNode())
					q.add(v.getRight());
				else{
					q.add(this.virtualLeaf);
				}

			}
		}
		AVLTreeExp.IAVLNode[][] alignedtable = this.aligningPrintTable(table);
		String[][] treetable = this.makeTreeAlike(alignedtable);
		printtreetable(treetable);
	}


	private AVLTreeExp.IAVLNode[][] aligningPrintTable (AVLTreeExp.IAVLNode[][] table){
		int height = this.getRoot().getHeight();
		if (height < 0) {
			return null;
		}
		AVLTreeExp.IAVLNode[][] alignedtable = new AVLTreeExp.IAVLNode[height+1][2*((int) Math.pow(2,height))-1];
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

	private String[][] makeTreeAlike (AVLTreeExp.IAVLNode[][] alignedtable){
		int height = this.getRoot().getHeight();
		if (height < 0) {
			return null;
		}
		String[][] treetable = new String[(height+1)*3-2][2*((int) Math.pow(2,height))-1];

		for (int r=0; r<treetable.length; r++){
			if (r%3 == 0){
				for (int j=0; j<treetable[0].length; j++) {
					AVLTreeExp.IAVLNode v = alignedtable[r/3][j];
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

	/**
	* public boolean empty()
	*
	* Returns true if and only if the tree is empty.
	*
	 * complexity : O(1)
	*/
	public boolean empty() {
		if (!(this.root.isRealNode())) {
			return true;
		}
		return false;
	}

	/**
	 * private IAVLNode treePosition(int k)
	 *
	 * binary search for node with key == k
	 * return node when node.key == k, else the parent of the node
	 *
	 * complexity : O(log n)
	 */
	private IAVLNode treePosition(int k) {
		IAVLNode p = this.root;

		while (p.isRealNode()) {
			if (p.getKey() == k) {
				return p;
			}
			//if node.key < k continue with smaller keys on the lest child
			else if (p.getKey() > k) {
				if (p.getLeft().isRealNode()) {
					p = p.getLeft();
				}
				//if there is no child - return the parent
				else {
					return p;
				}
			}
			//if node.key > k continue with bigger keys on the right child
			else {
				if (p.getRight().isRealNode()) {
					p = p.getRight();
				}
				//if there is no child - return the parent
				else {
					return p;
				}
			}
		}
		return p;
	}

	/**
	* public String search(int k)
	*
	* Returns the info of an item with key k if it exists in the tree.
	* otherwise, returns null.
	 *
	 * complexity : O(log n)
	*/
	public String search(int k) {
		//find k position in tree
		IAVLNode node = this.treePosition(k);

		if (node.getKey() == k) {
			return node.getValue();
		}
		return null;
	}

	/**
	* public int insert(int k, String i)
	*
	* Inserts an item with key k and info i to the AVL tree.
	* The tree must remain valid, i.e. keep its invariants.
	* Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
	* A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
	* Returns -1 if an item with key k already exists in the tree.
	 *
	 * complexity : 0(log n)
	*/
	public int insert(int k, String i) {
		int rebalanceActions = 0;

		//if the tree is empty - add the first node as the root
		if (this.empty()) {
			AVLNode firstNode = new AVLNode(k, i);
			this.root = firstNode;
			this.min = firstNode;
			this.max = firstNode;

			//updating the node variables:
			firstNode.setRight(this.virtualLeaf);
			firstNode.setLeft(this.virtualLeaf);
			firstNode.setParent(this.virtualLeaf);
			firstNode.setHeight(0);
			firstNode.setSize();
		}
		//otherwise - find the position to add the new node, add and rebalance
		else {
			IAVLNode parent = this.treePosition(k);
			if (parent.getKey() == k) {  //if key is already in the tree - return (-1)
				return -1;
			}
			IAVLNode child = new AVLNode(k, i);

			//updating the node variables:
			child.setRight(this.virtualLeaf);
			child.setLeft(this.virtualLeaf);
			child.setParent(parent);
			child.setHeight(0);
			child.setSize();

			//add the new node as left child if k is smaller, or right child if k is bigger than parent's key
			if (parent.getKey() < k) {
				parent.setRight(child);
			} else {
				parent.setLeft(child);
			}

			//check if k is smaller then min key
			if (this.min.getKey() > k) {
				this.min = child;
			}
			//check if k is bigger then max key
			if (this.max.getKey() < k) {
				this.max = child;
			}

			//rebalance the tree
			rebalanceActions += this.rebalance(parent, rebalanceActions);
		}
		return rebalanceActions;
	}

	/**
	* public int delete(int k)
	*
	* Deletes an item with key k from the binary tree, if it is there.
	* The tree must remain valid, i.e. keep its invariants.
	* Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
	* A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
	* Returns -1 if an item with key k was not found in the tree.
	 *
	 * complexity : O(log n)
	*/
	public int delete(int k) {
		int rebalanceActions = 0;
		IAVLNode toDelete = this.treePosition(k);

		//if there is no node with the key k in the tree - return -1
		if (toDelete.getKey() != k) {
			return -1;
		}

		//update min to it's successor if k is the key of the current minimum node
		if (this.min == this.max) {
			this.min = this.virtualLeaf;
			this.max = this.virtualLeaf;
		}
		if (this.min.getKey() == k) {
			IAVLNode successor = this.findSuccessor(this.min);
			this.min = successor;
		}
		//update max to its' predecessor if k is the key of the current maximum node
		if (this.max.getKey() == k) {
			IAVLNode predecessor = this.findPredecessor(this.max);
			this.max = predecessor;
		}

		IAVLNode startRebalance;
		//check if k node is a leaf
		if (!toDelete.getRight().isRealNode() && !toDelete.getLeft().isRealNode()) {
			startRebalance = this.deleteLeaf(toDelete);
		}
		//check if k node is unary
		else if ((!toDelete.getRight().isRealNode() && toDelete.getLeft().isRealNode()) || (toDelete.getRight().isRealNode() && !toDelete.getLeft().isRealNode())) {
			startRebalance = this.deleteUnar(toDelete);
		}
		//otherwise - k has 2 children
		else {
			startRebalance = this.deleteDouble(toDelete);
		}

		//rebalance the tree
		rebalanceActions += this.rebalance(startRebalance, rebalanceActions);

		return rebalanceActions;
	}


	/**
	 * private IAVLNode deleteLeaf(IAVLNode child)
	 *
	 * child is a leaf
	 * deleting child
	 * return child's parent
	 *
	 * complexity : O(1)
	 */
	private IAVLNode deleteLeaf(IAVLNode child) {
		IAVLNode parent = child.getParent();

		//if x is a leaf and the tree root - the tree becomes empty
		if (child == this.root) {
			this.root = this.virtualLeaf;
		}

		else {
			//if x is a right child
			if (parent.getRight() == child) {
				parent.setRight(this.virtualLeaf);
			}
			//if x is a left child
			else if (parent.getLeft() == child) {
				parent.setLeft(this.virtualLeaf);
			}
		}
		return parent;
	}

	/**
	 * private IAVLNode deleteUnar(IAVLNode child)
	 *
	 * child is an unary node
	 * deleting child
	 * return child's parent
	 *
	 * complexity : O(1)
	 */
	private IAVLNode deleteUnar(IAVLNode child) {
		IAVLNode parent = child.getParent();

		//if x is unary and the tree root - update root.tree to point on x child
		if (child == this.root) {
			if (child.getLeft().isRealNode()) {
				this.root = child.getLeft();
				child.getLeft().setParent(this.virtualLeaf);
			}
			if (child.getRight().isRealNode()) {
				this.root = child.getRight();
				child.getRight().setParent(this.virtualLeaf);
			}
		}


		else {
			if (parent.getLeft() == child) {
				//x is a left child and has only a right child
				if (child.getRight().isRealNode()) {
					IAVLNode right = child.getRight();
					parent.setLeft(right);
					right.setParent(parent);
				}
				//x is a left child and has only a left child
				else if (child.getLeft().isRealNode()) {
					IAVLNode left = child.getLeft();
					parent.setLeft(left);
					left.setParent(parent);
				}
			}
			else if (parent.getRight() == child) {
				//x is a right child and has only a right child
				if (child.getRight().isRealNode()) {
					IAVLNode right = child.getRight();
					parent.setRight(right);
					right.setParent(parent);
				}
				//x is a right child and has only a left child
				else if (child.getLeft().isRealNode()) {
					IAVLNode left = child.getLeft();
					parent.setRight(left);
					left.setParent(parent);
				}
			}
		}
		return parent;
	}

	/**
	 * private IAVLNode deleteDouble(IAVLNode child)
	 *
	 * child has left and right node
	 * swipe child with its' successor
	 * delete child
	 * return original parent of successor or the successor if its' parent is child
	 *
	 * complexity : O(1)
	 */
	private IAVLNode deleteDouble(IAVLNode child) {
		IAVLNode successor = this.findSuccessor(child);
		IAVLNode successorParent = successor.getParent();

		//if the successor parent is the node we want to delete - return successon. else - return the parent of original place successor
		if (successorParent == child) {
			successorParent = successor;
		}

		//if child successor is a leaf
		if (!successor.getRight().isRealNode() && !successor.getLeft().isRealNode()) {
			this.deleteLeaf(successor);
		}
		//if child successor is unary
		else if (!successor.getRight().isRealNode() && successor.getLeft().isRealNode() || successor.getRight().isRealNode() && !successor.getLeft().isRealNode()) {
			this.deleteUnar(successor);
		}

		//update successor to child pointers
		successor.setRight(child.getRight());
		successor.setLeft(child.getLeft());
		successor.setParent(child.getParent());
		child.getRight().setParent(successor);
		child.getLeft().setParent(successor);
		successor.setHeight(child.getHeight());

		//if child was the tree root - update the root to point to successor
		if (this.root == child) {
			this.root = successor;
		}
		//update child parents to point on successor
		else {
			IAVLNode parent = child.getParent();
			if (parent.getRight() == child) {
				parent.setRight(successor);
			}
			else {
				parent.setLeft(successor);
			}
		}
		return successorParent;
	}

	/**
	 * private int rebalance(IAVLNode node, int countActions)
	 *
	 * recursive function that locally rebalancing the tree based on algorithm
	 * keeps count of rebalance actions in countActions
	 * update size variable of relevant nodes
	 * stop condition - got to tree root
	 *
	 * complexity : O(log n)
	 */
	private int rebalance(IAVLNode node, int countActions) {
		//if node is a virtual leaf - we got to the tree root
		if (!node.isRealNode()) {
			return 0;
		}

		int rankDiffRight = node.getHeight() - node.getRight().getHeight();
		int rankDiffLeft = node.getHeight() - node.getLeft().getHeight();

		//if the rank difference is (1,1), (1,2), (2,1) add 0 to countActions
		if ((rankDiffRight == 1 && rankDiffLeft == 1) || (rankDiffRight == 1 && rankDiffLeft == 2) || (rankDiffRight == 2 && rankDiffLeft == 1)) {
			node.setSize();
			countActions += this.rebalance(node.getParent(), countActions);
		}

		//if the rank difference is (0,1) or (1,0) - promote node
		if ((rankDiffRight == 1 && rankDiffLeft == 0) || (rankDiffRight == 0 && rankDiffLeft == 1)) {
			countActions += this.promote(node);
			node.setSize();
			countActions += this.rebalance(node.getParent(), countActions);
		}
		//if the rank difference is (2,2) - demote node
		else if (rankDiffRight == 2 && rankDiffLeft == 2) {
			countActions += this.demote(node);
			node.setSize();
			countActions += this.rebalance(node.getParent(), countActions);
		}
		//if the rank difference is (0,2)
		else if (rankDiffRight == 2 && rankDiffLeft == 0) {
			IAVLNode left = node.getLeft();
			int rankDiffLeftRight = left.getHeight() - left.getRight().getHeight();
			int rankDiffLeftLeft = left.getHeight() - left.getLeft().getHeight();
			//if the rank difference of left child with his children is (1,1) - rotate right and promote left child (relevant for join)
			if (rankDiffLeftRight == 1 && rankDiffLeftLeft == 1) {
				countActions += this.rotateRight(node, left) + this.promote(left);
				node.setSize();
				left.setSize();
				countActions += this.rebalance(left.getParent(), countActions);
			}
			//if the rank difference of left child with his children is (1,2) - rotate right and demote node
			else if (rankDiffLeftRight == 2 && rankDiffLeftLeft == 1) {
				countActions += this.rotateRight(node, left) + this.demote(node);
				node.setSize();
				left.setSize();
				countActions += this.rebalance(left.getParent(), countActions);
			}
			//if the rank difference of left child with his children is (2,1) - double rotate right, demote node and left child, promote left child new parent
			else if (rankDiffLeftRight == 1 && rankDiffLeftLeft == 2) {
				countActions += this.doubleRotateRight(node, left) + this.demote(node) + this.demote(left) + this.promote(left.getParent());
				node.setSize();
				left.setSize();
				left.getParent().setSize();
				countActions += this.rebalance(left.getParent().getParent(), countActions);
			}
		}
		//if the rank difference is (2,0)
		else if (rankDiffRight == 0 && rankDiffLeft == 2) {
			IAVLNode right = node.getRight();
			int rankDiffRightRight = right.getHeight() - right.getRight().getHeight();
			int rankDiffRightLeft = right.getHeight() - right.getLeft().getHeight();
			//if the rank difference of right child with his children is (1,1) - rotate left and promote right child (relevant for join)
			if (rankDiffRightRight == 1 && rankDiffRightLeft == 1) {
				countActions += this.rotateLeft(node, right) + this.promote(right);
				node.setSize();
				right.setSize();
				countActions += this.rebalance(right.getParent(), countActions);
			}
			//if the rank difference of right child with his children is (2,1) - rotate left and demote node
			else if (rankDiffRightRight == 1 && rankDiffRightLeft == 2) {
				countActions += this.rotateLeft(node, right) + this.demote(node);
				node.setSize();
				right.setSize();
				countActions += this.rebalance(right.getParent(), countActions);
			}
			//if the rank difference of right child with his children is (1,2) - double rotate left, demote node and right child, promote right child new parent
			else if (rankDiffRightRight == 2 && rankDiffRightLeft == 1) {
				countActions += this.doubleRotateLeft(node, right) + this.demote(node) + this.demote(right) + this.promote(right.getParent());
				node.setSize();
				right.setSize();
				right.getParent().setSize();
				countActions += this.rebalance(right.getParent().getParent(), countActions);
			}
		}
		//if the rank difference is (3,1)
		else if (rankDiffRight == 1 && rankDiffLeft == 3) {
			IAVLNode right = node.getRight();
			int rankdiffRightRight = right.getHeight() - right.getRight().getHeight();
			int rankDiffRightLeft = right.getHeight() - right.getLeft().getHeight();
			//if the rank difference of right child with his children is (1,1) - rotate left and demote node and promote right child
			if (rankdiffRightRight == 1 && rankDiffRightLeft == 1) {
				countActions += this.rotateLeft(node, right) + this.demote(node) + this.promote(right);
				node.setSize();
				right.setSize();
				countActions += this.rebalance(right.getParent(), countActions);
			}
			//if the rank difference of right child with his children is (2,1) - rotate left, demote node twice
			else if (rankdiffRightRight == 1 && rankDiffRightLeft == 2) {
				countActions += this.rotateLeft(node, right) + this.demote(node) + this.demote(node);
				node.setSize();
				right.setSize();
				countActions += this.rebalance(right.getParent(), countActions);
			}
			//if the rank difference of right child with his children is (1,2) - double rotate left, demote node twice, demote right child and promote right child new parent
			else if (rankdiffRightRight == 2 && rankDiffRightLeft == 1) {
				countActions += this.doubleRotateLeft(node, right) + this.demote(node) + this.demote(node) + this.demote(right) + this.promote(right.getParent());
				node.setSize();
				right.setSize();
				right.getParent().setSize();
				countActions += this.rebalance(right.getParent().getParent(), countActions);
			}
		}
		//if the rank difference is (1,3)
		else if (rankDiffRight == 3 && rankDiffLeft == 1) {
			IAVLNode left = node.getLeft();
			int rankDiffLeftRight = left.getHeight() - left.getRight().getHeight();
			int rankDiffLeftLeft = left.getHeight() - left.getLeft().getHeight();
			//if the rank difference of left child with his children is (1,1) - rotate right and demote node and promote left child
			if (rankDiffLeftRight == 1 && rankDiffLeftLeft == 1) {
				countActions += this.rotateRight(node, left) + this.demote(node) + this.promote(left);
				node.setSize();
				left.setSize();
				countActions += this.rebalance(left.getParent(), countActions);
			}
			//if the rank difference of left child with his children is (1,2) - rotate right, demote node twice
			else if (rankDiffLeftRight == 2 && rankDiffLeftLeft == 1) {
				countActions += this.rotateRight(node, left) + this.demote(node) + this.demote(node);
				node.setSize();
				left.setSize();
				countActions += this.rebalance(left.getParent(), countActions);
			}
			//if the rank difference of left child with his children is (2,1) - double rotate right, demote node twice, demote left child and promote left child new parent
			else if (rankDiffLeftRight == 1 && rankDiffLeftLeft == 2) {
				countActions += this.doubleRotateRight(node, left) + this.demote(node) + this.demote(node) + this.demote(left) + this.promote(left.getParent());
				node.setSize();
				left.setSize();
				left.getParent().setSize();
				countActions += this.rebalance(left.getParent().getParent(), countActions);
			}
		}
		this.virtualLeaf.setLeft(null);
		this.virtualLeaf.setRight(null);
		return countActions;
	}

	/**
	 * private int promote(IAVLNode node)
	 *
	 * promotes node height + 1
	 *
	 * complexity : O(1)
	 */
	private int promote(IAVLNode node) {
		node.setHeight(node.getHeight() + 1);
		return 1;
	}

	/**
	 * private int demote(IAVLNode node)
	 *
	 * demotes node height - 1
	 *
	 * complexity : O(1)
	 */
	private int demote(IAVLNode node) {
		node.setHeight(node.getHeight() - 1);
		return 1;
	}

	/**
	 * private int rotateRight(IAVLNode node, IAVLNode left)
	 *
	 * locally rotate the tree to the right
	 *
	 * complexity : O(1)
	 */
	private int rotateRight(IAVLNode node, IAVLNode left) {
		//if the rotation involves the tree root - update it accordingly
		if (node == this.root) {
			this.root = left;
		}
		//otherwise - update node parent pointer to left child
		else {
			IAVLNode parent = node.getParent();
			if (parent.getRight() == node) {
				parent.setRight(left);
			} else {
				parent.setLeft(left);
			}
		}
		//switch pointers
		left.setParent(node.getParent());
		node.setLeft(left.getRight());
		node.getLeft().setParent(node);
		left.setRight(node);
		node.setParent(left);

		return 1;
	}

	/**
	 * private int rotateLeft(IAVLNode node, IAVLNode right)
	 *
	 * locally rotate the tree to the left
	 *
	 * complexity : O(1)
	 */
	private int rotateLeft(IAVLNode node, IAVLNode right) {
		//if the rotation involves the tree root - update it accordingly
		if (node == this.root) {
			this.root = right;
		}
		//otherwise - update x parent pointer to y
		else {
			IAVLNode parent = node.getParent();
			if (parent.getRight() == node) {
				parent.setRight(right);
			} else {
				parent.setLeft(right);
			}
		}
		//switch pointers
		right.setParent(node.getParent());
		node.setRight(right.getLeft());
		node.getRight().setParent(node);
		right.setLeft(node);
		node.setParent(right);

		return 1;
	}

	/**
	 * private int doubleRotateRight(IAVLNode node, IAVLNode left)
	 *
	 * locally double rotate the tree to the right
	 *
	 * complexity : O(1)
	 */
	private int doubleRotateRight(IAVLNode node, IAVLNode left) {
		IAVLNode leftRight = left.getRight();
		//if the rotation involves the tree root - update it accordingly
		if (node == this.root) {
			this.root = leftRight;
		}
		//switch pointers
		this.rotateLeft(left,leftRight);
		this.rotateRight(node,leftRight);
		return 2;
	}

	/**
	 * private int doubleRotateLeft(IAVLNode node, IAVLNode right)
	 *
	 * locally double rotate the tree to the left
	 *
	 * complexity : O(1)
	 */
	private int doubleRotateLeft(IAVLNode node, IAVLNode right) {
		IAVLNode rightLeft = right.getLeft();
		//if the rotation involves the tree root - update it accordingly
		if (node == this.root) {
			this.root = rightLeft;
		}
		//switch pointers
		this.rotateRight(right,rightLeft);
		this.rotateLeft(node,rightLeft);

		return 2;
	}

	/**
	 * private IAVLNode findSuccessor(IAVLNode node)
	 *
	 * find node's successor using the algorithm thought in class
	 *
	 * complexity : O(log n)
	 */
	private IAVLNode findSuccessor(IAVLNode node) {
		//if x is max - it has no successor
		if (node == this.max) {
			return this.virtualLeaf;
		}

		//successor algorithm
		IAVLNode curr = node;
		if (curr.getRight().isRealNode()) {
			curr = curr.getRight();
			while (curr.getLeft().isRealNode()) {
				curr = curr.getLeft();
			}
			return curr;
		}
		else {
			IAVLNode parent = curr.getParent();
			while (parent.isRealNode() && parent.getRight() == curr) {
				curr = curr.getParent();
				parent = parent.getParent();
			}
			if (parent.getLeft() == curr) {
				return parent;
			}
		}
		return curr;
	}

	/**
	 * private IAVLNode findPredecessor(IAVLNode node)
	 *
	 * find node's predecessor using the algorithm thought in class
	 *
	 * complexity : O(log n)
	 */
	private IAVLNode findPredecessor(IAVLNode node) {
		//if x is min - it has no predecessor
		if (node == this.min) {
			return this.virtualLeaf;
		}

		//predecessor algorithm
		IAVLNode curr = node;
		if (curr.getLeft().isRealNode()) {
			curr = curr.getLeft();
			while (curr.getRight().isRealNode()) {
				curr = curr.getRight();
			}
			return curr;
		}
		else {
			IAVLNode parent = curr.getParent();
			while (parent.isRealNode() && parent.getLeft() == curr) {
				curr = curr.getParent();
				parent = parent.getParent();
			}
			if (parent.getRight() == curr) {
				return parent;
			}
		}
		return curr;
	}

	/**
	* public String min()
	*
	* Returns the info of the item with the smallest key in the tree,
	* or null if the tree is empty.
	 *
	 * complexity : O(1)
	*/
	public String min()	{
		return this.min.getValue();
	}

	/**
	* public String max()
	*
	* Returns the info of the item with the largest key in the tree,
	* or null if the tree is empty.
	 *
	 * complexity : O(1)
	*/
	public String max()	{
		return this.max.getValue();
	}

	/**
	 * public IAVLNode getMin()
	 *
	 * return the min node
	 *
	 * complexity : O(1)
	 */
	public IAVLNode getMin() {
		return this.min;
	}

	/**
	 * public IAVLNode getMax()
	 *
	 * return the max node
	 *
	 * complexity : O(1)
	 */
	public IAVLNode getMax() {
		return this.max;
	}

	/**
	* public int[] keysToArray()
	*
	* Returns a sorted array which contains all keys in the tree,
	* or an empty array if the tree is empty.
	 *
	 * complexity : O(n)
	*/
	public int[] keysToArray() {
		//create an empty array to return
		int[] orederedKeys = new int[this.size()];
		//create an ordered array of tree nodes
		IAVLNode[] orderedNodes = new IAVLNode[this.size()];
		this.nodeToArray(this.root, orderedNodes, 0);

		//get key from ordered array of tree nodes
		for (int i = 0; i < orderedNodes.length; i++) {
			orederedKeys[i] = orderedNodes[i].getKey();
		}
		return orederedKeys;
	}

	/**
	* public String[] infoToArray()
	*
	* Returns an array which contains all info in the tree,
	* sorted by their respective keys,
	* or an empty array if the tree is empty.
	 *
	 * complexity : O(n)
	*/
	public String[] infoToArray() {
		//create an empty array to return
		String[] orederedInfo = new String[this.size()];
		//create an ordered array of tree nodes
		IAVLNode[] orderedNodes = new IAVLNode[this.size()];
		this.nodeToArray(this.root, orderedNodes, 0);

		//get info from ordered array of tree nodes
		for (int i = 0; i < orderedNodes.length; i++) {
			orederedInfo[i] = orderedNodes[i].getValue();
		}
		return orederedInfo;
	}

	/**
	 * private int nodeToArray(IAVLNode curr, IAVLNode[] orderedNodes, int i)
	 *
	 * recursivly inOrder traversal the tree and creates an ordered array of tree nodes
	 *
	 * complexity : O(n)
	 */
	private int nodeToArray(IAVLNode curr, IAVLNode[] orderedNodes, int i) {
		if (i == orderedNodes.length) {
			return i-1;
		}

		if (curr.isRealNode()) {
			i = this.nodeToArray(curr.getLeft(), orderedNodes, i);
			orderedNodes[i] = curr;
			i++;
			i = this.nodeToArray(curr.getRight(), orderedNodes, i);
		}
		return i;
	}

	/**
	* public int size()
	*
	* Returns the number of nodes in the tree.
	 *
	 * complexity : O(1)
	*/
	public int size() {
	   return this.root.getSize();
	}

	/**
	* public int getRoot()
	*
	* Returns the root AVL node, or null if the tree is empty
	 *
	 * complexity : O(1)
	*/
	public IAVLNode getRoot() {
		return this.root;
	}

	/**
	 * setRoot(IAVLNode newRoot)
	 *
	 * set the tree root to be newRoot
	 *
	 * complexity : O(1)
	 */
	private void setRoot(IAVLNode newRoot) {
		this.root = newRoot;
	}


	/**
	* public AVLTree[] split(int x)
	*
	* splits the tree into 2 trees according to the key x.
	* Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	*
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
	* postcondition: none
	 *
	 * complexity :
	*/
	public AVLTreeExp[] split(int x) {
		IAVLNode xNode = treePosition(x);
		AVLTreeExp[] result = new AVLTreeExp[2];

		result[0] = new AVLTreeExp(); // < x
		result[1] = new AVLTreeExp(); // > x

		//if x has children we set them first:
		if (xNode.getLeft().isRealNode()) {
			result[0].setRoot(xNode.getLeft());
		}
		if (xNode.getRight().isRealNode()) {
			result[1].setRoot(xNode.getRight());
		}

		IAVLNode yNode = xNode.getParent();

		//disconnect our xNode
		xNode.getLeft().setParent(this.virtualLeaf);
		xNode.getRight().setParent(this.virtualLeaf);
		xNode.setLeft(this.virtualLeaf);
		xNode.setRight(this.virtualLeaf);
		xNode.setParent(this.virtualLeaf);

		while (yNode.isRealNode()) {
			System.out.println(yNode.getKey());
			IAVLNode copyNode = new AVLNode(yNode.getKey(), yNode.getValue());

			if (yNode.getKey() < x) {
				AVLTreeExp lessThan = new AVLTreeExp();
				if (yNode.getLeft().isRealNode()) {
					lessThan.setRoot(yNode.getLeft());
					lessThan.getRoot().setParent(lessThan.virtualLeaf);
				}
				result[0].join(copyNode, lessThan);
			}
			else {
				AVLTreeExp moreThan = new AVLTreeExp();
				if (yNode.getRight().isRealNode()) {
					moreThan.setRoot(yNode.getRight());
					moreThan.getRoot().setParent(moreThan.virtualLeaf);
				}
				result[1].join(copyNode, moreThan);
			}
			yNode = yNode.getParent();
		}

		return result;
	}

	/**
	* public int join(IAVLNode x, AVLTree t)
	*
	* joins t and x with the tree.
	* Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
	* postcondition: none
	 *
	 * complexity :
	*/
	public int join(IAVLNode x, AVLTreeExp t) {
		int resultReturn = Math.abs(this.root.getHeight() - t.getRoot().getHeight()) + 1;

		if (t.empty() && this.empty()) {
			this.max = x;
			this.min = x;
			this.insert(x.getKey(), x.getValue());
			return resultReturn;
		}
		else {
			if (t.empty()) {
				this.insert(x.getKey(), x.getValue());
				return resultReturn;
			}
			if (this.empty()) {
				this.root = t.getRoot();
				this.max = t.getMax();
				this.min = t.getMin();
				this.insert(x.getKey(), x.getValue());
				return resultReturn;
			}
		}

		//neither is empty
		//check which tree should be on which side
		AVLTreeExp rightTree;
		AVLTreeExp leftTree;
		if (this.getRoot().getKey() > x.getKey()) {
			rightTree = this;
			leftTree = t;
		}
		else {
			rightTree = t;
			leftTree = this;
		}

		//compare heights
		int heightDelta = rightTree.getRoot().getHeight() - leftTree.getRoot().getHeight();
		if (heightDelta <= 1 && heightDelta >= -1) {
			x.setRight(rightTree.getRoot());
			x.getRight().setParent(x);
			x.setLeft(leftTree.getRoot());
			x.getLeft().setParent(x);
			this.root = x;
			this.max = rightTree.getMax();
			this.min = leftTree.getMin();
			x.setParent(this.virtualLeaf);
			x.setSize();
			x.setHeight(Math.max(x.getRight().getHeight(), x.getLeft().getHeight()) + 1);
			return 1;
		}
		else {
			if (heightDelta > 0) {
				IAVLNode tempNode = rightTree.getRoot();
				while (tempNode.getHeight() > leftTree.getRoot().getHeight()) {
					tempNode = tempNode.getLeft();
				}
				x.setParent(tempNode.getParent());
				x.setLeft(leftTree.getRoot());
				x.getLeft().setParent(x);
				x.setRight(tempNode);
				x.getRight().setParent(x);
				if (x.getParent() != this.virtualLeaf) {
					x.getParent().setLeft(x);
				}
				this.root = rightTree.getRoot();
				x.setHeight(Math.max(x.getRight().getHeight(), x.getLeft().getHeight()) + 1);
				this.rebalance(x, 0);

			}
			if (heightDelta < 0) {
				IAVLNode tempNode = leftTree.getRoot();
				while (tempNode.getHeight() > rightTree.getRoot().getHeight()) {
					tempNode = tempNode.getRight();
				}
				x.setParent(tempNode.getParent());
				x.setRight(rightTree.getRoot());
				x.setLeft(tempNode);
				x.getRight().setParent(x);
				x.getLeft().setParent(x);
				if (x.getParent() != this.virtualLeaf) {
					x.getParent().setRight(x);
				}
				this.root = leftTree.getRoot();
				x.setHeight(Math.max(x.getRight().getHeight(), x.getLeft().getHeight()) + 1);
				this.rebalance(x, 0);

			}
		}

		this.max = rightTree.max;
		this.min = leftTree.min;
		return resultReturn;
	}

	/**
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 *
	 * complexity :
	 */
	public interface IAVLNode{	
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).

		public void setSize(); //sets the size of the node's sub tree
		public int getSize(); //return the size veriable of the node
	}

	/**
	* public class AVLNode
	*
	* If you wish to implement classes other than AVLTree
	* (for example AVLNode), do it in this file, not in another file.
	*
	* This class can and MUST be modified (It must implement IAVLNode).
	 *
	 * complexity : O(1)
	*/
	public class AVLNode implements IAVLNode{
		IAVLNode left = null;
		IAVLNode right = null;
		IAVLNode parent = null;
		int height = -1;
		int key = -1;
		String info = null;
		int size = 0;

		/**
		* empty constructor
		*/
		public AVLNode() { }

		/**
		* constructor for a given key and value
		*/
		public AVLNode(int key, String info) {
			this.key = key;
			this.info = info;
			this.size = 1;
		}

		/**
		* return this.key
		 *
		 * complexity : O(1)
		*/
		public int getKey() {
			return this.key;
		}

		/**
		* return this.info
		 *
		 * complexity : O(1)
		*/
		public String getValue() {
			return this.info;
		}

		/**
		* set this.left = node
		 *
		 * complexity : O(1)
		*/
		public void setLeft(IAVLNode node) {
			this.left = node;
		}

		/**
		* return this.left
		 *
		 * complexity : O(1)
		*/
		public IAVLNode getLeft() {
			return this.left;
		}

		/**
		* set this.right = node
		 *
		 * complexity : O(1)
		*/
		public void setRight(IAVLNode node) {
			this.right = node;
		}

		/**
		* return this.right
		 *
		 * complexity : O(1)
		*/
		public IAVLNode getRight() {
			return this.right;
		}

		/**
		* set this.parent = node
		 *
		 * complexity : O(1)
		*/
		public void setParent(IAVLNode node) {
			this.parent = node;
		}

		/**
		* return this.parent
		 *
		 * complexity : O(1)
		*/
		public IAVLNode getParent()	{
			return this.parent;
		}

		/**
		* return true if not virtualNode
		 *
		 * complexity : O(1)
		*/
		public boolean isRealNode() {
			if (this.key != -1 ) {
				return true;
			}
			return false;
		}

		/**
		* set this.height = this.rank
		 *
		 * complexity : O(1)
		*/
		public void setHeight(int height) {
			this.height = height;
		}

		/**
		* return this.height
		 *
		 * complexity : O(1)
		*/
		public int getHeight() {
			return this.height;
		}

		/**
		* return this.size
		 *
		 * complexity : O(1)
		*/
		public int getSize() {
			return this.size;
		}

		/**
		* set the size of ths sub tree to be the sum of children sizes + 1
		 *
		 * complexity : O(1)
		*/
		public void setSize() {
			this.size = this.right.getSize() + this.left.getSize() + 1;
		}
	}

}
  
