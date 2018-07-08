package growth.util;

import growth.game.tilemap.Map;
import growth.game.tilemap.Tile;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * XmlReader class.
 * This class charge the different xml's files.
 *
 * @author MightyCode
 * @version 1.0
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
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XmlReader.class.getResourceAsStream("/map/" + map_path));
			Element root = document.getDocumentElement();

			int width = Integer.parseInt(root.getAttribute("width"));
			int height = Integer.parseInt(root.getAttribute("height"));

			Element subRoot;

			// Set the spawn point of map
			NodeList ins = root.getElementsByTagName("in");
			final int insNumber = ins.getLength();

			int newInsNumber = 0;
			for(int a = 0; a < insNumber; a++){
				subRoot = (Element) ins.item(a);
				if(Integer.parseInt(subRoot.getAttribute("name"))> newInsNumber)
					newInsNumber = Integer.parseInt(subRoot.getAttribute("name"));
			}

			// Instance the map
			Map map = new Map(Integer.parseInt(root.getAttribute("id")), newInsNumber);

			for(int a = 0; a < insNumber; a++){
				subRoot = (Element) ins.item(a);
				map.setSpawnTile(Integer.parseInt(subRoot.getAttribute("name"))-1,
						Float.parseFloat(subRoot.getAttribute("x")),
						Float.parseFloat(subRoot.getAttribute("y")));
			}

			// Set the exit point of map
			NodeList outs = root.getElementsByTagName("out");
			final int outNumber = outs.getLength();
			for(int a = 0; a < outNumber; a++){
				subRoot = (Element) outs.item(a);
				map.setExit(Integer.parseInt(subRoot.getAttribute("side"))-1,
						Integer.parseInt(subRoot.getAttribute("to"))-1,
						Integer.parseInt(subRoot.getAttribute("mapId")),
						Float.parseFloat(subRoot.getAttribute("beg")),
						Float.parseFloat(subRoot.getAttribute("end")));
			}


			// Loading the layers
			NodeList layers = root.getElementsByTagName("layer");
			final int nubLayer = layers.getLength();

			for(int a = 0; a < nubLayer; a++){
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

						mapId[counter1][counter2] = mapId[counter1][counter2] * x + Integer.parseInt(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1));
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
	 * Load the map number from the xml map option file and return it.
	 */
	public static int options_nbMap(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XmlReader.class.getResourceAsStream("/map/mapOptions.xml"));
			Element root = document.getDocumentElement();

			// Get all child nodes of the root
			NodeList rootNodes = root.getChildNodes();

			int i = 0;
			Element subRoot1;

			if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) subRoot1 = (Element) rootNodes.item(i);
			else {
				i++;
				subRoot1 = (Element) rootNodes.item(i);
			}

			while (!(subRoot1.getNodeName().equals("number"))) {
				i++;
				if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) subRoot1 = (Element) rootNodes.item(i);
			}

			return Integer.parseInt(subRoot1.getAttribute("number"))+1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
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

	/**
	 * Create the tileSet from the xml's file.
	 *
	 * @param tileSet_path Path to find the Xml's file
	 *
	 * @return tileSet
	 */
	public static Tile[] createTileSet(String tileSet_path) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XmlReader.class.getResourceAsStream(tileSet_path));
			Element root = document.getDocumentElement();

			// Get all child nodes of the root
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

				if(Integer.parseInt(layer.getAttribute("id"))>= tableLength) tableLength = Integer.parseInt(layer.getAttribute("id"))+1;
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

	public static int getTileSize(String tileSet_path){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XmlReader.class.getResourceAsStream(tileSet_path));
			Element root = document.getDocumentElement();

			return Integer.parseInt(root.getAttribute("size"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int[] getTileSetSize(String tileSet_path){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XmlReader.class.getResourceAsStream(tileSet_path));
			Element root = document.getDocumentElement();

			int[] data = new int[2];

			data[0] = Integer.parseInt(root.getAttribute("width"));
			data[1] = Integer.parseInt(root.getAttribute("height"));

			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static float[] loadConfig(){
		float[] config = new float[3];
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XmlReader.class.getResourceAsStream("/config/config.xml"));
			Element root = document.getDocumentElement();

			NodeList rootNode = root.getChildNodes();
			Element layer = (Element) rootNode.item(1);

			// Window size
			config[0] = Integer.parseInt(layer.getAttribute("width"));
			config[1] = Integer.parseInt(layer.getAttribute("height"));

			// Window fullscreen
			config[2] = Integer.parseInt(layer.getAttribute("fullscreen"));
			return config;
		} catch (Exception e) {
			e.printStackTrace();
			return new float[0];
		}
	}

	public static void test(){
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("company");
			doc.appendChild(rootElement);

			// staff elements
			Element staff = doc.createElement("Staff");
			rootElement.appendChild(staff);

			// set attribute to staff element
			Attr attr = doc.createAttribute("id");
			attr.setValue("1");
			staff.setAttributeNode(attr);

			// shorten way
			// staff.setAttribute("id", "1");

			// firstname elements
			Element firstname = doc.createElement("firstname");
			firstname.appendChild(doc.createTextNode("yong"));
			staff.appendChild(firstname);

			// lastname elements
			Element lastname = doc.createElement("lastname");
			lastname.appendChild(doc.createTextNode("mook kim"));
			staff.appendChild(lastname);

			// nickname elements
			Element nickname = doc.createElement("nickname");
			nickname.appendChild(doc.createTextNode("mkyong"));
			staff.appendChild(nickname);

			// salary elements
			Element salary = doc.createElement("salary");
			salary.appendChild(doc.createTextNode("100000"));
			staff.appendChild(salary);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("resources/config/test/file.xml" ));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
