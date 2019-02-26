package io.microconfig.configs.serializer;

import io.microconfig.configs.Property;
import io.microconfig.configs.files.io.ConfigIoService;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import static io.microconfig.utils.FileUtils.delete;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@RequiredArgsConstructor
public class ToFileConfigSerializer implements ConfigSerializer {
    private final FilenameGenerator filenameGenerator;
    private final ConfigIoService configIoService;

    @Override
    public Optional<File> serialize(String component, Collection<Property> properties) {
        File file = configDestination(component, properties);
        delete(file);

        if (properties.isEmpty()) return empty();

        configIoService.writeTo(file).write(properties);
        return of(file);
    }

    @Override
    public File configDestination(String component, Collection<Property> properties) {
        return filenameGenerator.fileFor(component, properties);
    }
}