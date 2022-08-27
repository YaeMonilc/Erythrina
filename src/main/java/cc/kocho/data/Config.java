package cc.kocho.data;

public class Config {
    private String host = "0.0.0.0";
    private int port = 1800;
    private Database database = new Database();
    private ImageFile imageFile = new ImageFile();

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
        private ImageFileDir imageFileDir = new ImageFileDir();
        private ImageFileQuality getImageFileQuality = new ImageFileQuality();
        private String format = "jpg";

        public class ImageFileDir{
            private String low = "./images/low/";
            private String middle = "./images/middle/";
            private String high = "./images/high/";

            public String getLow() {
                return low;
            }

            public String getMiddle() {
                return middle;
            }

            public String getHigh() {
                return high;
            }
        }

        public class ImageFileQuality{
            private float low = 0.35F;
            private float middle = 0.65F;
            private float high = 1F;

            public float getLow() {
                return low;
            }

            public float getMiddle() {
                return middle;
            }

            public float getHigh() {
                return high;
            }
        }

        public ImageFileDir getImageFileDir() {
            return imageFileDir;
        }

        public ImageFileQuality getGetImageFileQuality() {
            return getImageFileQuality;
        }

        public String getFormat() {
            return format;
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
}
