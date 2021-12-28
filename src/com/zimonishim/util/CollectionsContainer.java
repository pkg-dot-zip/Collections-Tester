package com.zimonishim.util;

import java.util.*;

public class CollectionsContainer {

    public static Collection<List> getLists(){
        return new ArrayList<>(Arrays.asList(
                new ArrayList<>(),
                new LinkedList<>(),
                new Stack<>(),
                new Vector<>()
        ));
    }

    public static Collection<Set> getSets(){
        return new ArrayList<>(Arrays.asList(
                new HashSet<>(),
                new LinkedHashSet<>(),
                new TreeSet<>()
        ));
    }
}
