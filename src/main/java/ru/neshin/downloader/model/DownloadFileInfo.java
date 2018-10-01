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
    private boolean isDeltaSaveLocalFile;
    private String fiasCompleteXmlLocalFile;
    private String fiasDeltaXmlLocalFile;

    protected DownloadFileInfo() {}

    public DownloadFileInfo(int versionId, String textVersion, String fiasCompleteDbfUrl, String fiasCompleteXmlUrl, String fiasDeltaDbfUrl, String fiasDeltaXmlUrl, boolean isSaveLocalFile, boolean isDeltaSaveLocalFile, String fiasCompleteXmlLocalFile, String fiasDeltaXmlLocalFile) {

        this.versionId = versionId;
        this.textVersion = textVersion;
        this.fiasCompleteDbfUrl = fiasCompleteDbfUrl;
        this.fiasCompleteXmlUrl = fiasCompleteXmlUrl;
        this.fiasDeltaDbfUrl = fiasDeltaDbfUrl;
        this.fiasDeltaXmlUrl = fiasDeltaXmlUrl;
        this.isSaveLocalFile = isSaveLocalFile;
        this.isDeltaSaveLocalFile = isDeltaSaveLocalFile;
        this.fiasCompleteXmlLocalFile = fiasCompleteXmlLocalFile;
        this.fiasDeltaXmlLocalFile = fiasDeltaXmlLocalFile;
    }

    public int getVersionId() {
        return versionId;
    }

    public String getTextVersion() {
        return textVersion;
    }

    public String getFiasCompleteDbfUrl() {
        return fiasCompleteDbfUrl;
    }

    public String getFiasCompleteXmlUrl() {
        return fiasCompleteXmlUrl;
    }

    public String getFiasDeltaDbfUrl() {
        return fiasDeltaDbfUrl;
    }

    public String getFiasDeltaXmlUrl() {
        return fiasDeltaXmlUrl;
    }

    public boolean isSaveLocalFile() {
        return isSaveLocalFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadFileInfo that = (DownloadFileInfo) o;

        return (versionId != that.versionId) &&
                (isSaveLocalFile != that.isSaveLocalFile) &&
                downloadFileInfoId.equals(that.downloadFileInfoId);
    }

    @Override
    public int hashCode() {
        int result = downloadFileInfoId.hashCode();
        result = 31 * result + versionId;
        result = 31 * result + (isSaveLocalFile ? 1 : 0);
        return result;
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

    public void setSaveLocalFile(boolean saveLocalFile) {
        isSaveLocalFile = saveLocalFile;
    }

    public String getFiasCompleteXmlLocalFile() {
        return fiasCompleteXmlLocalFile;
    }

    public void setFiasCompleteXmlLocalFile(String fiasCompleteXmlLocalFile) {
        this.fiasCompleteXmlLocalFile = fiasCompleteXmlLocalFile;
    }

    public String getFiasDeltaXmlLocalFile() {
        return fiasDeltaXmlLocalFile;
    }

    public void setFiasDeltaXmlLocalFile(String fiasDeltaXmlLocalFile) {
        this.fiasDeltaXmlLocalFile = fiasDeltaXmlLocalFile;
    }

    public boolean isDeltaSaveLocalFile() {
        return isDeltaSaveLocalFile;
    }

    public void setDeltaSaveLocalFile(boolean deltaSaveLocalFile) {
        isDeltaSaveLocalFile = deltaSaveLocalFile;
    }
}
