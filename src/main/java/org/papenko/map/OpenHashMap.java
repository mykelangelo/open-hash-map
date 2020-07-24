package org.papenko.map;

import java.util.Map;
import java.util.function.Consumer;

/**
 * HashMap without removal but with clear operation
 * with open addressing collision avoiding tactic
 * (more specifically - quadratic probing technique).
 * <p>
 * Note that this implementation is not synchronized.
 * If multiple threads access a hash map concurrently,
 * and at least one of the threads modifies the map structurally,
 * it must be synchronized externally.
 */
public class OpenHashMap {
    private static final int EXPONENTIAL_GROWTH_FACTOR = 2;
    private static final int INVERTED_MAX_LOAD_FACTOR = 2;
    private static final int INITIAL_CAPACITY = 32;
    private static final int FIRST_PRIME = 7;
    private static final int SECOND_PRIME = 3;
    /**
     * Value that is returned by {@link #get} and {@link #put} methods when no value by key was found.
     * This value can be stored in this map. Defaults to {@link Long#MIN_VALUE}.
     */
    private final long defaultValue;
    private int[] keys = new int[INITIAL_CAPACITY];
    private long[] values = new long[INITIAL_CAPACITY];
    private int size = 0;
    private boolean containsZeroKey = false;
    private long zeroValue = 0L;

    public OpenHashMap() {
        defaultValue = Long.MIN_VALUE;
    }

    public OpenHashMap(long defaultValue) {
        this.defaultValue = defaultValue;
    }

    private OpenHashMap(long defaultValue, int capacity) {
        this.defaultValue = defaultValue;
        keys = new int[capacity];
        values = new long[capacity];
    }

    private static int calculateOrdinal(int hash, int attempt, int capacity) {
        return Math.abs(hash + attempt * FIRST_PRIME + ((int) Math.pow(attempt, 2)) * SECOND_PRIME) % capacity;
    }

    private static void addOneEntry(int key, long value, OpenHashMap map) {
        if (key == 0) {
            map.containsZeroKey = true;
            map.zeroValue = value;
            map.size++;
            return;
        }

        int ordinal = key % map.keys.length;
        for (int attempt = 1; map.keys[ordinal] != 0; attempt++) {
            if (map.keys[ordinal] == key) {
                map.values[ordinal] = value;
                return;
            }
            ordinal = calculateOrdinal(key, attempt, map.keys.length);
        }
        map.keys[ordinal] = key;
        map.values[ordinal] = value;
        map.size++;
    }

    public long getDefaultValue() {
        return defaultValue;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(int key) {
        if (size == 0) {
            return false;
        }

        if (key == 0) {
            return containsZeroKey;
        }

        for (int attempt = 0, ordinal = key % keys.length; attempt <= size; ordinal = calculateOrdinal(key, ++attempt, keys.length)) {
            if (keys[ordinal] == 0) {
                return false;
            }
            if (keys[ordinal] == key) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(long value) {
        if (size == 0) {
            return false;
        }

        if (containsZeroKey && zeroValue == value) {
            return true;
        }

        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == 0) {
                continue;
            }
            if (values[i] == value) {
                return true;
            }
        }
        return false;
    }

    public long get(int key) {
        if (size == 0) {
            return defaultValue;
        }

        if (key == 0) {
            return containsZeroKey ? zeroValue : defaultValue;
        }

        for (int attempt = 0, ordinal = key % keys.length; attempt <= size; ordinal = calculateOrdinal(key, ++attempt, keys.length)) {
            if (keys[ordinal] == 0) {
                continue;
            }
            if (keys[ordinal] == key) {
                return values[ordinal];
            }
        }
        return defaultValue;
    }

    public long put(int key, long value) {
        long previous = get(key);
        if (size + 1 > keys.length / INVERTED_MAX_LOAD_FACTOR) {
            extendEntriesAndAddAll(1, map -> addOneEntry(key, value, map));
        } else {
            addOneEntry(key, value, this);
        }
        return previous;
    }

    private void extendEntriesAndAddAll(int newEntriesSize, Consumer<OpenHashMap> addNewEntries) {
        int newSize = newEntriesSize + this.size;
        int newCapacity = newSize * EXPONENTIAL_GROWTH_FACTOR * INVERTED_MAX_LOAD_FACTOR;
        OpenHashMap temporaryMap = new OpenHashMap(defaultValue, newCapacity);

        if (containsZeroKey) {
            addOneEntry(0, zeroValue, temporaryMap);
        }
        for (int i = 0, length = keys.length; i < length; i++) {
            if (keys[i] != 0) {
                addOneEntry(keys[i], values[i], temporaryMap);
            }
        }

        addNewEntries.accept(temporaryMap);

        keys = temporaryMap.keys;
        values = temporaryMap.values;
        if (temporaryMap.containsZeroKey) {
            containsZeroKey = true;
            zeroValue = temporaryMap.zeroValue;
        }
        size = newSize;
    }

    public void putAll(Map<Integer, Long> map) {
        if (map.entrySet().size() + size > keys.length / INVERTED_MAX_LOAD_FACTOR) {
            extendEntriesAndAddAll(map.entrySet().size(), temporaryMap -> {
                for (Map.Entry<Integer, Long> newEntry : map.entrySet()) {
                    addOneEntry(newEntry.getKey(), newEntry.getValue(), temporaryMap);
                }
            });
        } else {
            for (Map.Entry<Integer, Long> entry : map.entrySet()) {
                addOneEntry(entry.getKey(), entry.getValue(), this);
            }
        }
    }

    public void clear() {
        size = 0;
        keys = new int[INITIAL_CAPACITY];
        values = new long[INITIAL_CAPACITY];
        containsZeroKey = false;
    }

    /**
     * @return keys&values arrays capacity (not accounting for {@link #zeroValue}).
     */
    public int capacity() {
        return keys.length;
    }
}
