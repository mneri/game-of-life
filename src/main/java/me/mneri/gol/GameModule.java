package me.mneri.gol;

import com.google.inject.AbstractModule;
import com.google.inject.CreationException;
import com.google.inject.name.Names;
import com.google.inject.spi.Message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class GameModule extends AbstractModule {
    @Override
    protected void configure() {
        Names.bindProperties(binder(), getProperties());
    }

    private Properties getProperties() {
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

                return properties;
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
