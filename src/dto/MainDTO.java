package dto;

public class MainDTO {
	private int seq;
	private String writer;
	private String chat;

	public MainDTO() {}
	public MainDTO(int seq, String writer, String chat) {
		this.seq = seq;
		this.writer = writer;
		this.chat = chat;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getChat() {
		return chat;
	}
	public void setChat(String chat) {
		this.chat = chat;
	}
}
