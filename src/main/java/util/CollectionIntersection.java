package util;

import java.util.Collection;

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

}
