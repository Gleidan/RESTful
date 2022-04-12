package org.school21.restful.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorDto {

    private Integer status;
    private String message;
}
