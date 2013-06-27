package net.shhcode.xmlimporter.logic;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import net.shhcode.xmlimporter.object.Record;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class Importer {

    public Importer() {}

    public Record getRecord(File file) 
            throws JAXBException, ParserConfigurationException, XPathExpressionException, 
            SAXException, IOException {
        Record record = new Record();

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        Document doc = domBuilder.parse(file);

        NodeList nListA = doc.getElementsByTagName("admin:admin");
        for (int temp = 0; temp < nListA.getLength(); temp++) {
            Node nNode = nListA.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String site = eElement.getElementsByTagName("admin:bcr").item(0).getTextContent();
                //System.out.println("Site : " + site);
                record.setSite(site);
            }
        }

        doc = domBuilder.parse(file);
        NodeList nListB = doc.getElementsByTagName("brca:patient");
        for (int temp = 0; temp < nListB.getLength(); temp++) {
            Node nNode = nListB.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode; 
                String patientId = 
                        eElement.getElementsByTagName("shared:bcr_patient_barcode").item(0).getTextContent();
                String gender = eElement.getElementsByTagName("shared:gender").item(0).getTextContent();
                String vitalStatus = eElement.getElementsByTagName("shared:vital_status").item(0).getTextContent();
                //System.out.println("Patient Identifier : " + patientId);
                //System.out.println("Gender : " + gender);
                //System.out.println("Vital Status : " + vitalStatus);
                record.setPatientId(patientId);
                record.setGender(gender);
                record.setVitalStatus(vitalStatus);
            }
        }
        return record;
    }

    public void putIntoMongoDB(ArrayList<Record> records, String dbName, String collectionName) 
            throws UnknownHostException {
        Mongo mongoClient = new Mongo("localhost" , 27017);
        DB db = mongoClient.getDB(dbName);
        DBCollection coll = db.getCollection(collectionName);

        for(Record record: records) {
            BasicDBObject doc = new BasicDBObject("site", record.getSite()).
                    append("patientId", record.getPatientId()).
                    append("gender", record.getGender()).
                    append("age", record.getAge()).
                    append("alive", record.getVitalStatus());
            coll.insert(doc);
        }
    }
}
