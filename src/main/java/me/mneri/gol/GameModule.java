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
import com.google.inject.name.Names;
import lombok.SneakyThrows;
import me.mneri.gol.data.converter.ColorTypeConverter;
import me.mneri.gol.data.converter.SubclassMatcher;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Module defining the binding for Guice.
 *
 * @author Massimo Neri
 */
public final class GameModule extends AbstractModule {
    /**
     * Properties file name.
     */
    private static final String PROPERTIES_FILE_NAME = "application.properties";

    /**
     * Configure the Guice bindings.
     */
    @Override
    @SneakyThrows(IOException.class)
    protected void configure() {
        binder().convertToTypes(new SubclassMatcher(Color.class), new ColorTypeConverter());
        Names.bindProperties(binder(), getProperties());
    }

    /**
     * Return a new {@link Properties} instance loaded with the content of the {@code application.properties} file.
     *
     * @return A new properties instance.
     * @throws IOException If an I/O error occurs.
     */
    private Properties getProperties() throws IOException {
        try (InputStream input = getResourceAsStream(PROPERTIES_FILE_NAME)) {
            if (input == null) {
                throw new FileNotFoundException(PROPERTIES_FILE_NAME + " not found in the classpath.");
            }

            Properties properties = new Properties();
            properties.load(input);

            return properties;
        }
    }

    /**
     * Return a new {@link InputStream} associated with the specified resource.
     *
     * @param name The name of the resource.
     * @return A new input stream associated with the specified resource.
     */
    private InputStream getResourceAsStream(final String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }
}
