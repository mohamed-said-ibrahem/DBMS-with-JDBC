package dbms;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fileManager.IFileWriter;
import fileManager.JsonWriter;
import fileManager.WriterType;
import fileManager.XMLWriter;

/**
 * Please use constructor to intialize the variables.
 *
 * @author omar
 *
 */
public class TableManager {

	private MiniTable miniTable;
	private IFileWriter fileCreator;

	public TableManager() {
		miniTable = null;
		fileCreator = null;
	}

	public void setCurrentTable(MiniTable mT) {
		this.miniTable = mT;
	}

	public MiniTable createTable(String tName, String dbName, List<Column> cols,
			WriterType fileType) {
		miniTable = new MiniTable(tName, dbName, cols);
		switch (fileType) {
		case xml:
			fileCreator = new XMLWriter();
			break;
		case json:
			fileCreator = new JsonWriter();
			break;
		default:
			throw new RuntimeException("unsupported file type");
		}
		fileCreator.write(tName, dbName, cols, miniTable);
		return miniTable;
	}

	public void dropTable() {
		try {
			this.miniTable.getDataFile().delete();
			if (this.miniTable.getDtDFile() != null) {
				this.miniTable.getDtDFile().delete();
			}
			System.out.println("Bye Bye " + this.miniTable.getName());
			this.miniTable = null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void insert(List<String> values) {
		if (values.size() != this.miniTable.gettColumns().size()) {
			throw new RuntimeException(
					"number of values entered isn't compatible with number of columns of ("
							+ this.miniTable.getName()
							+ "). \nnumber of columns required = "
							+ this.miniTable.gettColumns().size());
		}
		String[] checker = new String[values.size()];
		checker = values.toArray(checker);
		checkType(checker);
		int i = 0;
		for (Column col : this.miniTable.gettColumns()) {
			if (i < values.size()) {
				col.getColElements().add(values.get(i));
				i++;
			}
		}
		fileCreator.write(this.miniTable.getName(), this.miniTable.getDBName(),
				this.miniTable.gettColumns(), this.miniTable);
	}

	public void insert(List<String> columns, List<String> values) {
		if (columns.size() != values.size()) {
			throw new RuntimeException(
					"number of values entered isn't compatible with number of columns of ("
							+ this.miniTable.getName()
							+ "). \nnumber of columns required = "
							+ this.miniTable.gettColumns().size());
		}
		String[] row = new String[this.miniTable.gettColumns().size()];
		int i = 0;
		for (Column col : this.miniTable.gettColumns()) {
			for (String colName : columns) {
				if (colName.equals(col.getName())) {
					row[i] = values.get(columns.indexOf(colName));
					break;
				} else {
					row[i] = "null";
				}
			}
			i++;
		}
		checkType(row);
		int j = 0;
		for (Column col : this.miniTable.gettColumns()) {
			if (j < row.length) {
				col.getColElements().add(row[j]);
				j++;
			}
		}
		fileCreator.write(this.miniTable.getName(), this.miniTable.getDBName(),
				this.miniTable.gettColumns(), this.miniTable);
	}

	private void checkType(String[] row) {
		int j = 0;
		for (Column col : this.miniTable.gettColumns()) {
			if (col.getType().equals("int") && row[j] != "null") {
				checkInt(row[j]);
			} else if (col.getType().equals("varchar")) {
				checkStr(row[j]);
			} else if (col.getType().equals("date") && row[j] != "null") {
				checkDate(row[j]);
			} else if (col.getType().equals("float") && row[j] != "null") {
				checkFloat(row[j]);
			}
			j++;
		}
	}

	private void checkDate(String s) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		try {
			formatter.parse(s);

		} catch (ParseException e) {
			throw new RuntimeException("wrong Date dataType");
		}
	}

	private void checkInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			throw new RuntimeException(
					"type of data you entered is not compatible with column dataType");
		}

	}

	private void checkStr(String s) {
		if (s.matches("^[+-]?\\d+$")) {
			throw new RuntimeException(
					"cannot enter numbers in column of varchar dataType");
		}
	}

	private void checkFloat(String s) {
		try {
			Float.parseFloat(s);
		} catch (Exception e) {
			throw new RuntimeException(
					"type of data you entered is not compatible with column dataType");
		}

	}

	public List<Column> select(List<String> columnsName,
			List<Integer> rowNumber) {
		List<Column> returnCol = new ArrayList<Column>();
		if (columnsName == null) {
			selectAll(rowNumber, returnCol);
			return returnCol;
		} else if (columnsName.size() > miniTable.gettColumns().size()) {
			throw new RuntimeException(
					"number of columns exceed the number of columns of "
							+ this.miniTable.getName() + " table");
		}

		int flag = 0;
		for (String colName : columnsName) {
			for (Column col : this.miniTable.gettColumns()) {
				if (colName.equals(col.getName())) {
					Column myCol = new Column();
					for (int i = 0; i < rowNumber.size(); i++) {
						if (rowNumber.get(i) < this.miniTable.gettColumns()
								.get(i).getColElements().size()) {
							myCol.setName(col.getName());
							myCol.setType(col.getType());
							myCol.getColElements().add(
									col.getColElements().get(rowNumber.get(i)));
						} else {
							throw new RuntimeException("wrong row number");
						}
					}
					returnCol.add(myCol);
					flag++;
					break;
				}
			}

		}
		if (flag < columnsName.size()) {
			throw new RuntimeException("wrong column name");
		}
		return returnCol;
	}

	private void selectAll(List<Integer> rowNumber, List<Column> returnCol) {
		for (Column c : this.miniTable.gettColumns()) {
			Column myCol = new Column();
			for (int i = 0; i < rowNumber.size(); i++) {
				if (rowNumber.get(i) < this.miniTable.gettColumns().get(i)
						.getColElements().size()) {
					myCol.setName(c.getName());
					myCol.setType(c.getType());
					myCol.getColElements()
							.add(c.getColElements().get(rowNumber.get(i)));
				} else {
					throw new RuntimeException("wrong row number");
				}
			}
			returnCol.add(myCol);
		}
	}

	public List<Column> select(List<String> columnsName) {
		if (columnsName == null) {
			return this.miniTable.gettColumns();
		} else if (columnsName.size() > miniTable.gettColumns().size()) {
			throw new RuntimeException(
					"number of columns exceed the number of columns of "
							+ this.miniTable.getName() + " table");
		}

		int flag = 0;
		List<Column> returnCol = new ArrayList<Column>();
		for (String colName : columnsName) {
			for (Column col : this.miniTable.gettColumns()) {

				if (colName.equals(col.getName())) {
					returnCol.add(col);
					flag++;
					break;
				}
			}
		}
		if (flag != columnsName.size()) {
			throw new RuntimeException("wrong column name");
		}
		return returnCol;
	}

	public void delete(List<Integer> rowNumber) {
		if (rowNumber == null) {
			for (Column c : this.miniTable.gettColumns()) {
				c.getColElements().clear();
			}
		} else {
			for (Column c : this.miniTable.gettColumns()) {
				for (int i = 0; i < rowNumber.size(); i++) {
					int index = rowNumber.get(i) - i;
					if (index < this.miniTable.gettColumns().get(i)
							.getColElements().size()) {
						c.getColElements().remove(index);
					} else {
						throw new RuntimeException("wrong row number");
					}
				}
			}
		}
		fileCreator.write(this.miniTable.getName(), this.miniTable.getDBName(),
				this.miniTable.gettColumns(), this.miniTable);
	}

	public void update(List<String> columnsName, List<String> Values) {
		int j = 0;
		int flag = 0;
		for (String colName : columnsName) {
			for (Column c : this.miniTable.gettColumns()) {

				if (colName.equals(c.getName())) {
					flag++;
					for (int i = 0; i < this.miniTable.gettColumns().get(0)
							.getColElements().size(); i++) {
						c.getColElements().set(i, Values.get(j));

					}
				}
			}
			j++;
		}
		if (flag != columnsName.size()) {
			throw new RuntimeException("wrong column name");
		}
		fileCreator.write(this.miniTable.getName(), this.miniTable.getDBName(),
				this.miniTable.gettColumns(), this.miniTable);
	}

	public void update(List<String> columnsName, List<String> Values,
			List<Integer> rowNumber) {

		int j = 0;
		int flag = 0;
		for (String colName : columnsName) {
			for (Column c : this.miniTable.gettColumns()) {
				if (colName.equals(c.getName())) {
					flag++;
					for (int i = 0; i < rowNumber.size(); i++) {
						if (rowNumber.get(i) < this.miniTable.gettColumns()
								.get(i).getColElements().size()) {
							c.getColElements().set(rowNumber.get(i),
									Values.get(j));
						} else {
							throw new RuntimeException("wrong row number");
						}
					}
				}
			}
			j++;
		}
		if (flag != columnsName.size()) {
			throw new RuntimeException();
		}
		fileCreator.write(this.miniTable.getName(), this.miniTable.getDBName(),
				this.miniTable.gettColumns(), this.miniTable);
	}

	/**
	 *
	 * @param columnsName
	 *            The Columns names given by user
	 * @return those Columns with distinct (different) values only. a column may
	 *         contain many duplicate values. we Remove them to list the
	 *         different values
	 */
	public List<Column> selectDistinct(List<String> columnsName) {
		List<Column> returnCol = select(columnsName);
		if (containNoDup(returnCol)) {
			return returnCol;
		} else {
			List<Integer> locDup = findDisLoc(returnCol);
			List<Column> DisColumns = removeDup(returnCol, locDup);
			return DisColumns;
		}
	}

	/**
	 *
	 * @param cols
	 *            the Selected columns
	 * @return a Boolean for Whether a selected column contains no duplicate
	 *         Elements.
	 */
	private boolean containNoDup(List<Column> cols) {
		List<String> list;
		Set<String> set;
		for (Column col : cols) {
			list = col.getColElements();
			set = new HashSet<String>(list);
			if (set.size() == list.size()) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param Cols
	 *            the Selected Columns
	 * @return the Distinct Elements Location without Duplicates
	 */
	private List<Integer> findDisLoc(List<Column> Cols) {
		List<String> nonDupList;
		List<Integer> temp = new ArrayList<Integer>();
		List<Integer> actualDupLoc = new ArrayList<Integer>();
		for (Column col : Cols) {
			int i = 0;
			nonDupList = new ArrayList<String>();
			// Adding Distinct Elements Location (Duplicate Accepted)
			for (String dupWord : col.getColElements()) {
				if (!nonDupList.contains(dupWord)) {
					nonDupList.add(dupWord);
					temp.add(i);
				}
				i++;
			}
		}
		// removing Duplicate Locations.
		actualDupLoc = actualLoc(temp);
		return actualDupLoc;
	}

	/**
	 *
	 * @param temp
	 *            the Distinct Elements Location with no matter if there are
	 *            Duplicates or not
	 * @return the Distinct Elements Location without Duplicates
	 */
	private List<Integer> actualLoc(List<Integer> temp) {
		List<Integer> Dup = new ArrayList<Integer>();
		for (int k = 0; k < temp.size(); k++) {
			// Adding rows number without duplicate.
			if (!Dup.contains(temp.get(k))) {
				Dup.add(temp.get(k));
			}
		}
		return Dup;
	}

	/**
	 *
	 * @param cols
	 *            The Selected Columns.
	 * @param minLoc
	 *            The Selected Columns without Duplicates
	 * @return
	 */
	private List<Column> removeDup(List<Column> cols, List<Integer> minLoc) {
		// removing duplicate rows.
		for (int i = 0; i < cols.get(0).getColElements().size(); i++) {
			if (!minLoc.contains(i)) {
				for (Column col : cols) {
					col.getColElements().remove(i);
					// ColElements' Size decreases.
					i--;
				}
			}
		}
		return cols;
	}

	/**
	 * Adding a column in a table
	 *
	 * @param cols
	 *            a List of new Columns to be added to the table
	 */
	public void alterAdd(List<Column> cols) {
		for (Column col : cols) {
			for (int i = 0; i < this.miniTable.gettColumns().get(0)
					.getColElements().size(); i++) {
				col.getColElements().add("null");
			}
			this.miniTable.gettColumns().add(col);
		}
		fileCreator.write(this.miniTable.getName(), this.miniTable.getDBName(),
				this.miniTable.gettColumns(), this.miniTable);
	}

	/**
	 * Removing a column in a table
	 */
	public void alterDelete(List<String> columnsName) {
		int flag = 0;
		for (String colName : columnsName) {
			for (Column col : this.miniTable.gettColumns()) {

				if (colName.equals(col.getName())) {
					this.miniTable.gettColumns().remove(col);
					flag++;
					break;
				}
			}
		}
		if (flag != columnsName.size()) {
			throw new RuntimeException("wrong column name");
		}
		fileCreator.write(this.miniTable.getName(), this.miniTable.getDBName(),
				this.miniTable.gettColumns(), this.miniTable);
	}

}