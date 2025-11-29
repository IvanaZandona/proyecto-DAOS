package excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1-- MANEJO DE VALIDACIONES (DTOs vacíos, nulos, negativos, etc.)
	// Este método captura cuando fallan las anotaciones del DTO (@NotBlank, @Positive, etc.)
	// el profe usaba "ErrorAtributo" para este manejo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nombreCampo = ((FieldError) error).getField();
            String mensajeError = error.getDefaultMessage();
            errores.put(nombreCampo, mensajeError);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    // 2-- MANEJO DE EXCEPCIÓN PERSONALIZADA (clase Excepcion.java)
    // útil y necesario para atrapar errores que no sean de validación (como lógica de negocio)
    @ExceptionHandler(Excepcion.class)
    public ResponseEntity<Map<String, Object>> handleExcepcionPropia(Excepcion ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("entidad", ex.getEntidad());
        respuesta.put("mensaje", ex.getMessage());
        respuesta.put("codigo", ex.getCodigo());
        
        // Convertimos el código numérico (ej: 404) a un HttpStatus real
        return ResponseEntity.status(ex.getCodigo()).body(respuesta);
    }
    
  
}