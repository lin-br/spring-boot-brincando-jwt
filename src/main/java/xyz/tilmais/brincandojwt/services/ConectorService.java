package xyz.tilmais.brincandojwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.tilmais.brincandojwt.services.exception.NoDataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe auxiliar, responsável por realizar a conexão com um banco de dados.
 *
 * @author Wesley Ribeiro
 * @since 1.0.0
 */
@Service
public class ConectorService {

    private Properties appProperties;

    @Autowired
    public ConectorService(Properties appProperties) {
        this.appProperties = appProperties;
    }

    public Connection getConnection() throws NoDataBaseConnection {
        try {
            return DriverManager.getConnection(
                    this.appProperties.getProperty("url"),
                    this.appProperties.getProperty("user"),
                    this.appProperties.getProperty("password")
            );
        } catch (SQLException e) {
            throw new NoDataBaseConnection("Não foi possível realizar conexão com a base de dados"
                    , e);
        }
    }
}