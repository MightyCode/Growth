package growth.util;

import growth.entity.module.player.Player_Jump;
import growth.entity.type.Player;
import growth.main.Config;
import growth.main.Window;
import growth.screen.screens.GameScreen;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public abstract class LoadPlayerTree {

    public static void playerTree(){
        Player player = GameScreen.entityManager.getPlayer();
        Element root = XmlReader.getRootNoRes(Window.config.getValue(Config.PART_PATH) + "temp/save.xml");
        if(player.getForm() == -1){
            assert root != null;
            Element subRoot = XmlReader.search("player", root);
            int form = Integer.parseInt(subRoot.getAttribute("form"));
            if(form < 0) form = 0;
            else if(form > 2) form = 2;

            player.setForm(form);
        }

        assert root != null;
        int[][] tree = loadTree(root);
        player.setTree(tree);

        // Create variables
        int tileSize = GameScreen.tileSize;
        int maxLife = 2;

        // Load tree of generality
        // 0-0 -> jumping
        if(tree[0][0] == 1){
            float jumpStart = tileSize/-4.8f;
            float stopJumpSpeed = tileSize/130f;
            player.addModule(new Player_Jump(player, jumpStart, stopJumpSpeed));
        }

        player.setMaxHealthPoint(maxLife);
        player.setHealthPoint(
                Integer.parseInt(XmlReader.getValueNoRes(
                        Window.config.getValue(Config.PART_PATH) + "temp/save.xml",
                        "life",
                        "player"
                        )
                )
        );

        switch(player.getForm()){
            case 1:



                break;
            /*
             * Next form
             */
            case 2:



                break;
            /*
             * Next form
             */
            case 3:



                break;
        }
        GameScreen.entityManager.setPlayer(player);
    }

    private static int[][] loadTree(Element root){
        int[][] load = new int[4][];
        NodeList object = root.getElementsByTagName("tree");
        for(int i = 0; i < 4; i++){
            Element subRoot = (Element) object.item(i);
            int length = 0;
            while(!subRoot.getAttribute("nd" + length).equals("")) length++;

            load[i] = new int[length];
            for(int a = 0; a < length; a++){
                load[i][a] = Integer.parseInt(subRoot.getAttribute("nd"+a));
            }
        }
        return load;
    }

    public static void changeNodeValue(String name){
        String[] values = name.split("-");
        int tree = Integer.parseInt(values[0]);
        int node = Integer.parseInt(values[1]);
        try {
            String path = Window.config.getValue(Config.PART_PATH) + "temp/save.xml";
            Document doc = XmlReader.getDocument(path);
            assert doc != null;
            Element root = doc.getDocumentElement();
            assert root != null;
            NodeList object = root.getElementsByTagName("tree");
            Element subRoot = (Element) object.item(tree);
            subRoot.setAttribute("nd"+node, "1");

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
