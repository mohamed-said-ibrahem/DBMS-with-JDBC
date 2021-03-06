package validator;

import java.util.regex.Pattern;

public abstract class Validator {
	// helper regex
	protected String oneOrMoreSpace = "\\s+";
	protected String zeroOrMoreSpace = "\\s*";
	protected String comma = ",";
	protected String or = "|";
	protected String semiColon = ";";
	protected String zeroOrOne = "?";
	private String oneWordName = "[a-z]+[0-9]*_?[a-z]*[0-9]*";
	private String manyWordsName = "([a-z]+[0-9]*_?[a-z]*[0-9]*\\s*){1,3}";
	private String number = "([0-9]{1,15})";

	// SQL keywords
	protected String from = "(?i)from";
	protected String into = "(?i)into";
	protected String set = "(?i)set";
	protected String where = "(?i)where";
	protected String values = "(?i)values";
	protected String all = "\\*";
	protected String database = "(?i)database";
	protected String table = "(?i)table";
	private String Int = "(?i)int";
	private String Float = "(?i)float";
	private String varchar = "(?i)varchar";
	private String date = "(?i)date";

	// statement regex parts
	protected String tableName = "(" + oneWordName + ")";
	protected String databaseName = "(" + oneWordName + ")";
	protected String columnName = oneWordName;
	protected String valueName = manyWordsName;
	protected String dataType = "(" + Int + or + varchar + or + Float + or + date + ")";
	protected String value = "'" + zeroOrMoreSpace + "(" + valueName + or + number + ")" + zeroOrMoreSpace + "'";
	protected String columnsCommas = "(((" + columnName + zeroOrMoreSpace + comma + zeroOrMoreSpace + ")*)" + zeroOrMoreSpace
			+ "(" + columnName + "))";
	private String valuesCommas = "((" + value + zeroOrMoreSpace + comma + zeroOrMoreSpace + ")*)" + zeroOrMoreSpace
			+ "(" + value + ")";

	protected String columnsCommasBrackets = "(\\(" + columnsCommas + "\\))";
	protected String valuesCommasBrackets = "(\\(" + valuesCommas + "\\))";
	protected String columnType = columnName + oneOrMoreSpace + dataType;
	protected String columnsCommasTypes = "(\\(((" + columnType + zeroOrMoreSpace + comma + zeroOrMoreSpace + ")*)" + zeroOrMoreSpace
			+ "(" + columnType + ")\\))";

	protected String columnEqualsValue = columnName + zeroOrMoreSpace + "=" + zeroOrMoreSpace + value;
	protected String columnsValuesCommas = "(((" + columnEqualsValue + zeroOrMoreSpace + comma + zeroOrMoreSpace + ")*)"
			+ zeroOrMoreSpace + "(" + columnEqualsValue + "))";

	// condition
	protected String firstOperand = "(" + oneWordName + ")";
	// second operand can be a columnName too
	protected String secondOperand = "('" + valueName + "'" + or + number + or + oneWordName + ")";
	protected String operator = "(<|>|=)";
	protected String condition = firstOperand + zeroOrMoreSpace + operator + zeroOrMoreSpace + secondOperand;

	// full regex string for sub-classes to implement
	protected String regex;
	
	// regex pattern for sub-classes to implement
	protected Pattern regexPattern;
	
	public Pattern getPattern() {
		return regexPattern;
	}
}
