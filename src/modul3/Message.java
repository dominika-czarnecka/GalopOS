package modul3;
import modul1.*;

public class Message {
	public Message(PCB sender, String content) {
		this.sender = sender;
		this.content = content;
	}
	public PCB sender;
	public String content;
}


