package extractor;

import java.util.List;

public interface Extractor {
	public enum commands {
		Select, SelectDistinct, AlterDrop, AlterAdd, Drop, Use, Delete, Create, Insert, Update
	}

	public boolean isValid();

	public String getCommand();

	public String getDatabaseName();

	public String getTableName();

	public List<String> getColumnsNames();

	public List<String> getColumnsTypes();

	public String getConditionFirstOperand();

	public String getConditionSecondOperand();

	public String getConditionOperator();

	public List<String> getValues();
}
