package br.com.Agenda.Controller;

import br.com.Agenda.Model.ResponseDTO;
import br.com.Agenda.Model.ToDo;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;

@Component
public class TodoResponseMapper {

    public ResponseDTO toDTOwithID(ToDo task){
        return new ResponseDTO( task.getName(), task.getDescription(), task.isDone(), task.getPriority());
    }
}
