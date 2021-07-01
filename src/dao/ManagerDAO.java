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

import org.apache.commons.lang3.StringUtils;


import config.ManagerConfig;

import dto.ManagerDTO;
import dto.MemberDTO;
import utils.Util;

public class ManagerDAO {
	private volatile static ManagerDAO instance;
	private ManagerDAO() {

	}
	public static ManagerDAO getInstance(){

		if(instance == null){
			synchronized (ManagerDAO.class) {
				if(instance == null)
					instance = new ManagerDAO();
			}
		}
		return instance;

	}
	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext(); 
		DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	public boolean login(String id, String pw) throws Exception{
		String sql = "select * from admin where id=? and pw=?";

		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){pstat.setNString(1, id);
				pstat.setNString(2, Util.getSHA512(pw));
				try(ResultSet rs= pstat.executeQuery();){
					return rs.next();

				}
		}
	}
	public List<MemberDTO> AllMemberList(String position)throws Exception{
		String sql = "select * from kh_member where position =?";
		List<MemberDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, position);
			try(ResultSet rs= pstat.executeQuery();){
				while(rs.next()) {
					
					String email =rs.getString("email");
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					String id = rs.getString("id");
					String khClass = rs.getString("khclass");
					String branch = rs.getString("branch");
					//position 값은 파라미터에
					Date signUpDate = rs.getDate("signUpDate");
					  list.add(new MemberDTO(email,name,phone,id,khClass,branch,position,signUpDate));
				}
				return list;
			}
		}
	}
	public List<MemberDTO> getPageList(String position , int startNum, int endNum ,String searchBranch,String category,String search) throws Exception{
		String isBranch="";
		String isSearch="";
		if(!searchBranch.contentEquals("all")) {
		isBranch+=" and branch = "+"'"+searchBranch+"'";
		}
		if(StringUtils.isBlank(category)) {
		isSearch +=" and id like '%"+search+"%' and khClass like '%"+search+"%' and name like '%"+search+"%'";
		}else {
			isSearch += " and "+ category+" like '%"+search+"%'";
		}
		String sql ="select * from (select row_number() over(order by branch) rnum ,email,name,phone,id,khClass,branch,position,sign_Up_Date from kh_member where position= '"+position+"'"+isBranch+isSearch+") where rnum between ? and ?";
		System.out.println(sql);
	List<MemberDTO> list = new ArrayList<>();
	try(Connection con = this.getConnection(); 
			PreparedStatement pstat = con.prepareStatement(sql);
			){
		pstat.setInt(1, startNum);
		pstat.setInt(2, endNum);
		try(
		ResultSet rs= pstat.executeQuery();){
			while(rs.next()) {
				
				String email =rs.getString("email");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String id = rs.getString("id");
				String khClass = rs.getString("khclass");
				String branch = rs.getString("branch");
				//position 값은 파라미터에
				Date signUpDate = rs.getDate("sign_Up_Date");
				  list.add(new MemberDTO(email,name,phone,id,khClass,branch,position,signUpDate));
			}
			return list;
		}
	}
	}
public int getRecordCount(String position,String category,String search ,String branch) throws Exception{
		
		String sql="select count(*) from kh_member where position = '"+position+"'";
		if(!branch.contentEquals("all")) {
			sql+=" and branch = "+"'"+branch+"'";
		}
		
		if(!StringUtils.isBlank(category)) {
			sql+=" and "+ category+" like '%"+search+"%'";
		}else {
			sql+=" and id like '%"+search+"%' and khClass like '%"+search+"%' and name like '%"+search+"%'";
		}
	
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs= pstat.executeQuery();){
			rs.next();
			return rs.getInt(1);

		}
	}
public List<String> getPageNavi(int currentPage,String category, String search,String position,String branch) throws Exception {
	int recordTotalCount = this.getRecordCount(position,category,search,branch);
	int recordCountPerPage = ManagerConfig.Record_count_Per_Page;	
	int naviCountPerPage = ManagerConfig.Navi_Count_Per_Page;
	int pageTotalCount = 0;
	if((recordTotalCount%recordCountPerPage)==0) {
		pageTotalCount = recordTotalCount/recordCountPerPage;
	}else {
		pageTotalCount = (recordTotalCount/recordCountPerPage) +1;
	}
	
	if(currentPage > pageTotalCount) {
		currentPage = pageTotalCount;
	}else if(currentPage < 1) {
		currentPage = 1;
	}
	int startNavi =  (currentPage-1) / naviCountPerPage * naviCountPerPage +1;
	int endNavi = startNavi + (naviCountPerPage-1);
	if(endNavi > pageTotalCount) {
		endNavi = pageTotalCount;
	}
	boolean needPrev = true;
	boolean needNext = true;

	if(startNavi ==1 ) {
		needPrev = false;
	}else if(	endNavi == pageTotalCount) {
		needNext = false;
	}
	List<String> pageNavi = new ArrayList<>();
	if(needPrev) {pageNavi.add("<");}
	for (int i = startNavi; i <= endNavi; i++) {
		pageNavi.add(String.valueOf(i));
	}
	if(needNext) {pageNavi.add(">");}
	return pageNavi;

}
}
