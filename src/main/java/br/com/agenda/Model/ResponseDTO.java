package br.com.Agenda.Model;

public record ResponseDTO(
        Long id,
        String name,
        String description,
        Boolean done,
        int priority
) {
}
