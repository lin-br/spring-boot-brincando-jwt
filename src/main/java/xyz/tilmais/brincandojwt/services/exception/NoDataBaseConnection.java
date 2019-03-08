package xyz.tilmais.brincandojwt.services.exception;

import java.sql.SQLException;

/**
 * Classe responsável por retratar um erro que ocorreu no momento de
 * realizar a conexão com um banco de dados.
 *
 * @author Wesley Ribeiro
 * @since 1.0.0
 */
public class NoDataBaseConnection extends SQLException {

    private static final long serialVersionUID = -8756003337416363203L;

    public NoDataBaseConnection(String reason, Throwable cause) {
        super(reason, cause);
    }
}
