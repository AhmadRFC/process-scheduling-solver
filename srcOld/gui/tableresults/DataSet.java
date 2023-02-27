package gui.tableresults;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DataSet extends AbstractTableModel {
    private Vector<ColumnInfo> columnNames;
    private List<List<Object>> data;

    public DataSet() {
        columnNames = new Vector<>();
        data = new ArrayList<>();
    }

    public String getColumnName(int index) {
        return columnNames.get(index).toString();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < data.size() && columnIndex < data.get(rowIndex).size()) {
            return data.get(rowIndex).get(columnIndex);
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        value = columnNames.get(col).getTypedValue(value);

        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);

    }

    public void addColumn(ColumnInfo column) {
        columnNames.add(column);
        updateRows();
    }

    public void addRow() {
        data.add(new ArrayList<>());
        updateRow(data.get(data.size() - 1));
    }

    private void updateRows() {
        int i = 0;
        for (List<Object> row : data) {
            if (row == null) {
                data.set(i, new ArrayList<>());
            }
            if (row != null) {
                updateRow(row);
            }
            i++;
        }
    }

    private void updateRow(List<Object> row) {
        while (row.size() < columnNames.size()) {
            int i = row.size();
            row.add(columnNames.get(i).getInitValue());
        }
    }
}