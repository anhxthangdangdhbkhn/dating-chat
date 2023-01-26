package vn.dating.chat.dto;

public class ResponsePrivateMessage {
    private boolean status;
    private String content;
    private String senderId;

    public ResponsePrivateMessage() {
    }

    public ResponsePrivateMessage(String senderId, String content, boolean status) {
        this.status = status;
        this.content = content;
        this.senderId = senderId;
    }

    public ResponsePrivateMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}