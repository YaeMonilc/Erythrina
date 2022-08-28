package cc.kocho.data.http;

import cc.kocho.data.image.Tag;
import dev.morphia.annotations.Entity;

import java.util.ArrayList;
import java.util.List;
@Entity
public class UploadData {
    private List<Tag> tags = new ArrayList<>();

    public List<Tag> getTags() {
        return tags;
    }
}
