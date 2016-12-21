package pl.morecraft.dev.settler.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.dao.repository.FileObjectRepository;
import pl.morecraft.dev.settler.domain.FileObject;
import pl.morecraft.dev.settler.domain.QFileObject;
import pl.morecraft.dev.settler.service.abstractService.prototype.AbstractService;
import pl.morecraft.dev.settler.service.util.MessageDigestComponent;
import pl.morecraft.dev.settler.web.dto.FileObjectDTO;
import pl.morecraft.dev.settler.web.dto.FileObjectListDTO;
import pl.morecraft.dev.settler.web.misc.FileObjectListFilters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.UnaryOperator;

@Service
public class FileService extends AbstractService<FileObject, FileObjectDTO, FileObjectListDTO, FileObjectListFilters, QFileObject, Long, FileObjectRepository> {

    private static Logger log = LoggerFactory.getLogger(FileService.class);

    private final FileObjectRepository fileObjectRepository;
    private final MessageDigestComponent messageDigestComponent;

    @Autowired
    public FileService(FileObjectRepository fileObjectRepository, MessageDigestComponent messageDigestComponent) {
        this.fileObjectRepository = fileObjectRepository;
        this.messageDigestComponent = messageDigestComponent;
    }

    protected FileObjectRepository getUserRepository() {
        return fileObjectRepository;
    }

    @Override
    protected Boolean isFilterClassExtended() {
        return false;
    }

    @Override
    protected Class<FileObject> getEntityClass() {
        return FileObject.class;
    }

    @Override
    protected Class<FileObjectDTO> getDtoClass() {
        return FileObjectDTO.class;
    }

    @Override
    protected Class<FileObjectListDTO> getListDtoClass() {
        return FileObjectListDTO.class;
    }

    @Override
    protected Class<FileObjectListFilters> getListFilterClass() {
        return FileObjectListFilters.class;
    }

    @Override
    protected QFileObject getEQ() {
        return QFileObject.fileObject;
    }

    @Override
    protected UnaryOperator<FileObject> getSaveSavePreProcessingFunction() {
        return fileObject -> {
            fileObject.setUpdated(new LocalDateTime());
            String filePath = fileObject.getPath();
            fileObject.setExtension(FilenameUtils.getExtension(filePath));
            fileObject.setOriginalName(FilenameUtils.getName(filePath));
            if (fileObject.getType() == null || fileObject.getType().isEmpty()) {
                fileObject.setType(getMimeType(fileObject.getContent()));
            }
            if (fileObject.getId() == null) {
                fileObject.setName(
                        messageDigestComponent.md5(
                                ArrayUtils.addAll(fileObject.getContent(), String.valueOf(System.nanoTime()).getBytes())
                        )
                );
            }
            if (fileObject.getName() == null) {
                fileObject.setName(
                        fileObjectRepository.findOne(fileObject.getId()).getName()
                );
            }
            return super.getSaveSavePreProcessingFunction().apply(fileObject);
        };
    }

    private String getMimeType(byte[] content) {
        try {
            File file = File.createTempFile("temp", Long.toString(System.nanoTime()));
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content);
            fos.flush();
            fos.close();
            String type = Files.probeContentType(file.toPath());
            if (!file.delete()) {
                log.warn("Cannot delete file: {}", file.getAbsolutePath());
            }
            return type;
        } catch (IOException e) {
            log.error("", e);
        }
        return null;
    }

}
