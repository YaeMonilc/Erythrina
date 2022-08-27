package cc.kocho.data.image;

import dev.morphia.annotations.Entity;

@Entity
public class Resolution {
    private int width = 0;
    private int height = 0;

    public Resolution() {

    }

    public Resolution(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return getWidth() + "x" + getHeight();
    }
}
