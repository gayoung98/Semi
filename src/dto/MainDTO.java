package dto;

public class MainDTO {
	private String writer;
	private String chat;

	public MainDTO() {}
	public MainDTO(String writer, String chat) {
		this.writer = writer;
		this.chat = chat;
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
