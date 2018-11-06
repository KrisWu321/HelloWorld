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
 * ��װ�����ݿ���صĲ���
 * @author Mr.Zhou
 *
 */
public class DBUtil {
	//�����ݿ�������صĲ���
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	//�����ӳ���صĲ���
	//DBCP���ӳص�����Դ
	private static BasicDataSource bds;
	//��ʼ������
	private static int initialSize;
	//���������
	private static int maxTotal;
	//���������
	private static int maxIdle;
	//��С������
	private static int minIdle;
	//���ȴ�ʱ��
	private static long maxWaitMillis;
	
	static{
		init();
	}
	
	//��ʼ������
	public static void init(){
		try {
			//��ʼ��DBCP����Դ
			bds = new BasicDataSource();
			//��ȡ���Զ���
			Properties pro = System.getProperties();
			//����ָ���������ļ�
			pro.load(new FileInputStream("D:/class/src/jdbc.properties"));
			//��ȡ�����ļ�
			driver = pro.getProperty("driver");
			url = pro.getProperty("url");
			user = pro.getProperty("user");
			password = pro.getProperty("password");
			//DBCP��������
			initialSize = Integer.parseInt(pro.getProperty("initialSize"));
			maxTotal = Integer.parseInt(pro.getProperty("maxTotal"));
			maxIdle = Integer.parseInt(pro.getProperty("maxIdle"));
			minIdle = Integer.parseInt(pro.getProperty("minIdle"));
			maxWaitMillis = Long.parseLong(pro.getProperty("maxWaitMillis"));
		
			//��������
			bds.setDriverClassName(driver);
			//����URL
			bds.setUrl(url);
			//�������ݿ���û���
			bds.setUsername(user);
			//�������ݿ������
			bds.setPassword(password);
			
			//���ӳ��е��������
			//���ó�ʼ������
			bds.setInitialSize(initialSize);
			//�������������
			bds.setMaxTotal(maxTotal);
			//�������������
			bds.setMaxIdle(maxIdle);
			//������С������
			bds.setMinIdle(minIdle);
			//���õȴ�ʱ��(����)
			bds.setMaxWaitMillis(maxWaitMillis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//��ȡ����
	public static Connection getConnection() throws SQLException{
		//�������ԴΪ�ջ�û�м���
		if(bds == null || bds.isClosed()){
			//��ʼ������
			init();
		}
		//������Դ�л�ȡ����
		return bds.getConnection();
	}
	
	//��װ��Դ���յķ���
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
	 * ��װ�����ݸ��²���
	 * @param conn ���ݿ�����
	 * @param sql ���²�����sql���
	 * @param obj ����
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
	 * ��װ��ѯ����
	 * @param call ������Ĵ���
	 * @param sql  ��ѯsql���
	 * @param obj  ����
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
	 * ��װ��ѯ����Ԫ�صĲ���
	 * @param call  �Խ�����Ĵ���
	 * @param sql	��ѯsql���
	 * @param obj	����
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
	
	
	//���Ӻ���   �ص�����  JDK1.8
//	public interface CallBack<T>{
//		default List<T> findAll(ResultSet rs){
//			return null;
//		}
//	}
	
	//JDK1.8����
	public static abstract class CallBack<T>{
		public List<T> getDatas(ResultSet rs){
			return null;
		}
		
		public T getData(ResultSet rs){
			return null;
		}
	}
	
	

}