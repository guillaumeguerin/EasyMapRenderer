package database;

import model.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SQLiteJDBC {

    private static final Logger logger = Logger.getLogger(SQLiteJDBC.class);

    // Constructor
    private SQLiteJDBC() {
        //Empty on purpose
    }

    public static void initTables() throws SQLException {


        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = ConnectionSingleton.getInstance().getConnection();
            logger.info("Opened database successfully");
            Statement stmt = null;
            stmt = c.createStatement();
            String sql = "CREATE TABLE NODE " +
                    "(ID DOUBLE PRIMARY KEY     NOT NULL," +
                    " LAT            REAL    NOT NULL, " +
                    " LON            REAL    NOT NULL, " +
                    " WAY            DOUBLE)";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE WAY " +
                    "(ID DOUBLE PRIMARY KEY    NOT NULL," +
                    " RELATION  DOUBLE)";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE RELATION " +
                    "(ID DOUBLE PRIMARY KEY     NOT NULL" +
                    " )";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE TAG " +
                    "(ID DOUBLE PRIMARY KEY     NOT NULL," +
                    " USED_BY DOUBLE," +
                    " TYPE1          TEXT, " +
                    " TYPE2          TEXT)";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE MEMBER " +
                    "(ID DOUBLE PRIMARY KEY     NOT NULL," +
                    " USED_BY DOUBLE," +
                    " RELATION DOUBLE," +
                    " ROLE          TEXT)";
            stmt.executeUpdate(sql);

            stmt.close();
        } catch (Exception e) {
            logger.error(e);
            System.exit(0);
        }
        logger.info("Opened database successfully");
    }

    public static boolean tableExists(String tableName) {
        String sql = "SELECT COUNT(*) AS NB_TABLE FROM sqlite_master WHERE type = \"table\" AND name = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tableName);
            rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                return (rs.getInt("NB_TABLE")) > 0;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
        return false;
    }

    public static void insertNode(Node node) {
        String sql = "INSERT INTO NODE(ID,LAT,LON,WAY) VALUES(?,?,?,?)";
        PreparedStatement pstmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, node.getId());
            pstmt.setDouble(2, node.getLat());
            pstmt.setDouble(3, node.getLon());
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    public static void insertWay(Way way) {
        String sql = "INSERT INTO WAY(ID) VALUES(?)";
        PreparedStatement pstmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, way.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }

        if (way.getTags() != null) {
            for (Tag t : way.getTags()) {
                insertTag(t, way.getId());
            }
        }

    }

    public static void insertRelation(Relation relation) {
        String sql = "INSERT INTO RELATION(ID) VALUES(?)";
        PreparedStatement pstmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, relation.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }

        if (relation.getTags() != null) {
            for (Tag t : relation.getTags()) {
                insertTag(t, relation.getId());
            }
        }
        if (relation.getMembers() != null) {
            for (Member m : relation.getMembers()) {
                insertMember(m, relation);
            }
        }
    }

    public static void insertTag(Tag tag) {
        String sql = "INSERT INTO TAG(ID, TYPE1, TYPE2) VALUES(?,?,?)";
        PreparedStatement pstmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, SequenceSingleton.getInstance().getId().intValue());
            pstmt.setString(2, tag.getType1());
            pstmt.setString(3, tag.getType2());
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    public static void insertTag(Tag tag, Double usedId) {
        String sql = "INSERT INTO TAG(ID, USED_BY, TYPE1, TYPE2) VALUES(?,?,?,?)";
        PreparedStatement pstmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, SequenceSingleton.getInstance().getId().intValue());
            pstmt.setDouble(2, usedId);
            pstmt.setString(3, tag.getType1());
            pstmt.setString(4, tag.getType2());
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    public static void cleanDatabase() {
        String sql = "DELETE FROM TAG WHERE LOWER(TYPE1) IN ('wikipedia', 'wikidata', 'website', 'url', 'source', 'start_date', 'population', 'political_division', 'phone', 'border', 'note', 'network', 'leisure', 'end_date', 'description', 'collection', 'access', 'border_type', 'boundary')";
        PreparedStatement pstmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }

        sql = "DELETE FROM TAG WHERE TYPE1 LIKE '%disused%' OR TYPE1 LIKE '%source%' OR TYPE1 LIKE '%seamark%' OR TYPE1 LIKE '%ref:%' OR TYPE1 LIKE '%planned%' OR TYPE1 LIKE '%mtb%' OR TYPE1 LIKE '%heritage%' OR TYPE1 LIKE '%boundary%' OR TYPE1 LIKE '%name%' OR TYPE1 LIKE '%ref%' OR TYPE1 LIKE '%CLC%' OR TYPE1 LIKE '%ISO%'  OR TYPE1 LIKE '%addr%' OR TYPE1 LIKE '%admin%'";
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }

        sql = "DELETE FROM WAY WHERE ID NOT IN (SELECT USED_BY FROM TAG)";
    }

    public static void insertMember(Member member) {
        String sql = "INSERT INTO MEMBER(ID, USED_BY, ROLE) VALUES(?,?,?)";
        PreparedStatement pstmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, SequenceSingleton.getInstance().getId().intValue());
            pstmt.setDouble(2, member.getUsedBy());
            pstmt.setString(3, member.getRole());
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    public static void insertMember(Member member, Relation relation) {
        String sql = "INSERT INTO MEMBER(ID, USED_BY, RELATION, ROLE) VALUES(?,?,?,?)";
        PreparedStatement pstmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, SequenceSingleton.getInstance().getId().intValue());
            pstmt.setDouble(2, member.getUsedBy());
            pstmt.setDouble(3, relation.getId());
            pstmt.setString(4, member.getRole());
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    public static void updateNode(Way w, Node node) {
        String sql = "UPDATE NODE SET WAY = ? WHERE ID = ?";
        PreparedStatement pstmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, w.getId());
            pstmt.setDouble(2, node.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    public static void updateWay(Relation r, Double wayID) {
        String sql = "UPDATE WAY SET RELATION = ? WHERE ID = ?";
        PreparedStatement pstmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, r.getId());
            pstmt.setDouble(2, wayID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    public static void updateWays(Relation r) {
        for (int i = 0; i < r.getWays().size(); i++) {
            updateWay(r, r.getWays().get(i));
        }
    }

    public static void updateTag(Double itemId, Double tagId) {
        String sql = "UPDATE TAG SET USED_BY = ? WHERE ID = ?";
        PreparedStatement pstmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, itemId);
            pstmt.setDouble(2, tagId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    public static void updateMember(Double itemId, Double memberId) {
        String sql = "UPDATE MEMBER SET USED_BY = ? WHERE ID = ?";
        PreparedStatement pstmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, itemId);
            pstmt.setDouble(2, memberId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
    }

    public static void updateAllNodes(Way w) {
        for (int i = 0; i < w.getNodes().size(); i++) {
            updateNode(w, w.getNodes().get(i));
        }
    }

    public static Map retrieveMapFromDB() {
        Map m = new Map();

        List<Way> ways = getWays();
        m.setWays(ways);
        List<Relation> relations = getRelations();
        m.setRelations(relations);
        return m;
    }

    public static List<Way> getWays() {
        String sql = "SELECT * FROM WAY";
        List<Way> ways = new ArrayList<>();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Double id = rs.getDouble("ID");
                Double usedBy = rs.getDouble("RELATION");
                List<Node> nodes = getNodesFromWayId(id);
                List<Tag> tags = getTagsFromWayId(id);
                Way currentWay = new Way(id, usedBy, nodes, tags);
                ways.add(currentWay);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
        return ways;
    }

    public static List<Way> getWaysFromRelationId(Double relationId) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        String sql = "SELECT * FROM WAY WHERE RELATION = " + df.format(relationId);
        List<Way> ways = new ArrayList<>();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Double id = rs.getDouble("ID");
                Double usedBy = rs.getDouble("RELATION");
                List<Node> nodes = getNodesFromWayId(id);
                List<Tag> tags = getTagsFromWayId(id);
                Way currentWay = new Way(id, usedBy, nodes, tags);
                ways.add(currentWay);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
        return ways;
    }

    public static List<Node> getNodesFromWayId(Double wayId) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        String sql = "SELECT * FROM NODE WHERE WAY = " + df.format(wayId);
        List<Node> nodes = new ArrayList<>();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Node currentNode = new Node(rs.getDouble("ID"), rs.getDouble("LAT"), rs.getDouble("LON"));
                nodes.add(currentNode);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
        return nodes;
    }

    public static List<Tag> getTagsFromWayId(Double wayId) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        String sql = "SELECT * FROM TAG WHERE USED_BY = " + df.format(wayId);
        List<Tag> tags = new ArrayList<>();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Tag currentTag = new Tag(rs.getDouble("ID"), rs.getDouble("USED_BY"), rs.getString("TYPE1"), rs.getString("TYPE2"));
                tags.add(currentTag);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
        return tags;
    }

    public static List<Member> getMembersFromRelationId(Double relationId) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        String sql = "SELECT * FROM MEMBER WHERE RELATION = " + df.format(relationId);
        ResultSet rs = null;
        Statement stmt = null;
        List<Member> members = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Member currentMember = new Member(rs.getDouble("ID"), rs.getDouble("USED_BY"), rs.getString("ROLE"));
                members.add(currentMember);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
        return members;
    }

    public static List<Relation> getRelations() {
        String sql = "SELECT * FROM RELATION";
        List<Relation> relations = new ArrayList<>();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = ConnectionSingleton.getInstance().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Double id = rs.getDouble("ID");
                List<Tag> tags = getTagsFromWayId(id);
                List<Way> ways = getWaysFromRelationId(id);
                List<Double> wayIds = new ArrayList<>();
                List<Member> members = new ArrayList<>();
                members.addAll(getMembersFromRelationId(id));
                for (int i = 0; i < ways.size(); i++) {
                    wayIds.add(ways.get(i).getId());
                }
                Relation currentRelation = new Relation(id, null, tags, members);
                relations.add(currentRelation);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
        }
        return relations;
    }

}