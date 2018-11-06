package com.softeem.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * 封装与数据库相关的操作
 * @author Mr.Zhou
 *
 */
public class DBUtil {
	//与数据库连接相关的参数
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	//与连接池相关的参数
	//DBCP连接池的数据源
	private static BasicDataSource bds;
	//初始连接数
	private static int initialSize;
	//最大连接数
	private static int maxTotal;
	//最大闲置数
	private static int maxIdle;
	//最小闲置数
	private static int minIdle;
	//最大等待时间
	private static long maxWaitMillis;
	
	static{
		init();
	}
	
	//初始化操作
	public static void init(){
		try {
			//初始化DBCP数据源
			bds = new BasicDataSource();
			//获取属性对象
			Properties pro = System.getProperties();
			//加载指定的属性文件
			pro.load(new FileInputStream("D:/class/src/jdbc.properties"));
			//读取配置文件
			driver = pro.getProperty("driver");
			url = pro.getProperty("url");
			user = pro.getProperty("user");
			password = pro.getProperty("password");
			//DBCP核心配置
			initialSize = Integer.parseInt(pro.getProperty("initialSize"));
			maxTotal = Integer.parseInt(pro.getProperty("maxTotal"));
			maxIdle = Integer.parseInt(pro.getProperty("maxIdle"));
			minIdle = Integer.parseInt(pro.getProperty("minIdle"));
			maxWaitMillis = Long.parseLong(pro.getProperty("maxWaitMillis"));
		
			//设置驱动
			bds.setDriverClassName(driver);
			//设置URL
			bds.setUrl(url);
			//设置数据库的用户名
			bds.setUsername(user);
			//设置数据库的密码
			bds.setPassword(password);
			
			//连接池中的相关设置
			//设置初始连接数
			bds.setInitialSize(initialSize);
			//设置最大连接数
			bds.setMaxTotal(maxTotal);
			//设置最大闲置数
			bds.setMaxIdle(maxIdle);
			//设置最小闲置数
			bds.setMinIdle(minIdle);
			//设置等待时间(毫秒)
			bds.setMaxWaitMillis(maxWaitMillis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//获取连接
	public static Connection getConnection() throws SQLException{
		//如果数据源为空或没有加载
		if(bds == null || bds.isClosed()){
			//初始化加载
			init();
		}
		//从数据源中获取连接
		return bds.getConnection();
	}
	
	//封装资源回收的方法
	public static void close(Connection conn,
			PreparedStatement ps,ResultSet rs){
		try {
			if(conn != null)conn.close();
			if(ps != null)ps.close();
			if(rs != null)rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 封装的数据更新操作
	 * @param conn 数据库连接
	 * @param sql 更新操作的sql语句
	 * @param obj 参数
	 * @return
	 * @throws Exception
	 */
	public static boolean executeUpdate(Connection conn, String sql,Object...obj) throws Exception{
		PreparedStatement ps = conn.prepareStatement(sql);
		for(int i=1;i<=obj.length;i++){
			ps.setObject(i, obj[i-1]);
		}
		int i = ps.executeUpdate();
//		ps.close();
		return i>0?true:false;
	}
	
	/**
	 * 封装查询所有
	 * @param call 结果集的处理
	 * @param sql  查询sql语句
	 * @param obj  参数
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> queryList(CallBack<T> call,String sql,Object...obj) throws Exception{
		PreparedStatement ps = getConnection().prepareStatement(sql);
		for(int i=1;i<=obj.length;i++){
			ps.setObject(i, obj[i-1]);
		}
		ResultSet rs = ps.executeQuery();
//		ps.close();
		return call.getDatas(rs);
	}
	
	
	/**
	 * 封装查询单个元素的操作
	 * @param call  对结果集的处理
	 * @param sql	查询sql语句
	 * @param obj	参数
	 * @return
	 * @throws SQLException
	 */
	public static <T> T queryOne(CallBack<T> call,String sql,Object...obj) throws SQLException{
		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		for(int i=1;i<=obj.length;i++){
			ps.setObject(i, obj[i-1]);
		}
		ResultSet rs = ps.executeQuery();
//		ps.close();
		return call.getData(rs);
	}
	
	
	//钩子函数   回调函数  JDK1.8
//	public interface CallBack<T>{
//		default List<T> findAll(ResultSet rs){
//			return null;
//		}
//	}
	
	//JDK1.8以下
	public static abstract class CallBack<T>{
		public List<T> getDatas(ResultSet rs){
			return null;
		}
		
		public T getData(ResultSet rs){
			return null;
		}
	}
	
	

}