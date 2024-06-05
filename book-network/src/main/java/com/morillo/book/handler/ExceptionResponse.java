package com.morillo.book.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)             //  en Jackson es utilizada para excluir valores que están vacíos en la serialización de objetos a JSON
public class ExceptionResponse {

    private Integer businessErrorCode;                  // Tendra un enum para manajer los posibles errores de codigo(numeros) que podra tener algun endpoint
    private String businessErrorDescription;
    private String error;
    private Set<String> validationErrors;               // Tendra la lista de los errores de validacion
    private Map<String, String> errors;
}
