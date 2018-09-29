package ru.neshin.downloader.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class DownloadFileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long downloadFileInfoId;
    private int versionId;
    private String textVersion;
    private String fiasCompleteDbfUrl;
    private String fiasCompleteXmlUrl;
    private String fiasDeltaDbfUrl;
    private String fiasDeltaXmlUrl;
    private boolean isSaveLocalFile;

    protected DownloadFileInfo() {};

    public DownloadFileInfo(int versionId, String textVersion, String fiasCompleteDbfUrl, String fiasCompleteXmlUrl, String fiasDeltaDbfUrl, String fiasDeltaXmlUrl, boolean isSaveLocalFile) {
        this.versionId = versionId;
        this.textVersion = textVersion;
        this.fiasCompleteDbfUrl = fiasCompleteDbfUrl;
        this.fiasCompleteXmlUrl = fiasCompleteXmlUrl;
        this.fiasDeltaDbfUrl = fiasDeltaDbfUrl;
        this.fiasDeltaXmlUrl = fiasDeltaXmlUrl;
        this.isSaveLocalFile = isSaveLocalFile;
    }

    @Override
    public String toString() {
        return "DownloadFileInfo{" +
                "downloadFileInfoId=" + downloadFileInfoId +
                ", versionId=" + versionId +
                ", textVersion='" + textVersion + '\'' +
                ", fiasCompleteDbfUrl='" + fiasCompleteDbfUrl + '\'' +
                ", fiasCompleteXmlUrl='" + fiasCompleteXmlUrl + '\'' +
                ", fiasDeltaDbfUrl='" + fiasDeltaDbfUrl + '\'' +
                ", fiasDeltaXmlUrl='" + fiasDeltaXmlUrl + '\'' +
                ", isSaveLocalFile=" + isSaveLocalFile +
                '}';
    }
}
