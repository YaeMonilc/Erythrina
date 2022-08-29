package cc.kocho.http.handle;

import cc.kocho.Erythrina;
import cc.kocho.http.Http;
import cn.hutool.core.io.file.FileReader;
import io.javalin.Javalin;

public class File implements Http {
    @Override
    public void handle(Javalin server) {
        server.get("/file/{path}", ctx -> {
            String path = ctx.pathParam("path");
            String filePath = Erythrina.getConfig().getFile().getPath();
            java.io.File file = new java.io.File(filePath + path);
            if (!file.exists()){
                ctx.status(503);
                return;
            }
            ctx.result(new FileReader(file).readBytes());
        });
    }
}
