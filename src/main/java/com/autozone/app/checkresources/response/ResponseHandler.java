package com.autozone.app.checkresources.response;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ResponseHandler {

    public static ResponseEntity<Response> response(Object rps, String message){
        Response resp = new Response(HttpStatus.OK, rps, message, LocalDateTime.now());

        return new ResponseEntity<>(resp, null, HttpStatus.OK);
    }


}
