package com.kostakov;

import com.google.gson.Gson;
import com.opencsv.*;
import com.opencsv.bean.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class Converter {
    public static List<Employee> csvToEmployee(String[] columnMapping, String fileName) {
        List<Employee> listEmployee = new ArrayList<>();
        ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
        mappingStrategy.setType(Employee.class);
        mappingStrategy.setColumnMapping(columnMapping);

        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(mappingStrategy)
                    .build();
            listEmployee = csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listEmployee;
    }

    private static String listToJson(List<Employee> listEmployee) {
        Gson gson = new Gson();
        return gson.toJson(listEmployee);
    }

    private static void stringGsonToFile(String stringGson, String fileName) {
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(stringGson);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void employeeToJson(List<Employee> listEmployee, String fileNameJson) {
        String stringGson = listToJson(listEmployee);
        stringGsonToFile(stringGson, fileNameJson);
    }

    public static List<Employee> xmlToEmployee(String fileName) {

        List<Employee> listEmployee = new ArrayList<>();


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = builder.parse(new File(fileName));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node root = doc.getDocumentElement();

        NodeList rootNodeList = root.getChildNodes();
        for (int i = 0; i < rootNodeList.getLength(); i++) {
            if (Node.ELEMENT_NODE == rootNodeList.item(i).getNodeType()) {
                Element employee = (Element) rootNodeList.item(i);
                listEmployee.add(new Employee(
                        Long.parseLong(employee.getElementsByTagName("id").item(0).getTextContent()),
                        employee.getElementsByTagName("firstName").item(0).getTextContent(),
                        employee.getElementsByTagName("lastName").item(0).getTextContent(),
                        employee.getElementsByTagName("country").item(0).getTextContent(),
                        Integer.parseInt(employee.getElementsByTagName("age").item(0).getTextContent())
                ));
            }
        }

        return listEmployee;
    }

    public static List<Employee> jsonToEmployee(String fileMane) {
        List<Employee> employeeList = new ArrayList<>();
        String stringGson = jsonToString(fileMane);

        Gson gson = new Gson();
        JSONParser parser = new JSONParser();
        JSONArray array = new JSONArray();

        try {
            array = (JSONArray) parser.parse(stringGson);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(Object employee : array){
            employeeList.add(gson.fromJson(employee.toString(), Employee.class));
        }
        return employeeList;
    }

    public static String jsonToString(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(fileName)) {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }
}


