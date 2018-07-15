package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;

import driver.Driver;

public class Statement implements java.sql.Statement {
	private Connection parentConnection;
	private ArrayList<String> batch;
	private boolean closed;
	private ResultSet resultSet;
	private int updateCount;
	private Driver sqlController;
	private final String selectClause;

	public Statement(Connection parentConnection) {
		this.parentConnection = parentConnection;
		this.batch = new ArrayList<String>();
		this.closed = false;
		this.updateCount = -1;
		this.sqlController = new Driver();
		this.selectClause = "(select)|(distinct)";
	}

	@Override
	public void addBatch(String sqlStatement) throws SQLException {
		batch.add(sqlStatement);
	}

	@Override
	public void clearBatch() throws SQLException {
		batch.clear();
	}

	@Override
	public void close() throws SQLException {
		closed = true;
	}

	@Override
	public boolean execute(String command) throws SQLException {
		boolean hasResultSet = sqlController.getQueryType()
				.equals(selectClause);
		sqlController.setStatement(command);

		if (hasResultSet) {
			resultSet = new jdbc.ResultSet(sqlController.getColumnsName(),
					sqlController.getDataType(),
					sqlController.getDataSelected(), this);
			updateCount = -1;
		} else {
			updateCount = sqlController.getAffectedRowsNumber();
			resultSet = null;
		}

		return hasResultSet;
	}

	// there is issues concerning failure or fault of a command
	// and number of affected rows of statements like drop , select
	@Override
	public int[] executeBatch() throws SQLException {
		boolean hasResultSet = sqlController.getQueryType()
				.equals(selectClause);
		int[] updateCounts = new int[batch.size()];

		for (int i = 0; i < batch.size(); i++) {
			try {
				sqlController.setStatement(batch.get(i));

				// if sql command has a ResultSet , its updateCounts = -2 ,
				// otherwise updateCounts > 0
				if (hasResultSet)
					updateCounts[i] = SUCCESS_NO_INFO;
				else
					updateCounts[i] = sqlController.getAffectedRowsNumber();

			} catch (Exception e) {
				updateCounts[i] = EXECUTE_FAILED;
			}
		}

		return updateCounts;
	}

	@Override
	public ResultSet executeQuery(String command) throws SQLException {
		sqlController.setStatement(command);

		resultSet = new jdbc.ResultSet(sqlController.getColumnsName(),
				sqlController.getDataType(), sqlController.getDataSelected(), this);

		// updateCount should be -1 if current result have resultSet
		updateCount = -1;

		return resultSet;
	}

	@Override
	public int executeUpdate(String command) throws SQLException {
		sqlController.setStatement(command);

		updateCount = sqlController.getAffectedRowsNumber();

		return updateCount;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return parentConnection;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return resultSet;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		return updateCount;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return closed;
	}

	// Unsupported methods throws java.lang.UnsupportedOperationException

	@Override
	public <T> T unwrap(Class<T> paramClass) throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public void setMaxFieldSize(int paramInt) throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public int getMaxRows() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public void setMaxRows(int paramInt) throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public void setEscapeProcessing(boolean paramBoolean) throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public void setQueryTimeout(int paramInt) throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public void cancel() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public void setCursorName(String paramString) throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public void setFetchDirection(int paramInt) throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public void setFetchSize(int paramInt) throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public int getResultSetType() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public boolean getMoreResults(int paramInt) throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public int executeUpdate(String paramString, int paramInt)
			throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public int executeUpdate(String paramString, int[] paramArrayOfInt)
			throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public int executeUpdate(String paramString, String[] paramArrayOfString)
			throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public boolean execute(String paramString, int paramInt)
			throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public boolean execute(String paramString, int[] paramArrayOfInt)
			throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public boolean execute(String paramString, String[] paramArrayOfString)
			throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public void setPoolable(boolean paramBoolean) throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public boolean isPoolable() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new UnsupportedOperationException("This method is not supported");
	}

}
