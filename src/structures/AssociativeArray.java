package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Tony
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> cloned = new AssociativeArray<>();
    cloned.pairs = new KVPair[this.pairs.length]; 
    cloned.size = this.size;
    for (int i = 0; i < this.size; i++) {
      cloned.pairs[i] = this.pairs[i] != null ? new KVPair<>(this.pairs[i].key, this.pairs[i].value) : null;
    }
    return cloned;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    if (size == 0) {
      return "{}";
  }
    StringBuilder sb = new StringBuilder();
    sb.append("{ ");
    for (int i = 0; i < size; i++) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append(pairs[i].key).append(": ").append(pairs[i].value);
    }
    sb.append(" }");
    return sb.toString();
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   * @throws KeyNotFoundException
   */
  public void set(K key, V value) throws NullKeyException {
    if (key == null) {
      throw new NullKeyException("Key cannot be null.");
    }
    try {
      int index = find(key);  
      pairs[index].value = value;  
  } catch (KeyNotFoundException e) {
      // Key not found, add new key-value pair
      if (size >= pairs.length) {
        expand();  // Expand pairs array if it's full
      }
      pairs[size++] = new KVPair<>(key, value);
    }
  }//set(K key, V value)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException, NullKeyException {
    if (key == null) {
      throw new KeyNotFoundException("Key cannot be null.");
    }
    int index = find(key);
    if (index >= 0) {
      return pairs[index].value;
    } else {
        throw new KeyNotFoundException("Key not found.");
    }
  }//get(K key)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   */
  public boolean hasKey(K key) {
    if (key == null) {
      return false;  
    }
    try {
      find(key);  
      return true;  
    } catch (KeyNotFoundException e) {
        return false;  
    }
  }//hasKey(K key)


  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    for (int i = 0; i < size; i++) {
      if (pairs[i].key.equals(key)) {
        pairs[i] = pairs[--size];
        pairs[size] = null;
        return;
      }
    }
  }//remove(K key)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  int find(K key) throws KeyNotFoundException {
    for (int i = 0; i < size; i++) {
      if (pairs[i].key.equals(key)) {
        return i;
      }
    }
    throw new KeyNotFoundException();
  }


} // class AssociativeArray
