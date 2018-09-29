package ru.neshin.downloader.dao;

import org.springframework.data.repository.CrudRepository;
import ru.neshin.downloader.model.DownloadFileInfo;

public interface DownloadFileInfoRepository extends CrudRepository<DownloadFileInfo, Long> {

}
