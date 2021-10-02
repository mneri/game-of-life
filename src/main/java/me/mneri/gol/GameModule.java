/*
 * Copyright 2020 Massimo Neri <hello@mneri.me>
 *
 * This file is part of mneri/gol.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.mneri.gol;

import com.google.inject.AbstractModule;
import com.google.inject.CreationException;
import com.google.inject.name.Names;
import com.google.inject.spi.Message;
import me.mneri.gol.data.converter.ColorTypeConverter;
import me.mneri.gol.data.converter.SubclassMatcher;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Module defining the binding for Guice.
 *
 * @author Massimo Neri
 */
public final class GameModule extends AbstractModule {
    /**
     * Configure the Guice bindings.
     */
    @Override
    protected void configure() {
        binder().convertToTypes(new SubclassMatcher(Color.class), new ColorTypeConverter());
        Names.bindProperties(binder(), getProperties());
    }

    /**
     * Return a {@link Properties} instance for the {@code application.properties} file.
     *
     * @return A new properties instance.
     */
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
