package com.quiet.onterview.file.mapper;

import com.quiet.onterview.file.dto.request.FileInformationRequest;
import com.quiet.onterview.file.dto.response.FileInformationResponse;
import com.quiet.onterview.file.entity.FileInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileInformationMapper {
    @Value("${file.base-url}")
    private String baseUrl;
    @Value("${file.file-key}")
    private String fileKey;
    @Value("${file.file-path-query}")
    private String filePathQuery;
    @Value("${file.file-name-query}")
    private String fileNameQuery;
    @Value("${file.file-path}")
    private String filePath;

    public FileInformation fileInformationRequestToEntity(FileInformationRequest fileInformationRequest) {
        return FileInformation.builder()
                .originFilename(fileInformationRequest.getOriginFilename())
                .saveFilename(getUrl(fileInformationRequest.getSaveFilename()))
                .build();
    }

    private String getUrl(String filename) {
        return baseUrl + "/" + fileKey + "?" + filePathQuery + "=" + filePath + "&" + fileNameQuery + "=" + filename;
    }

    public FileInformationResponse fileInformationToResponse(FileInformation fileInformation) {
        return FileInformationResponse.builder()
                .originFilename(fileInformation.getOriginFilename())
                .saveFilename(fileInformation.getSaveFilename())
                .build();
    }
}
