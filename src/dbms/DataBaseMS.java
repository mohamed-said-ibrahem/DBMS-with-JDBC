package dbms;

import java.util.Hashtable;
import java.util.List;

import fileManager.WriterType;

public class DataBaseMS {
	private Hashtable<String, DataBase> AllDataBases;
	private DataBase currentDB;

	public DataBaseMS() {
		AllDataBases = new Hashtable<String, DataBase>();
	}

	public void dropTable(String tableName) {
		this.currentDB.dropTable(tableName);
	}

	public void insertIntoTable(String tableName, List<String> columnsName,
			List<String> values) {
		this.currentDB.insertIntoTable(tableName, columnsName, values);
	}

	public void insertIntoTable(String tableName, List<String> values) {
		this.currentDB.insertIntoTable(tableName, values);
	}

	public void createTable(String tableName, String dbName, List<Column> cols,
			WriterType fileType) {
		this.currentDB.createTable(tableName, dbName, cols, fileType);
	}

	public List<Column> selectFromTable(String tableName,
			List<String> columnsName, List<Integer> rowNumber) {
		return this.currentDB.selectFromTable(tableName, columnsName,
				rowNumber);
	}

	public List<Column> selectFromTable(String tableName,
			List<String> columnsName) {
		// TODO Auto-generated method stub
		return this.currentDB.selectFromTable(tableName, columnsName);
	}

	public void deleteFromTable(String tableName, List<Integer> rowNumber) {
		// TODO Auto-generated method stub
		this.currentDB.deleteFromTable(tableName, rowNumber);
	}

	public void updateTable(String tableName, List<String> columnsName,
			List<String> Values) {
		// TODO Auto-generated method stub
		this.currentDB.updateTable(tableName, columnsName, Values);
	}

	public void updateTable(String tableName, List<String> columnsName,
			List<String> Values, List<Integer> rowNumber) {
		this.currentDB.updateTable(tableName, columnsName, Values, rowNumber);
	}

	public void createDB(String dbName) {
		// TODO Auto-generated method stub
		DataBase newDB = new DataBase();
		newDB.createDB(dbName);
		AllDataBases.put(dbName, newDB);
		this.currentDB = newDB;
	}

	public void dropDB(String dataBaseName) {
		// TODO Auto-generated method stub
		DataBase tempDataBase = AllDataBases.get(dataBaseName);
		if (tempDataBase != null) {
			this.currentDB = tempDataBase;
			this.currentDB.dropDB();
			AllDataBases.remove(dataBaseName, this.currentDB);
			this.currentDB = null;
		} else {
			throw new RuntimeException("database not found");
		}
	}

	public void useDB(String dataBaseName) {
		DataBase tempDataBase = AllDataBases.get(dataBaseName);
		if (tempDataBase != null) {
			this.currentDB = tempDataBase;
		} else {
			throw new RuntimeException("dataBase not found");
		}
	}

	public void useTable(String tableName) {
		this.currentDB.useTable(tableName);
	}
}
