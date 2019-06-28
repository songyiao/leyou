package com.leyou.common.exception;

import com.leyou.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LyException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
