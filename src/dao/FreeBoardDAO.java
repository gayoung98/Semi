package dao;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.FreeBoardDTO;
import config.BoardConfig;

public class FreeBoardDAO {
	private static FreeBoardDAO instance;

	public synchronized static FreeBoardDAO getInstance() {
		if (instance == null) {
			instance = new FreeBoardDAO();
		}
		return instance;
	}

	private FreeBoardDAO() {}

	private Connection getConnection() throws Exception { 
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}

	public int write(int seq,String branch, String writer, String title, String contents, String id) throws Exception{ //게시글 작성하기
		String sql="insert into freeBoard values(?,?,?,?,?,?,sysdate,0,0)";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setInt(1, seq);
			pstat.setNString(2, branch);
			pstat.setNString(3, writer);
			pstat.setNString(4, title);
			pstat.setNString(5, contents);
			pstat.setNString(6, id);
			int result = pstat.executeUpdate();

			return result;
		}

	}


	public List<FreeBoardDTO> boardList() throws Exception { //게시글 리스트 출력
		String sql = "select * from FreeBoard order by 1 desc";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			ResultSet rs = pstat.executeQuery();
			List<FreeBoardDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq = rs.getInt("seq");
				String branch = rs.getNString("branch");
				String title = rs.getNString("title");
				String contents = rs.getNString("contents");
				String writer =rs.getNString("writer");
				Date write_date =rs.getDate("write_date");
				int viewCount = rs.getInt("viewCount");

				FreeBoardDTO dto = new FreeBoardDTO(seq,branch,writer,title,contents,null,write_date,viewCount,0);
				list.add(dto);
			}
			return list;
		}	
	}
	public List<FreeBoardDTO> getPageList(int startNum, int endNum) throws Exception{ //게시글 범위 출력 
		String sql = "select * from "
				+ "(select "
				+ "    row_number() over(order by seq desc) rnum, "
				+ "    seq, "
				+ "    branch, "
				+ "    writer,"
				+ "    title, "
				+ "    write_date,"
				+ "    viewCount"
				+ "    from freeBoard)"
				+ "where rnum between ? and ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			 
			pstat.setInt(1,startNum); //사작 번호
			pstat.setInt(2,endNum); //끝 번호
			try(ResultSet rs = pstat.executeQuery();){
				List<FreeBoardDTO> list = new ArrayList<>();
				while(rs.next()) {
					int seq = rs.getInt("seq");
					String findBranch = rs.getNString("branch");
					String writer =rs.getNString("writer");
					String title = rs.getNString("title");
					Date write_date =rs.getDate("write_date");
					int viewCount = rs.getInt("viewCount");

					FreeBoardDTO dto = new FreeBoardDTO(seq,findBranch,writer,title,null,null,write_date,viewCount,0);		           
					list.add(dto);
				}
				return list;
			}
		}
	}
	public List<FreeBoardDTO> getEachBranch(int startNum, int endNum,String branch) throws Exception{ //게시글 범위 출력 
		String sql = "select * from "
				+ "(select "
				+ "    row_number() over(order by seq desc) rnum, "
				+ "    seq, "
				+ "    branch, "
				+ "    writer,"
				+ "    title, "
				+ "    write_date,"
				+ "    viewCount"
				+ "    from freeBoard where branch=? )"
				+ "where rnum between ? and ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setNString(1,branch); 
			pstat.setInt(2,startNum); //사작 번호
			pstat.setInt(3,endNum); //끝 번호
			try(ResultSet rs = pstat.executeQuery();){
				List<FreeBoardDTO> list = new ArrayList<>();
				while(rs.next()) {
					int seq = rs.getInt("seq");
					String findBranch = rs.getNString("branch");
					String writer =rs.getNString("writer");
					String title = rs.getNString("title");
					Date write_date =rs.getDate("write_date");
					int viewCount = rs.getInt("viewCount");

					FreeBoardDTO dto = new FreeBoardDTO(seq,findBranch,writer,title,null,null,write_date,viewCount,0);		           
					list.add(dto);
				}
				return list;
			}
		}
	}
