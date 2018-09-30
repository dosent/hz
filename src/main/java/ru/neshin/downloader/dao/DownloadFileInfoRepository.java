package ru.neshin.downloader.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.neshin.downloader.model.DownloadFileInfo;

public interface DownloadFileInfoRepository extends CrudRepository<DownloadFileInfo, Long> {

    DownloadFileInfo findByVersionId(int versionId);

    @Query(value = "SELECT * FROM download_file_info df where df.is_save_local_file = :is_save_local_file order by df.version_id DESC LIMIT 1",nativeQuery = true)
    DownloadFileInfo findTopByVersionIdAndSaveLocalFile(@Param("is_save_local_file") boolean isSaveLocalFile);
}
