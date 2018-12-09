import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

class EventScript {
    private static int currLevel = 0;
    private static int currTime = 0;
    private static int prevTime = 0;
    private static Document doc;
    private static int interval=10;

    static void loadDoc() {
        File inputFile = new File("files/script.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        doc = null;
        try {
            assert dBuilder != null;
            doc = dBuilder.parse(inputFile);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
    }


    static void readScript() {
        try {

            NodeList LevelList = doc.getElementsByTagName("Level");

            for (int temp = 0; temp < LevelList.getLength(); temp++) {
                NodeList EventList = doc.getElementsByTagName("Event");
                for (int EnentI = 0; EnentI < EventList.getLength(); EnentI++) {
                    Node nNode = EventList.item(EnentI);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        int stamp = Integer.parseInt(eElement.getAttribute("stamp"));
                        if (prevTime < stamp && stamp < currTime) {
                            prevTime = stamp;
                            String Entity = eElement
                                    .getElementsByTagName("Entity")
                                    .item(0)
                                    .getTextContent();
                            int health = Integer.parseInt(eElement
                                    .getElementsByTagName("health")
                                    .item(0)
                                    .getTextContent());
                            int armor = Integer.parseInt(eElement
                                    .getElementsByTagName("armor")
                                    .item(0)
                                    .getTextContent());
                            int shield = Integer.parseInt(eElement
                                    .getElementsByTagName("shield")
                                    .item(0)
                                    .getTextContent());
                            int speed = Integer.parseInt(eElement
                                    .getElementsByTagName("speed")
                                    .item(0)
                                    .getTextContent());
                            if (Entity.equals("EnemyShip")) {
                                double spawnpos = Math.random();
                                if (spawnpos < .33) {
                                    GameField.addEntity(new EnemyShip(-200, -300,
                                            32, 32, health, armor, shield, speed));
                                } else if (spawnpos > .33 && spawnpos < .66) {
                                    GameField.addEntity(new EnemyShip(1920/2, -300,
                                            32, 32, health, armor, shield, speed));
                                } else {
                                    GameField.addEntity(new EnemyShip(2000, -300,
                                            32, 32, health, armor, shield, speed));

                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void advanceScript() {
        currTime++;
        interval--;
        if(interval<=0){
            readScript();
            interval=100;
        }

    }
}
