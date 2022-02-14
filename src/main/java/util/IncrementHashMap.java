package util;

import java.util.*;

public class IncrementHashMap {

    public static<K> void incrementValue(Map<K, Integer> map, K key)
    {
        // get the value of the specified key
        Integer count = map.get(key);

        // if the map contains no mapping for the key,
        // map the key with a value of 1
        if (count == null) {
            map.put(key, 1);
        }
        // else increment the found value by 1
        else {
            map.put(key, count + 1);
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
