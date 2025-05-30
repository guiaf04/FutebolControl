package model;

public record Partida (
        int id,
        Clube mandante,
        Clube visitante,
        String data,
        String resultado,
        int golsMandante,
        int golsVisitante
){}
