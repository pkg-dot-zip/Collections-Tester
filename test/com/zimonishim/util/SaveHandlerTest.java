package com.zimonishim.util;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import com.zimonishim.tests.TestHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SaveHandlerTest {

    private final static String assertEqualsMessage = "TestHandlers do not equal, while they should.";
    private final static String assertNotEqualsMessage = "TestHandlers do equal, while they should not.";

    @Test
    void mergeTestHandlerCaseA() {
        //First we create the TestHandlers.
        TestHandler testHandler = generateDataSetEntry(generateResultEntryCollectionArray());
        TestHandler testHandler2 = generateDataSetEntry(generateResultEntryCollectionArray());

        //Then we check whether the returned TestHandler contains the same entries we created above.
        assertNotEquals(testHandler, testHandler2, assertEqualsMessage);
    }

    //TODO: Create unit tests that fill in some data here.
    @Test
    void mergeTestHandlerCaseB() {
        //First we create a dataSet.

        //Then we create a TestHandler using the method we're testing right here.

        //Then we check whether the returned TestHandler contains the same entries we created above.
    }

    private Collection<ResultEntry>[] generateResultEntryCollectionArray(){
        return generateResultEntryCollectionArray("Add All", "Sort Natural", "Remove");
    }

    private Collection<ResultEntry>[] generateResultEntryCollectionArray(String... actions){
        List<Collection<ResultEntry>> resultEntries = new ArrayList<>(actions.length);

        for (String action : actions) {
            resultEntries.add(generateResultEntryCollection(action, "ArrayList", "HashSet"));
        }

        return resultEntries.toArray(new Collection[0]);
    }

    private Collection<ResultEntry> generateResultEntryCollection(String action, String... collectionName){
        Collection<ResultEntry> collection = new ArrayList<>(3);

        for (String s : collectionName) {
            collection.addAll(Arrays.asList(generateResultEntryArray(s, action)));
        }

        return collection;
    }

    private ResultEntry[] generateResultEntryArray(String collectionName, String action){
        return generateResultEntryArray(10, collectionName, action, (long)(Math.random() * 100), 5);
    }

    private ResultEntry[] generateResultEntryArray(int sizeOfResultEntryArray, String collectionName, String action, long totalTime, int sizeOfActionArrays){
        return generateResultEntryArray(sizeOfResultEntryArray, collectionName, action, sizeOfActionArrays, totalTime, sizeOfActionArrays);
    }

    private ResultEntry[] generateResultEntryArray(int sizeOfResultEntryArray, String collectionName, String action, int sizeOfBeforeAction, long totalTime, int sizeOfAfterAction){
        ResultEntry[] arrayToReturn = new ResultEntry[sizeOfResultEntryArray];

        for (int i = 0; i < sizeOfResultEntryArray; i++){
            arrayToReturn[i] = generateResultEntry(collectionName, action, sizeOfBeforeAction, totalTime, sizeOfAfterAction);
        }

        return arrayToReturn;
    }

    private ResultEntry generateResultEntry(String collectionName, String action, int sizeOfBeforeAction, long totalTime, int sizeOfAfterAction){
        return new ResultEntry(collectionName, action, getRandomIntArray(sizeOfBeforeAction), totalTime, getRandomIntArray(sizeOfAfterAction));
    }

    private int[] getRandomIntArray(int size){
        int[] arrayToReturn = new int[size];

        for (int i = 0; i < size; ++i){
            arrayToReturn[i] = (int) (Math.random() * 100);
        }

        return arrayToReturn;
    }

    private Collection<ResultEntry>[] generateResultEntryCollectionFromTestHandler(TestHandler testHandler){
        //NOTE: Whenever a new test (list modification, NOT unit test) is added in the software we MUST add it here.
        //Order here matters. We do this in order of the methods defined in the TestHandler class.
        return (List<ResultEntry>[]) new ArrayList[]{
                new ArrayList<>(testHandler.getSortingResultEntryList()),
                new ArrayList<>(testHandler.getAddAllResultEntryList()),
                new ArrayList<>(testHandler.getRemoveResultEntryList())
        };
    }

    private TestHandler generateDataSetEntry(Collection<ResultEntry>[] collections){
        TestHandler testHandler = new TestHandler();

        if (collections.length < 2){
            throw new IllegalArgumentException("You need to add another collection for the tests.");
        }

        //NOTE: Whenever a new test (list modification, NOT unit test) is added in the software we MUST add it here.
        //Order here matters. We do this in order of the methods defined in the TestHandler class.
        testHandler.addSortingResultCollection(collections[0]);
        testHandler.addAddAllResultCollection(collections[1]);
        testHandler.addRemoveResultCollection(collections[2]);

        return testHandler;
    }

    /**
     * From here we test the methods we use to generate tests with.
     */
    @Test
    void dataSetEntriesCaseA() {
        //Here we create the first TestHandler manually.
        TestHandler testHandler1 = new TestHandler();

        testHandler1.addSortingResultCollection(Arrays.asList(
                new ResultEntry("ArrayList", "Sort Natural Order", new int[]{5, 6, 8}, (long) 6245, new int[]{5, 6, 8}),
                new ResultEntry("ArrayList", "Sort Natural Order", new int[]{12, 51, 2}, (long) 3851, new int[]{2, 12, 51}),
                new ResultEntry("ArrayList", "Sort Natural Order", new int[]{9, 6, 8}, (long) 7112, new int[]{6, 8, 9}))
        );

        testHandler1.addAddAllResultCollection(Arrays.asList(
                new ResultEntry("ArrayList", "Add All", new int[]{}, (long) 5675, new int[]{5, 6, 8}),
                new ResultEntry("ArrayList", "Add All", new int[]{}, (long) 6326, new int[]{12, 51, 2}),
                new ResultEntry("ArrayList", "Add All", new int[]{}, (long) 5426, new int[]{9, 6, 8}))
        );

        testHandler1.addRemoveResultCollection(Arrays.asList(
                new ResultEntry("ArrayList", "Remove", new int[]{5, 6, 8}, (long) 5764, new int[]{5}),
                new ResultEntry("ArrayList", "Remove", new int[]{12, 51, 2}, (long) 6426, new int[]{12, 51}),
                new ResultEntry("ArrayList", "Remove", new int[]{9, 6, 8}, (long) 5231, new int[]{9, 6, 8})
        ));

        //Then we create the second testHandler using methods in this test class.
        TestHandler testHandler2 = generateDataSetEntry(generateResultEntryCollectionFromTestHandler(testHandler1));

        assertEquals(testHandler1, testHandler2, assertEqualsMessage);
    }

    @Test
    void dataSetEntriesCaseB() {
        //Here we declare what collections to use.
        Collection<ResultEntry> sortingResultCollection = Arrays.asList(
                new ResultEntry("ArrayList", "Sort Natural Order", new int[]{5, 6, 8}, (long) 6245, new int[]{5, 6, 8}),
                new ResultEntry("ArrayList", "Sort Natural Order", new int[]{12, 51, 2}, (long) 3851, new int[]{2, 12, 51}),
                new ResultEntry("ArrayList", "Sort Natural Order", new int[]{9, 6, 8}, (long) 7112, new int[]{6, 8, 9}));

        Collection<ResultEntry> addAllCollection = Arrays.asList(
                new ResultEntry("ArrayList", "Add All", new int[]{}, (long) 5675, new int[]{5, 6, 8}),
                new ResultEntry("ArrayList", "Add All", new int[]{}, (long) 6326, new int[]{12, 51, 2}),
                new ResultEntry("ArrayList", "Add All", new int[]{}, (long) 5426, new int[]{9, 6, 8}));

        Collection<ResultEntry> removeCollection = Arrays.asList(
                        new ResultEntry("ArrayList", "Remove", new int[]{5, 6, 8}, (long) 5764, new int[]{5}),
                        new ResultEntry("ArrayList", "Remove", new int[]{12, 51, 2}, (long) 6426, new int[]{12, 51}),
                        new ResultEntry("ArrayList", "Remove", new int[]{9, 6, 8}, (long) 5231, new int[]{9, 6, 8}));

        //Here we create the first TestHandler using the data above.
        TestHandler testHandler1 = new TestHandler();
        testHandler1.addSortingResultCollection(sortingResultCollection);
        testHandler1.addAddAllResultCollection(addAllCollection);
        testHandler1.addRemoveResultCollection(removeCollection);

        //Then we create the second testHandler with our own set testData.
        TestHandler testHandler2 = generateDataSetEntry(new ArrayList[]{
                new ArrayList<>(sortingResultCollection),
                new ArrayList<>(addAllCollection),
                new ArrayList<>(removeCollection)});

        assertEquals(testHandler1, testHandler2, assertEqualsMessage);
    }

    @Test
    void dataSetEntriesCaseC() {
        //Here we declare what collections to use.
        Collection<ResultEntry> addAllCollection = Arrays.asList(
                new ResultEntry("HashSet", "Add All", new int[]{}, (long) 4312, new int[]{5, 6, 8}),
                new ResultEntry("HashSet", "Add All", new int[]{}, (long) 5416, new int[]{12, 51, 2}),
                new ResultEntry("HashSet", "Add All", new int[]{}, (long) 3514, new int[]{9, 6, 8}));

        Collection<ResultEntry> removeCollection = Arrays.asList(
                new ResultEntry("HashSet", "Remove", new int[]{5, 6, 8}, (long) 5764, new int[]{5}),
                new ResultEntry("HashSet", "Remove", new int[]{12, 51, 2}, (long) 6426, new int[]{12, 51}),
                new ResultEntry("HashSet", "Remove", new int[]{9, 6, 8}, (long) 5231, new int[]{9, 6, 8}));

        //Here we create the first TestHandler using the data above.
        TestHandler testHandler1 = new TestHandler();
        testHandler1.addAddAllResultCollection(addAllCollection);
        testHandler1.addRemoveResultCollection(removeCollection);

        //Then we create the second testHandler with our own set testData.
        TestHandler testHandler2 = generateDataSetEntry(new ArrayList[]{
                new ArrayList<>(Collections.singletonList(new ResultEntry("HashSet", "Remove", new int[]{5, 6, 8}, (long) 5764, new int[]{5}))),
                new ArrayList<>(addAllCollection),
                new ArrayList<>(removeCollection)});

        Assertions.assertNotEquals(testHandler1, testHandler2, assertNotEqualsMessage);
    }
}