package Utils;

import  jade.core.AID;
public class Message {
    public  String content;
    final  AID senderId;
    final AID receiverId;

    public Message(String content, AID senderId, AID receiverId) {
        this.content = content;
        this.senderId =  senderId;
        this.receiverId = receiverId;
    }

    public String getContent() {
        return this.content;
    }
}
