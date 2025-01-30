package br.com.Agenda.Model;

public record ResponseDTO(
        String name,
        String description,
        Boolean done,
        int priority
) {
}
