package vn.dating.chat.dto.messages.socket;

public class ConfirmPrivateGroupMessage {
    private String content;
    private String senderId;
    private String time;
    private Long groupId;

    public ConfirmPrivateGroupMessage() {
    }

    public ConfirmPrivateGroupMessage(String content, String senderId, String time, Long groupId) {
        this.content = content;
        this.senderId = senderId;
        this.time = time;
        this.groupId = groupId;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
