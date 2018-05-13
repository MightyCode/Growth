package growth.game.utils;

import growth.game.tilemap.Tile;
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
	 * Charge the map from the Xml's file.
	 *
	 * @param map_path Path to find the Xml's file
	 *
	 * @return map
	*/
	public static int[][] createMap(String map_path) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XmlReader.class.getResourceAsStream(map_path));
			Element root = document.getDocumentElement();

			// Get all child nodes of the root
            NodeList rootNode = root.getChildNodes();

			int i = 0;
			Element layer;

			if (rootNode.item(i).getNodeType() == Node.ELEMENT_NODE) layer = (Element) rootNode.item(i);
			else {
				i++;
				layer = (Element) rootNode.item(i);
			}

			while (!(layer.getNodeName().equals("layer"))) {
				if (rootNode.item(i).getNodeType() == Node.ELEMENT_NODE) layer = (Element) rootNode.item(i);
				i++;
			}

			Element data;
			data = (Element) layer.getElementsByTagName("data").item(0);

			int width = Integer.parseInt(layer.getAttribute("width"));
			int height = Integer.parseInt(layer.getAttribute("height"));

			int[][] map = new int[height][width];

			String sMap = data.getTextContent();

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
						System.out.println(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1));
						x = 1;
						numberOfCharacterRead++;
					}

					map[counter1][counter2] = map[counter1][counter2] * x + Integer.parseInt(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1));
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

			return map;
		} catch (Exception e) {
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

            Tile[] tileSet = new Tile[nbNodes];

			Element layer;
			int a = 0;

			for (int i = 1; a < nbNodes; i++) {
				if (rootNode.item(i).getNodeType() == Node.ELEMENT_NODE)
					layer = (Element) rootNode.item(i);
				else {
					i++;
					layer = (Element) rootNode.item(i);
				}

				tileSet[a] = new Tile("/images/tiles/" + (layer.getAttribute("name")), Integer.parseInt(layer.getAttribute("type")));
				a++;
			}
            return tileSet;
		} catch (Exception e) {
			e.printStackTrace();
            return null;
		}
	}
}
