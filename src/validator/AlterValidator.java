package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlterValidator extends Validator {

	public AlterValidator() {
		final String alter = "(?i)alter";
		final String table = "(?i)table";
		final String column = "(?i)column";
		final String add = "(?i)add";
		final String drop = "(?i)drop";

		regex = zeroOrMoreSpace + alter + oneOrMoreSpace + table
				+ oneOrMoreSpace + tableName + oneOrMoreSpace + "((" + "(" + add
				+ ")" + oneOrMoreSpace + "(" + columnName + ")" + oneOrMoreSpace
				+ dataType + ")" + or + "(" + "(" + drop + ")" + oneOrMoreSpace
				+ column + oneOrMoreSpace + "(" + columnName + ")" + "))"
				+ zeroOrMoreSpace + semiColon + zeroOrMoreSpace;

		regexPattern = Pattern.compile(regex);
	}

	public static void main(String[] args) {
		AlterValidator alter = new AlterValidator();
		Matcher matcher = alter.getPattern()
				.matcher("alter table  t1 add column int ;");

		System.out.println(alter.regex);
		System.out.println(matcher.matches());

		for (int i = 1; i <= matcher.groupCount(); i++) {
			System.out.println(i + "  " + matcher.group(i));
		}
	}
}
