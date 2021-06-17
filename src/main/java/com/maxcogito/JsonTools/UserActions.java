package com.maxcogito.JsonTools;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserActions {

    MENU("value"),
    ENTERURL("enterurl"),
    VERIFYURL("verifyurl"),
    CONFIRMURL("confirmurl"),
    ENTERFILEPTH("enterfilepth"),
    VERIFYFILEPTH("verifyfilepth"),
    CONFIRMFILEPTH("confirmfilepth"),
    NAMEAPIPTH("nameapipth"),
    BUILDAPIPTH("buildapipth"),
    CONFIRMFINAL("confirmfinal"),
    VIEWAPI("viewapi"),
    WRITEAPI("writeapi"),
    QUIT("quit"),
    Q("q");


    private String value;

    UserActions(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }


}
