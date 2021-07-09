package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.MemberDTO;

import utils.*;

public class MemberDAO {
	private static MemberDAO instance;

	public synchronized static MemberDAO getInstance() {
		if(instance == null) {
			instance = new MemberDAO();
		}
		return instance;
	}
	private MemberDAO() {}

	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}

	public int Join(MemberDTO dto)throws Exception{
		String sql = "insert into kh_member values(kh_seq.nextval,?,?,?,?,?,?,?,?,sysdate)";
		try(Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setString(1, dto.getEmail());
			preparedStatement.setString(2, Util.getSHA512(dto.getPw()));
			preparedStatement.setString(3, dto.getName());
			preparedStatement.setString(4, dto.getPhone());
			preparedStatement.setString(5, dto.getId());
			preparedStatement.setString(6, dto.getKhClass());
			preparedStatement.setString(7, dto.getBranch());
			preparedStatement.setString(8, dto.getPosition());
			int result = preparedStatement.executeUpdate();
			return result;
		}

	}

	public MemberDTO getInfo(String name, String phone) throws Exception{
		String sql = "select id,khclass,branch from kh_premember where name=? and phone=?";
		MemberDTO dto = null;
		try(Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, phone);
			try(ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()) {
					String id = rs.getString("id");
					String khClass = rs.getString("khclass");
					String branch = rs.getString("branch");
					dto = new MemberDTO(id,khClass,branch);
				}
			}
			return dto;
		}
	}

	public boolean sns(String email) throws Exception{
		String sql = "select * from kh_member where email=?";
		try(Connection Connection = this.getConnection();
				PreparedStatement preparedStatement = Connection.prepareStatement(sql);){
			preparedStatement.setString(1, email);
			try(ResultSet rs = preparedStatement.executeQuery();){
				boolean result = rs.next();
				return result;
			}
		}
	}

	public String login(String email, String pw) throws Exception{
		String sql = "select * from kh_member where email = ? and pw = ?";
		String tmpEmail = null;
		try(Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, Util.getSHA512(pw));
			try(ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()) {
					tmpEmail = rs.getString("email");
				}
			}
			return tmpEmail;
		}
	}

	public String snslogin(String email) throws Exception{
		String sql = "select * from kh_member where email = ?";
		String tmpEmail = null;
		try(Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setString(1, email);
			try(ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()) {
					tmpEmail = rs.getString("email");
				}
			}
			return tmpEmail;
		}
	}

	public String findId(String name, String phone) throws Exception{
		String sql = "select email from kh_member where name = ? and phone = ?";
		try(Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, phone);				
			try(ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()) {
					return rs.getString("email");
				}
			}
			return null;
		}
	}

	public int updatePw(String email, String pw) throws Exception{
		String sql = "update kh_member set pw=? where email = ?";
		try(Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setString(1, Util.getSHA512(pw));
			preparedStatement.setString(2, email);	
			int result = preparedStatement.executeUpdate();
			return result;
		}
	}

	public boolean checkInfo(String phone, String name, String email) throws Exception{
		String sql = "select * from kh_member where phone = ? and name = ? and email = ?";
		try(Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setString(1, phone);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, email);
			try(ResultSet rs = preparedStatement.executeQuery();){
				boolean result = rs.next();
				return result;
			}
		}
	}

	public boolean checkId(String id) throws Exception{
		String sql = "select * from kh_member where id = ? ";
		try(Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setString(1, id);
			try(ResultSet rs = preparedStatement.executeQuery();){
				boolean result = rs.next();
				return result;
			}
		}
	}

	public MemberDTO getMainInfo(String email) throws Exception{

		String sql = "select name, id, khClass, branch from kh_member where email=?";
		MemberDTO dto = null;
		try(Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setString(1, email);
			try(ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()) {

					String name = rs.getString("name");
					String id = rs.getString("id");
					String khClass = rs.getString("khclass");
					String branch = rs.getString("branch");
					dto = new MemberDTO(name, id, khClass, branch);
				}
			}
			return dto;
		}
	}

	public MemberDTO getAllInfo(String email) throws Exception{

		String sql = "select name, phone, id, khClass, branch, position, sign_up_date from kh_member where email=?";
		MemberDTO dto = null;
		try(Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setString(1, email);
			try(ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()) {

					String name = rs.getString("name");
					String phone = rs.getString("phone");
					String id = rs.getString("id");
					String khClass = rs.getString("khclass");
					String branch = rs.getString("branch");
					String position = rs.getString("position");
					Date signUpDate = rs.getDate("sign_up_date");
					dto = new MemberDTO(name, phone, id, khClass, branch, position, signUpDate);
				}
			}
			return dto;
		}
	}
	public String getEmailByName(String name) throws Exception {
		String sql = "select email from kh_member where name=?";
		try(Connection connection = this.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setString(1, name);
			String email = null;

			try(ResultSet rs = preparedStatement.executeQuery();){

				if(rs.next()) {
					email = rs.getString("email");
				}
			}
			return email;
		}

	}
}
