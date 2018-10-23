package com.rcm.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Jdbc实体类
 * 
 *
 * <p>
 *
 * @author hhp 2018年10月22日
 * @see
 * @since 1.0
 */
public class JdbcUtil {

	private static Connection connection;

	/**
	 * 获取数据库连接
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {

		connection = init();
		return connection;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return Connection
	 */
	private static Connection init() {
		Connection connection = null;
		try {

			Properties properties = new Properties();
			InputStream stream = JdbcUtil.class.getResourceAsStream("/jdbc.properties");

			properties.load(stream);

			String userName = properties.get("username").toString();
			String password = properties.get("password").toString();
			String driverName = properties.get("driverClassName").toString();
			String url = properties.get("url").toString();

			Class.forName(driverName);

			connection = DriverManager.getConnection(url, userName, password);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @param driverName
	 *            连接驱动名称
	 * @param url
	 *            url地址
	 * @param userName
	 *            用户名称
	 * @param password
	 *            登录密码
	 * @return Connection
	 */
	public static Connection getConnection(String driverName, String url, String userName, String password) {
		Connection connection = null;
		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}
