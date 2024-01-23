package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import view.util.Subject;

public class DatabaseManagement extends Subject {

    private DataSource dataSource;

    private Connection connection;

    private Table[] tables;

    public DatabaseManagement(){
        this.dataSource = null;
    }

    public Table[] getTables() {
        return tables;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        init();
    }

    private void init(){
        try {
            connection = dataSource.getConnection();
        } catch (ClassNotFoundException e) {
            System.err.println("Impossible de charger la classe: " + e.getMessage());
        } catch (SQLException e){
            System.err.println("Impossible d'établir la connection: " + e.getMessage());
        }
        reload();
    }

    public void reload(){
        loadTables();
        notifyObservers();
    }

    public void loadTables(){
        List<Table> tableList = new ArrayList<>();

        try {
            DatabaseMetaData dbMetaData = connection.getMetaData(); 
    
            String[] types = { "TABLE" };
    
            ResultSet rs = dbMetaData.getTables(null, null, "%", types);
    
            while(rs.next()){
                Table crntTable = new Table(rs.getString(3));
                crntTable.load(connection);
                tableList.add(crntTable);
            }
        } catch (SQLException e){
            System.err.println("Impossible d'établir la connection: " + e.getMessage());
        }

        tables = tableList.toArray(new Table[tableList.size()]);
    }

    public List<List<Object>> executeRequest(String sqlRequest) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sqlRequest);
        List<List<Object>> results = new ArrayList<>();
        
        while(rs.next()){
            List<Object> crntList = new ArrayList<>();
            for (int j = 1; j <= rs.getRow(); j++) {
                crntList.add(rs.getObject(j));
            }
            results.add(crntList);
        }

        return results;
    }

}
