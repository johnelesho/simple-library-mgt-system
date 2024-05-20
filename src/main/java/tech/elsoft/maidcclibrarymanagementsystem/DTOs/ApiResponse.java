package tech.elsoft.maidcclibrarymanagementsystem.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class ApiResponse implements Serializable  {
       private  LocalDateTime timestamp;
       private  Object data;
       private  boolean isSuccess;
       private  boolean isError;
       private  String description;


    public ApiResponse(Object data){
        this(LocalDateTime.now(), data, true, false, "Operation Completed Successfully");
    }
    public ApiResponse(String successDescription, Object data){
        this(LocalDateTime.now(), data, true, false, successDescription);
    }

    public ApiResponse(  Object data, String description){
        this(LocalDateTime.now(), data, false, true, description);
    }
    public ApiResponse(   String description){
        this(LocalDateTime.now(), null, false, true, description);
    }

    public ApiResponse() {
        this(LocalDateTime.now(), null, true, false, "Operation Completed Successfully");

    }
}
