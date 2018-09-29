package ru.neshin.downloader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.neshin.downloader.dao.DownloadFileInfoRepository;
import ru.neshin.downloader.model.DownloadFileInfo;

import java.util.logging.Logger;

@SpringBootApplication
@EnableScheduling
public class DownloaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DownloaderApplication.class, args);
	}
}
