package ru.neshin.downloader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.neshin.downloader.service.ReadFilesClientService;

@Configuration
public class ReadDilesConfiguration {

    private static final String HTTPS_FIAS_NALOG_RU_WEB_SERVICES_PUBLIC_DOWNLOAD_SERVICE_ASMX = "https://fias.nalog.ru/WebServices/Public/DownloadService.asmx";
    private static final String RU_NESHIN_DOWNLOADER_FIAS_WSDL = "ru.neshin.downloader.fias.WSDL";

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(RU_NESHIN_DOWNLOADER_FIAS_WSDL);
        return marshaller;
    }

    @Bean
    public ReadFilesClientService readFilesClient(Jaxb2Marshaller marshaller) {
        ReadFilesClientService client = new ReadFilesClientService();
        client.setDefaultUri(HTTPS_FIAS_NALOG_RU_WEB_SERVICES_PUBLIC_DOWNLOAD_SERVICE_ASMX);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
