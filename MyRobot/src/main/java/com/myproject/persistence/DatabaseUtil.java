package com.myproject.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DatabaseUtil {

	private static final String DATABASE_NAME = "robotDataBase";

	/**
	 * Create if needed a table in the HSQLDB database and then save this execution trace by filling the fields input, output and timestamp
	 * @param input
	 * @param output
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void saveRobotExecution(String input, String output) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection connexion = DriverManager.getConnection("jdbc:hsqldb:file:"+DATABASE_NAME, "sa",  "");

		Statement statementCreate = connexion.createStatement() ;
		statementCreate.executeUpdate("CREATE TABLE IF NOT EXISTS ROBOT_EXECUTIONS (" + 
			"input VARCHAR(500) , output VARCHAR(500), execution_date TIMESTAMP)");
		
		Statement statementInsert = connexion.createStatement() ;
		statementInsert.executeUpdate("INSERT INTO ROBOT_EXECUTIONS (input, output, execution_date)"
				+ " VALUES ('"+
		input+"','"+output+"', CURRENT_TIMESTAMP)");

		
		Statement statementSaveOnDisk = connexion.createStatement();
		statementSaveOnDisk.executeQuery("SHUTDOWN");
		statementSaveOnDisk.close();

		connexion.close() ;
	}
}
