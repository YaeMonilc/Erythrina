package cc.kocho.data.image;

import dev.morphia.annotations.Entity;

@Entity
public class Tag {
    private String text;

    public Tag(String text){
        this.text = text;
    }

}
