package vn.dating.chat.dto.messages;

public class ConfirmPrivateMessage {
    private boolean status;
    private String content;
    private String senderId;
    private String time;

    public ConfirmPrivateMessage() {
    }

    public ConfirmPrivateMessage(String senderId, String content, String time, boolean status) {
        this.status = status;
        this.content = content;
        this.senderId = senderId;
        this.time = time;
    }

    public ConfirmPrivateMessage(MessagePrivateDto messagePrivateDto) {
        this.status = true;
        this.content = messagePrivateDto.getContent();
        this.senderId = messagePrivateDto.getSenderId();
        this.time = messagePrivateDto.getTime();
    }

    public ConfirmPrivateMessage(String content) {
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