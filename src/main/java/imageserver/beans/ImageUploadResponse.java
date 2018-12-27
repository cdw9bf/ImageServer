package imageserver.beans;

import java.io.Serializable;
import java.util.UUID;

public class ImageUploadResponse implements Serializable {

    private UUID uuid;
    private String checksum;
    private boolean success;

    public ImageUploadResponse(){}

    public ImageUploadResponse(UUID uuid, String checksum){
        this.checksum=checksum;
        this.uuid=uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String toResponseJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"uuid\":\"");
        sb.append(this.uuid.toString());
        sb.append("\", \"checksum\":\"");
        sb.append(this.checksum);
        sb.append("\"");
        sb.append(", \"success\":\"");
        sb.append(this.success);
        sb.append("\"");
        sb.append("}");
        return sb.toString();
    }
}
