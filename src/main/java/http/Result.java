package http;

public enum Result {
    OK("ok","成功！"),
    FAIL("fail","失败！"),
    VERIFY_ERROR("verify_error","验证信息错误！"),
    SERVER_ERROR("server_error","服务器繁忙！"),
    NOTFOUND_ERROR("notfound_error","查找失败！"),
    ACCESSTOKEN_ERROR("accessToken_error","access_token不合法");

    private String resultCode;

    private String resultMsg;

    Result(String resultCode, String resultMsg){
        this.resultCode=resultCode;
        this.resultMsg=resultMsg;
    }

    public String getResultCode(){
        return this.resultCode;
    }

    public String getResultMsg(){
        return this.resultMsg;
    }
}
