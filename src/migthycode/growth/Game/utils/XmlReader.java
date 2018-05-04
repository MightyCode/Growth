package migthycode.growth.Game.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import migthycode.growth.Game.tilemap.Tile;

public class XmlReader {
	public XmlReader() {
		
		
	}
	    public int[][] createMap(String map_Path) {
    	
	    	int[][] map;
	    	
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	
	    	try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(getClass().getResourceAsStream(map_Path));
			    Element racine = document.getDocumentElement();
				
				// Récupérer tous les noeuds enfants de la racine
				final NodeList racineNoeuds1 = racine.getChildNodes();   
	
				int i = 0;
				Element layer;
				
				if(racineNoeuds1.item(i).getNodeType() == Node.ELEMENT_NODE) layer = (Element) racineNoeuds1.item(i);
				else {
					i++;
					layer = (Element) racineNoeuds1.item(i);
				}
	
				while(layer.getNodeName() != "layer") {
				    if(racineNoeuds1.item(i).getNodeType() == Node.ELEMENT_NODE) layer = (Element) racineNoeuds1.item(i);	
				    				
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
			    int numberOfCharcterRead = 1;
			    
			    while(counter1 < height) {
			    	int x  = 1;
			    	int counter2 = 0;
			    	
			    	while(counter2 < width) {		;
			    		map[counter1][counter2] = map[counter1][counter2] * x + Integer.parseInt(sMap.substring(numberOfCharcterRead,numberOfCharcterRead+1));
			    		numberOfCharcterRead++;
			    		x *= 10;	    		
			    		if(!estUnEntier(sMap.substring(numberOfCharcterRead , numberOfCharcterRead+1))) {
			    			x = 1;
			    			numberOfCharcterRead++;
				    		counter2++;	
			    		}
			    	}	   
			    	counter1++;
			    	numberOfCharcterRead++;
			    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map = null;
			}
	    	
	    	return map;
	    }
	    
		public boolean estUnEntier(String chaine) {
			try {
				Integer.parseInt(chaine);
			} catch (NumberFormatException e){
				return false;
			}
	 
			return true;
	}
	
	public Tile[] createTileSet(String tilesetPath_Path){
    	Tile[] tileset;
    	
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	
    	try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(getClass().getResourceAsStream(tilesetPath_Path));
		    Element racine = document.getDocumentElement();
			
			// Récupérer tous les noeuds enfants de la racine
			final NodeList racineNoeuds1 = racine.getChildNodes();   

			int nbRacineNoeuds = racine.getElementsByTagName("texture").getLength();
			
			tileset = new Tile[nbRacineNoeuds];
			
			Element layer;
			int a = 0;
			
			for(int i = 1; a < nbRacineNoeuds; i++) {
				if(racineNoeuds1.item(i).getNodeType() == Node.ELEMENT_NODE) 
					layer = (Element) racineNoeuds1.item(i);
				else {
					i++;
					layer = (Element) racineNoeuds1.item(i);
				}
				
				tileset[a] = new Tile("/images/tiles/"+(layer.getAttribute("name")),Integer.parseInt(layer.getAttribute("type")));
				a++;
			}
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tileset = null;
		}
    	return tileset;
    }
    
    public int[] createTimer(String path) {
    	int[] wainting = null;
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	
    	try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(getClass().getResourceAsStream(path + "config.xml"));
		    Element racine = document.getDocumentElement();
			
			// Récupérer tous les noeuds enfants de la racine
			final NodeList racineNoeuds1 = racine.getChildNodes();   

			int nbRacineNoeuds = racine.getElementsByTagName("texture").getLength();
			
			wainting = new int[nbRacineNoeuds];
			
			Element layer;
			int a = 0;
			
			for(int i = 1; a < nbRacineNoeuds; i++) {
				if(racineNoeuds1.item(i).getNodeType() == Node.ELEMENT_NODE) 
					layer = (Element) racineNoeuds1.item(i);
				else {
					i++;
					layer = (Element) racineNoeuds1.item(i);
				}
				
				wainting[a] = Integer.parseInt(layer.getAttribute("time"));
				a++;
			}
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return wainting;
    }
}
