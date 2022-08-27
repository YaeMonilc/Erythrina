package cc.kocho.http.handle;

import cc.kocho.Erythrina;
import cc.kocho.database.image.Image;
import cc.kocho.http.Http;
import io.javalin.Javalin;

import java.util.List;

public class GetImageRandom implements Http {
    @Override
    public void handle(Javalin server) {
        server.get("/image/{quality}/{methods}/", ctx -> {
            List<Image> imageList = Erythrina.getDatastore().find(Image.class).stream().toList();
            int randomNumber = (int) (Math.random() * imageList.size());
            String indexed = imageList.get(randomNumber).getIndexed();
            ctx.redirect(indexed);
        });
    }
}
