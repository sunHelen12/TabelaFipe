package br.com.code.TabelaFipe.principal;

import java.util.List;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.code.TabelaFipe.model.Dados;
import br.com.code.TabelaFipe.model.Modelos;
import br.com.code.TabelaFipe.service.ConsumoApi;
import br.com.code.TabelaFipe.service.ConverteDados;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){
        var menu = """
                =============OPÇÕES=============
                CARRO
                MOTO
                CAMINHÃO

                Digite uma das opções para consultar: 
                """;

        System.out.print(menu);
        var opcao = sc.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("carro")){
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("moto")){
            endereco = URL_BASE + "motos/marcas";
        }else{
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
            .sorted (Comparator.comparing (Dados::codigo))
            .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta:");
        var codigoMarca = sc.nextLine();
            
        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
            .sorted(Comparator.comparing(Dados::codigo))
            .forEach(System.out::println);

        System.out.println("\nDigite pelo menos um trecho do nome do veículo a ser buscado: ");
        var nomeVeiculo = sc.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
            .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
            .collect(Collectors.toList());
        System.out.println("\nModelos Filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo para buscar os valores de avaliação: ");
        var codigoModelo = sc.nextLine();
        endereco = endereco + "/" + codigoModelo + "/anos";
        List<Dados> anos =  conversor.obterLista(json, Dados.class);

        
    }
}
