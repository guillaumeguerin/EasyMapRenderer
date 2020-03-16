package database;

import exceptions.ParseException;
import model.*;
import org.apache.log4j.Logger;
import parser.XMLParserFacade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for the OSM file processing.
 */
public class FileParser {

    private static final Logger logger = Logger.getLogger(FileParser.class);

    // Constructor
    private FileParser() {
        //Empty on purpose
    }

    /**
     * Insert parsed OSM data to the SQLLite DB.
     *
     * @param osmList the parsed element list
     */
    public static void insertData(List<Object> osmList) {
        for (int i = 0; i < osmList.size(); i++) {
            if (osmList.get(i) instanceof Node) {
                SQLiteJDBC.insertNode((Node) osmList.get(i));
            } else if (osmList.get(i) instanceof Way) {
                SQLiteJDBC.insertWay((Way) osmList.get(i));
            } else if (osmList.get(i) instanceof Relation) {
                SQLiteJDBC.insertRelation((Relation) osmList.get(i));
            } else if (osmList.get(i) instanceof Tag) {
                SQLiteJDBC.insertTag((Tag) osmList.get(i));
            } else if (osmList.get(i) instanceof Member) {
                SQLiteJDBC.insertMember((Member) osmList.get(i));
            }

        }

        for (int i = 0; i < osmList.size(); i++) {
            if (osmList.get(i) instanceof Way) {
                //SQLiteJDBC.updateAllNodes((Way) osmList.get(i));
                SQLiteJDBC.insertWayNodes((Way) osmList.get(i));
            }
            if (osmList.get(i) instanceof Relation) {
                SQLiteJDBC.updateWays((Relation) osmList.get(i));
            }
        }
    }

    /**
     * Creates all DB Tables.
     */
    private static void createDBSchema() {
        if (!SQLiteJDBC.tableExists("NODE") && !SQLiteJDBC.tableExists("WAY")
                && !SQLiteJDBC.tableExists("RELATION")
                && !SQLiteJDBC.tableExists("WAY_TAG")
                && !SQLiteJDBC.tableExists("RELATION_TAG")) {
            try {
                SQLiteJDBC.initTables();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Reads the OSM file.
     *
     * @param filePath path of the file
     */
    public static List<Object> readFile(String filePath) {
        BufferedReader br = null;
        FileReader fr = null;
        List<Object> osmList = new ArrayList<>();
        try {
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);
            osmList = readFile(br);
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                logger.error(ex);
            }
        }

        return osmList;
    }

    /**
     * Reads the OSM file.
     *
     * @param br buffer containing the file
     */
    public static List<Object> readFile(BufferedReader br) {
        List<Object> osmList = new ArrayList<>();
        try {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                Object o = readLine(sCurrentLine);
                if (!o.equals(new Object())) {
                    osmList.add(o);
                }
            }
        } catch (IOException e) {
            logger.error(e);
        }

        return osmList;
    }

    private static Object readLine(String line) {
        Object o = new Object();
        try {
            o = XMLParserFacade.build(line);
        } catch (ParseException e) {
            logger.error(e);
        }
        return o;
    }

    public static Map readMapFromFile(BufferedReader br) {
        return processMap(readFile(br));
    }

    public static Map processMap(List<Object> mapItems) {
        Map resultMap = new Map();
        List<Node> nodes = new ArrayList<>();
        List<Way> ways = new ArrayList<>();
        List<Relation> relations = new ArrayList<>();

        for (Object o : mapItems) {
            if (o instanceof Way) {
                Way w = (Way) o;
                ways.add(w);
            } else if (o instanceof Relation) {
                relations.add((Relation) o);
            } else if (o instanceof Node) {
                nodes.add((Node) o);
            }
        }

        resultMap.setNodes(nodes);

        List<Way> correctedWays = new ArrayList<>();
        for (Way w : ways) {
            List<Node> wrongNodeList = w.getNodes();
            List<Node> correctedNodeList = new ArrayList<>();
            for (int i = 0; i < wrongNodeList.size(); i++) {
                Node wrongNode = wrongNodeList.get(i);
                correctedNodeList.add(findNodeFromId(wrongNode.getId(), nodes));
            }
            w.setNodes(correctedNodeList);
            correctedWays.add(w);
        }
        resultMap.setWays(correctedWays);
        resultMap.setRelations(relations);
        resultMap.setMinNode(resultMap.getMinNode());
        resultMap.setMaxNode(resultMap.getMaxNode());

        return resultMap;
    }

    private static Node findNodeFromId(Double id, List<Node> nodes) {
        for (Node node : nodes) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return new Node();
    }

    /**
     * Processes the OSM file.
     *
     * @param filePath path of the file
     */
    public static void processOSMFile(String filePath) {
        List<Object> osmList = readFile(filePath);
        createDBSchema();
        insertData(osmList);
        SQLiteJDBC.cleanDatabase();
    }
}
