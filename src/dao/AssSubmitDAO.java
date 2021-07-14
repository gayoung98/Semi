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

import dto.AssSubmitDTO;

public class AssSubmitDAO {

	private volatile static AssSubmitDAO instance;

	private AssSubmitDAO() {
	}

	public static AssSubmitDAO getInstance(){ //싱글톤 패턴

		if(instance == null){
			synchronized (AssFilesDAO.class) {
				if(instance == null)
					instance = new AssSubmitDAO();
			}
		}
		return instance;

	}


	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext(); 
		DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}

	public int insert(AssSubmitDTO dto) throws Exception{

		String sql = "insert into assSubmit values(assSubmit_seq.nextval, ?, ?, ?, ?, sysdate, ?)";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){

			pstat.setString(1, dto.getWriter());
			pstat.setString(2, dto.getId());
			pstat.setString(3, dto.getOriName());
			pstat.setString(4, dto.getSysName());
			pstat.setInt(5, dto.getParent());
			int result = pstat.executeUpdate();
			return result;

		}
	}

	public int delete(int seq) throws Exception{

		String sql = "delete from assSubmit where seq=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){

			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();

			return result;

		}
	}


	public AssSubmitDTO select(int seq) throws Exception{
		String sql="select * from assSubmit where seq=?";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setInt(1, seq);
			
			try(ResultSet rs = pstat.executeQuery()){
				AssSubmitDTO dto = null;
				if(rs.next()){
					
					String writer = rs.getString("writer");
					String id = rs.getString("id");
					MemberDAO daoM = MemberDAO.getInstance();
					String name = daoM.getAllInfo(writer).getName();
					String oriName = rs.getString("oriName");
					String sysName = rs.getString("sysName");
					Date reg_date = rs.getDate("reg_date");
					int parent = rs.getInt("parent");
					dto = new AssSubmitDTO(seq, writer, id, name, oriName, sysName, reg_date, parent);
					return dto;
				}else {return dto;}
			}
		}
	}

	public List<AssSubmitDTO> selectAll(int parent) throws Exception {

		String sql = "select * from (select row_number() over(order by seq desc) rnum, seq, writer, id, oriName, sysName, reg_date from assSubmit where parent = ?)";
		try(
				Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){

			pstat.setInt(1, parent);
			ResultSet rs = pstat.executeQuery();
			List<AssSubmitDTO> list = new ArrayList<>();

			while(rs.next()) {

				int seq = rs.getInt("seq");
				String writer = rs.getString("writer");
				String id = rs.getString("id");
				MemberDAO daoM = MemberDAO.getInstance();
				String name = daoM.getAllInfo(writer).getName();
				String oriName = rs.getString("oriName");
				String sysName = rs.getString("sysName");
				Date reg_date = rs.getDate("reg_date");
				AssSubmitDTO dto =new AssSubmitDTO(seq, writer, id, name, oriName, sysName, reg_date, parent);
				list.add(dto);
			}
			return list;
		}

	}
}
