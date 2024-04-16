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
    return null; // STUB
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    return "{}"; // STUB
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
      throw new NullKeyException();
  }
  int index = find(key);
  if (index >= 0) {
      pairs[index].value = value;  // Update existing value
  } else {
      if (size == pairs.length) {
          expand();  // Expand array if needed
      }
      pairs[size++] = new KVPair<>(key, value); // Add new key-value pair
  }
}

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException, NullKeyException {
    if (key == null) {
      throw new NullKeyException();
    }
    int index = find(key);
    if (index >= 0) {
      return pairs[index].value;
    } else {
        throw new KeyNotFoundException();
    }
  }

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   * @throws KeyNotFoundException
   */
  public boolean hasKey(K key) throws KeyNotFoundException {
    if (key == null) {
      return false;
  }
    int index = find(key);
    return index >= 0;
  }


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
  }

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
