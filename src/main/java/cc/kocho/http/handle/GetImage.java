package cc.kocho.http.handle;

import cc.kocho.Erythrina;
import cc.kocho.database.image.Image;
import cc.kocho.http.Http;
import cn.hutool.core.io.file.FileReader;
import dev.morphia.query.experimental.filters.Filters;
import io.javalin.Javalin;

import java.io.File;
import java.util.List;

public class GetImage implements Http {
    @Override
    public void handle(Javalin server) {
        server.get("/image/{quality}/{methods}/{indexed}", ctx -> {
            String quality = ctx.pathParam("quality");
            String methods = ctx.pathParam("methods");
            String indexed = ctx.pathParam("indexed");

            List<Image> imageList = Erythrina.getDatastore().find(Image.class).filter(Filters.eq("indexed", indexed)).stream().toList();
            if (methods.equals("normal")){
                if (imageList.size() == 0){
                    ctx.status(406);
                    return;
                }
                File file;
                String tmp_after = indexed + "." + Erythrina.getConfig().getImageFile().getFormat();
                switch (quality){
                    case "low":
                        file = new File(Erythrina.getConfig().getImageFile().getImageFileDir().getLow() + tmp_after);
                        break;
                    case "middle":
                        file = new File(Erythrina.getConfig().getImageFile().getImageFileDir().getMiddle() + tmp_after);
                        break;
                    case "high":
                        file = new File(Erythrina.getConfig().getImageFile().getImageFileDir().getHigh() + tmp_after);
                        break;
                    default:
                        ctx.res.setStatus(406);
                        return;
                }
                ctx.result(new FileReader(file).readBytes());
            }else if (methods.equals("json")){
                ctx.status(405);
            }else {
                ctx.status(412);
            }
        });
    }
}
