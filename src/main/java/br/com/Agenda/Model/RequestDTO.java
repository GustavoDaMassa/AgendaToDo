package br.com.Agenda.Model;

public record RequestDTO(
        String name,
        String description,
        int priority
) {
}
