package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.FileObject;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;

@Component
@EntityConverter
public class FileObjectLongConverter extends AbstractConverter<FileObject, Long> {

    @Override
    protected Long convert(FileObject fileObject) {
        return fileObject == null ? null : fileObject.getId();
    }

}
