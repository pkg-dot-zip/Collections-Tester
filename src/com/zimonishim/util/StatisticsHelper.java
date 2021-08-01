package com.zimonishim.util;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;

import java.util.*;

public class StatisticsHelper {

    //TODO: Implement a correction factor, which determines what entries strafe away too far from the average, and should thus not be included.
    public static Map<String, Long> getAverageOfAllCollections(Collection<ResultEntry> results){
        Map<String, Long> map = new HashMap<>();

        Map<String, Set<Long>> allTimes = getCollectionMap(results);

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

    public static Map<String, Set<Long>> getCollectionMap(Collection<ResultEntry> results){
        Map<String, Set<Long>> allTimes = new HashMap<>();

        for (ResultEntry result : results) {
            if (allTimes.containsKey(result.getCollectionClassName())){
                allTimes.get(result.getCollectionClassName()).add(result.getTotalTime());
            } else {
                allTimes.put(result.getCollectionClassName(), new HashSet<>(Collections.singleton(result.getTotalTime())));
            }
        }

        return allTimes;
    }
}