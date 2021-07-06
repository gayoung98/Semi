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
import dto.AssDTO;
import dto.FreeBoardDTO;
import dto.FreePoliceDTO;
import dto.InquireDTO;
import dto.ManagerDTO;
import dto.MemberDTO;
import dto.NoticeBoardDTO;
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

	public List<MemberDTO> getMemberPageList(String position , int startNum, int endNum ,String searchBranch,String category,String search) throws Exception{
		String isBranch="";
		String isSearch="";
		if(!searchBranch.contentEquals("all")) {
			isBranch+=" and branch = "+"'"+searchBranch+"'";
		}
		if(StringUtils.isBlank(category)) {
			isSearch +=" and (id like '%"+search+"%' or khClass like '%"+search+"%' or name like '%"+search+"%')";
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
					String kBranch="";
					if(branch.contentEquals("J")) {
						kBranch+="종로";
					}else if(branch.contentEquals("D")) {
						kBranch+="당산";
					}else if(branch.contentEquals("K")) {
						kBranch+="강남";
					}else{
						kBranch+="미정";
					}
					//position 값은 파라미터에
					Date signUpDate = rs.getDate("sign_Up_Date");
					list.add(new MemberDTO(email,name,phone,id,khClass,kBranch,position,signUpDate));
				}
				return list;
			}
		}
	}
	public int getMemberRecordCount(String position,String category,String search ,String branch) throws Exception{

		String sql="select count(*) from kh_member where position = '"+position+"'";
		if(!branch.contentEquals("all")) {
			sql+=" and branch = "+"'"+branch+"'";
		}

		if(!StringUtils.isBlank(category)) {
			sql+=" and "+ category+" like '%"+search+"%'";
		}else {
			sql+=" and (id like '%"+search+"%' or khClass like '%"+search+"%' or name like '%"+search+"%')";
		}

		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs= pstat.executeQuery();){
			rs.next();
			return rs.getInt(1);

		}
	}
	public List<String> getMemberPageNavi(int currentPage,String category, String search,String position,String branch) throws Exception {
		int recordTotalCount = this.getMemberRecordCount(position,category,search,branch);
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

	////////////////////////////////////////////////////////////////////////자유게시판
	public List<FreeBoardDTO> getBoardPageList( int startNum, int endNum ,String searchBranch,String category,String search) throws Exception{
		String isBranch="";
		String isSearch="";
		if(!searchBranch.contentEquals("all")) {
			isBranch+=" branch = "+"'"+searchBranch+"' and";
		}
		if(StringUtils.isBlank(category)) {
			isSearch +=" (writer like '%"+search+"%' or title like '%"+search+"%' or contents like '%"+search+"%')";
		}else {
			isSearch += " "+ category+" like '%"+search+"%'";
		}
		String sql ="select * from (select row_number() over(order by 1 desc) rnum ,seq,branch,writer,title,contents,id,write_date,viewCount,policecount from freeboard where "+isBranch+isSearch+") where rnum between ? and ?";
		System.out.println(sql);
		List<FreeBoardDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, startNum);
			pstat.setInt(2, endNum);
			try(
					ResultSet rs= pstat.executeQuery();){
				while(rs.next()) {

					int seq = rs.getInt("seq");
					String branch = rs.getString("branch");
					String kBranch="";
					if(branch.contentEquals("J")) {
						kBranch+="종로";
					}else if(branch.contentEquals("D")) {
						kBranch+="당산";
					}else if(branch.contentEquals("K")) {
						kBranch+="강남";
					}else{
						kBranch+="미정";
					}
					String writer=rs.getString("writer");
					String title = rs.getString("title");
					String contents = rs.getString("contents");
					String id= rs.getString("id");
					Date write_date = rs.getDate("write_date");
					int viewCount = rs.getInt("viewCount");
					int policeCount = rs.getInt("policecount");
					list.add(new FreeBoardDTO(seq,kBranch,writer,title,contents,id,write_date,viewCount,policeCount));
				}
				return list;
			}
		}
	}
	public int getBoardRecordCount(String category,String search ,String branch) throws Exception{

		String sql="select count(*) from freeboard where ";
		if(!branch.contentEquals("all")) {
			sql+=" branch = "+"'"+branch+"' and ";
		}

		if(!StringUtils.isBlank(category)) {
			sql+=category+" like '%"+search+"%'";
		}else {
			sql+="(writer like '%"+search+"%' or title like '%"+search+"%' or contents like '%"+search+"%')";
		}

		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs= pstat.executeQuery();){
			rs.next();
			return rs.getInt(1);

		}
	}
	public List<String> getBoardPageNavi(int currentPage,String category, String search,String branch) throws Exception {
		int recordTotalCount = this.getBoardRecordCount(category,search,branch);
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
///////////////////////////////////////////////////////////////////////// 공지사항
	public List<NoticeBoardDTO> getNoticePageList( int startNum, int endNum ,String searchBranch,String category,String search) throws Exception{
		String isBranch="";
		String isSearch="";
		if(!searchBranch.contentEquals("all")) {
			isBranch+=" branch = "+"'"+searchBranch+"' and";
		}
		if(StringUtils.isBlank(category)) {
			isSearch +=" (title like '%"+search+"%' or contents like '%"+search+"%')";
		}else {
			isSearch += " "+ category+" like '%"+search+"%'";
		}
		String sql ="select * from (select row_number() over(order by 1 desc) rnum ,seq,writer,title,contents,write_date,khClass,branch,viewCount from noticeboard where "+isBranch+isSearch+") where rnum between ? and ?";
		System.out.println(sql);
		List<NoticeBoardDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, startNum);
			pstat.setInt(2, endNum);
			try(
					ResultSet rs= pstat.executeQuery();){
				while(rs.next()) {

					int seq = rs.getInt("seq");
					String writer=rs.getString("writer");
					String title = rs.getString("title");
					String contents = rs.getString("contents");
					Date write_date = rs.getDate("write_date");
					String khClass =rs.getString("khClass");
					String branch = rs.getString("branch");
					String kBranch="";
					if(branch.contentEquals("J")) {
						kBranch+="종로";
					}else if(branch.contentEquals("D")) {
						kBranch+="당산";
					}else if(branch.contentEquals("K")) {
						kBranch+="강남";
					}else{
						kBranch+="미정";
					}
					int viewCount = rs.getInt("viewCount");
					
					list.add(new NoticeBoardDTO(seq,writer,title,contents,write_date,khClass,kBranch,viewCount));
				}
				return list;
			}
		}
	}
	public int getNoticeBoardRecordCount(String category,String search ,String branch) throws Exception{

		String sql="select count(*) from noticeboard where ";
		if(!branch.contentEquals("all")) {
			sql+=" branch = "+"'"+branch+"' and ";
		}

		if(!StringUtils.isBlank(category)) {
			sql+=category+" like '%"+search+"%'";
		}else {
			sql+="(title like '%"+search+"%' or contents like '%"+search+"%')";
		}

		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs= pstat.executeQuery();){
			rs.next();
			return rs.getInt(1);

		}
	}
	public List<String> getNoticeBoardPageNavi(int currentPage,String category, String search,String branch) throws Exception {
		int recordTotalCount = this.getNoticeBoardRecordCount(category,search,branch);
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
	//////////////////////////////////////////////////////////////////////////////과제게시판
	public List<AssDTO> getAssPageList( int startNum, int endNum ,String searchBranch,String category,String search) throws Exception{
		String isBranch="";
		String isSearch="";
		if(!searchBranch.contentEquals("all")) {
			isBranch+=" branch = "+"'"+searchBranch+"' and";
		}
		if(StringUtils.isBlank(category)) {
			isSearch +=" (writer like '%"+search+"%' or title like '%"+search+"%' or contents like '%"+search+"%')";
		}else {
			isSearch += " "+ category+" like '%"+search+"%'";
		}
		String sql ="select * from (select row_number() over(order by 1 desc) rnum ,seq,writer,id,title,contents,khClass,branch,write_date,viewCount from Ass where "+isBranch+isSearch+") where rnum between ? and ?";
		System.out.println(sql);
		List<AssDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, startNum);
			pstat.setInt(2, endNum);
			try(
					ResultSet rs= pstat.executeQuery();){
				while(rs.next()) {

					int seq = rs.getInt("seq");
					String writer=rs.getString("writer");
					String id= rs.getString("id");
					String title = rs.getString("title");
					String contents = rs.getString("contents");
					String khClass = rs.getString("khClass");
					String branch = rs.getString("branch");
					String kBranch="";
					if(branch.contentEquals("J")) {
						kBranch+="종로";
					}else if(branch.contentEquals("D")) {
						kBranch+="당산";
					}else if(branch.contentEquals("K")) {
						kBranch+="강남";
					}else{
						kBranch+="미정";
					}
					Date write_date = rs.getDate("write_date");
					int viewCount = rs.getInt("viewCount");
					
					list.add(new AssDTO(seq,writer,title,contents,id,khClass,kBranch,write_date,viewCount));
				}
				return list;
			}
		}
	}
	public int getAssRecordCount(String category,String search ,String branch) throws Exception{

		String sql="select count(*) from Ass where ";
		if(!branch.contentEquals("all")) {
			sql+=" branch = "+"'"+branch+"' and ";
		}

		if(!StringUtils.isBlank(category)) {
			sql+=category+" like '%"+search+"%'";
		}else {
			sql+="(writer like '%"+search+"%' or title like '%"+search+"%' or contents like '%"+search+"%')";
		}

		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs= pstat.executeQuery();){
			rs.next();
			return rs.getInt(1);

		}
	}
	public List<String> getAssPageNavi(int currentPage,String category, String search,String branch) throws Exception {
		int recordTotalCount = this.getAssRecordCount(category,search,branch);
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
///////////////////////////////////////////////////////////////////////게시물 신고
	
	public List<FreePoliceDTO> getFreePolicePageList( int startNum, int endNum ,String category,String search) throws Exception{
		
		String isSearch="";
		
		if(StringUtils.isBlank(category)) {
			isSearch +=" ID like '%"+search+"%' or CONTENTS like '%"+search+"%'";
		}else {
			isSearch += " "+ category+" like '%"+search+"%'";
		}
		String sql ="select * from (select row_number() over(order by 1 desc) rnum ,seq,id,contents,parent,reg_date from freepolice where "+isSearch+") where rnum between ? and ?";
		System.out.println(sql);
		List<FreePoliceDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, startNum);
			pstat.setInt(2, endNum);
			try(
					ResultSet rs= pstat.executeQuery();){
				while(rs.next()) {

					int seq = rs.getInt("seq");
				
					String id= rs.getString("id");
					
					String contents = rs.getString("contents");
					int parent =rs.getInt("parent");
					Date reg_date = rs.getDate("reg_date");
					
					
					list.add(new FreePoliceDTO(seq,id,contents,parent,reg_date));
				}
				return list;
			}
		}
	}
	public int getFreePoliceRecordCount(String category,String search ) throws Exception{

		String sql="select count(*) from FreePolice where ";
		

		if(!StringUtils.isBlank(category)) {
			sql+=category+" like '%"+search+"%'";
		}else {
			sql+="ID like '%"+search+"%' or contents like '%"+search+"%'";
		}

		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs= pstat.executeQuery();){
			rs.next();
			return rs.getInt(1);

		}
	}
	public List<String> getFreePolicePageNavi(int currentPage,String category, String search) throws Exception {
		int recordTotalCount = this.getFreePoliceRecordCount(category,search);
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
	//////////////////////////////////////////////////////문의하기 목록
public List<InquireDTO> getInquirePageList( int startNum, int endNum ,String category,String search) throws Exception{
		
		String isSearch="";
		
		if(StringUtils.isBlank(category)) {
			isSearch +=" ID like '%"+search+"%' or CONTENTS like '%"+search+"%' or major_category like '%"+search+"%' or sub_category like '%"+search+"%'";
		}else {
			isSearch += " "+ category+" like '%"+search+"%'";
		}
		String sql ="select * from (select row_number() over(order by 2 desc) rnum ,seq,id,major_category,sub_category,contents,recomment,reg_date from inquire where "+isSearch+") where rnum between ? and ?";
		System.out.println(sql);
		List<InquireDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, startNum);
			pstat.setInt(2, endNum);
			try(
					ResultSet rs= pstat.executeQuery();){
				while(rs.next()) {

					int seq = rs.getInt("seq");
					String id= rs.getString("id");
					String major_category = rs.getString("major_category");
					String sub_category = rs.getString("sub_category");
					String contents = rs.getString("contents");
					String recomment = rs.getString("recomment");
					Date reg_date = rs.getDate("reg_date");
					
					
					list.add(new InquireDTO(seq,id,major_category,sub_category,contents,recomment,reg_date));
				}
				return list;
			}
		}
	}
	public int getInquireRecordCount(String category,String search ) throws Exception{

		String sql="select count(*) from inquire where ";
		

		if(!StringUtils.isBlank(category)) {
			sql+=category+" like '%"+search+"%'";
		}else {
			sql+="ID like '%"+search+"%' or CONTENTS like '%"+search+"%' or major_category like '%"+search+"%' or sub_category like '%"+search+"%'";
		}

		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs= pstat.executeQuery();){
			rs.next();
			return rs.getInt(1);

		}
	}
	public List<String> getInquirePageNavi(int currentPage,String category, String search) throws Exception {
		int recordTotalCount = this.getInquireRecordCount(category,search);
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
	///////////////////////////////////////////////////////////////인덱스 게시판
	public List<FreeBoardDTO> indexBoard() throws Exception {
		String sql ="select * from (select row_number() over(order by 1 desc) rnum ,seq,branch,title,writer,write_date from freeboard) where rnum between 1 and 5";
		List<FreeBoardDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs= pstat.executeQuery();){
			while(rs.next()) {
				int seq = rs.getInt("seq");
				String branch = rs.getString("branch");
				String kBranch="";
				if(branch.contentEquals("J")) {
					kBranch+="종로";
				}else if(branch.contentEquals("D")) {
					kBranch+="당산";
				}else if(branch.contentEquals("K")) {
					kBranch+="강남";
				}else{
					kBranch+="미정";
				}
				
				String writer = rs.getString("writer");
				String title = rs.getString("title");
				Date write_date = rs.getDate("write_date");
				list.add(new FreeBoardDTO(seq, kBranch,writer,title,write_date));
			}
			return list;
		}
	}
	///////////////////////////////////////////////인데스 신고
	public List<FreePoliceDTO> indexPolice() throws Exception {
		String sql ="select * from (select row_number() over(order by 1) rnum ,seq,id,contents,parent,reg_date from freepolice) where rnum between 1 and 5";
		List<FreePoliceDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs= pstat.executeQuery();){
			while(rs.next()) {
				int seq = rs.getInt("seq");
				
				String id = rs.getString("id");
				String contents = rs.getString("contents");
				int parent = rs.getInt("parent");
				Date reg_date = rs.getDate("reg_date");
				list.add(new FreePoliceDTO(seq,id,contents,parent,reg_date));
			}
			return list;
		}
	}
	public List<InquireDTO> getInquire(int seq) throws Exception {
		String sql ="select * from inquire where seq=?";
		List<InquireDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, seq);
			try(ResultSet rs= pstat.executeQuery();){
				while(rs.next()) {
					String id= rs.getString("id");
					String major_category = rs.getString("major_category");
					String sub_category = rs.getString("sub_category");
					String contents = rs.getString("contents");
					String recomment = rs.getString("recomment");
					Date reg_date = rs.getDate("reg_date");
					list.add(new InquireDTO(seq,id,major_category,sub_category,contents,recomment,reg_date));
				}
				return list;
			}
		}
	}
	public boolean hasNotRecomment(int seq) throws Exception{
		List<InquireDTO> list=this.getInquire(seq);
		boolean result= StringUtils.isBlank(list.get(0).getRecomment());
		return result;
	}	
	public int insertRecomment(int seq, String recomment)throws Exception{
		String sql= "update inquire set recomment =? where seq=?";
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, recomment);
			pstat.setInt(2, seq);
			int result = pstat.executeUpdate();
			
			return result;
		}
		
	}
	public int memberDelete(String delId) throws Exception{
		String sql= "delete from kh_member where id=?";
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, delId);
		
			int result = pstat.executeUpdate();
			
			return result;
		}
	}
	public int inquireDelete(int delSeq)throws Exception {
		String sql= "update inquire set recomment =? where seq=?";
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, "");
			pstat.setInt(2, delSeq);
			int result = pstat.executeUpdate();
			
			return result;
		}
	}
	public int inquireModify(int delSeq, String recomment)throws Exception {
		String sql= "update inquire set recomment =? where seq=?";
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, recomment);
			pstat.setInt(2, delSeq);
			int result = pstat.executeUpdate();
			
			return result;
		}
	}
	public int deleteChat () throws Exception {
		String sql="delete from chatboard";
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);){
			int result = pstat.executeUpdate();
			return result;
		}
	}
	public int deleteSeat () throws Exception {
		String sql="delete from seat";
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);){
			int result = pstat.executeUpdate();
			return result;
		}
	}
	public int deleteFreeBoard (int seq)throws Exception{
		String sql="delete from freeboard where seq =?";
		try(Connection con = this.getConnection(); 
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			return result;
		}
	}
}
					
					
