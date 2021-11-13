package com.example.demo.entity;


import java.util.Date;

public class FileInfo {

    private Integer id;
    private String uuid;
    private String fileName;
    private Long fileSize;
    private String fileType;
    private String originalName;
    private String filePath;
    private Date createTime;


    public FileInfo() {
    }

    public FileInfo(Integer id, String uuid, String fileName, Long fileSize, String fileType, String originalName,
                    String filePath, Date createTime) {
        this.id = id;
        this.uuid = uuid;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.originalName = originalName;
        this.createTime = createTime;
        this.filePath = filePath;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
