package com.bimasakti.diskominfosan.response;

import com.bimasakti.diskominfosan.User.ModelUser;

public class loginResponse {
    private boolean error;
    private String message;
    private ModelUser user;


    public loginResponse(boolean error, String message, ModelUser user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ModelUser getModelUser() {
        return user;
    }
}
