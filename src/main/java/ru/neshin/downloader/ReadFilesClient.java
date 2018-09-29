package ru.neshin.downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.neshin.downloader.dao.DownloadFileInfoRepository;
import ru.neshin.downloader.fias.WSDL.*;

@Component
public class ReadFilesClient extends WebServiceGatewaySupport {

    public static final String HTTP_FIAS_NALOG_RU_WEB_SERVICES_PUBLIC_DOWNLOAD_SERVICE_ASMX_GET_LAST_DOWNLOAD_FILE_INFO = "http://fias.nalog.ru/WebServices/Public/DownloadService.asmx/GetLastDownloadFileInfo";
    public static final String HTTP_FIAS_NALOG_RU_WEB_SERVICES_PUBLIC_DOWNLOAD_SERVICE_ASMX_GET_ALL_DOWNLOAD_FILE_INFO = "http://fias.nalog.ru/WebServices/Public/DownloadService.asmx/GetAllDownloadFileInfo";

    private static boolean isReadyToWork = true;


    @Autowired
    private DownloadFileInfoRepository downloadFileInfoRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

    public void work() {
        if (isReadyToWork) {
            isReadyToWork = false;
            readFromFaip();
            isReadyToWork = true;
        }
    }

    private void readFromFaip() {
        LOG.info("Read web service start");
        try {
            ArrayOfDownloadFileInfo getAllDownloadFileInfoResult = getAllDownloadFileInfo().getGetAllDownloadFileInfoResult();
            for (DownloadFileInfo fileInfo : getAllDownloadFileInfoResult.getDownloadFileInfo()) {
                saveDownloadFileInfo(fileInfo);
            }
        } catch (Exception e) {
            LOG.error("Error read web service:");
            e.printStackTrace();
        }
        for (ru.neshin.downloader.model.DownloadFileInfo d: downloadFileInfoRepository.findAll()) {
        }
        LOG.info("Read web service stop");
    }

    private void saveDownloadFileInfo(DownloadFileInfo fileInfo) {
        if (downloadFileInfoRepository.findByVersionId(fileInfo.getVersionId()) == null) {
            ru.neshin.downloader.model.DownloadFileInfo downloadFileInfo = new
                    ru.neshin.downloader.model.DownloadFileInfo(
                    fileInfo.getVersionId(),
                    fileInfo.getTextVersion(),
                    fileInfo.getFiasCompleteDbfUrl(),
                    fileInfo.getFiasCompleteXmlUrl(),
                    fileInfo.getFiasDeltaDbfUrl(),
                    fileInfo.getFiasDeltaXmlUrl(),
                    false
            );
            downloadFileInfoRepository.save(downloadFileInfo);
        }
    }

    /***
     * Возвращает информацию о всех версиях файлов, доступных для скачивания
     * @return
     */
    private GetAllDownloadFileInfoResponse getAllDownloadFileInfo() {

        GetAllDownloadFileInfo request = new GetAllDownloadFileInfo();
        String soapAction = HTTP_FIAS_NALOG_RU_WEB_SERVICES_PUBLIC_DOWNLOAD_SERVICE_ASMX_GET_ALL_DOWNLOAD_FILE_INFO;
        SoapActionCallback requestCallback = new SoapActionCallback(
                soapAction);
        GetAllDownloadFileInfoResponse response =  (GetAllDownloadFileInfoResponse )getWebServiceTemplate()
                .marshalSendAndReceive(request, requestCallback);
        return response;
    }

    /***
     * Возвращает информацию о последней версии файлов, доступных для скачивания
     * @return
     */
    private GetLastDownloadFileInfoResponse getLastDownloadFileInfoResponse() {
        GetLastDownloadFileInfo request = new GetLastDownloadFileInfo();
        String soapAction = HTTP_FIAS_NALOG_RU_WEB_SERVICES_PUBLIC_DOWNLOAD_SERVICE_ASMX_GET_LAST_DOWNLOAD_FILE_INFO;
        SoapActionCallback requestCallback = new SoapActionCallback(
                soapAction);
        GetLastDownloadFileInfoResponse response =  (GetLastDownloadFileInfoResponse )getWebServiceTemplate()
                .marshalSendAndReceive(request, requestCallback);
        return response;
    }
}
