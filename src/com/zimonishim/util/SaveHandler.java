package com.zimonishim.util;

import com.zimonishim.tests.TestHandler;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SaveHandler {

    public static class AsObject{

        public static void write(TestHandler testHandler){
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("test.dat");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                long startTime = System.nanoTime();
                objectOutputStream.writeObject(testHandler);
                long endTime = System.nanoTime();
                System.out.println("Total time for writing TestHandler: " + (endTime - startTime));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        public static TestHandler read(){
            TestHandler testHandler = null;

            try {
                FileInputStream fileInputStream = new FileInputStream("test.dat");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                long startTime = System.nanoTime();
                testHandler = (TestHandler) objectInputStream.readObject();
                long endTime = System.nanoTime();
                System.out.println("Total time for reading TestHandler: " + (endTime - startTime));

            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }

            return testHandler;
        }
    }

    //TODO: Create a .txt or .json variant for saving data. This way we can use older data in newer versions of this software.
    public static class AsText{

    }


    public static TestHandler mergeTestHandlers(Collection<TestHandler> testHandlers){
        if (testHandlers.size() < 2) {
            return new TestHandler();
        }

        return mergeTestHandlers(testHandlers.toArray(new TestHandler[0]));
    }

    public static TestHandler mergeTestHandlers(TestHandler[] testHandlers){
        if (testHandlers.length == 2){
            return mergeTestHandler(testHandlers[0], testHandlers[1]);
        }

        return mergeTestHandlers(processIteration(testHandlers, testHandlers[1]));
    }

    private static TestHandler[] processIteration(TestHandler[] testHandlers, TestHandler testHandler){
        testHandlers[0] = mergeTestHandler(testHandlers[0], testHandler); //NOTE: IntelliJ says that testHandlers[0] is already assigned this value. This is NOT true. Do not change this.

        List<TestHandler> testHandlerList = Arrays.asList(testHandlers);
        testHandlerList.remove(1);

        return testHandlerList.toArray(new TestHandler[0]);
    }

    public static TestHandler mergeTestHandler(TestHandler testHandler1, TestHandler testHandler2){
        testHandler1.addAddAllResultCollection(testHandler2.getAddAllResultEntryList());
        testHandler1.addRemoveResultCollection(testHandler2.getRemoveResultEntryList());
        testHandler1.addSortingResultCollection(testHandler2.getSortingResultEntryList());

        return testHandler1;
    }
}
