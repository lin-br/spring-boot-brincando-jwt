package xyz.tilmais.brincandojwt.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Objeto responsável por representar erros de validação de dados e/ou,
 * campos enviados por requisições ao servidor.
 *
 * @author Wesley Ribeiro
 * @since 1.0.0
 */
public class AppExceptionModel {

    private Date timestamp;
    private String message;
    private List<String> fields = new ArrayList<>();

    public AppExceptionModel(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addFieldError(String field) {
        fields.add(field);
    }

    public List<String> getFields() {
        return fields;
    }
}
