package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private String name;
    private TableColumn[] columns;
    
    protected Table(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TableColumn[] getColumns() {
        return columns;
    }
    
    public void load(Connection connection) {
        List<TableColumn> columnsList = new ArrayList<>();
        
        try {
            Statement stmt = connection.createStatement();
            String query = "select * from " + name + " where 1=2";
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int nbCols = rsmd.getColumnCount();
            for (int i = 1; i <= nbCols; i++) {
                TableColumn column = new TableColumn();
                column.load(rsmd, i);
                columnsList.add(column);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        columns = columnsList.toArray(new TableColumn[columnsList.size()]);
    }
}