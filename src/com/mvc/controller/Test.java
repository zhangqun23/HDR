package com.mvc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Test {

	public static long importData(String sql) {
		String url = "jdbc:mysql://192.168.1.30:3306/db_smit_capital?user=root&password=smit0296139&zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8&useCursorFetch=true";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		long allStart = System.currentTimeMillis();
		long count = 0;

		Connection con = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(url);
			ps = (PreparedStatement) con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ps.setFetchSize(Integer.MIN_VALUE);
			ps.setFetchDirection(ResultSet.FETCH_REVERSE);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 此处处理业务逻辑
				count++;
				if (count % 30 == 0) {
					System.out.println(" 写入到第 " + (count / 30) + " 个文件中！");
					long end = System.currentTimeMillis();
				}
			}
			System.out.println("取回数据量为  " + count + " 行！");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;

	}

	public static void main(String[] args) throws InterruptedException {

		String sql = "select * from case_info cs where open_time between '2014-07-01' and '2016-07-08' ";
		// String sql = "select * from case_info cs";
		importData(sql);

	}

}