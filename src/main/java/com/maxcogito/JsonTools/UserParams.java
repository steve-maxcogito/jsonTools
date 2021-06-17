package com.maxcogito.JsonTools;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserParams {
    USE_DEFAULTPTH("USE_DEFAULTPTH"),
    USE_DEFAULTURL("USE_DEFAULTURL"),
    USE_NEWURL("USE_NEWURL"),
    USE_NEWPTH("USE_NEWPTH"),
    USE_CURRENTPTH("USE_CURRENTPTH"),
    USE_CURRENTURL("USE_CURRENTURL"),
    USE_QUITACTION("USE_QUITACTION"),
    USE_NAMEDIR("USE_NAMEDIR"),
    USE_HOMEDIR("USE_HOMEDIR"),
    USE_APISET("USE_APISET"),
    USE_FILEPTHSET("USE_FILEPTHSET"),
    USE_APIANDFILEPTHSET("USE_APIANDFILEPTHSET"),
    USE_APINOTSET("USE_APINOTSET"),
    USE_FILEPTHNOTSET("USE_FILEPTHNOTSET"),
    USE_FILEANDAPINOTSET("USE_FILEANDAPINOTSET"),
    USE_FILEOUTPUTONLY("USE_FILEOUTPUTONLY"),
    USE_VIEWAPIONLY("USE_VIEWAPIONLY"),
    USE_REENTER("USE_REENTER");

    private String value;

    UserParams(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
