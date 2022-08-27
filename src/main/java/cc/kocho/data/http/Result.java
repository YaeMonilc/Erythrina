package cc.kocho.data.http;

public class Result {
    private int code;
    private String message;

    public Result(int code, String message){
        this.code = code;
        this.message = message;
    }

}
