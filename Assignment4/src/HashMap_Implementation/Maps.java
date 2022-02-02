package HashMap_Implementation;

import java.util.*;

public class Maps {
    //This class consists exclusively of static methods that operate on or return maps

    public static <K, V> List<Map.Entry<K, V>> getMappingsAsList(Map<K, V> map) {
        List<Map.Entry<K, V>> mappings = new ArrayList<>();
        mappings.addAll(map.entrySet());
        return mappings;
    }

    public static <K, V> Map<K, V> getListAsOrderedMap(List<Map.Entry<K, V>> mappings) {
        Map<K, V> map = new LinkedHashMap<>();
        for (Map.Entry<K, V> mapping : mappings) {
            map.put(mapping.getKey(), mapping.getValue());
        }

        return map;
    }

    public static <K, V> Map<K, V> sortByValues(Map<K, V> unsorted, Comparator<? super V> comparator) {
        List<Map.Entry<K, V>> mappings = getMappingsAsList(unsorted);
        mappings.sort(Map.Entry.comparingByValue(comparator));
        return getListAsOrderedMap(mappings);
    }
}
