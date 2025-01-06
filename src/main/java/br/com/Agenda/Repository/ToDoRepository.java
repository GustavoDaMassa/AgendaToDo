package br.com.Agenda.Repository;

import br.com.Agenda.Model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
