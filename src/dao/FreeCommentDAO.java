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


import dto.FreeCommentDTO;

public class FreeCommentDAO {
	private static FreeCommentDAO instance;

	public synchronized static FreeCommentDAO getInstance() {
		if (instance == null) {
			instance = new FreeCommentDAO();
		}
		return instance;
	}

	private FreeCommentDAO() {}

	private Connection getConnection() throws Exception { 
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	
	public int writeReply(FreeCommentDTO dto) throws Exception{
		String sql="insert into freecomment values(freeComment_seq.nextval,?,?,?,sysdate,?)";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setNString(1, dto.getId());
			pstat.setNString(2, dto.getWriter());
			pstat.setNString(3, dto.getComments());
			pstat.setInt(4, dto.getParent());
			int result = pstat.executeUpdate();
			return result;
		}

	}

	public FreeCommentDTO CommentsView(int board_seq) throws Exception {
		String sql = "select * from freecomment where parent=? order by 1 desc";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, board_seq);
			try(ResultSet rs = pstat.executeQuery();){
				rs.next();
				int num = rs.getInt("seq");
				String id = rs.getNString("id");
				String writer = rs.getNString("writer");
				MemberDAO daoM = MemberDAO.getInstance();
				String name = daoM.getAllInfo(writer).getName();
				String comments = rs.getNString("comments");
				Date writer_date = rs.getDate("write_date");
				int parent_seq = rs.getInt("parent");

				FreeCommentDTO result = new FreeCommentDTO(num,id,name,writer,comments,writer_date,parent_seq);
				return result;

			}
		}	
	}

	public List<FreeCommentDTO> CommentsList(int boardseq) throws Exception {
		String sql = "select * from freecomment where parent=? order by 1 desc";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			pstat.setInt(1, boardseq);
			ResultSet rs = pstat.executeQuery();
			List<FreeCommentDTO> list = new ArrayList<>();
			while(rs.next()) {
				int num = rs.getInt("seq");
				String id = rs.getNString("id");
				String writer = rs.getNString("writer");
				MemberDAO daoM = MemberDAO.getInstance();
				String name = daoM.getAllInfo(writer).getName();
				String comments = rs.getNString("comments");
				Date writer_date = rs.getDate("write_date");
				int parent_seq = rs.getInt("parent");

				FreeCommentDTO dto = new FreeCommentDTO(num,id,name,writer,comments,writer_date,parent_seq);
				list.add(dto);
			}
			return list;
		}	
	}

	public int deleteReply(int comment_seq)throws Exception{ //댓글 삭제
		String sql= "delete from freecomment where seq =?";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setInt(1, comment_seq);
			int result = pstat.executeUpdate();
			return result;
		}
	}
	public int modifyReply(int comment_seq, String comments) throws Exception { //댓글 수정
		String sql = "update freecomment set comments =? ,write_date = sysdate where seq =?";

		try (
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){

			pstat.setString(1, comments);
			pstat.setInt(2, comment_seq);

			int result = pstat.executeUpdate();
			return result;
		}
	}
	
	public int replyCount(int seq) throws Exception{ //댓글 카운팅
		String sql ="select count(*) from freecomment where parent=?";
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql)){
			psmt.setInt(1, seq);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
			return rs.getInt(1);
			}
			return 0;
		}
	 }

}
