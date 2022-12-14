package cc.kocho.data;

public class Config {
    private String host = "0.0.0.0";
    private int port = 1800;
    private Database database = new Database();
    private ImageFile imageFile = new ImageFile();
    private File file = new File();

    public class Database{
        private String name = "Erythrina";
        private String url = "mongodb://localhost:27017";

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }

    public class ImageFile{
        private Directory directory = new Directory();
        private Quality quality = new Quality();
        private String format = "jpg";

        public class Directory{
            private String low = "./images/low/";
            private String medium = "./images/middle/";
            private String high = "./images/high/";

            public String getLow() {
                return low;
            }

            public String getMedium() {
                return medium;
            }

            public String getHigh() {
                return high;
            }
        }

        public class Quality{
            private float low = 0.35F;
            private float medium = 0.65F;
            private float high = 1F;

            public float getLow() {
                return low;
            }

            public float getMedium() {
                return medium;
            }

            public float getHigh() {
                return high;
            }
        }

        public Directory getDirectory() {
            return directory;
        }

        public Quality getQuality() {
            return quality;
        }

        public String getFormat() {
            return format;
        }
    }

    public class File{
        private String path = "./file/";

        public String getPath() {
            return path;
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Database getDatabase() {
        return database;
    }

    public ImageFile getImageFile() {
        return imageFile;
    }

    public File getFile() {
        return file;
    }
}
