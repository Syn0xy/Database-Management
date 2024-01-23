package view;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import model.Table;
import model.TableColumn;

public class TablePanel extends JPanel {
    
    private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
    
    protected TablePanel(){
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    protected void setTable(Table table){
        removeAll();
        
        JLabel name = new JLabel(table.getName());

        TableColumn[] columns = table.getColumns();

        String[] columnNames = new String[]{
            "Name", "Type", "Precision", "ReadOnly", "AutoIncrement", "Nullable"
        };
        Object[][] data = new Object[columns.length][];
        
        for (int i = 0; i < columns.length; i++) {
            data[i] = getColumnPanel(columns[i]);
        }

        JTable columnsTable = new JTable(data, columnNames);

        add(name);
        add(columnsTable.getTableHeader());
        add(columnsTable);

        repaint();
        validate();

        
    }

    private Object[] getColumnPanel(TableColumn column){
        return new Object[]{
            column.getName(),
            column.getType(),
            column.getPrecision(),
            column.isReadOnly(),
            column.isAutoIncrement(),
            column.getNullable()
        };
    }

}
