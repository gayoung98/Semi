package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.TypingDTO;

public class TypingDAO {

	private volatile static TypingDAO instance;
	private TypingDAO() {

	}
	public static TypingDAO getInstance(){ //싱글톤 패턴

		if(instance == null){
			synchronized (TypingDAO.class) {
				if(instance == null)
					instance = new TypingDAO();
			}
		}
		return instance;

	}


	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext(); 
		DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}

	public int insert(TypingDTO dto) throws Exception {

		String sql = "insert into Typing values(typing_seq.nextval,?,?,?,?, sysdate)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){

			pstat.setNString(1, dto.getWriter());
			pstat.setNString(2, dto.getId());
			pstat.setInt(3, dto.getRecord());
			pstat.setInt(4, dto.getAccuracy());
			int result = pstat.executeUpdate();
			return result;
		}			
	}

	public List<TypingDTO> getRecentList(String id) throws Exception{
		String sql = "select * from (select row_number() over(order by seq desc) rnum, seq, writer, id, record, accuracy, reg_date from typing where id=?) where rnum <= 12 order by seq";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){

			pstat.setString(1, id);
			ResultSet rs = pstat.executeQuery();

			List<TypingDTO> list = new ArrayList<>();
			while(rs.next()) {

				int seq = rs.getInt("seq");
				String writer = rs.getString("writer");
				int record = rs.getInt("record");
				int accuracy = rs.getInt("accuracy");
				Date reg_date = rs.getDate("reg_date");
				TypingDTO dto = new TypingDTO(seq, writer, id, record, accuracy, reg_date);
				list.add(dto);

			}
			return list;
		}			

	}



}
