package me.mneri.gol.data.config;

import com.google.inject.CreationException;
import com.google.inject.Provider;
import com.google.inject.spi.Message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class ConfigurationProvider implements Provider<Configuration> {
    @Override
    public Configuration get() {
        try {
            InputStream input = null;

            try {
                Properties properties = new Properties();
                String propertiesFileName = "application.properties";
                input = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

                if (input == null) {
                    throw new FileNotFoundException(propertiesFileName + " not found in the classpath.");
                }

                properties.load(input);

                return new Configuration(properties);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        } catch (IOException e) {
            throw new CreationException(List.of(new Message(e.getMessage())));
        }
    }
}
