package br.com.code.TabelaFipe.service;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados (String json, Class <T> classe); //converte dados de uma classe para outra classe

    <T> List<T> obterLista(String json, Class<T> classe);
}
