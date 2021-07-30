package com.zimonishim.util;

import java.util.*;

public class CollectionsContainer {

    public static List<List> getLists(){
        return new ArrayList<>(Arrays.asList(
                new ArrayList<>(),
                new LinkedList<>(),
                new Stack<>(),
                new Vector<>()
        ));
    }

    public static List<Set> getSets(){
        return new ArrayList<>(Arrays.asList(
                new HashSet<>(),
                new LinkedHashSet<>(),
                new TreeSet<>()
        ));
    }
}