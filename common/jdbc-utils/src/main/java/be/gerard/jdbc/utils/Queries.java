package be.gerard.jdbc.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Queries {

    public static <T> String load(
            final String filename
    ) {
        return loadFromClassPath(
                Thread.currentThread().getContextClassLoader(),
                filename
        );
    }

    public static <T> String loadFromClassPath(
            final Class<T> clazz,
            final String filename
    ) {
        return loadFromClassPath(
                clazz.getClassLoader(),
                filename
        );
    }

    private static <T> String loadFromClassPath(
            final ClassLoader classLoader,
            final String filename
    ) {
        Assert.notNull(filename, "filename is invalid [filename=null]");

        final URL url = classLoader.getResource(filename);
        Assert.notNull(url, () -> String.format("filename not found [filename=%s]", filename));

        try {
            final Path path = Paths.get(url.toURI());
            return Files.readString(path, Charset.defaultCharset());
        } catch (URISyntaxException | IOException e) {
            throw new IllegalArgumentException(
                    String.format("filename.uri is invalid [filename=%s]", filename),
                    e
            );
        }
    }

    /*
    public static <T> String loadFromClassPathOld(
            final Class<T> clazz,
            final String filename
    ) {
        try (final InputStream in = clazz.getResourceAsStream("/" + filename)) {
            if (Objects.isNull(in)) {
                throw new IllegalArgumentException(
                        String.format(
                                "invalid [query=%1$s] [search for Queries.loadFromClassPath(%1$s) and correct the filename]",
                                filename
                        )
                );
            }

            return IOUtils.toString(in, Charset.defaultCharset());
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    String.format(
                            "invalid [query=%s]",
                            filename
                    ),
                    e
            );
        }
    }
     */

}
