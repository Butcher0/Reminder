package com.zml.persistence;


import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCHelper {
	
	public static JdbcTemplate createMysqlTemplate() {	
		JdbcTemplate template = new JdbcTemplate(getDataSource());
		return template;
	}
	
	private static DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/reminder");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}
}
