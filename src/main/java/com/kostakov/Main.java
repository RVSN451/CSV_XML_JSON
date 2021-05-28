package com.kostakov;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;


public class Main {

    public static void main(String[] args) {

        List<Employee> listEmployee;
        //Type listType = new TypeToken<List<Employee>>() {}.getType();

        String fileNameCsv = "data.csv";
        String fileNameJson = "data.json";
        String fileNameJson2 = "data2.json";
        String fileNameXml = "data.xml";

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        listEmployee = Converter.csvToEmployee(columnMapping, fileNameCsv);
        Converter.employeeToJson(listEmployee, fileNameJson);
        listEmployee.clear();

        listEmployee = Converter.xmlToEmployee(fileNameXml);
        Converter.employeeToJson(listEmployee, fileNameJson2);
        listEmployee.clear();

        listEmployee.addAll(Converter.jsonToEmployee(fileNameJson2));
        listEmployee.addAll(Converter.jsonToEmployee(fileNameJson));

        System.out.println("Список сотрудников из .json файлов: \n" + listEmployee);
    }
}
