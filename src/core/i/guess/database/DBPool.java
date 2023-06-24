package core.i.guess.database;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

public final class DBPool
{
	private static DataSource ACCOUNT;

	private static DataSource openDataSource(String jdbc)
	{
		BasicDataSource source = new BasicDataSource();
		source.addConnectionProperty("autoReconnect", "true");
		source.addConnectionProperty("allowMultiQueries", "true");
		source.addConnectionProperty("zeroDateTimeBehavior", "convertToNull");
		source.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		source.setDriverClassName("com.mysql.jdbc.Driver");
		source.setUrl(jdbc);
		source.setMaxTotal(5);
		source.setMaxIdle(5);
		source.setTimeBetweenEvictionRunsMillis(180 * 1000);
		source.setSoftMinEvictableIdleTimeMillis(180 * 1000);

		return source;
	}

	public static DataSource getAccount()
	{
		if (ACCOUNT == null)
			loadDataSources();

		return ACCOUNT;
	}
	
	private static void loadDataSources()
	{
		try
		{
			File configFile = new File("database-config.dat");

			if (configFile.exists())
			{
				List<String> lines = Files.readAllLines(configFile.toPath(), Charset.defaultCharset());

				for (String line : lines)
				{
					deserializeConnection(line);
				}
			}
			else
			{
				System.out.println("database-config.dat not found at " + configFile.toPath().toString());
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			System.out.println("---Unable To Parse DBPOOL Configuration File---");
		}
	}

	private static void deserializeConnection(String line)
	{
		ACCOUNT = openDataSource(line);
	}
}
