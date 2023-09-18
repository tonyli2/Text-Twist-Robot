package robot.trainer;

public class CustomHNode<K, V> {

    private K key;
    private V value;
    private int hashCode;
    private CustomHNode<K, V> nextNode; // Points to next HashNode

    /**
     * Constructor for the Custom Hashtable Node
     * @param key key of this node
     * @param value value of this node
     * @param hashCode hashCode of this node
     */
    public CustomHNode(K key, V value, int hashCode) {
        this.key = key;
        this.value = value;
        this.hashCode = hashCode;
    }

    /**
     * Getter method for key of node
     * @return key of the node
     */
    public K getKey() {
        return key;
    }

    /**
     * Getter method for value of node
     * @return value of node
     */
    public V getValue() {
        return value;
    }

    /**
     * Setter method to set value of node
     * @param value value of this node
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Getter method for the hashCode of node
     * @return hashCode of node
     */
    public int getHashCode() {
        return hashCode;
    }

    /**
     * Getter method for the node that follows the current node in the chain
     * @return the node that follows the current node in the chain
     */
    public CustomHNode<K, V> getNextNode() {
        return nextNode;
    }

    /**
     * Setter method for the node that follows the current node in the chain
     */
    public void setNextNode(CustomHNode<K, V> nextNode) {
        this.nextNode = nextNode;
    }
}
