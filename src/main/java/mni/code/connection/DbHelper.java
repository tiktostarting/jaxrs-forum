package mni.code.connection;

import oracle.jdbc.OracleDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Singleton;

import mni.code.model.Thread;

@ApplicationScoped
public class DbHelper {
	
	private List<Thread> threadList = new ArrayList<Thread>();

	private static Connection koneksi;

	@PostConstruct
	private void init(){
    	try {
			if(koneksi == null){
				String url = "jdbc:oracle:thin:@localhost:1521:orcl";
				DriverManager.registerDriver(new OracleDriver());
				koneksi = DriverManager.getConnection(url, "tikto", "tikto1234");
			}
    	} catch(Exception e) {
    		//System.out.println(e.getStackTrace()[0]);
    		e.printStackTrace();
    	}
    }
    
    public String tesKoneksi(){
    	String result = "";	
        if(koneksi == null) {
            try {
                String url = "jdbc:oracle:thin:@localhost:1521:orcl";
                DriverManager.registerDriver(new OracleDriver());
                koneksi = DriverManager.getConnection(url, "tikto", "tikto1234");
                result = "Koneksi baru Berhasil";
            } catch (SQLException t) {
                result = "koneksi baru gagal !";
            }
        }else {
        	result = "Koneksi sudah ada dan tidak ada perubahan";
        }
        return result;
    }
    
    public String insertData(String sql) throws SQLException {
    	String result = "Gagal";
    	PreparedStatement preparedStmt = koneksi.prepareStatement(sql);
    	Boolean status = preparedStmt.execute();
    	if (!status) {
    		result = "Berhasil ";
    	}
    	return result;
    }
    
    public ResultSet getAllData(String sql) throws SQLException {
		Statement stmt = koneksi.createStatement();
    	ResultSet rs;
    	rs = stmt.executeQuery(sql);
    	return rs;
    }
    
    public ResultSet getSingleData(String sql) throws SQLException {
    	Statement stmt = koneksi.createStatement();
		ResultSet rs;
		rs = stmt.executeQuery(sql);
    	return rs;
    }

	public String updateDatabyId(String sql) throws SQLException {
		Statement stmt = koneksi.createStatement();
		Boolean result = stmt.execute(sql);
		if(!result){
			return "Berhasil";
		}else{
			return "Gagal";
		}
	}

	public String deleteById(String sql) throws SQLException{
		Statement stmt = koneksi.createStatement();
		Boolean result = stmt.execute(sql);
		if(!result){
			return "Berhasil";
		}else{
			return "Gagal";
		}
	}
}
