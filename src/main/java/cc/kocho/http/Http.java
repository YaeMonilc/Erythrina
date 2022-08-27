package cc.kocho.http;

import io.javalin.Javalin;

public interface Http {
    void handle(Javalin server);
}
