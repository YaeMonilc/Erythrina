package cc.kocho.utils;

import cc.kocho.Erythrina;
import cc.kocho.data.http.UploadData;
import cc.kocho.data.image.Resolution;
import cc.kocho.database.image.Image;
import cn.hutool.core.img.Img;
import cn.hutool.core.io.file.FileReader;

import java.awt.image.BufferedImage;
import java.io.File;

public class DatabaseUtils {
    public static void uploadImage(BufferedImage bufferedImage, UploadData uploadData, long size, String md5){
        Img imgLow = new Img(bufferedImage);
        Img imgMiddle = new Img(bufferedImage);
        Img imgHigh = new Img(bufferedImage);

        Image image = new Image(uploadData, new Resolution(bufferedImage.getWidth(), bufferedImage.getHeight()), size, md5);

        imgLow.scale(Erythrina.getConfig().getImageFile().getQuality().getLow());
        imgMiddle.scale(Erythrina.getConfig().getImageFile().getQuality().getMedium());
        imgHigh.scale(Erythrina.getConfig().getImageFile().getQuality().getHigh());

        imgLow.write(FileUtil.getImageFile(image.getIndexed(), "low"));
        imgMiddle.write(FileUtil.getImageFile(image.getIndexed(), "medium"));
        imgHigh.write(FileUtil.getImageFile(image.getIndexed(), "high"));

        Erythrina.getDatastore().save(image);
    }
    public static byte[] getImage(String indexed, String quality){
        File file = FileUtil.getImageFile(indexed, quality);
        return new FileReader(file).readBytes();
    }
}
