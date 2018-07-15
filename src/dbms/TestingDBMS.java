//package dbms;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//
//public class TestingDBMS {
//	// test1 for data extraction.
////	@Test
////	public void test1() {
////		Parser parser = new Parser(new Table(), new DataBase());
////		ArrayList<String> arr = new ArrayList<>();
////		String text1 = "Create dataBase omar;";
////		parser.validate(text1);
////		assertEquals("omar", parser.getDataBaseName());
////		/////////////////////////////////////////////////////////////
////		text1 = "Create table omar (id int,name varchar,salary int);";
////		parser.validate(text1);
////		assertEquals("omar", parser.getTableName());
////		arr.add("id");
////		arr.add("name");
////		arr.add("salary");
////		assertEquals(arr, parser.getTableColumnsName());
////		arr.clear();
////		arr.add("int");
////		arr.add("varchar");
////		arr.add("int");
////		assertEquals(arr, parser.getDataType());
////		/////////////////////////////////////////////////////////////
////		text1 = "DELETE FROM table_name ;";
////		parser.validate(text1);
////		assertEquals("table_name", parser.getTableName());
////		/////////////////////////////////////////////////////////////
////		text1 = "DROP DATABASE database_name;";
////		parser.validate(text1);
////		assertEquals("database_name", parser.getDataBaseName());
////		/////////////////////////////////////////////////////////////
////		text1 = "INSERT INTO table_name VALUES (5, \"asdf\", 64);";
////		parser.validate(text1);
////		arr.clear();
////		arr.add("5");
////		arr.add("asdf");
////		arr.add("64");
////		assertEquals(arr, parser.getValues());
////		/////////////////////////////////////////////////////////////
////		text1 = "INSERT INTO table_name (id, name, salary) VALUES (5, \"asdf\", 64);";
////		parser.validate(text1);
////		arr.clear();
////		arr.add("id");
////		arr.add("name");
////		arr.add("salary");
////		assertEquals(arr, parser.getTableColumnsName());
////		arr.clear();
////		arr.add("5");
////		arr.add("asdf");
////		arr.add("64");
////		assertEquals(arr, parser.getValues());
////		/////////////////////////////////////////////////////////////
////		text1 = "UPDATE table_name SET column1=\"asf\", column2=5;";
////		parser.validate(text1);
////		arr.clear();
////		arr.add("column1");
////		arr.add("column2");
////		assertEquals(arr, parser.getTableColumnsName());
////		arr.clear();
////		arr.add("asf");
////		arr.add("5");
////		assertEquals(arr, parser.getValues());
////		/////////////////////////////////////////////////////////////
////		text1 = "SELECT * FROM table_name;";
////		parser.validate(text1);
////		assertEquals("table_name", parser.getTableName());
////		assertEquals(null, parser.getTableColumnsName());
////		/////////////////////////////////////////////////////////////
////		text1 = "SELECT abc,def,ghy FROM table_name;";
////		parser.validate(text1);
////		arr.clear();
////		arr.add("abc");
////		arr.add("def");
////		arr.add("ghy");
////		assertEquals(arr, parser.getTableColumnsName());
////	}
//
//	Table testTable = new Table();
//
//	DataBase db = new DataBase();
//
//	public void Initialize() {
//		// Suppose we have the following table:
//		// Id Name Degree
//		// 1 Student1 98
//		// 2 Student2 78
//		// 3 Student3 86
//		// 4 Student4 92
//		db.createDB("testDB");
//		ArrayList<Column> ali = new ArrayList<Column>();
//		// Creating Elements
//		ArrayList<String> col1 = new ArrayList<String>();
//		col1.add("1");
//		col1.add("2");
//		col1.add("3");
//		col1.add("4");
//		ArrayList<String> col2 = new ArrayList<String>();
//		col2.add("Student1");
//		col2.add("Student2");
//		col2.add("Student3");
//		col2.add("Student4");
//		ArrayList<String> col3 = new ArrayList<String>();
//		col3.add("98");
//		col3.add("78");
//		col3.add("86");
//		col3.add("92");
//
//		// Creating Columns
//		Column ali1 = new Column();
//		ali1.setColElements(col1);
//		ali1.setName("Id");
//		ali1.setType("int");
//		Column ali2 = new Column();
//		ali2.setColElements(col2);
//		ali2.setName("Name");
//		ali2.setType("varchar");
//		Column ali3 = new Column();
//		ali3.setColElements(col3);
//		ali3.setName("Degree");
//		ali3.setType("int");
//
//		// Adding Columns to out table
//		ali.add(ali1);
//		ali.add(ali2);
//		ali.add(ali3);
//		testTable.createTable("testTable", "testDB", ali);
//	}
//
//	@org.junit.Test
//	/**
//	 * Testing Select specific element from table implement.
//	 */
//	public void test() {
//
//		Initialize();
//		// Testing select specific cell For 1 Column
//		ArrayList<String> cols = new ArrayList<String>();
//		cols.add("Degree");
//		List<Integer> rowNumber = new ArrayList<Integer>();
//		rowNumber.add(3);
//		List<Column> s = testTable.select(cols, rowNumber);
//		assertEquals(1, s.size());
//		assertEquals("Degree", s.get(0).getName());
//		assertEquals("int", s.get(0).getType());
//		assertEquals("92", s.get(0).getColElements().get(0));
//	}
//
//	@org.junit.Test
//	/**
//	 * Testing select a specific row form the table
//	 */
//	public void test2() {
//		Initialize();
//		List<Integer> rowNumber2 = new ArrayList<Integer>();
//		rowNumber2.add(1);
//		List<Column> sec = testTable.select(null, rowNumber2);
//		assertEquals(3, sec.size());
//		// Cell 1 in 2nd row
//		assertEquals("Id", sec.get(0).getName());
//		assertEquals("int", sec.get(0).getType());
//		assertEquals("2", sec.get(0).getColElements().get(0));
//		// Cell 2 in 2nd row
//		assertEquals("Name", sec.get(1).getName());
//		assertEquals("varchar", sec.get(1).getType());
//		assertEquals("Student2", sec.get(1).getColElements().get(0));
//		// Cell 3 in 2nd row
//		assertEquals("Degree", sec.get(2).getName());
//		assertEquals("int", sec.get(2).getType());
//		assertEquals("78", sec.get(2).getColElements().get(0));
//	}
//
//	@org.junit.Test
//	/**
//	 * Testing select the whole table.
//	 */
//	public void test3() {
//		Initialize();
//		List<Column> third = testTable.select(null);
//		assertEquals(3, third.size());
//		// column 1
//		assertEquals("Id", third.get(0).getName());
//		assertEquals("int", third.get(0).getType());
//		assertEquals("1", third.get(0).getColElements().get(0));
//		assertEquals("2", third.get(0).getColElements().get(1));
//		assertEquals("3", third.get(0).getColElements().get(2));
//		assertEquals("4", third.get(0).getColElements().get(3));
//		// column 2
//		assertEquals("Name", third.get(1).getName());
//		assertEquals("varchar", third.get(1).getType());
//		assertEquals("Student1", third.get(1).getColElements().get(0));
//		assertEquals("Student2", third.get(1).getColElements().get(1));
//		assertEquals("Student3", third.get(1).getColElements().get(2));
//		assertEquals("Student4", third.get(1).getColElements().get(3));
//		// column 3
//		assertEquals("Degree", third.get(2).getName());
//		assertEquals("int", third.get(2).getType());
//		assertEquals("98", third.get(2).getColElements().get(0));
//		assertEquals("78", third.get(2).getColElements().get(1));
//		assertEquals("86", third.get(2).getColElements().get(2));
//		assertEquals("92", third.get(2).getColElements().get(3));
//	}
//
//	@org.junit.Test
//	/**
//	 * Testing insert Elements in new row.
//	 */
//	public void test4() {
//		Initialize();
//		ArrayList<String> values = new ArrayList<String>();
//		values.add("5");
//		values.add("Student5");
//		values.add("95");
//		testTable.insert(values);
//
//		// Number of rows
//		assertEquals(5, testTable.gettColumns().get(0).getColElements().size());
//		// Cells in the inserted row
//		assertEquals("5",
//				testTable.gettColumns().get(0).getColElements().get(4));
//		assertEquals("Student5",
//				testTable.gettColumns().get(1).getColElements().get(4));
//		assertEquals("95",
//				testTable.gettColumns().get(2).getColElements().get(4));
//	}
//
//	@org.junit.Test
//	/**
//	 * Testing insert Elements in just Specific cells (rest of the cell must be
//	 * assigned to "null")
//	 */
//	public void test5() {
//		Initialize();
//		ArrayList<String> values2 = new ArrayList<String>();
//		values2.add("Student5");
//		ArrayList<String> columns2 = new ArrayList<String>();
//		columns2.add("Name");
//		testTable.insert(columns2, values2);
//
//		// Number of rows
//		assertEquals(5, testTable.gettColumns().get(0).getColElements().size());
//		// Cells in the inserted row
//		assertEquals("null",
//				testTable.gettColumns().get(0).getColElements().get(4));
//		assertEquals("Student5",
//				testTable.gettColumns().get(1).getColElements().get(4));
//		assertEquals("null",
//				testTable.gettColumns().get(2).getColElements().get(4));
//	}
//
//	@org.junit.Test
//	/**
//	 * Testing deleting specific row from the table.
//	 */
//	public void test6() {
//		// delete row number 0 >> "1" "Student1" "98"
//
//		Initialize();
//		ArrayList<Integer> rowNumber3 = new ArrayList<Integer>();
//		rowNumber3.add(0);
//		testTable.delete(rowNumber3);
//
//		// Number of rows
//		assertEquals(3, testTable.gettColumns().get(0).getColElements().size());
//		// testing Cells in the new row 0
//		assertEquals("2",
//				testTable.gettColumns().get(0).getColElements().get(0));
//		assertEquals("Student2",
//				testTable.gettColumns().get(1).getColElements().get(0));
//		assertEquals("78",
//				testTable.gettColumns().get(2).getColElements().get(0));
//	}
//
//	@org.junit.Test
//	/**
//	 * Testing updating specific row
//	 */
//	public void test7() {
//		// Update row number 0 >> "2" "Student2" "78"
//		Initialize();
//		ArrayList<String> values3 = new ArrayList<String>();
//		values3.add("10");
//		values3.add("Student10");
//		values3.add("100");
//
//		ArrayList<String> columns3 = new ArrayList<String>();
//		columns3.add("Id");
//		columns3.add("Name");
//		columns3.add("Degree");
//
//		ArrayList<Integer> rowNumber3 = new ArrayList<Integer>();
//		rowNumber3.add(0);
//
//		testTable.update(columns3, values3, rowNumber3);
//
//		// Number of rows
//		assertEquals(4, testTable.gettColumns().get(0).getColElements().size());
//		// testing Cells in the updated row 0
//		assertEquals("10",
//				testTable.gettColumns().get(0).getColElements().get(0));
//		assertEquals("Student10",
//				testTable.gettColumns().get(1).getColElements().get(0));
//		assertEquals("100",
//				testTable.gettColumns().get(2).getColElements().get(0));
//
//	}
//}
