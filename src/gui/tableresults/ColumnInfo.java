package gui.tableresults;

public class ColumnInfo {
    private String title;
    private ColumnType type;


    public ColumnInfo(String title, ColumnType type) {
        this.title = title;
        this.type = type;
    }

    @Override
    public String toString() {
        return title;
    }

    public Object getInitValue() {
        if (type == ColumnType.String) {
            return "";
        } else if (type == ColumnType.Integer) {
            return 0;
        }
        return "";
    }

    public Object getTypedValue(Object value) {
        if (type == ColumnType.String) {
            return value;
        } else if (type == ColumnType.Integer) {
            return Integer.parseInt(value.toString());
        }
        return "";
    }
}