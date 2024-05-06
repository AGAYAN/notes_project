package org.example.project_notes.enumType;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    TITLE_IS_EMPTY("Title is empty"),
    // Другие константы ошибок, если необходимо
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
