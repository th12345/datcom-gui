/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author aleksey
 */
public class DOMparser {
    private String groundLift;
    private String groundDrag;
    private String CLwbh;
    private String CLq;
    private String CLad;
    private String CD;
    private String Cyb;
    private String Cyp;
    private String Clb;
    private String Clp;
    private String Clr;
    private String Cm_basic;
    private String Cmq;
    private String Cmadot;
    private String CmDe;
    private String Cnb;
    private String Cnp;
    private String Cnr;
    private String elevHingeMoment;
    private String elev_pos;

    private ArrayList tables;

    private getFor005 printFile = new getFor005();
    private runDatcom runBabyRun = new runDatcom();

    public DOMparser(){}

    public void parseXML(String xmlFile){
        tables = new ArrayList();

        try {
       File file = new File(xmlFile);
       if(file.exists()){
         // Create a factory
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         // Use the factory to create a builder
         DocumentBuilder builder = factory.newDocumentBuilder();
         Document doc = builder.parse(xmlFile);
         // Get a list of all elements in the document
         NodeList list = doc.getElementsByTagName("tableData");
         System.out.println("XML Elements: ");
         for (int i=0; i<list.getLength(); i++) {
           // Get element
           Element element = (Element)list.item(i);
           //System.out.println(element.getNodeName() + " ; TAG = " + element.getTagName()+ " --> " + element.getTextContent());
           tables.add(element.getTextContent());
         }
       }
       else{
         System.out.print("File not found!");
       }
     }
     catch (Exception e) {
       System.exit(1);
     }

        System.out.println("Size = " + tables.size() + tables.toString());
        storeArrayVariables();
        writeAeroVariables();
    }

     //Store the variables from the ArrayList
        public void storeArrayVariables(){
            if (CD == null){
                groundLift = (String) tables.get(0);
                groundDrag = (String) tables.get(1);
                CLwbh = (String) tables.get(2);
                CLq = (String) tables.get(3);
                CLad = (String) tables.get(4);
                CD = (String) tables.get(5);
                Cyb = (String) tables.get(6);
                Cyp = (String) tables.get(7);
                Clb = (String) tables.get(8);
                Clp = (String) tables.get(9);
                Clr = (String) tables.get(10);
                Cm_basic = (String) tables.get(11);
                Cmq = (String) tables.get(12);
                Cmadot = (String) tables.get(13);
                CmDe = (String) tables.get(14);
                Cnb = (String) tables.get(15);
                Cnp = (String) tables.get(16);
                Cnr = (String) tables.get(17);
                elevHingeMoment = (String) tables.get(18);
                elev_pos = (String) tables.get(19);
            }
            else {
                groundLift = groundLift + (String) tables.get(0);
                groundDrag = groundDrag + (String) tables.get(1);
                CLwbh = CLwbh + (String) tables.get(2);
                CLq = CLq + (String) tables.get(3);
                CLad = CLad + (String) tables.get(4);
                CD = CD + (String) tables.get(5);
                Cyb = Cyb + (String) tables.get(6);
                Cyp = Cyp + (String) tables.get(7);
                Clb = Clb + (String) tables.get(8);
                Clp = Clp + (String) tables.get(9);
                Clr = Clr + (String) tables.get(10);
                Cm_basic = Cm_basic + (String) tables.get(11);
                Cmq = Cmq + (String) tables.get(12);
                Cmadot = Cmadot + (String) tables.get(13);
                CmDe = CmDe + (String) tables.get(14);
                Cnb = Cnb + (String) tables.get(15);
                Cnp = Cnp + (String) tables.get(16);
                Cnr = Cnr + (String) tables.get(17);
                elevHingeMoment = elevHingeMoment + (String) tables.get(18);
                elev_pos = elev_pos + (String) tables.get(19);
            }
        }

        public void writeAeroVariables(){
            String masterTable;
            String[] mkDir = {"mkdir", "/home/aleksey/Desktop/C3PO/MASTER"};
            runBabyRun.executeDatcom(mkDir);

            masterTable = "groundLift--------------------------------\n" +
                    groundLift + "\n\n\n\n\n" +
                    "groundDrag-------------------------------\n" +
                    groundDrag + "\n\n\n\n\n" +
                    "CLwbh----------------------------------\n" +
                    CLwbh + "\n\n\n\n\n" +
                    "CLq--------------------------------\n" +
                    CLq + "\n\n\n\n\n" +
                    "Clad----------------------------------\n" +
                    CLad + "\n\n\n\n\n" +
                    "CD----------------------------------\n" +
                    CD + "\n\n\n\n\n\n" +
                    "Cyb----------------------------------\n" +
                    Cyb + "\n\n\n\n\n\n" +
                    "Cyp----------------------------------\n" +
                    Cyp + "\n\n\n\n\n\n" +
                    "Clb----------------------------------\n" +
                    Clb + "\n\n\n\n\n\n" +
                    "Clp----------------------------------\n" +
                    Clp + "\n\n\n\n\n\n" +
                    "Clr----------------------------------\n" +
                    Clr + "\n\n\n\n\n\n" +
                    "Cm_basic----------------------------------\n" +
                    Cm_basic + "\n\n\n\n\n\n" +
                    "Cmq----------------------------------\n" +
                    Cmq + "\n\n\n\n\n\n" +
                    "Cmadot----------------------------------\n" +
                    Cmadot + "\n\n\n\n\n\n" +
                    "CmDe----------------------------------\n" +
                    CmDe + "\n\n\n\n\n\n" +
                    "Cnb----------------------------------\n" +
                    Cnb + "\n\n\n\n\n\n" +
                    "Cnp----------------------------------\n" +
                    Cnp + "\n\n\n\n\n\n" +
                    "Cnr----------------------------------\n" +
                    Cnr + "\n\n\n\n\n\n" +
                    "elevHingeMoment----------------------------------\n" +
                    elevHingeMoment + "\n\n\n\n\n\n" +
                    "elev_pos----------------------------------\n" +
                    elev_pos + "\n\n\n\n\n\n";

            //write the MASTER file
            printFile.writeFile("/home/aleksey/Desktop/C3PO/MASTER/", "MASTER.txt", masterTable);
        }
}