package migthycode.growth.game.utils;

import migthycode.growth.game.tilemap.Tile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlReader {

	public static int[][] createMap(String map_Path) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XmlReader.class.getResourceAsStream(map_Path));
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

					while (!isInteger(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1))) {
						System.out.println(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1));
						x = 1;
						numberOfCharacterRead++;
					}

					map[counter1][counter2] = map[counter1][counter2] * x + Integer.parseInt(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1));
					numberOfCharacterRead++;
					x *= 10;

					if(!isInteger(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1))) {
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
            int[][] map = null;
            return map;
		}
	}

	private static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static Tile[] createTileSet(String tileSetPath) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XmlReader.class.getResourceAsStream(tileSetPath));
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
            Tile[] tileSet = null;
            return tileSet;
		}
	}
}
