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
	*/
	public boolean empty() {
		if (!(this.root.isRealNode())) {
			return true;
		}
		return false;
	}

	/**
	 * binary search for node with key == k
	 * @return node when node.key == k, else the parent of the node
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

		//check if k node is a leaf
		if (!x.getRight().isRealNode() && !x.getLeft().isRealNode()) {
			IAVLNode y = this.deleteLeaf(x);
		}





	}


	/**
	 *
	 * @param x
	 * @return
	 */
	private IAVLNode deleteLeaf(IAVLNode x) {

	}

	/**
	 *
	 * @param x
	 * @return
	 */
	private IAVLNode deleteUnar(IAVLNode x) {

	}

	/**
	 *
	 * @param x
	 * @return
	 */
	private IAVLNode deleteDouble(IAVLNode x) {

	}

	/**
	 *
	 * @param x
	 * @param countActions
	 * @return
	 */
	private int rebalance(IAVLNode x, int countActions) {
		return 0;
	}

	/**
	 *
	 * @param x
	 * @return
	 */
	private int promote(IAVLNode x) {
		x.setTempRank(x.getRank() + 1);
		return 1;
	}

	/**
	 *
	 * @param x
	 * @return
	 */
	private int demote(IAVLNode x) {
		x.setTempRank(x.getRank() - 1);
		return 1;
	}

	/**
	 *
	 * @param x
	 * @param y
	 * @return
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
	 * @param x
	 * @param y
	 * @return
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
	 * @param x
	 * @param y
	 * @return
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
	 * @param x
	 * @param y
	 * @return
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
	 * @param x
	 * @return
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
	 * @param x
	 * @return
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
	*/
	public String min()	{
		return this.min.getValue();
	}

	/**
	* public String max()
	*
	* Returns the info of the item with the largest key in the tree,
	* or null if the tree is empty.
	*/
	public String max()	{
		return this.max.getValue();
	}

	/**
	* public int[] keysToArray()
	*
	* Returns a sorted array which contains all keys in the tree,
	* or an empty array if the tree is empty.
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
	*/
	public int size() {
	   return return this.root.getSize();
	}

	/**
	* public int getRoot()
	*
	* Returns the root AVL node, or null if the tree is empty
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
	*/
	public int join(IAVLNode x, AVLTree t) {
		return -1;
	}

	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
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
	*/
	public class AVLNode implements IAVLNode{
		AVLNode left;
		AVLNode right;
		AVLNode parent;
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
		* @return this.key
		*/
		public int getKey() {
			return this.key;
		}

		/**
		* @return this.info
		*/
		public String getValue() {
			return this.info;
		}

		/**
		* @param node
		* set this.left = node
		*/
		public void setLeft(IAVLNode node) {
			if (node != null) {
				this.left = node;
			}
		}

		/**
		* @return this.left
		*/
		public IAVLNode getLeft() {
			return this.left;
		}

		/**
		* @param node
		* set this.right = node
		*/
		public void setRight(IAVLNode node) {
			if (node != null) {
				this.right = node;
			}
		}

		/**
		* @return this.right
		*/
		public IAVLNode getRight() {
			return this.right;
		}

		/**
		* @param node
		* set this.parent = node
		*/
		public void setParent(IAVLNode node) {
			if (node != null) {
				this.parent = node;
			}
		}

		/**
		* @return this.parent
		*/
		public IAVLNode getParent()	{
			return this.parent;
		}

		/**
		* @return true if not virtualNode
		*/
		public boolean isRealNode() {
			if (this.key != -1) {
				return true;
			}
			return false;
		}

		/**
		* @param height
		* set this.height = this.rank
		*/
		public void setHeight(int height) {
			this.height = height;
		}

		/**
		* @return this.height
		*/
		public int getHeight() {
			return this.height;
		}

		/**
		* @return this.rank
		*/
		public int getRank() {
			return this.rank;
		}

		/**
		 * set specific rank value
		 */
		public void setTempRank(int rank) {
			this.rank = rank;
		}

		/**
		* set the rank of the node to be the maximum between his children ranks + 1
		*/
		public void resetRank() {
			this.rank = Math.max(this.right.getRank(), this.left.getRank()) + 1;
		}

		/**
		* @return this.size
		*/
		public int getSize() {
			return this.size;
		}

		/**
		* set the size of ths sub tree to be the sum of children sizes + 1
		*/
		public void setSize() {
			this.size = this.right.getSize() + this.left.getSize() + 1;
		}

   }

}
  
