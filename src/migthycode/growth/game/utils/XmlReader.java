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
		int[][] map;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(XmlReader.class.getResourceAsStream(map_Path));
			Element racine = document.getDocumentElement();

			// Récupérer tout les noeuds enfants de la racine
			final NodeList racineNoeuds1 = racine.getChildNodes();

			int i = 0;
			Element layer;

			if (racineNoeuds1.item(i).getNodeType() == Node.ELEMENT_NODE) layer = (Element) racineNoeuds1.item(i);
			else {
				i++;
				layer = (Element) racineNoeuds1.item(i);
			}

			while (!layer.getNodeName().equals("layer")) {
				if (racineNoeuds1.item(i).getNodeType() == Node.ELEMENT_NODE) layer = (Element) racineNoeuds1.item(i);

				i++;
			}

			Element data;
			data = (Element) layer.getElementsByTagName("data").item(0);

			int width = Integer.parseInt(layer.getAttribute("width"));
			int height = Integer.parseInt(layer.getAttribute("height"));

			map = new int[height][width];

			String sMap = data.getTextContent();

			// Traduction de la map
			int counter1 = 0;
			int numberOfCharacterRead = 1;

			while (counter1 < height) {
				int x = 1;
				int counter2 = 0;

				while (counter2 < width) {

					while (estUnEntier(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1))) {
						System.out.println(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1));
						x = 1;
						numberOfCharacterRead++;
					}

					map[counter1][counter2] = map[counter1][counter2] * x + Integer.parseInt(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1));
					numberOfCharacterRead++;
					x *= 10;

					if(estUnEntier(sMap.substring(numberOfCharacterRead, numberOfCharacterRead + 1))) {
						x = 1;
						numberOfCharacterRead++;
						counter2++;
					}
				}
				counter1++;
				numberOfCharacterRead++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = null;
		}

		return map;
	}

	private static boolean estUnEntier(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static Tile[] createTileSet(String tilesetPath_Path) {
        Tile[] tileset;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(XmlReader.class.getResourceAsStream(tilesetPath_Path));
            Element racine = document.getDocumentElement();

            // Récupérer tous les noeuds enfants de la racine
            final NodeList racineNoeuds1 = racine.getChildNodes();

            int nbRacineNoeuds = racine.getElementsByTagName("texture").getLength();

            tileset = new Tile[nbRacineNoeuds];

            Element layer;
            int a = 0;

            for (int i = 1; a < nbRacineNoeuds; i++) {
                if (racineNoeuds1.item(i).getNodeType() == Node.ELEMENT_NODE)
                    layer = (Element) racineNoeuds1.item(i);
                else {
                    i++;
                    layer = (Element) racineNoeuds1.item(i);
                }

                tileset[a] = new Tile("/images/tiles/" + (layer.getAttribute("name")), Integer.parseInt(layer.getAttribute("type")));
                a++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            tileset = null;
        }
        return tileset;
    }
}
