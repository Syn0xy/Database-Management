package model;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class TableColumn {

    private String name;
    private String type;
    private int precision;
    private boolean readOnly;
    private boolean autoIncrement;
    private int nullable;
    
    protected TableColumn(){}

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPrecision() {
        return precision;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public int getNullable() {
        return nullable;
    }

    protected void load(ResultSetMetaData rsmd, int index) throws SQLException{
        name = rsmd.getColumnName(index);
        type = rsmd.getColumnTypeName(index);
        precision = rsmd.getPrecision(index);
        readOnly = rsmd.isReadOnly(index);
        autoIncrement = rsmd.isAutoIncrement(index);
        nullable = rsmd.isNullable(index);
    }
}
