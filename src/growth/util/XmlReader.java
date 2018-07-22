package growth.util;

import growth.game.tilemap.Map;
import growth.game.tilemap.Tile;
import growth.main.Config;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * XmlReader class.
 * This class charge the different xml's files.
 *
 * @author MightyCode
 * @version 2.1
 */
public abstract class XmlReader {

	/**
	 * Create the map from the Xml's file.
	 *
	 * @param map_path Path to find the Xml's file
	 *
	 * @return map
	 */
	public static Map createMap(String map_path) {
		try {

			Element root = getRootInJar(Config.MAP_PATH + map_path);

			assert root != null;
			int width = Integer.parseInt(root.getAttribute("width"));
			int height = Integer.parseInt(root.getAttribute("height"));

			Element subRoot;

			// Set the spawn point of map
			NodeList ins = root.getElementsByTagName("in");

			int newInsNumber = 0;
			for(int a = 0; a < ins.getLength(); a++){
				subRoot = (Element) ins.item(a);
				if(Integer.parseInt(subRoot.getAttribute("name"))> newInsNumber)
					newInsNumber = Integer.parseInt(subRoot.getAttribute("name"));
			}

			// Instance the map
			Map map = new Map(Integer.parseInt(root.getAttribute("id")), newInsNumber);

			for(int a = 0; a < ins.getLength(); a++){
				subRoot = (Element) ins.item(a);
				map.setSpawnTile(Integer.parseInt(subRoot.getAttribute("name"))-1,
						Float.parseFloat(subRoot.getAttribute("x")),
						Float.parseFloat(subRoot.getAttribute("y")));
			}

			// Set the exit point of map
			NodeList outs = root.getElementsByTagName("out");
			for(int a = 0; a < outs.getLength(); a++){
				subRoot = (Element) outs.item(a);
				map.setExit(Integer.parseInt(subRoot.getAttribute("side"))-1,
						Integer.parseInt(subRoot.getAttribute("to"))-1,
						Integer.parseInt(subRoot.getAttribute("mapId")),
						Float.parseFloat(subRoot.getAttribute("beg")),
						Float.parseFloat(subRoot.getAttribute("end")));
			}

			// Loading the layers
			NodeList layers = root.getElementsByTagName("layer");

			for(int a = 0; a < layers.getLength(); a++){
				subRoot = (Element) layers.item(a);

				int[][] mapId = new int[height][width];

				String sMap = subRoot.getTextContent();

				// Map converting from String to int[][]
				int counter1 = 0;
				int numberOfCharacterRead = 1;

				// For all cols
				while (counter1 < height) {
					int x = 1;
					int counter2 = 0;

					// For all rows
					while (counter2 < width) {

						while (notInteger(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1))) {
							x = 1;
							numberOfCharacterRead++;
						}

						mapId[counter1][counter2] =
								mapId[counter1][counter2] * x + Integer.parseInt(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1));
						numberOfCharacterRead++;
						x *= 10;

						if(notInteger(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1))) {
							x = 1;
							numberOfCharacterRead++;
							counter2++;
						}
					}

					counter1++;
					numberOfCharacterRead++;
				}

