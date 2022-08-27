package cc.kocho;

import cc.kocho.http.Http;
import cc.kocho.utils.ClassUtil;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import io.javalin.Javalin;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Erythrina {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Erythrina.class);
    private static final Javalin server = Javalin.create();

    static {
        Logger mongoLogger = (Logger) LoggerFactory.getLogger("org.mongodb.driver");
        Logger javalinLogger = (Logger) LoggerFactory.getLogger("io.javalin");
        Logger jettyLogger = (Logger) LoggerFactory.getLogger("org.eclipse.jetty");

        mongoLogger.setLevel(Level.OFF);
        javalinLogger.setLevel(Level.OFF);
        jettyLogger.setLevel(Level.OFF);
    }

    public static void main(String[] args) {
        server.start(1800);
        addAllHttpHandle();
    }

    private static void addAllHttpHandle(){
        Set<Class<?>> httpClass = ClassUtil.getInstances(Http.class);
        httpClass.forEach(aClass -> {
            try {
                Http httpHandle = (Http) aClass.getDeclaredConstructor().newInstance();
                httpHandle.handle(getServer());
                getLogger().info("Http listen {} launching", aClass.getSimpleName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                getLogger().error(e.toString());
            }
        });
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Javalin getServer() {
        return server;
    }
}