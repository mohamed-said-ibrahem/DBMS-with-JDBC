package condition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import dbms.DataBaseMS;
import validator.Validator;

public class ConditionParser extends Validator {
	private ConditionFinder cF;
	private DataBaseMS dbms;
	private String tableName;
	private String firstOperand, operator, secondOperand;
	/**
	 * a String created from the input statement which can be executed in
	 * engineManger.
	 */
	private StringBuilder excutableStatement;
	/**
	 * scripting engine.
	 */
	private ScriptEngineManager mgr = null;
	/**
	 * engine to evaluate the infix operation.
	 */
	private ScriptEngine engine = null;
	private Pattern conditionPattern;
	private Matcher matcher;

	public ConditionParser(String statement, DataBaseMS dbms,
			String tableName) {
		this.dbms = dbms;
		this.dbms = dbms;
		this.tableName = tableName;
		this.excutableStatement = new StringBuilder();
		mgr = new ScriptEngineManager();
		engine = mgr.getEngineByName("JavaScript");
		matcher = conditionPattern.matcher(statement);
		parseStatement();
		try {
			excuteStatement();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int conditionNo = 0;

	private void excuteStatement() throws ScriptException {
		int answer = Integer.parseInt(
				engine.eval(excutableStatement.toString()).toString());
		System.out.print("Convert to binary is:");
		printBinaryform(answer);
	}

	private static void printBinaryform(int number) {
		int remainder;

		if (number <= 1) {
			System.out.print(number);
			return; // KICK OUT OF THE RECURSION
		}

		remainder = number % 2;
		printBinaryform(number >> 1);
		System.out.print(remainder);
	}

	private void parseStatement() {
		for (int i = 0; i < matcher.groupCount(); i++) {
			if (matcher.group(i) != null && matcher.group(i) == "("
					|| matcher.group(i) == ")") {
				excutableStatement.append(matcher.group(i));
			} else if (matcher.group(i) != null
					&& matcher.group(i).equalsIgnoreCase("and")) {
				excutableStatement.append("&");
			} else if (matcher.group(i) != null
					&& matcher.group(i).equalsIgnoreCase("or")) {
				excutableStatement.append("|");
			} else if (matcher.group(i) != null
					&& matcher.group(i).equalsIgnoreCase("not")) {
				excutableStatement.append("^");
			} else if (matcher.group(i) != null
					&& Pattern.matches(condition, matcher.group(i))) {
				getCondtionParameter(matcher.group(i));
				// call condition finder to get the mask.
				System.out.println("1>> " + firstOperand + " " + operator + " "
						+ secondOperand);
				if (conditionNo == 0) {
					excutableStatement.append("9");
				} else if (conditionNo == 1) {
					excutableStatement.append("31");
				} else if (conditionNo == 2) {
					excutableStatement.append("44");
				} else if (conditionNo == 3) {
					excutableStatement.append("8");

				}
			}
		}
	}

	private void getCondtionParameter(String condition) {
		Matcher m = conditionPattern.matcher(condition);
		for (int i = 0; i < m.groupCount(); i++) {
			if (m.group(i) != null) {
				if (Pattern.matches(firstOperand, m.group(i))) {
					this.firstOperand = m.group(i);
				} else if (Pattern.matches(operator, m.group(i))) {
					this.secondOperand = m.group(i);
				} else if (Pattern.matches(secondOperand, m.group(i))) {
					this.secondOperand = m.group(i);
				}
			}
		}
	}

	public static void main(String[] args) {
		ConditionParser cP = new ConditionParser(
				"((s>=20 AND N='A')OR(c='Am' And s >2))", null, null);
	}
}
