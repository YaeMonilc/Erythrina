package cc.kocho.http.handle;

import cc.kocho.Erythrina;
import cc.kocho.data.image.Resolution;
import cc.kocho.data.image.Tag;
import cc.kocho.database.image.Image;
import cc.kocho.http.Http;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.List;

public class Index implements Http {
    @Override
    public void handle(Javalin server) {
        server.get("/", ctx -> {
            List<Tag> tagList = new ArrayList<>();
            tagList.add(new Tag("美铝"));
            tagList.add(new Tag("烧鸡"));
            Image image = new Image(tagList, new Resolution(5000, 8000), 800000000);
            Erythrina.getDatastore().save(image);
            ctx.result(String.format("Indexes: %s ; Resolution: %s ; Size: %d;", image.getIndexed(), image.getResolution().toString(), image.getSize()));
        });
    }
}
