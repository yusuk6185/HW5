package dbHelpers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Barcasquad;

public class SearchQuery {

    private Connection conn;
    private ResultSet results;

    public SearchQuery() {

        Properties props = new Properties();
        InputStream instr = getClass().getResourceAsStream("dbConn.properties");
        try {
            props.load(instr);
        } catch (IOException ex) {
            Logger.getLogger(SearchQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            instr.close();
        } catch (IOException ex) {
            Logger.getLogger(SearchQuery.class.getName()).log(Level.SEVERE, null, ex);
        }

        String driver = props.getProperty("driver.name");
        String url = props.getProperty("server.name");
        String username = props.getProperty("user.name");
        String passwd = props.getProperty("user.password");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection(url, username, passwd);
        } catch (SQLException ex) {
            Logger.getLogger(SearchQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doSearch(String playerName) {

        try {
            String query = "SELECT * FROM barcasquad WHERE UPPER(playerName) LIKE ? ORDER BY playerID ASC";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "%" + playerName + "%");
            this.results = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(SearchQuery.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getHTMLTable() {

        String table = "";

        try {
            while (this.results.next()) {

                Barcasquad barca = new Barcasquad();
                barca.setPlayerID(this.results.getInt("playerID"));
                barca.setPlayerName(this.results.getString("playerName"));
                barca.setPlayerPosition(this.results.getString("playerPosition"));
                barca.setAge(this.results.getInt("age"));
                table += "<tr>";
                table += "<th>PlayerID</th>";
                table += "<th>PlayerName</th>";
                table += "<th>PlayerPosition</th>";
                table += "<th>Age</th>";
                table += "</tr>";

                table += "<tr>";
                table += "<td>";
                table += barca.getPlayerID();
                table += "</td>";

                table += "<td>";
                table += barca.getPlayerName();
                table += "</td>";

                table += "<td>";
                table += barca.getPlayerPosition();
                table += "</td>";

                table += "<td>";
                table += barca.getAge();
                table += "</td>";

                table += "<td>";
                table += "<a href=update?playerID=" + barca.getPlayerID() + "> Update </a>" + "<a href=delete?playerID=" + barca.getPlayerID() + ">Delete </a>";
                table += "</td>";

                table += "</tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchQuery.class.getName()).log(Level.SEVERE, null, ex);
        }

        table += "</table>";

        return table;
    }
}
