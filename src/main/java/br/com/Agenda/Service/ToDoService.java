package br.com.Agenda.Service;

import br.com.Agenda.Model.ToDo;
import br.com.Agenda.Repository.ToDoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository){
        this.toDoRepository = toDoRepository;
    }

    public ToDo create(ToDo task){
        return toDoRepository.save(task);
    }

    public List<ToDo> show(){
        Sort sorted = Sort.by("priority").descending().and(Sort.by("name").ascending());
        return toDoRepository.findAll(sorted);
    }

    public List<ToDo> Update(ToDo task){
        toDoRepository.save(task);
        return show();
    }

    public  List<ToDo> delete(long id){
        toDoRepository.deleteById(id);
        return show();
    }
}
