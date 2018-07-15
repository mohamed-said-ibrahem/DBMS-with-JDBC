package fileManager;

import java.util.List;

import dbms.Column;
import dbms.MiniTable;

public interface IFileWriter {
	public void write(String tName, String dbName, List<Column> cols,
			MiniTable requiredTable);

}
