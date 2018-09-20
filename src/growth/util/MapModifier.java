package growth.util;

import growth.screen.screens.GameScreen;
import growth.entity.EntityManager;
import growth.main.Config;
import growth.main.Window;
import org.w3c.dom.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public abstract class MapModifier {

    public static void deleteEntity(String name){
        deleteEntity(name, GameScreen.tileMap.getCurrentMap());
    }

    public static void deleteEntity(String name, int map){
        try {
            String path = Window.config.getValue(Config.PART_PATH) + "temp/map"+(map+1)+".xml";
            Document doc = XmlReader.getDocument(path);
            assert doc != null;
            Element root = doc.getDocumentElement();
            assert root != null;
            NodeList object = root.getElementsByTagName("object");
            Element subRoot;
            for (int i = 0; i < object.getLength(); i++) {
                subRoot = (Element) object.item(i);
                if (subRoot.getAttribute("a" + EntityManager.NAME).equals(name)) subRoot.getParentNode().removeChild(subRoot);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void changeValue(String name, String attib, String newValue){
        changeValue(name, attib, newValue, GameScreen.tileMap.getCurrentMap());
    }

    public static void changeValue(String name, String attib, String newValue, int map){
        try {
            String path = Window.config.getValue(Config.PART_PATH) + "temp/map"+(map+1)+".xml";
            Document doc = XmlReader.getDocument(path);
            assert doc != null;
            Element root = doc.getDocumentElement();
            assert root != null;
            NodeList object = root.getElementsByTagName("object");
            Element subRoot;
            for (int i = 0; i < object.getLength(); i++) {
                subRoot = (Element) object.item(i);
                if (subRoot.getAttribute("a" + EntityManager.NAME).equals(name)) subRoot.setAttribute(attib, newValue);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
