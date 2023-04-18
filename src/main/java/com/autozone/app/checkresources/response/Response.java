package com.autozone.app.checkresources.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Response {

    private HttpStatus status;
    private Object response;
    private String details;
    private LocalDateTime time;

    public Response(HttpStatus status, Object response, String details, LocalDateTime time) {
        this.status = status;
        this.response = response;
        this.details = details;
        this.time = time;
    }
}
