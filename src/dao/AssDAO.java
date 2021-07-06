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

import config.AssConfig;
import dto.AssDTO;

public class AssDAO {

	private volatile static AssDAO instance;
	private AssDAO() {

	}
	public static AssDAO getInstance(){ //싱글톤 패턴

		if(instance == null){
			synchronized (AssDAO.class) {
				if(instance == null)
					instance = new AssDAO();
			}
		}
		return instance;

	}


	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext(); 
		DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}

	public int getSeq() throws Exception{

		int seq = 0;
		String sql01 = "insert into ass_seq_table values(ass_seq.nextval)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql01);
				){
			pstat.executeUpdate();

		}
		String sql02 = "select * from (select row_number() over(order by ass_seq desc)rnum, ass_seq from ass_seq_table) where rnum = 1";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql02);
				){
			ResultSet rs = pstat.executeQuery();

			if(rs.next()) {
				seq=rs.getInt("ass_seq");
			}

			return seq;
		}			
	}

	public int insert(AssDTO dto) throws Exception{
		String sql = "insert into ass values(?,?,?,?,?,?,?,sysdate,0)";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, dto.getSeq());
			pstat.setNString(2, dto.getWriter());
			pstat.setNString(3, dto.getId());
			pstat.setNString(4, dto.getTitle());
			pstat.setNString(5, dto.getContents());
			pstat.setNString(6, dto.getKhClass());
			pstat.setNString(7, dto.getBranch());
			int result = pstat.executeUpdate();
			return result;
		}		
	}

	public int delete(int seq) throws Exception{
		String sql = "delete from ass where seq = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, seq);

			int result = pstat.executeUpdate();
			return result;
		}					
	}

	public int update(AssDTO dto) throws Exception{
		String sql = "update ass set title=?, contents=?  where seq=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){


			pstat.setNString(1, dto.getTitle());
			pstat.setNString(2, dto.getContents());
			pstat.setInt(3, dto.getSeq());

			int result = pstat.executeUpdate();
			return result;
		}					
	}

	public AssDTO select(int seq) throws Exception{
		String sql = "select * from ass where seq=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);

				){

			pstat.setInt(1, seq);
			ResultSet rs = pstat.executeQuery();
			AssDTO dto = null;
			if(rs.next()) {


				String writer = rs.getString("writer");
				String id =rs.getString("id");
				String title = rs.getString("title");
				String contents= rs.getString("contents");
				String khClass = rs.getString("khClass");
				String branch = rs.getNString("branch");
				Date write_date = rs.getDate("write_date");
				int viewCount = rs.getInt("viewCount");
				dto = new AssDTO(seq, writer, id, title, contents, khClass, branch, write_date, viewCount);
			}
			return dto;
		}
	}


	//	public int search(String title) throws Exception {
	//		
	//	}
	//	public int search(String contents) throws Exception {
	//		
	//	}



	public int addViewCount(int seq, int viewCount) throws Exception{
		String sql = "update ass set viewCount=? where seq=?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, viewCount++);
			pstat.setInt(2, seq);
			int result = pstat.executeUpdate();

			return result;
		}
	}

	public List<AssDTO> getPageList(int startNum, int endNum, String category, String keyword) throws Exception{


		if(StringUtils.isBlank(category) || StringUtils.isBlank(keyword)) {
			String sql = "select * from (select row_number() over(order by seq desc) rnum, seq, writer, id, title, contents, khClass, branch, write_date, viewCount from ass) where rnum between ? and ?";
			try(
					Connection con = this.getConnection();
					PreparedStatement pstat = con.prepareStatement(sql);

					){

				pstat.setInt(1,  startNum);
				pstat.setInt(2,  endNum);
				ResultSet rs = pstat.executeQuery();
				List<AssDTO> list = new ArrayList<>();

				while(rs.next()) {

					int seq = rs.getInt("seq");
					String writer = rs.getString("writer");
					String id =rs.getString("id");
					String title = rs.getString("title");
					String contents= rs.getString("contents");
					String khClass = rs.getString("khClass");
					String branch = rs.getNString("branch");
					Date write_date = rs.getDate("write_date");
					int viewCount = rs.getInt("viewCount");
					AssDTO dto = new AssDTO(seq, writer, id, title, contents, khClass, branch, write_date, viewCount);
					list.add(dto);
				}
				return list;
			}
		}else {

			String sql = "select * from (select row_number() over(order by seq desc) rnum, seq, writer, id, lower(title) l_title, title, lower(contents) l_contents, contents, khClass, branch, write_date, viewCount from ass)where l_"+category+" like ? and rnum between ? and ?";
			try(
					Connection con = this.getConnection();
					PreparedStatement pstat = con.prepareStatement(sql);

					){

				pstat.setString(1, "%"+keyword+"%");
				pstat.setInt(2,  startNum);
				pstat.setInt(3,  endNum);

				ResultSet rs = pstat.executeQuery();
				List<AssDTO> list = new ArrayList<>();

				while(rs.next()) {

					int seq = rs.getInt("seq");
					String writer = rs.getString("writer");
					String id =rs.getString("id");
					String title = rs.getString("title");
					String contents= rs.getString("contents");
					String khClass = rs.getString("khClass");
					String branch = rs.getNString("branch");
					Date write_date = rs.getDate("write_date");
					int viewCount = rs.getInt("viewCount");

					AssDTO dto = new AssDTO(seq, writer, id, title, contents, khClass, branch, write_date, viewCount);
					list.add(dto);
				}
				return list;
			}
		}
	}

	public List<AssDTO> getPageList(int startNum, int endNum) throws Exception{


		String sql = "select * from (select row_number() over(order by seq desc) rnum, seq, writer, id, title, contents, khClass, branch, write_date, viewCount from ass) where rnum between ? and ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);

				){

			pstat.setInt(1,  startNum);
			pstat.setInt(2,  endNum);
			ResultSet rs = pstat.executeQuery();
			List<AssDTO> list = new ArrayList<>();

			while(rs.next()) {

				int seq = rs.getInt("seq");
				String writer = rs.getString("writer");
				String id =rs.getString("id");
				String title = rs.getString("title");
				String contents= rs.getString("contents");
				String khClass = rs.getString("khClass");
				String branch = rs.getNString("branch");
				Date write_date = rs.getDate("write_date");
				int viewCount = rs.getInt("viewCount");
				AssDTO dto = new AssDTO(seq, writer, id, title, contents, khClass, branch, write_date, viewCount);
				list.add(dto);
			}
			return list;
		}
	}

	private int getRecordCount(String category, String keyword) throws Exception {
		String sql = "select count(*) from ass";
		if(!StringUtils.isBlank(category) && !StringUtils.isBlank(keyword)) {
			sql+=" where "+category+" like '%"+keyword+"%'";
		}
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){

			rs.next();
			return rs.getInt(1);

		}
	}

	public List<String> getPageNavi(int currentPage, String category, String keyword) throws Exception{

		int recordTotalCount = this.getRecordCount(category, keyword);

		int pageTotalCount = 0;


		if(recordTotalCount%AssConfig.recordCountPage > 0) {
			pageTotalCount = recordTotalCount/AssConfig.recordCountPage+1;
			//전체 레코드에서 페이지 당 보여줄 게시글 수를 나눴을 때 나머지가 0보다 크면
			//총 페이지의 개수는 몫 +1
		}else if(recordTotalCount%AssConfig.recordCountPage == 0) {
			pageTotalCount = recordTotalCount/AssConfig.recordCountPage;
			//전체 레코드에서 페이지 당 보여줄 게시글 수를 나눴을 때 나머지가 0이면
			//총 페이지의 개수는 몫
		}

		//int currentPage = ?; //현재 내가 위치하는 페이지 번호

		if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}else if(currentPage < 1) {
			currentPage = 1;
		}

		int startNavi = 0;
		int endNavi = 0;
		for(int i=1; i<=currentPage; i+=AssConfig.naviCountPerPage){
			startNavi = i;
			endNavi = i+AssConfig.naviCountPerPage-1;
			if (endNavi>pageTotalCount) {
				endNavi = pageTotalCount;
			}
		}
		System.out.println(startNavi);
		System.out.println(endNavi);


		//				int startNavi = (currentPage-1) / BoardConfig.naviCountPerPage * naviCountPerPage +1;
		//				int endNavi = startNavi + BoardConfig.naviCountPerPage - 1;
		//				if(endNavi>pageTotalCount) {
		//					endNavi = pageTotalCount;
		//				}

		boolean needPrev = true;
		boolean needNext = true;

		if(startNavi==1) {
			needPrev=false;
		}
		if(endNavi==pageTotalCount) {
			needNext = false;
		}

		List<String> pageNavi = new ArrayList<>();
		if(needPrev) {
			pageNavi.add("<");
		}
		for(int i = startNavi; i<=endNavi; i++) {
			pageNavi.add(String.valueOf(i));
		}
		if(needNext) {
			pageNavi.add(">");
		}
		System.out.println(pageNavi);
		return pageNavi;	
	}






}
