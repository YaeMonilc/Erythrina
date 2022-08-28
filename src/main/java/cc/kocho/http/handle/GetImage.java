package cc.kocho.http.handle;

import cc.kocho.Erythrina;
import cc.kocho.data.http.Result;
import cc.kocho.database.image.Image;
import cc.kocho.http.Http;
import cc.kocho.utils.DatabaseUtils;
import dev.morphia.query.experimental.filters.Filters;
import io.javalin.Javalin;

import java.util.List;

public class GetImage implements Http {
    @Override
    public void handle(Javalin server) {
        server.get("/image/get/{quality}/{methods}/{indexed}", ctx -> {
            String quality = ctx.pathParam("quality");
            String methods = ctx.pathParam("methods");
            String indexed = ctx.pathParam("indexed");

            List<Image> imageList = Erythrina.getDatastore().find(Image.class).filter(Filters.eq("indexed", indexed)).stream().toList();

            Erythrina.getLogger().info("IP {} get {} image. MD5: {}", quality, ctx.ip(), indexed);

            if (methods.equals("normal")){
                if (imageList.size() == 0){
                    ctx.result(Erythrina.getGson().toJson(new Result(304, "NOT FOUND")));
                    return;
                }
                ctx.result(DatabaseUtils.getImage(indexed, quality));
            }else if (methods.equals("json")){
                ctx.result(Erythrina.getGson().toJson(new Result(999, "NOT SUPPLY")));
            }
        });
    }
}
