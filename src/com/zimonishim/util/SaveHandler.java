package com.zimonishim.util;

import com.zimonishim.GUI.IGUICallback;
import com.zimonishim.tests.TestHandler;

import java.io.*;

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

        public static void read(IGUICallback callback){
            TestHandler testHandler = null;

            try {
                FileInputStream fileInputStream = new FileInputStream("test.dat");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                long startTime = System.nanoTime();
                testHandler = (TestHandler) objectInputStream.readObject();
                long endTime = System.nanoTime();
                System.out.println("Total time for reading TestHandler: " + (endTime - startTime));

                System.out.println(testHandler.getAddAllResultEntryList().size());

            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }

            long startTime = System.nanoTime();
            callback.refresh(testHandler);
            long endTime = System.nanoTime();
            System.out.println("Total time for refreshing: " + (endTime - startTime));
        }
    }

    //TODO: Create a .txt or .json variant for saving data. This way we can use older data in newer versions of this software.
    public static class AsText{

    }

}