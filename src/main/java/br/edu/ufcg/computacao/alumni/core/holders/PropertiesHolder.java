package br.edu.ufcg.computacao.alumni.core.holders;

import br.edu.ufcg.computacao.alumni.constants.Messages;
import br.edu.ufcg.computacao.alumni.constants.SystemConstants;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHolder {
    private Logger LOGGER = Logger.getLogger(PropertiesHolder.class);

    public static final String PRIVATE_DIRECTORY = "private/";
    private Properties properties;
    private static PropertiesHolder instance;

    private PropertiesHolder() throws IOException {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + PRIVATE_DIRECTORY;
        this.properties = readProperties(path + SystemConstants.CONFIG_FILE);
    }

    public static synchronized PropertiesHolder getInstance() throws IOException {
        if (instance == null) {
            instance = new PropertiesHolder();
        }
        return instance;
    }

    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    public String getProperty(String propertyName, String defaultPropertyValue) {
        String propertyValue = this.properties.getProperty(propertyName, defaultPropertyValue);
        if (propertyValue.trim().isEmpty()) {
            propertyValue = defaultPropertyValue;
        }
        return propertyValue;
    }

    public Properties getProperties() {
        return this.properties;
    }

    private Properties readProperties(String fileName) throws IOException {
        Properties prop = new Properties();
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(fileName);
            prop.load(fileInputStream);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    LOGGER.error(String.format(Messages.UNABLE_TO_CLOSE_FILE_S, fileName), e);
                }
            }
        }
        return prop;
    }
}