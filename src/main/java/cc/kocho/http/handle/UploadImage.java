package cc.kocho.http.handle;

import cc.kocho.Erythrina;
import cc.kocho.data.http.Result;
import cc.kocho.data.http.UploadData;
import cc.kocho.database.image.Image;
import cc.kocho.http.Http;
import cc.kocho.utils.DatabaseUtils;
import cc.kocho.utils.JsonUtils;
import cn.hutool.crypto.digest.MD5;
import dev.morphia.query.experimental.filters.Filters;
import io.javalin.Javalin;
import io.javalin.http.UploadedFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;

public class UploadImage implements Http {
    @Override
    public void handle(Javalin server) {
        server.post("/image/upload/", ctx -> {
            UploadedFile imageFile = ctx.uploadedFile("image");
            String data = ctx.formParam("uploadData");

            if (imageFile == null || imageFile.getSize() < 1){
                ctx.result(Erythrina.getGson().toJson(new Result(301, "IMAGE NOT FOUND")));
                return;
            }

            byte[] imageFileContent = imageFile.getContent().readAllBytes();

            String md5 = new MD5().digestHex16(imageFileContent);
            if (Erythrina.getDatastore().find(Image.class).filter(Filters.eq("md5", md5)).stream().count() > 0){
                ctx.result(Erythrina.getGson().toJson(new Result(302, "IMAGE EXISTS")));
                return;
            }

            Erythrina.getLogger().info("IP {} upload image. MD5: {}", ctx.ip(), md5);

            UploadData uploadData = new UploadData();
            if (!(data == null || data.equals(""))){
                if (JsonUtils.check(data)){
                    uploadData = Erythrina.getGson().fromJson(data, UploadData.class);
                }
            }

            DatabaseUtils.uploadImage(ImageIO.read(new ByteArrayInputStream(imageFileContent)), uploadData, imageFile.getSize(), md5);

            ctx.result(Erythrina.getGson().toJson(new Result(200, "SUCCESS")));
        });
    }
}
