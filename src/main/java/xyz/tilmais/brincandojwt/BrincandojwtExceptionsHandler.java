package xyz.tilmais.brincandojwt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.tilmais.brincandojwt.models.AppExceptionModel;

import java.util.Date;

@ControllerAdvice
public class BrincandojwtExceptionsHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return getParametersValidation(ex.getBindingResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity handlerBindException(BindException ex) {
        return getParametersValidation(ex.getBindingResult());
    }

    private ResponseEntity getParametersValidation(BindingResult bindingResult) {

        /*
         * Processo que monta o model AppException para
         * ser retornado como Body HTTP no response para o cliente HTTP.
         */
        AppExceptionModel appException = new AppExceptionModel(new Date(), bindingResult
                .getAllErrors()
                .get(0)
                .getDefaultMessage()
        );

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            appException.addFieldError(fieldError.getField());
        }

        return ResponseEntity.badRequest().body(appException);
    }
}
