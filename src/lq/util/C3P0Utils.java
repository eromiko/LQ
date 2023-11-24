package lq.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class C3P0Utils {

	private static final Logger logger = LogManager.getLogger(C3P0Utils.class);
	private static final ComboPooledDataSource dataSource;
	// 当前线程关联的数据库连接对象
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	static {
		System.setProperty("com.mchange.v2.c3p0.cfg.xml", "classloader:/lq/config/c3p0-config.xml");
		dataSource = new ComboPooledDataSource();
		String encryptUser = dataSource.getUser();
		String encryptPassword = dataSource.getPassword();
		dataSource.setUser(encryptUser);
		dataSource.setPassword(encryptPassword);
	}

	public static Connection getConnection() {
		// 从线程中获取conneciton
		Connection conn = threadLocal.get();
		try {
			if (conn == null || conn.isClosed()) {
				try {
					conn = dataSource.getConnection();
					// 和当前线程绑定
					threadLocal.set(conn);
				} catch (SQLException e) {
					logger.error(LogManager.getLogger().getName() + e);
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 开启事务
	 */
	public static void startTransaction() {
		try {
			Connection conn = threadLocal.get();
			if (conn == null) {
				conn = getConnection();
				// 把 conn绑定到当前线程上
				threadLocal.set(conn);
			}
			// 开启事务
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 回滚事务
	 */
	public static void rollback() {
		try {
			// 从当前线程中获取Connection
			Connection conn = threadLocal.get();
			if (conn != null) {
				// 回滚事务
				conn.rollback();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 提交事务
	 **/
	public static void commit() {
		try {
			// 从当前线程中获取Connection
			Connection conn = threadLocal.get();
			if (conn != null) {
				// 提交事务
				conn.commit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭数据库连接(注意，并不是真的关闭，而是把连接还给数据库连接池)
	 */
	public static void close() {
		try {
			// 从当前线程中获取Connection
			Connection conn = threadLocal.get();
			if (conn != null) {
				conn.close();
				// 解除当前线程上绑定conn
				threadLocal.remove();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭连接
	 *
	 * @param conn
	 *            需要关闭的连接
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

	public static void close(PreparedStatement pst) {
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

	public static void closeAll(Object... objs) {
		for (Object obj : objs) {
			if (obj instanceof Connection) {
				close((Connection) obj);
			}
			if (obj instanceof Statement) {
				close((Statement) obj);
			}
			if (obj instanceof ResultSet) {
				close((ResultSet) obj);
			}
			if (obj instanceof PreparedStatement) {
				close((PreparedStatement) obj);
			}
		}
	}

	public static ComboPooledDataSource getdataSource() {
		// 从数据源中获取数据库连接
		return dataSource;
	}
}
