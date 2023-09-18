package robot.trainer;

import java.util.ArrayList;
import java.util.Objects;

public class CustomHTable<K, V> {

    private ArrayList<CustomHNode<K,V>> buckets;

    // Max number of buckets
    private int numBuckets;

    // Number of buckets populated
    private int occupiedBuckets;

    /**
     * Constructor for Custom Hash Tables
     */
    public CustomHTable() {

        this.numBuckets = 20000; // Should be on order of magnitude of unique words in training-text
        this.buckets = new ArrayList<>();
        this.occupiedBuckets = 0;

        // Get our empty chain ready
        for(int i = 0; i < numBuckets; i++) {
            buckets.add(null);
        }
    }

    /**
     * Generates hashcode of an element with specified key
     * @param key key of node to generate hashcode from
     * @return hashcode corresponding to the key of specified node
     */
    private int hashCodeGen(K key) { return Objects.hashCode(key); }


    /**
     * Converts hashCode of node key into an index to determine what bucket node belongs to
     * @param key key of the node whose index is being searched for
     * @return the index of the node in the list of buckets.
     */
    private int getBucketIndex(K key) {

        int hashCode = hashCodeGen(key);
        int index = hashCode % numBuckets;

        // In case we get negative hashCode
        if(hashCode < 0) {
            index *= -1;
        }

        return index;
    }

    /**
     * Removes a CustomHNode with the specified key from the Hashtable
     * @param key key of node to be removed
     * @return true: Node with correct key is removed successfully from Table | false: otherwise
     */
    public boolean remove(K key) {

        // Get bucket-index and hashcode of item to be removed, then access first node in this bucket
        final int index = getBucketIndex(key);
        final int hashCode = hashCodeGen(key);
        CustomHNode<K,V> currentNode = buckets.get(index);

        // Search through chain of nodes to remove correct node
        CustomHNode<K, V> prevNode = null; // There is no prevNode at first

        while(currentNode != null) {
            // While we have not reached the end of the chain, keep looking.

            if(currentNode.getKey().equals(key) && hashCode == currentNode.getHashCode()){
                // Once we find the correct node, end the loop
                break;
            }

            // If the current node is not the correct one, move to the next node
            prevNode = currentNode;
            currentNode = currentNode.getNextNode();
        }

        if(currentNode == null) {
            // Meaning we reached the end of chain without finding correct node
            return false;
        }

        // Remove Node if found
        reduceSize();

        if(prevNode != null) {
            // Meaning the node to remove is not the first node in chain
            prevNode.setNextNode(currentNode.getNextNode());
        }
        else {
            // Meaning the node to remove is the first node in chain
            buckets.set(index, currentNode.getNextNode());
        }

        return true;
    }

    /**
     * Returns the value of the node with the specified key. Precondition: The key must exist
     * @param key key of the node whose value is to be returned
     * @return value of the node with given key. Null if the node with the key does not exist
     */
    public V get(K key) {

        final int index = getBucketIndex(key);
        final int hashCode = hashCodeGen(key);
        CustomHNode<K,V> currentNode = buckets.get(index);

        while(currentNode != null) {

            if(currentNode.getKey().equals(key) && hashCode == currentNode.getHashCode()){
                return currentNode.getValue();
            }

            currentNode = currentNode.getNextNode();
        }

        return null;
    }


    /**
     * Adds an entry to the Hashtable in the form of a CustomHNode
     * @param key key of the node to be added
     * @param value value of the node to be added
     * @return true: if the key already exists | false: if the key did not exist
     */
    public void add(K key, V value) {

        final int index = getBucketIndex(key);
        final int hashCode = hashCodeGen(key);
        CustomHNode<K,V> currentNode = buckets.get(index);

        // Check for duplicates of node to be added
        while(currentNode != null) {

            if(currentNode.getKey().equals(key) && hashCode == currentNode.getHashCode()){
                currentNode.setValue(value);
                return;
            }
            currentNode = currentNode.getNextNode();
        }

        // Add new node to front of the list
        increaseSize();
        CustomHNode<K,V> newNode = new CustomHNode<>(key, value, hashCode);
        newNode.setNextNode(buckets.get(index)); // Move first existing node to second spot in chain
        buckets.set(index, newNode);

        // If occupied number of buckets gets too close to max, dynamically expand table
        dynamicallyExpandTable();

    }

    /**
     * Checks whether occupied number of buckets occupies equal to or more than 80% of available buckets.
     * If they do, this function dynamically doubles the number of buckets in the table. Otherwise, does not mutate
     * pre-existing table
     */
    private void dynamicallyExpandTable() {

        /*
         *   We don't just append null values to end of table,
         *   because there must be an even distribution across all buckets.
        */

        if((double) occupiedBuckets / numBuckets >= 0.8) {

            // Empty out table and double size
            ArrayList<CustomHNode<K, V> > temp = buckets;
            buckets = new ArrayList<>();
            numBuckets *= 2;
            occupiedBuckets = 0;

            for (int i = 0; i < numBuckets; i++) {
                buckets.add(null);
            }

            // Populate table Depth-first
            for (CustomHNode<K, V> headNode : temp) {
                while (headNode != null) {
                    add(headNode.getKey(), headNode.getValue());
                    headNode = headNode.getNextNode();
                }
            }

        }

    }

    /**
     * Returns number of occupied buckets, AKA the size of the Table
     * @return integer representing the number of occupied buckets in Hashtable
     */
    public int getSize(){
        return this.occupiedBuckets;
    }

    /**
     * Reduces the number of occupied nodes by one. Only used when remove() is called successfully
     */
    private void reduceSize() { this.occupiedBuckets--; }

    /**
     * Increases the number of occupied nodes by one. Only used when add() is called successfully
     */
    private void increaseSize() { this.occupiedBuckets++; }

    /**
     * Determines whether the Table is empty or not
     * @return true: table is empty (no occupied buckets) | false: table is not empty (occupied buckets exist)
     */
    public boolean isEmpty() { return getSize() == 0;}
}