				map.setLayer(Integer.parseInt(subRoot.getAttribute("position"))-1,mapId);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create the tileSet from the xml's file.
	 *
	 * @param tileSet_path Path to find the Xml's file
	 *
	 * @return tileSet
	 */
	public static Tile[] createTileSet(String tileSet_path) {
		try {
			Element root = getRootInJar(tileSet_path);

			// Get all child nodes of the root
			assert root != null;
			NodeList rootNode = root.getChildNodes();

			int nbNodes = root.getElementsByTagName("texture").getLength();

			int tableLength = 0;

			int a = 0;
			Element layer;

			for (int i = 1; a < nbNodes; i++) {
				if (rootNode.item(i).getNodeType() == Node.ELEMENT_NODE)
					layer = (Element) rootNode.item(i);
				else {
					i++;
					layer = (Element) rootNode.item(i);
				}

				if(Integer.parseInt(layer.getAttribute("id"))>= tableLength)
					tableLength = Integer.parseInt(layer.getAttribute("id"))+1;
				a++;
			}

			Tile[] tileSet = new Tile[tableLength];

			a = 0;

			for (int i = 1; a < nbNodes; i++) {
				if (rootNode.item(i).getNodeType() == Node.ELEMENT_NODE)
					layer = (Element) rootNode.item(i);
				else {
					i++;
					layer = (Element) rootNode.item(i);
				}

				tileSet[Integer.parseInt(layer.getAttribute("id"))] =
						new Tile(layer.getAttribute("name"),
								Integer.parseInt(layer.getAttribute("type")),
								Integer.parseInt(layer.getAttribute("x")),
								Integer.parseInt(layer.getAttribute("y")),
								Integer.parseInt(root.getAttribute("size")),
								Integer.parseInt(root.getAttribute("width")),
								Integer.parseInt(root.getAttribute("height"))
						);
				a++;
			}
			return tileSet;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Load the configurations at the launch of the game.
	 */
	public static void loadConfig(){
		try {
			Element root = getRoot(Config.CONFIG_PATH);

			assert root != null;
			// General configuration
			Element tag = search("general", root);
			Config.setLanguage(tag.getAttribute("language"));

			// Window size
			 tag = search("window", root);
			Config.setWindowWidth(Integer.parseInt(tag.getAttribute("width")));
			Config.setWindowHeight(Integer.parseInt(tag.getAttribute("height")));

			// Window fullscreen
			Config.setFullscreen(Integer.parseInt(tag.getAttribute("fullscreen")));

			// Inputs configuration
			tag = search("inputs", root);

			int inputNumber = Integer.parseInt(tag.getAttribute("number"));

			int[][] inputs = new int[inputNumber][2];

			for(int i = 0; i < inputNumber; i++){
				String data = tag.getAttribute("i"+i);
				inputs[i][Integer.parseInt(data.substring(0,1))] = Integer.parseInt(data.substring(2,data.length()));
				inputs[i][Math.abs(Integer.parseInt(data.substring(0,1))-1)] = -1;
			}

			Config.setInputs(inputs);

			Config.setPartyNumber(search("game",root).getAttribute("number"));
			tag = search("sound", root);

			Config.setMusicVolume(Integer.parseInt(tag.getAttribute("music")));
			Config.setNoiseVolume(Integer.parseInt(tag.getAttribute("noise")));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save configurations at the ending of the game.
	 */
	public static void saveConfiguration(){
		try {
			Document doc = getDocument(Config.CONFIG_PATH);
			assert doc != null;
			Element root = doc.getDocumentElement();

			assert root != null;
			// General configuration
			Element tag = search("general", root);
			tag.setAttribute("language",Config.getLanguage());
			tag = search("window", root);
			setAttribute(tag,"fullscreen",Config.getFullscreen());
			setAttribute(tag,"width",Config.getWindowWidth());
			setAttribute(tag,"height",Config.getWindowHeight());
			tag = search("sound",root);
			setAttribute(tag,"music",Config.getMusicVolume());
			setAttribute(tag,"noise",Config.getNoiseVolume());

			tag = search("inputs",root);
			int[][]inputs = Config.getInputs();
			for(int i = 0; i < inputs.length; i++){
				if(inputs[i][1] == -1){
					tag.setAttribute("i"+i,"0-"+inputs[i][0]);
				} else{
					tag.setAttribute("i"+i,"1-"+inputs[i][1]);
				}
			}

			tag = search("game",root);

			tag.setAttribute("number", Config.getPartyNumber());

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(Config.CONFIG_PATH));
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Change a value.
	 * @param path The path of the xml's file to change.
	 * @param attributeName The name of the attribute to change.
	 * @param newValue The new value.
	 * @param nodesName The node(s) to access the attribute's tag.
	 */
	public static void changeValue(String path, String attributeName, String newValue, String... nodesName){
		try{
			Document doc = getDocument(path);
			assert doc != null;
			Element root = doc.getDocumentElement();
			search(nodesName,root).setAttribute(attributeName, newValue);

			Transformer t = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
			t.transform(source, result);

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Change a value while a document is open on another methods and with a integer new value.
	 * @param tag The tag of the attribute.
	 * @param name The attribute's name.
	 * @param value The new value.
	 */
	public static void setAttribute(Element tag, String name, int value){ tag.setAttribute(name, String.valueOf(value)); }

	/**
	 * Surcharge method to use with a boolean new value.
	 */
	public static void setAttribute(Element tag, String name, boolean value){ tag.setAttribute(name, (value)? "1" : "0"); }

	/**
	 * Get a value from a xml file.
	 * @param path The path of the file.
	 * @param attributeName The attributeName of the value.
	 * @param nodeName The node(s) to access the attribute's tag.
	 * @return The value (string).
	 */
	public static String getValue(String path, String attributeName, String... nodeName){
		try{
			Element root = getRoot(path);
			assert root != null;
			return search(nodeName, root).getAttribute(attributeName);
		} catch (Exception e){
			e.printStackTrace();
			return "fail !!";
		}
	}

    public static String getValueInJar(String path, String attributeName, String... nodeName){
        try{
            Element root = getRootInJar(path);
            assert root != null;
            return search(nodeName, root).getAttribute(attributeName);
        } catch (Exception e){
            e.printStackTrace();
            return "fail !!";
        }
    }

	/**
	 * Load the word after getting the language configurations.
	 * @return The table with the sentences (string).
	 */
	public static String[] loadWord(){
		try{
			String[] word;
			Element root = getRootInJar("/word/" + Config.getLanguage() + ".xml");
			assert root != null;
			Element tag = search("info",root);
			int size = Integer.parseInt(tag.getAttribute("number"));
			word = new String[size];
			tag = search("sentence",root);

			for(int i = 0; i < size; i++){
				word[i]=tag.getAttribute("s"+i);
			}
			return word;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method to charge a document easier.
	 * @param path The path of the document.
	 * @return The document class.
	 */
	private static Document getDocument(String path){
		try {
			return ((DocumentBuilderFactory.newInstance()).newDocumentBuilder()).parse(path);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Search a tag.
	 * @param name The name of the tag.
	 * @param root The name root's tag.
	 * @return The tag class.
	 */
	private static Element search(String name, Element root){
		NodeList rootNode = root.getChildNodes();
		int i = 0;
		if (rootNode.item(i).getNodeType() != Node.ELEMENT_NODE) i++;
		while(!name.equals(rootNode.item(i).getNodeName())){
			i++;
			if (rootNode.item(i).getNodeType() != Node.ELEMENT_NODE) i++;
		}

		return (Element) rootNode.item(i);
	}

	/**
	 * Surcharge methode to search the tag with many intermediary tags.
	 * @param nodesName The nodes name.
	 * @param root The initial root's tag.
	 * @return THe final tag.
	 */
	private static Element search(String[] nodesName, Element root){
		Element layer = search(nodesName[0],root);
		for(int i = 1; i < nodesName.length; i++){
			layer = search(nodesName[i], layer);
		}
		return layer;
	}

	/**
	 * Get a root's tag with the xml file's path.
	 * @param path The path of the file.
	 * @return The root tag class.
	 */
	private static Element getRoot(String path){
		try {
			return (((DocumentBuilderFactory.newInstance()).newDocumentBuilder()).parse(path)).getDocumentElement();
		} 	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * Get a root's tag with the xml file's path.
     * @param path The path of the file.
     * @return The root tag class.
     */
    private static Element getRootInJar(String path){
        try {
            return (((DocumentBuilderFactory.newInstance()).newDocumentBuilder()).parse(XmlReader.class.getResourceAsStream(path))).getDocumentElement();
        } 	catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	/**
	 * Test if a string is a number
	 *
	 * @param string String to test
	 *
	 * @return boolean's result
	 */
	private static boolean notInteger(String string) {
		try {
			Integer.parseInt(string);
			return false;
		} catch (NumberFormatException e) {
			return true;
		}
	}
}
