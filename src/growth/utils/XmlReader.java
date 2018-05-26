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
	public static Map createMapT(String map_path) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XmlReader.class.getResourceAsStream("/map/" + map_path));
			Element root = document.getDocumentElement();

			int width = Integer.parseInt(root.getAttribute("width"));
			int height = Integer.parseInt(root.getAttribute("height"));


			// Get all child nodes of the root
			NodeList rootNodes = root.getChildNodes();

			int i = 0;
			Element subRoot1;


			if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) subRoot1 = (Element) rootNodes.item(i);
			else {
				i++;
				subRoot1 = (Element) rootNodes.item(i);
			}

			while (!(subRoot1.getNodeName().equals("goto"))) {
				i++;
				if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) subRoot1 = (Element) rootNodes.item(i);
			}

			int right = 0, left = 0, up = 0, down= 0;

			// Set the neighbour'ID of our map
			for(int a = 0; a < 4; a++){
				if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					subRoot1 = (Element) rootNodes.item(i);
					if((subRoot1.getNodeName().equals("goto"))){
						int j = Integer.parseInt(subRoot1.getAttribute("id"));
							switch (j) {
								case 1:
									left = Integer.parseInt(subRoot1.getAttribute("mapId"));
									break;
								case 2:
									up = Integer.parseInt(subRoot1.getAttribute("mapId"));
									break;
								case 3:
									right = Integer.parseInt(subRoot1.getAttribute("mapId"));
									break;
								case 4:
									down =  Integer.parseInt(subRoot1.getAttribute("mapId"));
									break;

						}
						i++;
					}
				} else {
					a--;
					i++;
				}
			}

			Map map = new Map(left, up, right, down);

			while (!(subRoot1.getNodeName().equals("layer"))) {
				if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) subRoot1 = (Element) rootNodes.item(i);
				i++;
			}

			for(int a = 0; a < 6; a++){
				if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {

					subRoot1 = (Element) rootNodes.item(i);
					if((subRoot1.getNodeName().equals("layer"))){

						int[][] mapId = new int[height][width];

						String sMap = subRoot1.getTextContent();

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
						map.setLayer(Integer.parseInt(subRoot1.getAttribute("id"))-1,mapId);
						i++;
					}
				} else {
					a--;
					i++;
				}

			}

			for(int a = 0; a < 4; a++){
				if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					subRoot1 = (Element) rootNodes.item(i);
					if(subRoot1.getNodeName().equals("begin")){
							map.setTileToCome(Integer.parseInt(subRoot1.getAttribute("id")),
									Float.parseFloat(subRoot1.getAttribute("x")),
									Float.parseFloat(subRoot1.getAttribute("y")));
						i++;
					}
				} else {
					a--;
					i++;

				}
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
