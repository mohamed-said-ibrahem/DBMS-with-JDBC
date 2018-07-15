package dbms;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;

import fileManager.WriterType;


/**
 * Please use constructor to intialize the variables.
 *
 * @author omar
 *
 */
public class DataBase {
	private String name;
	private Path myPath;
	private TableManager tableManager;
	private Hashtable<String, MiniTable> myTables;
	private MiniTable currentTable;

	public DataBase() {
		tableManager = new TableManager();
		myTables = new Hashtable<String, MiniTable>();
	}

	public String getName() {
		return name;
	}

	public void createDB(String dataBaseName) {
		this.name = dataBaseName;
		this.myPath = Paths.get("." + File.separator + "DataBases"
				+ File.separator + dataBaseName);
        if (!new File("." + File.separator + "DataBases" + File.separator
                + dataBaseName).mkdir()) {
            throw new RuntimeException(
                    "cannot create " + this.myPath.getFileName()
                            + " folder.May be " + this.myPath.getFileName()
                            + " is duplicate database name");
        }
	}

	public void dropDB() {
		try {
			delete(this.myPath.toFile());
			System.out.println("Bye Bye " + this.name);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"cannot delete " + this.myPath.getFileName() + " folder");
		}
		this.myTables.clear();
	}

	/**
	 * to delete the directory when it have files.
	 *
	 * @param index
	 */
	private void delete(File index) {
		String[] entries = index.list();
		for (String s : entries) {
			File currentFile = new File(index.getPath(), s);
			currentFile.delete();
		}
		// check the directory again, if empty then delete it
		if (index.list().length == 0) {
			index.delete();
		}
	}

	public void useTable(String tableName) {
		MiniTable tempTable = this.myTables.get(tableName);
		if (tempTable != null) {
			this.currentTable = tempTable;
		} else {
			throw new RuntimeException("table " + tableName + " not found");
		}

	}

	public void dropTable(String tableName) {
		MiniTable tempTable = this.myTables.get(tableName);
		if (tempTable != null) {
			this.currentTable = tempTable;
			this.tableManager.setCurrentTable(tempTable);
			this.tableManager.dropTable();
			myTables.remove(tableName);
			this.currentTable = null;
		} else {
			throw new RuntimeException("table " + tableName + " not found");
		}
	}

	public void insertIntoTable(String tableName, List<String> columnsName,
			List<String> values) {
		this.currentTable = myTables.get(tableName);
		this.tableManager.setCurrentTable(this.currentTable);
		this.tableManager.insert(columnsName, values);
	}

	public void insertIntoTable(String tableName, List<String> values) {
		this.currentTable = myTables.get(tableName);
		this.tableManager.setCurrentTable(this.currentTable);
		this.tableManager.insert(values);
	}

	public void createTable(String tableName, String dbName,
			List<Column> cols,WriterType fileType) {
		MiniTable newTable = this.tableManager.createTable(tableName, dbName,
				cols,fileType);
		this.myTables.put(tableName, newTable);
		this.currentTable = newTable;
	}

	public List<Column> selectFromTable(String tableName,
			List<String> columnsName, List<Integer> rowNumber) {
		this.currentTable = myTables.get(tableName);
		this.tableManager.setCurrentTable(this.currentTable);
		return this.tableManager.select(columnsName, rowNumber);
	}

	public List<Column> selectFromTable(String tableName,
			List<String> columnsName) {
		this.currentTable = myTables.get(tableName);
		this.tableManager.setCurrentTable(this.currentTable);
		return this.tableManager.select(columnsName);
	}

	public void deleteFromTable(String tableName, List<Integer> rowNumber) {
		this.currentTable = myTables.get(tableName);
		this.tableManager.setCurrentTable(this.currentTable);
		this.tableManager.delete(rowNumber);

	}

	public void updateTable(String tableName, List<String> columnsName,
			List<String> Values) {
		this.currentTable = myTables.get(tableName);
		this.tableManager.setCurrentTable(this.currentTable);
		this.tableManager.update(columnsName, Values);
	}

	public void updateTable(String tableName, List<String> columnsName,
			List<String> Values, List<Integer> rowNumber) {
		this.currentTable = myTables.get(tableName);
		this.tableManager.setCurrentTable(this.currentTable);
		this.tableManager.update(columnsName, Values, rowNumber);
	}

}
