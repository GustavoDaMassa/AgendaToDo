package br.com.Agenda.Controller;

import br.com.Agenda.Model.ToDo;
import br.com.Agenda.Service.ToDoService;
import org.springframework.http.HttpStatus;
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
    ResponseEntity<ToDo> create(@RequestBody ToDo task){
        return new ResponseEntity<>(toDoService.create(task), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<ToDo>> show(){
        return ResponseEntity.ok(toDoService.show());
    }

    @PutMapping("/{id}")
     ResponseEntity<?> Update(@PathVariable Long id ,@RequestBody ToDo task){
        try {
            return ResponseEntity.ok(toDoService.update(id, task));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id){
        try {
            return ResponseEntity.ok(toDoService.delete(id));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());        }
    }
}
