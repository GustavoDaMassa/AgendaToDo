package br.com.Agenda.Service;

import br.com.Agenda.Model.ToDo;
import br.com.Agenda.Repository.ToDoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<ToDo> Update(Long id, ToDo task){
        ToDo newTask = toDoRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Tarefa com ID " + id + " não encontrada"));
        newTask.setName(task.getName());
        newTask.setDescription(task.getDescription());
        newTask.setDone(task.isDone());
        newTask.setPriority(task.getPriority());
        toDoRepository.save(newTask);
        return show();
    }

    public  List<ToDo> delete(long id){
        toDoRepository.deleteById(id);
        return show();
    }
}
