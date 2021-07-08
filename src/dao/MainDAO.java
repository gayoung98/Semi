package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.MainDTO;
import dto.MemberDTO;

public class MainDAO {
	private volatile static MainDAO instance;
	private MainDAO() {

	}
	public static MainDAO getInstance() {
		if(instance == null){
			synchronized(MainDAO.class) {
				if(instance == null)
					instance = new MainDAO();
			}
		}
		return instance;
	}
	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}

	public int writechat(String writer, String id, String contents, String kh_class)throws Exception{
		String sql = "insert into chatBoard values(chatBoard_SEQ.nextval, ?, ?, ?, ?, sysdate)";
		try(Connection con =  this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, writer);
			pstat.setString(2, id);
			pstat.setString(3, contents);
			pstat.setString(4, kh_class);
			int result = pstat.executeUpdate();
			return result;
		}

	}
	public List<MainDTO> getAllList() throws Exception{
		String sql = "select * from chatBoard";
		List<MainDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			while(rs.next()) {
				list.add(new MainDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6)));
			}
			return list;
		}
	}
	public List<MainDTO> likeFacebook(int viewcount,int index, String khclass, String branch) throws Exception{
		String sql = "select "
				+ "c.seq, c.writer, c.id, c.contents, c.class, c.write_date "
				+ "from "
				+ "(select row_number() over(order by seq desc) rnum, seq, writer, id, contents, class, write_date from chatboard  where contents is not null) c join kh_member m on c.writer = m.email "
				+ "where rnum between ? and ? and c.class=? and m.branch=?";
		List<MainDTO> li = new ArrayList();
		try(Connection conn = this.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql)){
			psmt.setInt(1, 1+viewcount*(index-1));
			psmt.setInt(2, viewcount*index);
			psmt.setString(3, khclass);
			psmt.setString(4, branch);
			try(ResultSet rs = psmt.executeQuery()){
				while(rs.next()) {
					li.add(new MainDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6)));
				}
			}
			return li;
		}
	}

	public String getName(String email) throws Exception{
		String sql = "select name from kh_member where email = ?";
		String name = null;
		try(Connection con = this.getConnection();
				PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, email);
			try(ResultSet rs = psmt.executeQuery();){
				if(rs.next()) {
					name = rs.getString("name");
				}
			}
			return name;
		}

	}
	public List<MainDTO> classList(String khclass, String branch) throws Exception{
		String sql = "select chatboard.seq, chatboard.writer, chatboard.id, chatboard.contents, chatboard.class, chatboard.write_date from CHATBOARD join kh_member on chatboard.writer = kh_member.email where khclass = ? and branch = ?";
		List<MainDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, khclass);
			pstat.setString(2, branch);
			try(ResultSet rs = pstat.executeQuery()){
				while(rs.next()) {
					list.add(new MainDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6)));
				}
				return list;
			}
		}
	}

}



