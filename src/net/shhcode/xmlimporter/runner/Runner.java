package net.shhcode.xmlimporter.runner;

import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.shhcode.xmlimporter.logic.Importer;
import net.shhcode.xmlimporter.object.Record;


public class Runner {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Record> listOfRecord = new ArrayList<Record>();

        String dir = args[0] + "/";
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
        Importer importer = new Importer();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                try {
                    listOfRecord.add(importer.getRecord(new File(dir + listOfFiles[i].getName())));
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            } 
        }
        try {
            String dbName = args[1];
            String collectionName = args[2];
            importer.putIntoMongoDB(listOfRecord, dbName, collectionName);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

}
