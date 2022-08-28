package cc.kocho.database.image;

import cc.kocho.data.http.UploadData;
import cc.kocho.data.image.Resolution;
import cc.kocho.data.image.Tag;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Entity(value = "images", useDiscriminator = false)
public class Image {
    @Id
    private ObjectId id;
    @Indexed
    private final String indexed = ObjectId.get().toString();
    private UploadData uploadData = new UploadData();
    private Resolution resolution = new Resolution();
    private long size = 0;
    private String md5 = "";
    private final long uploadTime = System.currentTimeMillis();

    public Image() {

    }

    public Image(UploadData uploadData, Resolution resolution, long size, String md5){
        this.uploadData = uploadData;
        this.resolution = resolution;
        this.size = size;
        this.md5 = md5;
    }

    public String getIndexed() {
        return indexed;
    }

    public UploadData getUploadData() {
        return uploadData;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public long getSize() {
        return size;
    }

    public String getMd5() {
        return md5;
    }

    public long getUploadTime() {
        return uploadTime;
    }
}
