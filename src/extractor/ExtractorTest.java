package extractor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ExtractorTest {

	@Test
	public void test1() {
		String statement = "create table table1 (students varchar, grades int);";
		Extractor extractor = new CreateExtractor(statement);
		assertTrue(extractor.isValid());
		// assertArrayEquals(new String[]{"students","grades"},
		// extractor.getColumnsNames());
		assertEquals("table1", extractor.getTableName());
		assertNull(extractor.getDatabaseName());
		assertNull(extractor.getConditionOperator());
	}

	@Test
	public void test2() {
		String statement = "drop dataBasE database;";
		Extractor extractor = new DropExtractor(statement);
		assertTrue(extractor.isValid());
		assertNull(extractor.getColumnsNames());
		assertEquals("database", extractor.getDatabaseName());
		assertNull(extractor.getTableName());
		assertNull(extractor.getConditionOperator());
	}

	@Test
	public void test3() {
		String statement = "insert INTO t1 values ('Ahmed' , '18');";
		Extractor extractor = new InsertExtractor(statement);
		assertTrue(extractor.isValid());
		assertNull(extractor.getColumnsNames());
		assertEquals("t1", extractor.getTableName());
		assertNull(extractor.getDatabaseName());
		assertNull(extractor.getConditionOperator());
	}

	@Test
	public void test4() {
		String statement = "select * from t1 where c1 < 12 ;";
		Extractor extractor = new SelectExtractor(statement);
		assertTrue(extractor.isValid());
		assertNull(extractor.getColumnsNames());
		assertEquals("t1", extractor.getTableName());
		assertNull(extractor.getDatabaseName());
		assertEquals("c1", extractor.getConditionFirstOperand());
		assertEquals("<", extractor.getConditionOperator());
		assertEquals("12", extractor.getConditionSecondOperand());
	}

	@Test

	public void test5() {
		String statement = "select c1 , c2 from tableName;";
		Extractor extractor = new SelectExtractor(statement);
		assertTrue(extractor.isValid());
		List<String> test = new ArrayList<String>();
		test.add("c1");
		test.add("c2");
		assertEquals(test, extractor.getColumnsNames());
		assertEquals("tableName", extractor.getTableName());
		assertNull(extractor.getDatabaseName());
	}

	@Test
	public void test6() {
		String statement = "update t1 set c1 = '1' , c2 = 'v2', c4 = 'N5';";
		Extractor extractor = new UpdateExtractor(statement);
		assertTrue(extractor.isValid());
		assertEquals("t1", extractor.getTableName());
		assertNull(extractor.getDatabaseName());
		assertNull(extractor.getConditionOperator());
		List<String> test = new ArrayList<String>();
		test.add("c1");
		test.add("c2");
		test.add("c4");
		assertEquals(test, extractor.getColumnsNames());
		test.clear();
		test.add("1");
		test.add("v2");
		test.add("N5");
		assertEquals(test, extractor.getValues());
	}

	@Test

	public void test7() {
		String statement = "update t1 set c1 = '1' , c2 = '2', c4 = '4';";
		Extractor extractor = new UpdateExtractor(statement);
		assertTrue(extractor.isValid());
		List<String> test = new ArrayList<String>();
		test.add("1");
		test.add("2");
		test.add("4");
		assertEquals(test, extractor.getValues());/** gives an error */
	}

	@Test
	public void test8() {
		/** it accepts statement without semicolon. */
		String statement = "delete from omar;";
		Extractor extractor = new DeleteExtractor(statement);
		assertTrue(extractor.isValid());
		assertNull(extractor.getColumnsNames());
	}

	@Test

	public void test9() {
		String statement = "select Distinct * from t1 where c1 < 12 ;";
		Extractor extractor = new SelectExtractor(statement);
		assertTrue(extractor.isValid());
		assertNull(extractor.getColumnsNames());
		assertEquals("t1", extractor.getTableName());
		assertNull(extractor.getDatabaseName());
		assertEquals("c1", extractor.getConditionFirstOperand());
		assertEquals("<", extractor.getConditionOperator());
		assertEquals("12", extractor.getConditionSecondOperand());
	}

	@Test
	public void test10() {
		String statement = "select Distinct gh_cx , Abo_42 from t_t1 where c1 < '12' ;";
		Extractor extractor = new SelectExtractor(statement);
		assertFalse(extractor.isValid());
		assertEquals("gh_cx", extractor.getColumnsNames().get(0));
		assertEquals("t_t1", extractor.getTableName());
		assertNull(extractor.getDatabaseName());
		assertEquals("c1", extractor.getConditionFirstOperand());
		assertEquals("<", extractor.getConditionOperator());
		assertEquals("12", extractor.getConditionSecondOperand());
	}

	@Test
	public void test11() {
		String statement = "alter  table t add c2 date;";
		Extractor extractor = new AlterExtractor(statement);
		assertTrue(extractor.isValid());
		assertEquals("add", extractor.getCommand());
		assertEquals("c2", extractor.getColumnsNames().get(0));
		assertEquals("t", extractor.getTableName());
		assertNull(extractor.getDatabaseName());
		assertNull(extractor.getConditionOperator());
	}

	@Test
	public void test12() {
		String statement = "alTer taBle t dRop column c ; ";
		Extractor extractor = new AlterExtractor(statement);
		assertTrue(extractor.isValid());
		assertEquals("drop", extractor.getCommand().toLowerCase());
		assertEquals("c", extractor.getColumnsNames().get(0));
		assertEquals("t", extractor.getTableName());
		assertNull(extractor.getDatabaseName());
		assertNull(extractor.getConditionOperator());
	}

	@Test
	public void test13() {
		String statement = "use monster_university ;";
		Extractor extractor = new UseExtractor(statement);
		assertTrue(extractor.isValid());
		assertEquals("use", extractor.getCommand().toLowerCase());
		assertEquals("monster_university", extractor.getDatabaseName());
		assertNull(extractor.getColumnsNames());
		assertNull(extractor.getTableName());
		assertNull(extractor.getConditionOperator());
	}
}