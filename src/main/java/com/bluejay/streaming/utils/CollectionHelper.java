package com.bluejay.streaming.utils;

import java.util.*;

public class CollectionHelper {

    private CollectionHelper() {}

    public static <E>List<E> putInList(E... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    public static <E> Set<E> putInSet(E... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }
}
