package util;

import java.util.Collection;
import java.util.List;

public class CollectionIntersection {

    public static <T, C extends Collection<T>> C findIntersection(C newCollection,
                                                                  Collection<T>... collections) {
        boolean first = true;
        for (Collection<T> collection : collections) {
            if (first) {
                newCollection.addAll(collection);
                first = false;
            } else {
                newCollection.retainAll(collection);
            }
        }
        return newCollection;
    }

    public static Integer maxValue(List<Integer> valueList){
        Integer max = Integer.MIN_VALUE;
        for(Integer integer : valueList){
            if(integer != null && integer > max){
                max = integer;
            }
        }
        return max;
    }

    public static Integer minValue(List<Integer> valueList){
        Integer min = Integer.MAX_VALUE;
        for(Integer integer : valueList){
            if(integer != null && integer < min){
                min = integer;
            }
        }
        return min;
    }
}
