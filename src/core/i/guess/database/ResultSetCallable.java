package core.i.guess.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetCallable 
{
	public void processResultSet(ResultSet resultSet) throws SQLException;
}
