
package com.pakaking.sojun_final_project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Station {

    @SerializedName("shortestRouteList")
    @Expose
    private List<ShortestRouteList> shortestRouteList = null;
    @SerializedName("errorMessage")
    @Expose
    private ErrorMessage errorMessage;
    @SerializedName("message_info_ok")
    @Expose
    private Object messageInfoOk;

    public List<ShortestRouteList> getShortestRouteList() {
        return shortestRouteList;
    }

    public void setShortestRouteList(List<ShortestRouteList> shortestRouteList) {
        this.shortestRouteList = shortestRouteList;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getMessageInfoOk() {
        return messageInfoOk;
    }

    public void setMessageInfoOk(Object messageInfoOk) {
        this.messageInfoOk = messageInfoOk;
    }

}
