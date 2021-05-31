package com.jitai.amqDemo.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 生成model
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月8日 下午3:03:10
 */
public class GenerateMysql {

	private String packageOutPath = "com.loyofo.InnerAmq.model";// 指定实体生成所在包的路径
	private String authorName = "luoyanfu";// 作者名字
	private String tablename = "CANCEL";// 表名

	// 数据库连接
	private static final String URL = "jdbc:mysql://47.106.82.153:3306/student?useSSL=false";
	private static final String NAME = "root";
	private static final String PASS = "luojbin2Mysql";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	private String[] colnames; // 列名数组
	private String[] colTypes; // 列名类型数组
	private String[] colRemarks; // 注释数组
	private int[] colSizes; // 列名大小数组
	private boolean f_util = false; // 是否需要导入包java.util.*
	private boolean f_sql = false; // 是否需要导入包java.sql.*

	/*
	 * 构造函数
	 */
	public GenerateMysql() {
		// 创建连接
		Connection con;
		// 查要生成实体类的表
		String sql = "select * from " + tablename;
		PreparedStatement pStemt = null;
		try {
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			con = DriverManager.getConnection(URL, NAME, PASS);
			pStemt = con.prepareStatement(sql);
			ResultSetMetaData rsmd = pStemt.getMetaData();
			int size = rsmd.getColumnCount(); // 统计列
			colnames = new String[size];
			colTypes = new String[size];
			colRemarks = new String[size];
			colSizes = new int[size];
			int i = 0;
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getColumns(null, "%", tablename, "%");

			while (rs.next()) {
				colnames[i] = underlineToCamel(rs.getString("COLUMN_NAME").toLowerCase());
				colTypes[i] = rs.getString("TYPE_NAME");
				colRemarks[i] = rs.getString("REMARKS");
				if (colTypes[i].equalsIgnoreCase("datetime")) {
					f_util = true;
				}
				if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text") || colTypes[i].equalsIgnoreCase("timestamp")) {
					f_sql = true;
				}

				i++;
			}

			String content = parse(colnames, colTypes, colSizes);

			try {
				File directory = new File("");
				String outputPath = directory.getAbsolutePath() + "/src/main/java/" + this.packageOutPath.replace(".", "/") + "/" + initcap(underlineToCamel(tablename)) + ".java";
				FileWriter fw = new FileWriter(outputPath);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * 功能：生成实体类主体代码
	 * 
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return
	 */
	private String parse(String[] colnames, String[] colTypes, int[] colSizes) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + this.packageOutPath + ";\r\n\r\n");
		// 判断是否导入工具包
		if (f_util) {
			sb.append("import java.util.Date;\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n");
		}
		sb.append("import org.springframework.data.annotation.Id;\r\n\r\n");
		sb.append("import lombok.AllArgsConstructor;\r\n");
		sb.append("import lombok.Data;\r\n");
		sb.append("import lombok.NoArgsConstructor;\r\n");
		sb.append("\r\n");
		// 注释部分
		sb.append("/**\r\n");
		sb.append(" * " + tablename + " 对应实体类\r\n *\r\n");
		sb.append(" * @author " + this.authorName + "\r\n");
		sb.append(" * @version 2.0\r\n");
		sb.append(" * @time " + DateUtils.toStr() + "\r\n");
		sb.append(" */ \r\n");
		// 实体部分
		sb.append("@Data\r@NoArgsConstructor\r@AllArgsConstructor\rpublic class " + initcap(underlineToCamel2(tablename)) + " {\r\n\r\n");
		processAllAttrs(sb);// 属性
		sb.append("}");

		return sb.toString();
	}

	/**
	 * 功能：生成所有属性
	 * 
	 * @param sb
	 */
	private void processAllAttrs(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			if (!StringUtil.isEmpty(colRemarks[i])) {
				sb.append("    // " + colRemarks[i] + "").append("\r\n");
			}
			if (i == colnames.length - 1) {
				sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + ";\r\r");
			} else {
				if (i == 0) {
					sb.append("\t@Id\r\n");
					sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + ";\r\n\r");
				} else {
					sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + ";\r\n\r");
				}
			}
		}

	}

	/**
	 * 功能：将输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private String initcap(String str) {

		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}

		return new String(ch);
	}

	/**
	 * 功能：获得列的数据类型
	 * 
	 * @param sqlType
	 * @return
	 */
	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "Boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "Float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric") || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			return "BigDecimal";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char") || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("timestamp")) {
			return "Timestamp";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		}

		return null;
	}

	public static final char UNDERLINE = '_';

	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel2(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		StringBuilder sb = new StringBuilder(param);
		Matcher mc = Pattern.compile("_").matcher(param);
		int i = 0;
		while (mc.find()) {
			int position = mc.end() - (i++);
			sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 出口 TODO
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new GenerateMysql();
		System.out.println("GenerateMysql ok");
	}

}