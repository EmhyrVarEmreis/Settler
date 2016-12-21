package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.FileObject;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;

@Component
@EntityConverter
public class FileObjectStringConverter extends AbstractConverter<FileObject, String> {

    @Override
    protected String convert(FileObject fileObject) {
        return fileObject == null ? null : fileObject.getName();
    }

}
