package cc.kocho.database.image;

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
    private List<Tag> tag = new ArrayList<>();
    private Resolution resolution = new Resolution();
    //SIZE byte
    private long size = 0;

    public Image() {

    }

    public Image(List<Tag> tag, Resolution resolution, long size){
        this.tag = tag;
        this.resolution = resolution;
        this.size = size;
    }

    public String getIndexed() {
        return indexed;
    }

    public List<Tag> getTag() {
        return tag;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public long getSize() {
        return size;
    }
}
