package vn.dating.chat.dto.messages;

public class ConfirmPrivateMessage {
    private boolean status;
    private String content;
    private String senderId;
    private String time;
    private String recipientId;
    private String conversationId;

    public ConfirmPrivateMessage(String msg) {
    }


    public ConfirmPrivateMessage(MessagePrivateDto messagePrivateDto) {
        this.status = true;
        this.content = messagePrivateDto.getContent();
        this.senderId = messagePrivateDto.getSenderId();
        this.time = messagePrivateDto.getTime();
        this.recipientId = messagePrivateDto.getRecipientId();
    }

    @Override
    public String toString() {
        return "ConfirmPrivateMessage{" +
                "status=" + status +
                ", content='" + content + '\'' +
                ", senderId='" + senderId + '\'' +
                ", time='" + time + '\'' +
                ", recipientId='" + recipientId + '\'' +
                '}';
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
}