package cc.kocho.http.handle;

import cc.kocho.http.Http;
import io.javalin.Javalin;

public class Index implements Http {
    @Override
    public void handle(Javalin server) {
        server.get("/", ctx -> {
            ctx.res.setStatus(403);
        });
    }
}
