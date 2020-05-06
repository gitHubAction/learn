package http;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;


@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class ApiResult implements Serializable {


    private String error=Result.OK.getResultCode();

    @JsonProperty("errorMsg")
    private String errorMsg;

    private Object resultdata;

    public ApiResult(){

    }

    
    public ApiResult(Result result, Object data){
        this.error=result.getResultCode();
        this.errorMsg=result.getResultMsg();
        this.resultdata=data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getResultdata() {
        return resultdata;
    }

    public void setResultdata(Object resultdata) {
        this.resultdata = resultdata;
    }

    public static ApiResult buildResult(Result result, String msg, Object data){
        ApiResult apiResult=new ApiResult(result,data);
        if(Tools.notEmpty(msg)){
            apiResult.setErrorMsg(msg);
        }
        return apiResult;
    }

    public static ApiResult buildResult(Result result, Object data){
        return buildResult(result,null,data);
    }

    public static ApiResult buildResult(Result result, String msg){
        return buildResult(result,msg,null);
    }

    public static ApiResult buildResult(Result result){
        return buildResult(result,null,null);
    }
}
