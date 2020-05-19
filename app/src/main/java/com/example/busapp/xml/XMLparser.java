package com.example.busapp.xml;

import com.example.busapp.services.BusStop;
import com.example.busapp.services.Route;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLparser {
    public XMLparser(){}

    public ArrayList<Route> GetRouteArrayFromString(String xmlRecords) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlRecords));

            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("Route");
            ArrayList<Route> list = new ArrayList<>();
            for(int i = 0;i<nodes.getLength();i++)
            {
                Node node =  nodes.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element)node;
                    if(element.getElementsByTagName("Type").item(0).getTextContent().equals("bus")) {
                        Route route = new Route();
                        route.setId(element.getElementsByTagName("Id").item(0).getTextContent());
                        route.setRouteNumber(element.getElementsByTagName("RouteNumber").item(0).getTextContent());
                        route.setStopA(element.getElementsByTagName("StopA").item(0).getTextContent());
                        route.setStopB(element.getElementsByTagName("StopB").item(0).getTextContent());
                        list.add(route);
                    }
                }
            }
            return list;

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }


}
