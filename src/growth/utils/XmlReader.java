package growth.utils;

import growth.tilemap.Map;
import growth.tilemap.Tile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

			System.out.println("Map " + Integer.parseInt(root.getAttribute("id")) + " loaded.");
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
}