public List<FreeBoardDTO> searchAll(int startNum, int endNum,String category, String keyWord) throws Exception{ // 검색할 때, 게시글 범위 출력 +카테고리, 키워드 포함
		
		String sql = "select * from "
				+ "(select "
				+ "    row_number() over(order by seq desc) rnum, "
				+ "    seq, "
				+ "    branch, "
				+ "    writer,"
				+ "    title, "
				+ "    write_date,"
				+ "    viewCount"
				+ "    from freeBoard where "+category+" like ? )"
				+ "where rnum between ? and ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setNString(1,"%"+keyWord+"%"); //sql 구문에 like 뒤에 %keyword%
			pstat.setInt(2,startNum); //사작 번호
			pstat.setInt(3,endNum); //끝 번호
			try(ResultSet rs = pstat.executeQuery();){
				List<FreeBoardDTO> list = new ArrayList<>();
				while(rs.next()) {
					int seq = rs.getInt("seq");
					String searchedBranch = rs.getNString("branch");
					String writer =rs.getNString("writer");
					String title = rs.getNString("title");
					Date write_date =rs.getDate("write_date");
					int viewCount = rs.getInt("viewCount");

					FreeBoardDTO dto = new FreeBoardDTO(seq,searchedBranch,writer,title,null,null,write_date,viewCount,0);		           
					list.add(dto);
				}
				return list;
			}
		}
	}
	public List<FreeBoardDTO> searchBranch(String branch, String category, String keyWord,int startNum, int endNum) throws Exception{ // 검색할 때, 게시글 범위 출력 +카테고리, 키워드 포함

		String sql = "select * from "
				+ "(select "
				+ "    row_number() over(order by seq desc) rnum, "
				+ "    seq, "
				+ "    branch,"
				+ "    writer,"
				+ "    title, "
				+ "    write_date,"
				+ "    viewCount"
				+ "    from freeBoard where branch= ? and "+category+" like ? )"
				+ "where rnum between ? and ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setNString(1, branch); 
			pstat.setNString(2,"%"+keyWord+"%"); //sql 구문에 like 뒤에 %keyword%
			pstat.setInt(3,startNum); //사작 번호
			pstat.setInt(4,endNum); //끝 번호
			try(ResultSet rs = pstat.executeQuery();){
				List<FreeBoardDTO> list = new ArrayList<>();
				while(rs.next()) {
					int seq = rs.getInt("seq");
					String searchedBranch = rs.getNString("branch");
					String writer =rs.getNString("writer");
					String title = rs.getNString("title");
					Date write_date =rs.getDate("write_date");
					int viewCount = rs.getInt("viewCount");

					FreeBoardDTO dto = new FreeBoardDTO(seq,searchedBranch,writer,title,null,null,write_date,viewCount,0);		           
					list.add(dto);
				}
				return list;
			}
		}
	}
	public int viewCountPlus(int seq) throws Exception{ //게시글 조회수 증가!!
		String sql = "update freeBoard set viewCount = viewCount+1 where seq = ?";
		try(
				Connection con= this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){			
			pstat.setInt(1, seq);

			int result = pstat.executeUpdate();

			return result;
		}
	}


	public FreeBoardDTO detailView(int boardseq) throws Exception{ //리스트-> 게시글 상세보기
		String sql = "select * from freeBoard where seq=?";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1,boardseq);
			try(ResultSet rs = pstat.executeQuery();){
				rs.next();
				int num = rs.getInt("seq");
				String branch = rs.getString("branch");
				String title = rs.getString("title");
				String contents = rs.getString("contents");
				String writer = rs.getString("writer");
				Date writer_date = rs.getDate("write_date");
				int viewCount = rs.getInt("viewCount");

				FreeBoardDTO result = new FreeBoardDTO(num,branch,writer,title,contents,null,writer_date,viewCount,0);
				return result;
			}
		}
	}
	public FreeBoardDTO boardView(int boardseq) throws Exception{ //게시글 조회수 출력
		String sql = "select viewCount from freeBoard where seq = ?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, boardseq);
			try(
					ResultSet rs = pstat.executeQuery();
					){
				if(rs.next()) {
					return new FreeBoardDTO(boardseq, rs.getInt("viewCount"));
				}
				return null;
			}
		}
	}
	public int delete(int seq)throws Exception{  //게시글 삭제 
		String sql= "delete from freeBoard where seq =?";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setInt(1, seq); //게시글 번호
			int result = pstat.executeUpdate();
			return result;
		}
	}
	public int modify(int seq, String title, String contents) throws Exception { //게시글 수정
		String sql = "update freeBoard set title =? , contents = ?, write_date = sysdate where seq =?";

		try (
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setString(1, title);
			pstat.setString(2, contents);
			pstat.setInt(3, seq);

			int result = pstat.executeUpdate();
			return result;
		}
	}

	private int getPageCount() throws Exception{ //페이지 수 카운팅 출력
		String sql= "select count(*) from freeBoard";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs =pstat.executeQuery();){
			rs.next();
			return rs.getInt(1);
		}

	}

	private int getPageCount(String category, String keyWord,String branch) throws Exception{ //페이지 수 카운팅 출력(카테고리/검색어로 카운팅!)
		String sql= "select count(*) from FreeBoard where branch=? and "+category+" like ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setNString(1, branch);
			pstat.setNString(2, "%"+keyWord+"%");
			try(ResultSet rs =pstat.executeQuery();){
				rs.next();
				return rs.getInt(1);
			}
		}
	}
	
	private int getPageCount(String category, String keyWord) throws Exception{ //페이지 수 카운팅 출력(카테고리/검색어로 카운팅!)
		String sql= "select count(*) from FreeBoard where "+category+" like ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			
			pstat.setNString(1, "%"+keyWord+"%");
			try(ResultSet rs =pstat.executeQuery();){
				rs.next();
				return rs.getInt(1);
			}
		}
	}
	public List<String>getPageNavi(int currentPage,String category, String keyWord) throws Exception{ //페이지 네비게이션 리스트
		int recodeTotalCount =0;

		if(keyWord==null|| keyWord.contentEquals("")) {
			recodeTotalCount =this.getPageCount(); //전체 레코드의 개수
		}else {
			recodeTotalCount =this.getPageCount(category,keyWord); //전체 레코드의 개수

		}

		int recodeCountPerPage =BoardConfig.Recode_Count_Per_Page; //한 페이지 당 보여줄 게시글의 개수
		int naviCountPerPage= BoardConfig.Navi_Count_Per_Page; //내 위치 페이지를 기준으로 시작부터 끝까지 페이지가 총 몇개인지.
		int pageTotalCount =0; //전체 페이지의 갯수

		if(recodeTotalCount % recodeCountPerPage>0) {// 전체페이지에 페이지당 보여줄 게시글의 갯수로 나눈 나머지가 0이상일 경우에는
			pageTotalCount =recodeTotalCount/recodeCountPerPage +1;  //page의 갯수가 한 페이지를 추가
		}else{
			pageTotalCount =recodeTotalCount/recodeCountPerPage; //0일경우에는 페이지 추가하지 X
		}
		//		int currentPage = 3; //현재 내가 위치하는 페이지 번호.
		if(currentPage>pageTotalCount) {
			currentPage = pageTotalCount; // 현재페이즈를 총 페이지 갯수를 초과하지 않는다.
		}else if(currentPage <1) {
			currentPage=1; //current 
		}

		int startNavi= (currentPage-1)/naviCountPerPage * naviCountPerPage +1; //currentPage/naviCountPerPage(14/10=1) * naviCountPerPage (1*10)= 10페이지 +1
		int endNavi = startNavi + naviCountPerPage-1; //첫페이지의 +9

		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		boolean needPrev =true; //이전페이지
		boolean needNext =true; //다음페이지

		if(startNavi==1) { //첫페이지가 1과 같을 땐, 이전페이지 추가 안함
			needPrev=false;
		}

		if(endNavi==pageTotalCount) { //마지막페이지가 총 페이지 갯수와 같을 시, 다음페이지 추가 안함
			needNext=false;
		}

		List<String> pageNavi = new ArrayList<>();

		if(needPrev) {  //네이 페이지 이전 페이지 단위로 가기 추가
			pageNavi.add("<"); 
		}

		for(int i = startNavi;i<=endNavi;i++) {
			pageNavi.add(String.valueOf(i)); //10단위로 1~10/21~30 이렇게 나열
		}

		if(needNext) { // 네비 페이지에 다음 페이지 단위로 가기 추가
			pageNavi.add(">");
		}

		return pageNavi;
	}

	public List<String>getPageNavi(int currentPage,String category, String keyWord,String branch) throws Exception{ //페이지 네비게이션 리스트
		int recodeTotalCount =0;

		if(keyWord==null|| keyWord.contentEquals("")) {
			recodeTotalCount =this.getPageCount(); //전체 레코드의 개수
		}else {
			recodeTotalCount =this.getPageCount(category,keyWord,branch); //전체 레코드의 개수

		}

		int recodeCountPerPage =BoardConfig.Recode_Count_Per_Page; //한 페이지 당 보여줄 게시글의 개수
		int naviCountPerPage= BoardConfig.Navi_Count_Per_Page; //내 위치 페이지를 기준으로 시작부터 끝까지 페이지가 총 몇개인지.
		int pageTotalCount =0; //전체 페이지의 갯수

		if(recodeTotalCount % recodeCountPerPage>0) {// 전체페이지에 페이지당 보여줄 게시글의 갯수로 나눈 나머지가 0이상일 경우에는
			pageTotalCount =recodeTotalCount/recodeCountPerPage +1;  //page의 갯수가 한 페이지를 추가
		}else{
			pageTotalCount =recodeTotalCount/recodeCountPerPage; //0일경우에는 페이지 추가하지 X
		}
		//		int currentPage = 3; //현재 내가 위치하는 페이지 번호.
		if(currentPage>pageTotalCount) {
			currentPage = pageTotalCount; // 현재페이즈를 총 페이지 갯수를 초과하지 않는다.
		}else if(currentPage <1) {
			currentPage=1; //current 
		}

		int startNavi= (currentPage-1)/naviCountPerPage * naviCountPerPage +1; //currentPage/naviCountPerPage(14/10=1) * naviCountPerPage (1*10)= 10페이지 +1
		int endNavi = startNavi + naviCountPerPage-1; //첫페이지의 +9

		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		boolean needPrev =true; //이전페이지
		boolean needNext =true; //다음페이지

		if(startNavi==1) { //첫페이지가 1과 같을 땐, 이전페이지 추가 안함
			needPrev=false;
		}

		if(endNavi==pageTotalCount) { //마지막페이지가 총 페이지 갯수와 같을 시, 다음페이지 추가 안함
			needNext=false;
		}

		List<String> pageNavi = new ArrayList<>();

		if(needPrev) {  //네이 페이지 이전 페이지 단위로 가기 추가
			pageNavi.add("<"); 
		}

		for(int i = startNavi;i<=endNavi;i++) {
			pageNavi.add(String.valueOf(i)); //10단위로 1~10/21~30 이렇게 나열
		}

		if(needNext) { // 네비 페이지에 다음 페이지 단위로 가기 추가
			pageNavi.add(">");
		}

		return pageNavi;
	}

	public int getSeq() throws SQLException, Exception {
		String sql= "select freeBoard_seq.nextval from dual";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			ResultSet rs =pstat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}	
}

