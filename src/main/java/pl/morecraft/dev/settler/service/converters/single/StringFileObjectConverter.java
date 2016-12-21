package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.dao.repository.FileObjectRepository;
import pl.morecraft.dev.settler.domain.FileObject;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;

@Component
@EntityConverter
public class StringFileObjectConverter extends AbstractConverter<String, FileObject> {

    private final FileObjectRepository fileObjectRepository;

    @Autowired
    public StringFileObjectConverter(FileObjectRepository fileObjectRepository) {
        this.fileObjectRepository = fileObjectRepository;
    }

    @Override
    protected FileObject convert(String s) {
        return fileObjectRepository.findOneByName(s);
    }

}
