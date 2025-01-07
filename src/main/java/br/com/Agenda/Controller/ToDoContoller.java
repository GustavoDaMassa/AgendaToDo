package br.com.Agenda.Controller;

import br.com.Agenda.Model.ToDo;
import br.com.Agenda.Service.ToDoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class ToDoContoller {

    private final ToDoService toDoService;

    public ToDoContoller (ToDoService toDoService){
        this.toDoService = toDoService;;
    }

    @PostMapping
    ToDo create(@RequestBody ToDo task){
        return toDoService.create(task);
    }

    @GetMapping
    List<ToDo> show(){
        return toDoService.show();
    }

    @PutMapping("{id}")
    List<ToDo> Update(@PathVariable Long id ,@RequestBody ToDo task){
        return toDoService.Update(id,task);
    }

    @DeleteMapping("{id}")
    List<ToDo> delete(@PathVariable Long id){
        return toDoService.delete(id);
    }
}
