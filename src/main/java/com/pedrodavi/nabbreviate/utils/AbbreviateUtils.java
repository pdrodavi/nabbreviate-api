package com.pedrodavi.nabbreviate.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AbbreviateUtils {

    public static String shortName(String name) {

        String nomeFormatado = "";

        if (name.length() > 27) {

            List<String> preposicoes = new ArrayList<>();
            preposicoes.add("DE");
            preposicoes.add("DA");
            preposicoes.add("DAS");
            preposicoes.add("DOS");
            String[] nomes = name.toUpperCase().split(" ");

            if (nomes.length < 2) {
                return name;
            }

            if (nomes.length == 4) {

                boolean isPreposicao = isPreposicaoSubName(nomes, preposicoes);

                if (isPreposicao) nomeFormatado = tratSubNameComPrepo(nomes, preposicoes);
                else nomeFormatado = nomes[0]
                        + " " + nomes[1] + " "
                        + Arrays.stream(nomes)
                        .limit(nomes.length - 1)
                        .skip(2)
                        .filter(nome -> !preposicoes.contains(nome))
                        .map(nome -> nome.substring(0, 1) + ". ")
                        .collect(Collectors.joining())
                        + nomes[nomes.length - 1];

            } else {

                nomeFormatado = nomes[0]
                        + " " + nomes[1] + " "
                        + Arrays.stream(nomes)
                        .limit(nomes.length - 1)
                        .skip(2)
                        .filter(nome -> !preposicoes.contains(nome))
                        .map(AbbreviateUtils::verifyLenghtName)
                        .collect(Collectors.joining())
                        + nomes[nomes.length - 1];
            }

            return nomeFormatado;
        }

        return name;
    }

    private static boolean isPreposicaoSubName(String[] nomes, List<String> preposicoes) {

        String parte = nomes[nomes.length - 2].toUpperCase();
        boolean isPreposicao = false;

        for (String prep : preposicoes) {
            if (parte.equals(prep)) {
                isPreposicao = true;
                break;
            }
        }

        return isPreposicao;
    }

    private static String tratSubNameComPrepo(String[] nomes, List<String> preposicoes) {
        return nomes[0]
                + " "
                + Arrays.stream(nomes)
                .limit(nomes.length - 1)
                .skip(1)
                .filter(nome -> !preposicoes.contains(nome))
                .map(nome -> nome.substring(0, 1) + ". ")
                .collect(Collectors.joining())
                + nomes[nomes.length - 1];
    }

    private static String verifyLenghtName(String name) {
        if (name.length() <= 4) return name + " ";
        return name.substring(0, 1) + ". ";
    }

}
