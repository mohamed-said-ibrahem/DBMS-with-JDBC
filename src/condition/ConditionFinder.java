package condition;

import java.util.ArrayList;
import java.util.List;

import dbms.Column;
import dbms.DataBaseMS;

public class ConditionFinder {
	private String firstOperand, operator, secondOperand, tableName;
	private DataBaseMS dbms;
	private List<Integer> rowsNumber;
	/**
	 * mask is an String used to get the row at which the condition has
	 * matched. Example: if the matched rows are (0,1,2,3,4) in a column of 6
	 * elements then mask = "011111".
	 */
	private int mask;

	public ConditionFinder(String firstOperand, String operator,
			String secondOperand, DataBaseMS dbms, String tableName) {
		this.firstOperand = firstOperand;
		this.operator = operator;
		this.secondOperand = secondOperand;
		this.dbms = dbms;
		this.tableName = tableName;
		this.mask = 0;
		generateMask();
	}

	public int getMask() {
		return mask;
	}

	public List<Integer> getRowsNumber() {
		return rowsNumber;
	}

	private void generateMask() {
		boolean validComparison = false;
		dbms.useTable(tableName);
		
		List<Column> tableData = dbms.selectFromTable(this.tableName, null);
		for (int i = 0; i < tableData.size(); i++) {
			if (tableData.get(i).getName().equals(firstOperand)) {
				ArrayList<String> rows = tableData.get(i).getColElements();
				for (int j = 0; j < rows.size(); j++) {
					System.out.println(rows.get(j).toString());
					if (operator.contains("=")
							&& rows.get(j).equals(secondOperand.toString())) {
						 rowsNumber.add(j);
						validComparison = true;
					} else if (tableData.get(i).getType().equals("int")
							&& operator.contains("<")
							&& compare(operator, rows.get(j), secondOperand)) {
						 rowsNumber.add(j);
						validComparison = true;
					} else if (tableData.get(i).getType().equals("int")
							&& operator.contains(">")
							&& compare(operator, rows.get(j), secondOperand)) {
						 rowsNumber.add(j);
						validComparison = true;
					}
				}
			}
		}
		if (!validComparison) {
			throw new RuntimeException("wrong condition statement");
		}
		// System.out.println("condition rows : " + rowsNumber);
	}

	private boolean compare(String comparisonOP, String element, Object Value) {
		try {
			int operand = Integer.parseInt(Value.toString());
			if (comparisonOP.contains("<")) {
				// System.out.println(Integer.parseInt(element) < operand);
				return Integer.parseInt(element) < operand;
			} else if (comparisonOP.contains(">")) {
				return Integer.parseInt(element) > operand;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

//	private void generateRowNumbers() {
//		rowsNumber = new ArrayList<Integer>();
//		int index = 1;
//		while(mask != 0){
//			if(mask % (Math.pow(10, index)) == 1){
//				
//			}
//			index++;
//		}
//	}
}
