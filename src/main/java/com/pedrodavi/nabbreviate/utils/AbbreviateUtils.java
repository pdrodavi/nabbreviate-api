package com.pedrodavi.nabbreviate.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AbbreviateUtils {

    // Limite de caracteres para aplicar abreviação
    private static final int LIMITE_CARACTERES = 15;

    // Tamanho máximo para manter palavra sem abreviação
    private static final int TAMANHO_MAXIMO_SEM_ABREVIAR = 4;

    // Conjunto de preposições comuns em nomes portugueses
    private static final Set<String> PREPOSICOES = new HashSet<>(Arrays.asList(
            "DE", "DA", "DO", "DAS", "DOS", "E", "A", "O", "EM", "NA", "NO", "NAS", "NOS",
            "POR", "PARA", "COM", "SEM", "SOB", "SOBRE"
    ));

    /**
     * Abrevia um nome completo se seu tamanho (incluindo espaços) for superior ao limite.
     *
     * @param nomeCompleto Nome completo a ser abreviado
     * @return Nome abreviado conforme as regras, ou o original se não atender aos critérios
     */
    public static String shortName(String nomeCompleto) {
        // Validação de entrada
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) {
            return nomeCompleto;
        }

        String nomeTrim = nomeCompleto.trim();

        // Se o nome não ultrapassar o limite, retorna o original
        if (nomeTrim.length() <= LIMITE_CARACTERES) {
            return nomeCompleto;
        }

        // Divide o nome em palavras (ignorando espaços múltiplos)
        String[] palavras = nomeTrim.split("\\s+");

        // Se houver apenas uma palavra, retorna o original
        if (palavras.length <= 1) {
            return nomeCompleto;
        }

        // Converte palavras para maiúsculas para comparação de preposições
        String[] palavrasMaiusculas = new String[palavras.length];
        for (int i = 0; i < palavras.length; i++) {
            palavrasMaiusculas[i] = palavras[i].toUpperCase();
        }

        // Encontra o índice da última preposição
        int indiceUltimaPreposicao = encontrarUltimaPreposicao(palavrasMaiusculas);

        // Tratamento especial para nomes com exatamente 4 palavras
        if (palavras.length == 4) {
            return tratarNomeQuatroPalavras(palavras, palavrasMaiusculas, indiceUltimaPreposicao);
        }

        // Abreviação padrão para outros casos
        return abreviarNomePadrao(palavras, palavrasMaiusculas, indiceUltimaPreposicao);
    }

    /**
     * Encontra o índice da última preposição no array de palavras.
     *
     * @param palavrasMaiusculas Array de palavras em maiúsculas
     * @return Índice da última preposição ou -1 se não encontrada
     */
    private static int encontrarUltimaPreposicao(String[] palavrasMaiusculas) {
        for (int i = palavrasMaiusculas.length - 1; i >= 0; i--) {
            if (PREPOSICOES.contains(palavrasMaiusculas[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Tratamento especial para nomes com exatamente 4 palavras.
     * Mantém a lógica original mas integrada com o novo sistema de preposições.
     */
    private static String tratarNomeQuatroPalavras(String[] palavras, String[] palavrasMaiusculas,
                                                   int indiceUltimaPreposicao) {
        // Se a última preposição está na posição 2 (terceira palavra)
        if (indiceUltimaPreposicao == 2) {
            return palavras[0] + " " +
                    palavras[1].substring(0, 1).toUpperCase() + ". " +
                    palavras[2] + " " +
                    palavras[3];
        }

        // Se a última preposição está em outra posição ou não existe
        return palavras[0] + " " +
                palavras[1] + " " +
                (PREPOSICOES.contains(palavrasMaiusculas[2]) ? "" :
                        palavras[2].substring(0, 1).toUpperCase() + ". ") +
                palavras[3];
    }

    /**
     * Abreviação padrão para nomes com qualquer número de palavras.
     * Mantém primeiro nome, abrevia os nomes do meio (respeitando preposições e nomes curtos),
     * e preserva a última preposição e o último sobrenome.
     */
    private static String abreviarNomePadrao(String[] palavras, String[] palavrasMaiusculas,
                                             int indiceUltimaPreposicao) {
        StringBuilder resultado = new StringBuilder();

        // Primeiro nome sempre é mantido
        resultado.append(palavras[0]);

        // Processa as palavras do meio
        for (int i = 1; i < palavras.length - 1; i++) {
            String palavraOriginal = palavras[i];
            String palavraMaiuscula = palavrasMaiusculas[i];

            // Se é a última preposição, mantém como está
            if (i == indiceUltimaPreposicao) {
                resultado.append(" ").append(palavraOriginal);
            }
            // Se é preposição mas não é a última, ignora
            else if (PREPOSICOES.contains(palavraMaiuscula)) {
                continue;
            }
            // Se é um nome, abrevia conforme o tamanho
            else {
                resultado.append(" ").append(formatarNomeIntermediario(palavraOriginal));
            }
        }

        // Último sobrenome (se não for preposição)
        String ultimoNome = palavras[palavras.length - 1];
        if (!PREPOSICOES.contains(palavrasMaiusculas[palavras.length - 1])) {
            resultado.append(" ").append(ultimoNome);
        }

        return resultado.toString();
    }

    /**
     * Formata um nome intermediário baseado em seu tamanho:
     * - Nomes com até TAMANHO_MAXIMO_SEM_ABREVIAR caracteres são mantidos
     * - Nomes maiores são reduzidos à inicial + ponto
     */
    private static String formatarNomeIntermediario(String nome) {
        if (nome.length() <= TAMANHO_MAXIMO_SEM_ABREVIAR) {
            return nome;
        }
        return nome.substring(0, 1).toUpperCase() + ".";
    }

    /**
     * Versão alternativa usando Stream API (mantida do código original).
     * Pode ser usada como alternativa ao método principal.
     */
    public static String shortNameStream(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty() || nomeCompleto.length() <= LIMITE_CARACTERES) {
            return nomeCompleto;
        }

        String[] palavras = nomeCompleto.trim().split("\\s+");

        if (palavras.length <= 1) {
            return nomeCompleto;
        }

        String[] palavrasMaiusculas = Arrays.stream(palavras)
                .map(String::toUpperCase)
                .toArray(String[]::new);

        int indiceUltimaPreposicao = encontrarUltimaPreposicao(palavrasMaiusculas);

        return palavras[0] + " " +
                Arrays.stream(palavras, 1, palavras.length - 1)
                        .filter(p -> !PREPOSICOES.contains(p.toUpperCase()) ||
                                Arrays.asList(palavrasMaiusculas).indexOf(p.toUpperCase()) == indiceUltimaPreposicao)
                        .map(p -> p.length() <= TAMANHO_MAXIMO_SEM_ABREVIAR &&
                                !PREPOSICOES.contains(p.toUpperCase()) ? p : p.substring(0, 1).toUpperCase() + ".")
                        .collect(Collectors.joining(" ")) +
                (palavrasMaiusculas[palavras.length - 1].equals(palavrasMaiusculas[indiceUltimaPreposicao]) ?
                        "" : " " + palavras[palavras.length - 1]);
    }

}