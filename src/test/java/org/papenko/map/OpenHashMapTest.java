package org.papenko.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OpenHashMapTest {

    private OpenHashMap map;

    @BeforeEach
    void beforeEach() {
        map = new OpenHashMap();
    }

    @Test
    void size_shouldReturnZero_whenMapWasAlwaysEmpty() {
        int actual = map.size();

        assertEquals(0, actual);
    }

    @Test
    void size_shouldReturnOne_whenMapContainsOneEntry() {
        map.put(1, 2L);

        int actual = map.size();

        assertEquals(1, actual);
    }

    @Test
    void size_shouldReturnTwo_whenMapContainsTwoEntries() {
        map.put(1, 2L);
        map.put(3, 4L);

        int actual = map.size();

        assertEquals(2, actual);
    }

    @Test
    void isEmpty_shouldReturnTrue_whenMapWasAlwaysEmpty() {
        boolean actual = map.isEmpty();

        assertTrue(actual);
    }

    @Test
    void isEmpty_shouldReturnTrue_whenMapBecameEmpty() {
        map.put(1, 2L);
        map.clear();

        boolean actual = map.isEmpty();

        assertTrue(actual);
    }

    @Test
    void isEmpty_shouldReturnFalse_whenMapContainsOneEntry() {
        map.put(1, 2L);

        boolean actual = map.isEmpty();

        assertFalse(actual);
    }

    @Test
    void isEmpty_shouldReturnFalse_whenMapContainsTwoEntries() {
        map.put(1, 2L);
        map.put(3, 4L);

        boolean actual = map.isEmpty();

        assertFalse(actual);
    }

    @Test
    void containsKey_shouldReturnFalse_whenMapIsEmpty() {
        boolean actual = map.containsKey(1);

        assertFalse(actual);
    }

    @Test
    void containsKey_shouldReturnFalse_whenMapDoesNotContainThatKey() {
        map.put(2, 1L);

        boolean actual = map.containsKey(1);

        assertFalse(actual);
    }

    @Test
    void containsKey_shouldReturnFalse_whenMapDoesNotContainZeroKey() {
        map.put(2, 1L);

        boolean actual = map.containsKey(0);

        assertFalse(actual);
    }

    @Test
    void containsKey_shouldReturnTrue_whenMapContainsZeroKey() {
        map.put(0, 1L);

        boolean actual = map.containsKey(0);

        assertTrue(actual);
    }

    @Test
    void containsKey_shouldReturnTrue_whenMapContainsOnlyThatKey() {
        map.put(1, 2L);

        boolean actual = map.containsKey(1);

        assertTrue(actual);
    }

    @Test
    void containsKey_shouldReturnTrue_whenMapContainsNotOnlyThatKey() {
        map.put(1, 2L);
        map.put(2, 2L);

        boolean actual = map.containsKey(1);

        assertTrue(actual);
    }

    @Test
    void containsKey_shouldReturnTrue_whenMapIsOccupiedFully() {
        map.put(1, 1L);
        map.put(33, 2L);
        map.put(65, 3L);
        map.put(97, 4L);
        map.put(129, 5L);
        map.put(161, 6L);
        map.put(193, 7L);
        map.put(225, 8L);
        map.put(257, 9L);
        map.put(289, 10L);
        map.put(321, 11L);
        map.put(353, 12L);
        map.put(385, 13L);
        map.put(417, 14L);
        map.put(449, 15L);
        map.put(481, 16L);

        boolean actual = map.containsKey(1);

        assertTrue(actual);
    }

    @Test
    void containsKey_shouldReturnFalse_whenMapIsOccupiedFullyWithDifferentValuesOfTheSameOrdinalOfFirstAttempt() {
        map.put(1, 2L);
        map.put(33, 2L);
        map.put(65, 2L);
        map.put(97, 2L);
        map.put(129, 2L);
        map.put(161, 2L);
        map.put(193, 2L);
        map.put(225, 2L);
        map.put(257, 2L);
        map.put(289, 2L);
        map.put(321, 2L);
        map.put(353, 2L);
        map.put(385, 2L);
        map.put(417, 2L);
        map.put(449, 2L);
        map.put(481, 2L);

        boolean actual = map.containsKey(513);

        assertFalse(actual);
    }

    @Test
    void containsValue_shouldReturnFalse_whenMapIsEmpty() {
        boolean actual = map.containsValue(1L);

        assertFalse(actual);
    }

    @Test
    void containsValue_shouldReturnFalse_whenMapDoesNotContainThatValue() {
        map.put(1, 2L);

        boolean actual = map.containsValue(1L);

        assertFalse(actual);
    }

    @Test
    void containsValue_shouldReturnTrue_whenMapContainsOnlyThatValue() {
        map.put(1, 1L);

        boolean actual = map.containsValue(1L);

        assertTrue(actual);
    }

    @Test
    void containsValue_shouldReturnTrue_whenMapContainsOnlyZeroKey() {
        map.put(0, 1L);

        boolean actual = map.containsValue(1L);

        assertTrue(actual);
    }

    @Test
    void containsValue_shouldReturnTrue_whenMapContainsNotOnlyThatValue() {
        map.put(1, 2L);
        map.put(2, 3L);

        boolean actual = map.containsValue(3L);

        assertTrue(actual);
    }

    @Test
    void get_shouldReturnDefaultDefaultValue_whenMapIsEmpty() {
        long actual = map.get(3);

        assertEquals(Long.MIN_VALUE, actual);
    }

    @Test
    void get_shouldReturnValue_whenMapContainsThatValue() {
        map.put(3, 1L);

        long actual = map.get(3);

        assertEquals(1L, actual);
    }

    @Test
    void put_shouldIncreaseSizeByOne_whenCalledOnce() {
        map.put(3, 1L);

        int actual = map.size();

        assertEquals(1, actual);
    }

    @Test
    void put_shouldIncreaseSizeByTwo_whenCalledTwiceWithUniqueKeys() {
        map.put(3, 1L);
        map.put(2, 1L);

        int actual = map.size();

        assertEquals(2, actual);
    }

    @Test
    void put_shouldIncreaseSizeByOne_whenCalledTwiceWithSameKey() {
        map.put(3, 1L);
        map.put(3, 2L);

        int actual = map.size();

        assertEquals(1, actual);
    }

    @Test
    void put_shouldIncreaseSizeByOne_whenCalledOnceWithZeroKey() {
        map.put(0, 1L);

        int actual = map.size();

        assertEquals(1, actual);
    }

    @Test
    void put_shouldIncreaseSizeByTWo_whenCalledTwiceWithCollisionOccurring() {
        map.put(1, 1L);
        map.put(33, 1L);

        int actual = map.size();

        assertEquals(2, actual);
    }

    @Test
    void put_shouldIncreaseSizeByPlenty_whenAddingMoreThanAllowedSizeWithUniqueKeysWithZeroKeyLast() {
        map.put(1, 1L);
        map.put(2, 2L);
        map.put(3, 2L);
        map.put(4, 2L);
        map.put(5, 2L);
        map.put(6, 2L);
        map.put(7, 2L);
        map.put(8, 2L);
        map.put(9, 2L);
        map.put(10, 2L);
        map.put(11, 2L);
        map.put(12, 2L);
        map.put(13, 2L);
        map.put(14, 2L);
        map.put(15, 2L);
        map.put(16, 2L);
        map.put(0, 2L);

        int actualSize = map.size();
        int actualCapacity = map.capacity();

        assertEquals(17 * 4, actualCapacity);
        assertEquals(17, actualSize);
        assertEquals(2L, map.get(0));
        assertEquals(1L, map.get(1));
        assertEquals(2L, map.get(2));
        assertEquals(2L, map.get(3));
        assertEquals(2L, map.get(4));
        assertEquals(2L, map.get(5));
        assertEquals(2L, map.get(6));
        assertEquals(2L, map.get(7));
        assertEquals(2L, map.get(8));
        assertEquals(2L, map.get(9));
        assertEquals(2L, map.get(10));
        assertEquals(2L, map.get(11));
        assertEquals(2L, map.get(12));
        assertEquals(2L, map.get(13));
        assertEquals(2L, map.get(14));
        assertEquals(2L, map.get(15));
        assertEquals(2L, map.get(16));
    }

    @Test
    void put_shouldIncreaseSizeByPlenty_whenAddingMoreThanAllowedSizeWithUniqueKeysWithZeroKeyFirst() {
        map.put(0, 2L);
        map.put(1, 1L);
        map.put(2, 2L);
        map.put(3, 2L);
        map.put(4, 2L);
        map.put(5, 2L);
        map.put(6, 2L);
        map.put(7, 2L);
        map.put(8, 2L);
        map.put(9, 2L);
        map.put(10, 2L);
        map.put(11, 2L);
        map.put(12, 2L);
        map.put(13, 2L);
        map.put(14, 2L);
        map.put(15, 2L);
        map.put(16, 2L);

        int actualSize = map.size();
        int actualCapacity = map.capacity();

        assertEquals(17 * 4, actualCapacity);
        assertEquals(17, actualSize);
        assertEquals(2L, map.get(0));
        assertEquals(1L, map.get(1));
        assertEquals(2L, map.get(2));
        assertEquals(2L, map.get(3));
        assertEquals(2L, map.get(4));
        assertEquals(2L, map.get(5));
        assertEquals(2L, map.get(6));
        assertEquals(2L, map.get(7));
        assertEquals(2L, map.get(8));
        assertEquals(2L, map.get(9));
        assertEquals(2L, map.get(10));
        assertEquals(2L, map.get(11));
        assertEquals(2L, map.get(12));
        assertEquals(2L, map.get(13));
        assertEquals(2L, map.get(14));
        assertEquals(2L, map.get(15));
        assertEquals(2L, map.get(16));
    }

    @Test
    void putAll_shouldIncreaseSizeByPlenty_whenAddingMoreThanAllowedSizeWithUniqueKeys() {
        Map<Integer, Long> hashMap = new HashMap<>();
        hashMap.put(1, 1L);
        hashMap.put(2, 2L);
        hashMap.put(3, 2L);
        hashMap.put(4, 2L);
        hashMap.put(5, 2L);
        hashMap.put(6, 2L);
        hashMap.put(7, 2L);
        hashMap.put(8, 2L);
        hashMap.put(9, 2L);
        hashMap.put(10, 2L);
        hashMap.put(11, 2L);
        hashMap.put(12, 2L);
        hashMap.put(13, 2L);
        hashMap.put(14, 2L);
        hashMap.put(15, 2L);
        hashMap.put(16, 2L);
        hashMap.put(0, 2L);
        map.putAll(hashMap);

        int actualSize = map.size();
        int actualCapacity = map.capacity();

        assertEquals(17 * 4, actualCapacity);
        assertEquals(17, actualSize);
        assertEquals(2L, map.get(0));
        assertEquals(1L, map.get(1));
        assertEquals(2L, map.get(2));
        assertEquals(2L, map.get(3));
        assertEquals(2L, map.get(4));
        assertEquals(2L, map.get(5));
        assertEquals(2L, map.get(6));
        assertEquals(2L, map.get(7));
        assertEquals(2L, map.get(8));
        assertEquals(2L, map.get(9));
        assertEquals(2L, map.get(10));
        assertEquals(2L, map.get(11));
        assertEquals(2L, map.get(12));
        assertEquals(2L, map.get(13));
        assertEquals(2L, map.get(14));
        assertEquals(2L, map.get(15));
        assertEquals(2L, map.get(16));
    }

    @Test
    void putAll_shouldNotIncreaseSize_whenEmptyMapIsAdded() {
        map.putAll(new HashMap<>());

        int actual = map.size();

        assertEquals(0, actual);
    }

    @Test
    void putAll_shouldIncreaseSizeByOne_whenMapWithOneEntryIsAdded() {
        Map<Integer, Long> mapToBeAdded = new HashMap<>();
        mapToBeAdded.put(1, 2L);
        map.putAll(mapToBeAdded);

        int actual = map.size();

        assertEquals(1, actual);
    }

    @Test
    void putAll_shouldIncreaseSizeByOne_whenMapWithZeroKeyIsAdded() {
        Map<Integer, Long> mapToBeAdded = new HashMap<>();
        mapToBeAdded.put(0, 2L);
        map.putAll(mapToBeAdded);

        int actual = map.size();

        assertEquals(1, actual);
    }

    @Test
    void putAll_shouldIncreaseSizeByTwo_whenMapWithTwoEntriesIsAdded() {
        Map<Integer, Long> mapToBeAdded = new HashMap<>();
        mapToBeAdded.put(1, 2L);
        mapToBeAdded.put(3, 4L);
        map.putAll(mapToBeAdded);

        int actual = map.size();

        assertEquals(2, actual);
    }

    @Test
    void clear_shouldChangeNothing_whenMapIsEmpty() {
        map.clear();

        int actual = map.size();

        assertEquals(0, actual);
    }

    @Test
    void clear_shouldClearMap_whenMapContainsOneEntry() {
        map.put(1, 2L);
        map.clear();

        int actual = map.size();

        assertEquals(0, actual);
    }

    @Test
    void clear_shouldClearMap_whenMapContainsTwoEntries() {
        map.put(1, 2L);
        map.put(3, 4L);
        map.clear();

        int actual = map.size();

        assertEquals(0, actual);
    }

    @Test
    void getDefaultValue_shouldReturnDefaultDefaultValue_whenNoCustomValueWasSet() {
        long actual = map.getDefaultValue();

        assertEquals(Long.MIN_VALUE, actual);
    }

    @Test
    void getDefaultValue_shouldReturnCustomDefaultValue_whenCustomValueWasSet() {
        OpenHashMap mapWithCustomDefaultValue = new OpenHashMap(Long.MAX_VALUE);

        long actual = mapWithCustomDefaultValue.getDefaultValue();

        assertEquals(Long.MAX_VALUE, actual);
    }
}