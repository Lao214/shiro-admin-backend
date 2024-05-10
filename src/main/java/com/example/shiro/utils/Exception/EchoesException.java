package com.example.shiro.utils.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EchoesException extends RuntimeException{

    private Integer code;
    private String msg;

}
