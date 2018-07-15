package driver;

import java.util.List;

/**
 * this is a temporary class.
 *
 * @author omar
 *
 */
public class Driver {

    private static Controller control;

    public Driver() {
        control = new Controller();
    }

    public void setStatement(String txt) {
        control.excuteStatement(txt);
    }

    public String getQueryType() {
        return control.excutedStatementName();
    }

    public int getAffectedRowsNumber() {
        return control.getEffectedRowsNumber();
    }

    public List<List<String>> getDataSelected() {
        return control.getSelectedDataValues();
    }

    public List<String> getDataType() {
        return control.getSelectedColumnDataType();
    }

    public List<String> getColumnsName() {
        return control.getSelectedColumnNames();
    }
}