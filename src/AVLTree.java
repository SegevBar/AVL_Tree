import java.util.List;
import java.util.Stack;

/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {

	private IAVLNode virtualLeaf = new AVLNode();
	private IAVLNode root;
	private IAVLNode min;
	private IAVLNode max;

	/**
	 * empty constructor
	 */
	public AVLTree() {
		this.root = virtualLeaf;
		this.min = virtualLeaf;
		this.max = virtualLeaf;
	}

	/**
	 * non empty constructor
	 * gets a list of AVLNodes and insert them to the tree
	 * for debugging
	 */
	public AVLTree(int[] keys) {
		this();
		char info = 'a';
		for (int key : keys) {
			this.insert(key, String.valueOf(info));
			info = (char)(info + 1);
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
	 * binary search for node with key == k
	 * return node when node.key == k, else the parent of the node
	 *
	 * complexity : O(log n)
	 */
	private IAVLNode treePosition(int k) {
		IAVLNode p = this.root;

		while (p.getLeft() != this.virtualLeaf && p.getRight() != this.virtualLeaf) {
			if (p.getKey() == k) {
				return p;
			} else if (p.getKey() > k) {  //if node.key < k continue with smaller keys on the lest child
				if (p.getLeft().isRealNode()) {
					p = p.getLeft();
				} else {   //if there is no child - return the parent
					return p;
				}
			} else {   //if node.key > k continue with bigger keys on the right child
				if (p.getRight().isRealNode()) {
					p = p.getRight();
				} else {    //if there is no child - return the parent
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
			firstNode.resetRank();
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
			child.resetRank();
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

			//update the size variable of all the nodes from child to tree root
			this.updateSize(child);
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
		IAVLNode x = this.treePosition(k);

		//if there is no node with the key k in the tree - return -1
		if (max.getKey() != k) {
			return -1;
		}

		//update min to it's successor if k is the key of the current minimum node
		if (this.min.getKey() == k) {
			IAVLNode successor = this.findSuccessor(this.min);
			this.min = successor;
		}
		//update max to its' predecessor if k is the key of the current maximum node
		if (this.max.getKey() == k) {
			IAVLNode predecessor = this.findPredecessor(this.max);
			this.max = predecessor;
		}

		IAVLNode y;
		//check if k node is a leaf
		if (!x.getRight().isRealNode() && !x.getLeft().isRealNode()) {
			y = this.deleteLeaf(x);
		}
		//check if k node is unary
		else if ((!x.getRight().isRealNode() && x.getLeft().isRealNode()) || (x.getRight().isRealNode() && !x.getLeft().isRealNode())) {
			y = this.deleteUnar(x);
		}
		//otherwise - k has 2 children
		else {
			y = this.deleteDouble(x);
		}

		//rebalance the tree
		rebalanceActions += this.rebalance(y, rebalanceActions);

		//update the size variable of all the nodes from x parent (y) to tree root
		this.updateSize(y);

		return rebalanceActions;
	}


	/**
	 *
	 * complexity : O(1)
	 */
	private IAVLNode deleteLeaf(IAVLNode x) {
		IAVLNode y = x.getParent();

		//if x is a leaf and the tree root - the tree becomes empty
		if (!y.isRealNode()) {
			this.root = this.virtualLeaf;
		}

		else {
			//if x is a right child
			if (y.getRight() == x) {
				y.setRight(this.virtualLeaf);
			}
			//if x is a left child
			else if (y.getLeft() == x) {
				y.setLeft(this.virtualLeaf);
			}
		}
		return y;
	}

	/**
	 *
	 * complexity : O(1)
	 */
	private IAVLNode deleteUnar(IAVLNode x) {
		boolean rightChild = false;
		IAVLNode y = x.getParent();

		//if x is unary and the tree root - update root.tree to point on x child
		if (!y.isRealNode()) {
			if (x.getLeft().isRealNode()) {
				this.root = x.getLeft();
				x.getLeft().setParent(this.virtualLeaf);
			}
			if (x.getRight().isRealNode()) {
				this.root = x.getRight();
				x.getRight().setParent(this.virtualLeaf);
				rightChild = true;
			}
		}

		else if (y.getLeft() == x) {
			//x is a left child and has only a right child
			if (rightChild) {
				IAVLNode z = x.getRight();
				y.setLeft(z);
				z.setParent(y);
			}
			//x is a left child and has only a left child
			else {
				IAVLNode z = x.getLeft();
				y.setLeft(z);
				z.setParent(y);
			}
		}
		else if (y.getRight() == x) {
			//x is a right child and has only a right child
			if (rightChild) {
				IAVLNode z = x.getRight();
				y.setRight(z);
				z.setParent(y);
			}
			//x is a right child and has only a left child
			else {
				IAVLNode z = x.getLeft();
				y.setRight(z);
				z.setParent(y);
			}
		}
		return y;
	}

	/**
	 *
	 * complexity : O(1)
	 */
	private IAVLNode deleteDouble(IAVLNode x) {
		IAVLNode y = this.findSuccessor(x);
		IAVLNode z = y.getParent();

		//if x successor is a leaf
		if (!y.getRight().isRealNode() && !y.getLeft().isRealNode()) {
			this.deleteLeaf(y);
		}
		//if x successor is unary
		else if (!y.getRight().isRealNode() && y.getLeft().isRealNode() || y.getRight().isRealNode() && !y.getLeft().isRealNode()) {
			this.deleteUnar(y);
		}

		//update y to x pointers
		y.setRight(x.getRight());
		y.setLeft(x.getLeft());
		x.getRight().setParent(y);
		x.getLeft().setParent(y);

		//if x was the tree root - update the root to point to y
		if (this.root == x) {
			this.root = y;
			y.setParent(this.virtualLeaf);
		}
		//update x parents to point on y
		else {
			IAVLNode eps = x.getParent();
			if (eps.getRight() == x) {
				eps.setRight(y);
			}
			else {
				eps.setLeft(y);
			}
			y.setParent(eps);
		}
		return z;
	}

	/**
	 *
	 * complexity : O(log n)
	 */
	private int rebalance(IAVLNode x, int countActions) {
		int rankDeltaRight = x.getRank() - x.getRight().getRank();
		int rankDeltaLeft = x.getRank() - x.getLeft().getRank();

		//if the rank delta is (1,1), (1,2), (2,1) stop rebalance
		if ((rankDeltaRight == 1 && rankDeltaLeft == 1) || (rankDeltaRight == 1 && rankDeltaLeft == 2) || (rankDeltaRight == 2 && rankDeltaLeft == 1)) {
			return 0;
		}

		//if the rank delta is (0,1) or (1,0) - promote x and call recursivly to rebalance on x parent
		if ((rankDeltaRight == 1 && rankDeltaLeft == 0) || (rankDeltaRight == 0 && rankDeltaLeft == 1)) {
			countActions += this.promote(x);
			countActions += this.rebalance(x.getParent(), countActions);
		}
		//if the rank delta is (2,2) - demote x and call recursivly to rebalance on x parent
		else if (rankDeltaRight == 2 && rankDeltaLeft == 2) {
			countActions += this.demote(x.getParent());
			this.rebalance(x.getParent(), countActions);
		}
		//if the rank delta is (0,2)
		else if (rankDeltaRight == 2 && rankDeltaLeft == 0) {
			IAVLNode y = x.getLeft();
			int rankDeltaLeftRight = y.getRank() - y.getRight().getRank();
			int rankDeltaLeftLeft = y.getRank() - y.getLeft().getRank();
			//if the rank delta of left child with his children is (1,2) - rotate right and demote x
			if (rankDeltaLeftRight == 2 && rankDeltaLeftLeft == 1) {
				countActions += this.rotateRight(x, y) + this.demote(x);
			}
			//if the rank delta of left child with his children is (2,1) - double rotate right, demote x and y, promote y right child
			else if (rankDeltaLeftRight == 1 && rankDeltaLeftLeft == 2) {
				countActions += this.doubleRotateRight(x, y) + this.demote(x) + this.demote(y) + this.promote(y.getRight());
			}
		}
		//if the rank delta is (2,0)
		else if (rankDeltaRight == 0 && rankDeltaLeft == 2) {
			IAVLNode y = x.getRight();
			int rankDeltaRightRight = y.getRank() - y.getRight().getRank();
			int rankDeltaRightLeft = y.getRank() - y.getLeft().getRank();
			//if the rank delta of right child with his children is (2,1) - rotate left and demote x
			if (rankDeltaRightRight == 1 && rankDeltaRightLeft == 2) {
				countActions += this.rotateLeft(x, y) + this.demote(x);
			}
			//if the rank delta of right child with his children is (1,2) - double rotate left, demote x and y, promote y right child
			else if (rankDeltaRightRight == 2 && rankDeltaRightLeft == 1) {
				countActions += this.doubleRotateLeft(x, y) + this.demote(x) + this.demote(y) + this.promote(y.getLeft());
			}
		}
		//if the rank delta is (3,1)
		else if (rankDeltaRight == 1 && rankDeltaLeft == 3) {
			IAVLNode y = x.getRight();
			int rankDeltaRightRight = y.getRank() - y.getRight().getRank();
			int rankDeltaRightLeft = y.getRank() - y.getLeft().getRank();
			//if the rank delta of right child with his children is (1,1) - rotate left and demote x and promote y
			if (rankDeltaRightRight == 1 && rankDeltaRightLeft == 1) {
				countActions += this.rotateLeft(x, y) + this.demote(x) + this.promote(y);
			}
			//if the rank delta of right child with his children is (2,1) - rotate left, demote x twice
			else if (rankDeltaRightRight == 1 && rankDeltaRightLeft == 2) {
				countActions += this.rotateLeft(x, y) + this.demote(x) + this.demote(x);
				countActions += this.rebalance(x.getParent(), countActions);
			}
			//if the rank delta of right child with his children is (1,2) - double rotate left, demote x twice, demote y and promote y left child
			else if (rankDeltaRightRight == 2 && rankDeltaRightLeft == 1) {
				countActions += this.doubleRotateLeft(x, y) + this.demote(x) + this.demote(x) + this.demote(y) + this.promote(y.getLeft());
			}
		}
		//if the rank delta is (1,3)
		else if (rankDeltaRight == 3 && rankDeltaLeft == 1) {
			IAVLNode y = x.getLeft();
			int rankDeltaLeftRight = y.getRank() - y.getRight().getRank();
			int rankDeltaLeftLeft = y.getRank() - y.getLeft().getRank();
			//if the rank delta of right child with his children is (1,1) - rotate right and demote x and promote y
			if (rankDeltaLeftRight == 1 && rankDeltaLeftLeft == 1) {
				countActions += this.rotateRight(x, y) + this.demote(x) + this.promote(y);
			}
			//if the rank delta of right child with his children is (2,1) - rotate right, demote x twice
			else if (rankDeltaLeftRight == 1 && rankDeltaLeftLeft == 2) {
				countActions += this.rotateRight(x, y) + this.demote(x) + this.demote(x);
				countActions += this.rebalance(x.getParent(), countActions);
			}
			//if the rank delta of right child with his children is (1,2) - double rotate right, demote x twice, demote y and promote y right child
			else if (rankDeltaLeftRight == 2 && rankDeltaLeftLeft == 1) {
				countActions += this.doubleRotateRight(x, y) + this.demote(x) + this.demote(x) + this.demote(y) + this.promote(y.getRight());
			}
		}

		return countActions;
	}

	/**
	 *
	 * complexity : O(1)
	 */
	private int promote(IAVLNode x) {
		x.setTempRank(x.getRank() + 1);
		return 1;
	}

	/**
	 *
	 * complexity : O(1)
	 */
	private int demote(IAVLNode x) {
		x.setTempRank(x.getRank() - 1);
		return 1;
	}

	/**
	 *
	 * complexity : O(1)
	 */
	private int rotateRight(IAVLNode x, IAVLNode y) {
		//if the rotation involves the tree root - update it accordingly
		if (x == this.root) {
			this.root = y;
		}
		//switch pointers
		x.setLeft(y.getRight());
		x.getLeft().setParent(x);
		y.setRight(x);
		x.setParent(y);

		return 1;
	}

	/**
	 *
	 * complexity : O(1)
	 */
	private int rotateLeft(IAVLNode x, IAVLNode y) {
		//if the rotation involves the tree root - update it accordingly
		if (x == this.root) {
			this.root = y;
		}
		//switch pointers
		x.setRight(y.getLeft());
		x.getRight().setParent(x);
		y.setLeft(x);
		x.setParent(y);

		return 1;
	}

	/**
	 *
	 * complexity : O(1)
	 */
	private int doubleRotateRight(IAVLNode x, IAVLNode y) {
		IAVLNode z = y.getRight();
		//if the rotation involves the tree root - update it accordingly
		if (x == this.root) {
			this.root = z;
		}
		//switch pointers
		this.rotateLeft(y,z);
		this.rotateRight(x,z);
		return 2;
	}

	/**
	 *
	 * complexity : O(1)
	 */
	private int doubleRotateLeft(IAVLNode x, IAVLNode y) {
		IAVLNode z = y.getRight();
		//if the rotation involves the tree root - update it accordingly
		if (x == this.root) {
			this.root = z;
		}
		//switch pointers
		this.rotateRight(y,z);
		this.rotateLeft(x,z);
		return 2;
	}

	/**
	 *
	 * complexity : O(log n)
	 */
	private IAVLNode findSuccessor(IAVLNode x) {
		//if x is max - it has no successor
		if (x == this.max) {
			return null;
		}

		//successor algorithm
		IAVLNode curr = x;
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
	 *
	 * complexity : O(log n)
	 */
	private IAVLNode findPredecessor(IAVLNode x) {
		//if x is min - it has no predecessor
		if (x == this.min) {
			return null;
		}

		//predecessor algorithm
		IAVLNode curr = x;
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
		//create a help stack
		Stack<IAVLNode> helpStack = new Stack<>();

		//inOrder using a stack
		IAVLNode curr = this.root;
		for (int i = 0; i < orederedKeys.length; i++) {
			while (curr.getLeft().isRealNode()) {
				helpStack.push(curr);
				curr = curr.getLeft();
			}
			curr = helpStack.pop();
			orederedKeys[i] = curr.getKey();
			curr = curr.getRight();
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
		//create a help stack
		Stack<IAVLNode> helpStack = new Stack<>();

		//inOrder using a stack
		IAVLNode curr = this.root;
		for (int i = 0; i < orederedInfo.length; i++) {
			while (curr.getLeft().isRealNode()) {
				helpStack.push(curr);
				curr = curr.getLeft();
			}
			curr = helpStack.pop();
			orederedInfo[i] = curr.getValue();
			curr = curr.getRight();
		}
		return orederedInfo;
	}

	/**
	 * Update size of node x and all the way to the root
	 *
	 * complexity : O(log n)
	 */
	private void updateSize(IAVLNode x) {
		IAVLNode p = x;

		//update size from x to the root with setSize()
		while (p.getParent().isRealNode()) {
			p.setSize();
			p = p.getParent();
		}
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
	public AVLTree[] split(int x) {
		return null;
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
	public int join(IAVLNode x, AVLTree t) {
		return -1;
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

		public int getRank(); //return current rank
		public void resetRank(); //sets the rank of the node
		public void setTempRank(int delta); //set specific rank value
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
		IAVLNode left;
		IAVLNode right;
		IAVLNode parent;
		int rank = -1;
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
			if (node != null) {
				this.left = node;
			}
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
			if (node != null) {
				this.right = node;
			}
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
			if (node != null) {
				this.parent = node;
			}
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
			if (this.key != -1) {
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
		* return this.rank
		 *
		 * complexity : O(1)
		*/
		public int getRank() {
			return this.rank;
		}

		/**
		 * set specific rank value
		 *
		 * complexity : O(1)
		 */
		public void setTempRank(int rank) {
			this.rank = rank;
		}

		/**
		* set the rank of the node to be the maximum between his children ranks + 1
		 *
		 * complexity : O(1)
		*/
		public void resetRank() {
			this.rank = Math.max(this.right.getRank(), this.left.getRank()) + 1;
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
  
