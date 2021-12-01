import java.util.List;

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
	private IAVLNode root = virtualLeaf;
	private IAVLNode min;
	private IAVLNode max;

	/**
	 * empty constructor
	 */
	public AVLTree() {	}

	/**
	 * non empty constructor
	 * gets a list of AVLNodes and insert them to the tree
	 * for debugging
	 */
	public AVLTree(List<IAVLNode> nodes) {
		for (IAVLNode node : nodes) {
			this.insert(node.getKey(), node.getValue());
		}
	}

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
  	if (this.root == virtualLeaf) {
  		return true;
	}
  	return false;
  }

  private IAVLNode treePosition(int k, IAVLNode root) {
	IAVLNode p = this.root;

	while
  }

	/**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k) {

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
	  return 420;	// to be replaced by student code
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
   public int delete(int k)
   {
	   return 421;	// to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
	   return "minDefaultString"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   return "maxDefaultString"; // to be replaced by student code
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
        return new int[33]; // to be replaced by student code
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
        return new String[55]; // to be replaced by student code
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size()
   {
	   return 422; // to be replaced by student code
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
	   return null;
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
   public AVLTree[] split(int x)
   {
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
   public int join(IAVLNode x, AVLTree t)
   {
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
		* set the rank of the node to be the maximum between his children ranks + 1
		*/
	   public void setRank() {
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
  
