package driver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import condition.ConditionFinder;
import dbms.Column;
import dbms.DataBaseMS;
import extractor.CreateExtractor;
import extractor.DeleteExtractor;
import extractor.DropExtractor;
import extractor.Extractor;
import extractor.InsertExtractor;
import extractor.SelectExtractor;
import extractor.UpdateExtractor;
import extractor.UseExtractor;
import fileManager.WriterType;

public class Controller {
	/**
	 * read input from user.
	 */
	private static Scanner input;
	private static Extractor extractor;
	private static ConditionFinder conditionFinder;
	private DataBaseMS dbms;
	private List<Integer> affectedRowList;
	private List<Column> selectedDataList;
	private List<String> selectedColumnsNameList;
	private String currentDB;

	public Controller() {
		// TODO Auto-generated constructor stub
		dbms = new DataBaseMS();
		affectedRowList = null;
		conditionFinder = null;
		selectedDataList = null;
		selectedColumnsNameList = null;
		currentDB = null;
	}

	public void createDB() {
		currentDB = extractor.getDatabaseName();
		dbms.createDB(currentDB);
	}

	public void createTable() {
		affectedRowList = null;
		List<Column> tableData = new ArrayList<Column>();
		List<String> columnsList = extractor.getColumnsNames();
		List<String> dataTypeList = extractor.getColumnsTypes();
		for (int i = 0; i < columnsList.size(); i++) {
			Column columnI = new Column();
			tableData.add(columnI);
			tableData.get(i).setName(columnsList.get(i));
			tableData.get(i).setType(dataTypeList.get(i));
		}
		System.out.println(currentDB);
		dbms.createTable(extractor.getTableName(), currentDB, tableData,WriterType.json);
	}

    public void insertIntoTable() {
        affectedRowList = null;
        String tableName = extractor.getTableName();
        dbms.useTable(tableName);
        if (extractor.getColumnsNames() != null
                && extractor.getColumnsNames().size() > 0) {
            // System.out.println("ana hena yad");
            dbms.insertIntoTable(tableName, extractor.getColumnsNames(),
                    extractor.getValues());
        } else {
            dbms.insertIntoTable(tableName, extractor.getValues());
        }
        printFormatedTable(dbms.selectFromTable(tableName, null));
    }

