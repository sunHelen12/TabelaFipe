package br.com.code.TabelaFipe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelos(List<Dados>modelos) {

}
