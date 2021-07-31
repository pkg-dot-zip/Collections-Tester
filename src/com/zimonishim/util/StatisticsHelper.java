package com.zimonishim.util;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.util.*;

public class StatisticsHelper {

    public static Map<String, Long> getAverageOfAllCollections(Collection<ResultEntry> results){
        Map<String, Long> map = new HashMap<>();

        Map<String, Set<Long>> allTimes = new HashMap<>();

        for (ResultEntry result : results) {

            if (allTimes.containsKey(result.getCollectionClassName())){
                allTimes.get(result.getCollectionClassName()).add(result.getTotalTime());
            } else {
                allTimes.put(result.getCollectionClassName(), new HashSet<>(Collections.singleton(result.getTotalTime())));
            }

        }

        allTimes.forEach((s, longs) -> {
            long totalTime = 0;
            for (Long aLong : longs) {
                totalTime += aLong;
            }

            int amountOfEntries = longs.size();

            map.put(s, totalTime / amountOfEntries);
        });

        return map;
    }
}