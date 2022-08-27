package cc.kocho;

import cc.kocho.data.Config;
import cc.kocho.database.image.Image;
import cc.kocho.http.Http;
import cc.kocho.utils.ClassUtil;
import cc.kocho.utils.FileUtil;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import io.javalin.Javalin;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Erythrina {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Erythrina.class);
    private static final Gson gson = new Gson();
    private static final Config config = readConfig();
    private static MongoClient mongoClient;
    private static Datastore datastore;
    private static final Javalin server = Javalin.create();

    private static final Class<?>[] mappedClasses = new Class<?>[] {
            Image.class
    };

    static {
        Logger mongoLogger = (Logger) LoggerFactory.getLogger("org.mongodb.driver");
        Logger javalinLogger = (Logger) LoggerFactory.getLogger("io.javalin");
        Logger jettyLogger = (Logger) LoggerFactory.getLogger("org.eclipse.jetty");

        mongoLogger.setLevel(Level.OFF);
        javalinLogger.setLevel(Level.OFF);
        jettyLogger.setLevel(Level.OFF);
    }

    public static void main(String[] args) {
        getLogger().info("Connect mongodb,Url: {}", getConfig().getDatabase().getUrl());
        mongoClient = MongoClients.create(getConfig().getDatabase().getUrl());
        MapperOptions mapperOptions = MapperOptions.builder()
                .storeEmpties(true).storeNulls(false).build();
        datastore = Morphia.createDatastore(mongoClient, getConfig().getDatabase().getName(), mapperOptions);
        datastore.getMapper().map(mappedClasses);

        init();

        server.start(getConfig().getHost(), getConfig().getPort());
        getLogger().info("Server is launch at {}:{}", getConfig().getHost(), getConfig().getPort());
        addAllHttpHandle();
    }

    private static Config readConfig(){
        File configFile = new File("./config.json");
        Config config;
        if (!configFile.exists()){
            getLogger().info("Config not found,Creating");
            config = new Config();
            FileUtil.writeFile(configFile, getGson().toJson(config));
            return config;
        }
        getLogger().info("Loading config");
        return getGson().fromJson(FileUtil.readFile(configFile), Config.class);
    }

    private static void init(){
        File lowFile = new File(Erythrina.getConfig().getImageFile().getImageFileDir().getLow());
        File middleFile = new File(Erythrina.getConfig().getImageFile().getImageFileDir().getMiddle());
        File highFile = new File(Erythrina.getConfig().getImageFile().getImageFileDir().getHigh());

        if (!lowFile.exists()){
            lowFile.mkdirs();
        }
        if (!middleFile.exists()){
            middleFile.mkdirs();
        }
        if (!highFile.exists()){
            highFile.mkdirs();
        }
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

    public static Config getConfig() {
        return config;
    }

    public static Datastore getDatastore() {
        return datastore;
    }

    public static Javalin getServer() {
        return server;
    }

    public static Gson getGson() {
        return gson;
    }
}