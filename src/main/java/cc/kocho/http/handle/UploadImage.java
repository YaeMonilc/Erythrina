package cc.kocho.http.handle;

import cc.kocho.Erythrina;
import cc.kocho.data.http.Result;
import cc.kocho.data.http.Tags;
import cc.kocho.data.image.Resolution;
import cc.kocho.data.image.Tag;
import cc.kocho.database.image.Image;
import cc.kocho.http.Http;
import cc.kocho.utils.JsonUtils;
import cn.hutool.core.img.Img;
import io.javalin.Javalin;
import io.javalin.http.UploadedFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UploadImage implements Http {
    @Override
    public void handle(Javalin server) {
        server.post("/image/upload/", ctx -> {
            UploadedFile imageFile = ctx.uploadedFile("image");
            if (imageFile == null || imageFile.getSize() < 1){
                ctx.result(Erythrina.getGson().toJson(new Result(301, "IMAGE NOT FOUND")));
                return;
            }

            ctx.result(Erythrina.getGson().toJson(new Result(200, "SUCCESS")));

            String tag = ctx.formParam("tag");
            Tags tags;
            List<Tag> tagList = new ArrayList<>();
            if (!(tag == null || tag.equals(""))){
                if (JsonUtils.check(tag)){
                    tags = Erythrina.getGson().fromJson(tag, Tags.class);
                    tagList = tags.getTags();
                }
            }

            BufferedImage bufferedImage = ImageIO.read(imageFile.getContent());

            Img imgLow = new Img(bufferedImage);
            Img imgMiddle = new Img(bufferedImage);
            Img imgHigh = new Img(bufferedImage);

            Image image = new Image(tagList, new Resolution(bufferedImage.getWidth(), bufferedImage.getHeight()), imageFile.getSize());

            imgLow.scale(Erythrina.getConfig().getImageFile().getGetImageFileQuality().getLow());
            imgMiddle.scale(Erythrina.getConfig().getImageFile().getGetImageFileQuality().getMiddle());
            imgHigh.scale(Erythrina.getConfig().getImageFile().getGetImageFileQuality().getHigh());

            imgLow.write(new File(Erythrina.getConfig().getImageFile().getImageFileDir().getLow() + image.getIndexed() + "." + Erythrina.getConfig().getImageFile().getFormat()));
            imgMiddle.write(new File(Erythrina.getConfig().getImageFile().getImageFileDir().getMiddle() + image.getIndexed() + "." + Erythrina.getConfig().getImageFile().getFormat()));
            imgHigh.write(new File(Erythrina.getConfig().getImageFile().getImageFileDir().getHigh() + image.getIndexed() + "." + Erythrina.getConfig().getImageFile().getFormat()));

            Erythrina.getDatastore().save(image);
        });
    }
}