    public void printFormatedTable(List<Column> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).getName() + "\t");
        }
        ArrayList<String> subList = list.get(0).getColElements();
        for (int j = 0; j < subList.size(); j++) {
            System.out.println();
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i).getColElements().get(j) + "\t");
            }
        }
        System.out.println();
    }

    public void selectFromTable() {
        String tableName = extractor.getTableName();
        dbms.useTable(tableName);
        selectedDataList = null;
        affectedRowList = null;
        if (extractor.getConditionFirstOperand() != null) {
            conditionFinder = new ConditionFinder(
                    extractor.getConditionFirstOperand(),
                    extractor.getConditionOperator(),
                    extractor.getConditionSecondOperand(), dbms, tableName);
            affectedRowList = conditionFinder.getRowsNumber();
        }
        selectedColumnsNameList = extractor.getColumnsNames();
        if (affectedRowList == null) {
            selectedDataList = dbms.selectFromTable(tableName,
                    selectedColumnsNameList);
            printFormatedTable(selectedDataList);
        } else {
            selectedDataList = dbms.selectFromTable(tableName,
                    selectedColumnsNameList, affectedRowList);
            printFormatedTable(selectedDataList);
        }
    }

    public List<String> getSelectedColumnNames() {
        return selectedColumnsNameList;
    }

    public List<String> getSelectedColumnDataType() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < selectedDataList.size(); i++) {
            data.add(selectedDataList.get(i).getName());
        }
        return data;
    }

    public List<List<String>> getSelectedDataValues() {
        List<List<String>> data = new ArrayList<List<String>>();
        List<String> subList = selectedDataList.get(0).getColElements();
        for (int j = 0; j < subList.size(); j++) {
            data.add(new ArrayList<String>());
            for (int i = 0; i < selectedDataList.size(); i++) {
                data.get(j)
                        .add(selectedDataList.get(i).getColElements().get(j));
            }
        }
        return data;
    }

	public void useDataBase() {
		currentDB = extractor.getDatabaseName();
		dbms.useDB(currentDB);
	}

    public void updateTable() {
        String tableName = extractor.getTableName();
        dbms.useTable(tableName);
        affectedRowList = null;
        if (extractor.getConditionFirstOperand() != null) {
            conditionFinder = new ConditionFinder(
                    extractor.getConditionFirstOperand(),
                    extractor.getConditionOperator(),
                    extractor.getConditionSecondOperand(), dbms, tableName);
            affectedRowList = conditionFinder.getRowsNumber();
        }
        List<String> cList = extractor.getColumnsNames();
        List<String> vList = extractor.getValues();
        if (affectedRowList == null) {
            dbms.updateTable(tableName, cList, vList);
        } else {
            dbms.updateTable(tableName, cList, vList, affectedRowList);
        }
        printFormatedTable(dbms.selectFromTable(tableName, null));
    }

	public void drop() {
		affectedRowList = null;
		String dataBaseName = extractor.getDatabaseName();
		String tableName = extractor.getTableName();
		// table = dataBase.useTable(extractor.getTableName());
		if (extractor.getTableName() != null) {
			dbms.dropTable(tableName);
		} else {
			dbms.dropDB(dataBaseName);
		}
	}

    public void delete() {
        affectedRowList = null;
        String tableName = extractor.getTableName();
        dbms.useDB(extractor.getDatabaseName());
        affectedRowList = null;
        if (extractor.getConditionFirstOperand() != null) {
            conditionFinder = new ConditionFinder(
                    extractor.getConditionFirstOperand(),
                    extractor.getConditionOperator(),
                    extractor.getConditionSecondOperand(), dbms, tableName);
            affectedRowList = conditionFinder.getRowsNumber();
        }
        dbms.useTable(extractor.getTableName());
        if (affectedRowList == null) {
            // delete all table data
            dbms.deleteFromTable(tableName, null);
        } else {
            dbms.deleteFromTable(tableName, affectedRowList);
        }
        printFormatedTable(dbms.selectFromTable(tableName, null));
    }

    public int getEffectedRowsNumber() {
        if (affectedRowList == null) {
            return 0;
        }
        return affectedRowList.size();
    }

    public String excutedStatementName() {
        return extractor.getCommand();
    }

	public void excuteStatement(String txt) {
		CreateExtractor createExtractor = new CreateExtractor(txt);
		InsertExtractor insertExtractor = new InsertExtractor(txt);
		SelectExtractor selectExtractor = new SelectExtractor(txt);
		UpdateExtractor updateExtractor = new UpdateExtractor(txt);
		DeleteExtractor deleteExtractor = new DeleteExtractor(txt);
		DropExtractor dropExtractor = new DropExtractor(txt);
		UseExtractor useExtractor = new UseExtractor(txt);
		if (createExtractor.isValid()
				&& createExtractor.getDatabaseName() != null) {
			extractor = createExtractor;
			createDB();
		} else if (createExtractor.isValid()
				&& createExtractor.getDatabaseName() == null) {
			extractor = createExtractor;
			createTable();
		} else if (insertExtractor.isValid()) {
			extractor = insertExtractor;
			insertIntoTable();
		} else if (selectExtractor.isValid()) {
			extractor = selectExtractor;
			selectFromTable();
			System.out.println(getSelectedDataValues());
		} else if (updateExtractor.isValid()) {
			extractor = updateExtractor;
			updateTable();
		} else if (deleteExtractor.isValid()) {
			extractor = deleteExtractor;
			delete();
		} else if (dropExtractor.isValid()) {
			extractor = dropExtractor;
			drop();
		} else if (useExtractor.isValid()) {
			extractor = useExtractor;
			useDataBase();
		} else {
			throw new RuntimeException("Syntax error");
		}
	}

    public static void main(String[] args) throws IOException {
        String path = new File("." + File.separator + "DataBases")
                .getCanonicalPath();
        File f = new File(path);
        ArrayList<String> names = new ArrayList<String>(
                Arrays.asList(f.list()));
        names.remove("bin");
        // System.out.println(names);
        Controller control = new Controller();
        // parser = new Parser(control.table, control.dataBase);
        input = new Scanner(System.in);
        System.out.println("Welcome, To stop the program write quit");
        System.out.print(">>");
        String txt = input.nextLine();
        while (!txt.equalsIgnoreCase("quit")) {
            try {
                // parser.validate(txt);
                control.excuteStatement(txt);
                System.out.print(">>");
                txt = input.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print(">>");
                txt = input.nextLine();
            }
        }
    }

}