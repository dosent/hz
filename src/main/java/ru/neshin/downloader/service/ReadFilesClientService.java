package ru.neshin.downloader.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.neshin.downloader.dao.DownloadFileInfoRepository;
import ru.neshin.downloader.fias.WSDL.*;

@Component
public class ReadFilesClientService extends WebServiceGatewaySupport {

    private static final String HTTP_FIAS_NALOG_RU_WEB_SERVICES_PUBLIC_DOWNLOAD_SERVICE_ASMX_GET_LAST_DOWNLOAD_FILE_INFO = "http://fias.nalog.ru/WebServices/Public/DownloadService.asmx/GetLastDownloadFileInfo";
    private static final String HTTP_FIAS_NALOG_RU_WEB_SERVICES_PUBLIC_DOWNLOAD_SERVICE_ASMX_GET_ALL_DOWNLOAD_FILE_INFO = "http://fias.nalog.ru/WebServices/Public/DownloadService.asmx/GetAllDownloadFileInfo";

    private static boolean isReadyToWork = true;


    @Autowired
    private DownloadFileInfoRepository downloadFileInfoRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasksService.class);

    void work() {
        if (isReadyToWork) {
            isReadyToWork = false;
            readFromFaip();
            downloadFile();
            isReadyToWork = true;
        }
    }

    private void downloadFile() {
        //файл который сохранен т.е. максимальная версия и флаг загрузки установлен
        ru.neshin.downloader.model.DownloadFileInfo wasSaveLocalFile = downloadFileInfoRepository.findTopByVersionIdAndSaveLocalFile(true);
        //файл который необходимо сохранить т.е. максимальная версия и флаг загрузки не установлен
        ru.neshin.downloader.model.DownloadFileInfo savedLocalFile = downloadFileInfoRepository.findTopByVersionIdAndSaveLocalFile(false);

        //нет ниодно сохраненного файла
        if (wasSaveLocalFile == null) {
            LOG.info("Надо загрузить послению максимальную версию");
        } else if (wasSaveLocalFile.getVersionId() >= savedLocalFile.getVersionId()) {
            LOG.info("Загружена максимальная версия, ничего делать не надо");
        } else {
            LOG.info("нужно загружать обновления");
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
     */
    private GetAllDownloadFileInfoResponse getAllDownloadFileInfo() {

        GetAllDownloadFileInfo request = new GetAllDownloadFileInfo();
        SoapActionCallback requestCallback = new SoapActionCallback(HTTP_FIAS_NALOG_RU_WEB_SERVICES_PUBLIC_DOWNLOAD_SERVICE_ASMX_GET_ALL_DOWNLOAD_FILE_INFO);
        return(GetAllDownloadFileInfoResponse)getWebServiceTemplate()
                .marshalSendAndReceive(request, requestCallback);
    }

    /***
     * Возвращает информацию о последней версии файлов, доступных для скачивания
     */
    private GetLastDownloadFileInfoResponse getLastDownloadFileInfoResponse() {
        GetLastDownloadFileInfo request = new GetLastDownloadFileInfo();
        SoapActionCallback requestCallback = new SoapActionCallback(HTTP_FIAS_NALOG_RU_WEB_SERVICES_PUBLIC_DOWNLOAD_SERVICE_ASMX_GET_LAST_DOWNLOAD_FILE_INFO);
        return (GetLastDownloadFileInfoResponse)getWebServiceTemplate()
                .marshalSendAndReceive(request, requestCallback);
    }
}
