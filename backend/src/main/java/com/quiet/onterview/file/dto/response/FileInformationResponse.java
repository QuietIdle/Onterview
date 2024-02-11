package com.quiet.onterview.file.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileInformationResponse {

    private String saveFilename;
    private String originFilename;

    public FileInformationResponse(String saveFilename, String originFilename) {
        this.saveFilename = saveFilename;
        this.originFilename = originFilename;
    }
}
