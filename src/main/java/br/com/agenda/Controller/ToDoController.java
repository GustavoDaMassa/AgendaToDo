package br.com.Agenda.Controller;

import br.com.Agenda.Model.RequestDTO;
import br.com.Agenda.Model.ResponseDTO;
import br.com.Agenda.Model.ToDo;
import br.com.Agenda.Service.ToDoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Agenda")
public class ToDoController {

    private final TodoResponseMapper todoResponseMapper;
    private final ToDoService toDoService;

    public ToDoController(TodoResponseMapper todoResponseMapper, ToDoService toDoService) {
        this.todoResponseMapper = todoResponseMapper;
        this.toDoService = toDoService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar tarefa", description = "Cria uma nova tarefa com nome, descrição, status e prioridade.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição invalidos")
    })
    ResponseEntity<ResponseDTO> create(@RequestBody @Valid RequestDTO taskDTO){
        ResponseDTO taskCreated = todoResponseMapper.toDTOwithID(toDoService.create(taskDTO));
        return  ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping
    @Operation(summary = "Mostrar todas as tarefas",description = "Exibe todas as tarefas ordenadas pelo prioridade.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todas as tarefas retorndas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    ResponseEntity<List<ToDo>> show(){
        return ResponseEntity.ok(toDoService.show());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tarefa", description = "Encontra a tarefa pelo id informado e substitue suas informações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição invalidos"),
            @ApiResponse(responseCode = "404", description = "Id não encontrado")
    })
     ResponseEntity<?> Update(@Parameter(description = "Id da tarefa que deve ser atualizada") @PathVariable Long id ,@RequestBody @Valid ToDo task){
        try {
            return ResponseEntity.ok(toDoService.update(id, task));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tarefa", description = "Remove a tarefa correspondente ao id informado do banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Id não encontrado")
    })
    ResponseEntity<?> delete(@Parameter(description = "Id da tarefa que deve ser removida") @PathVariable Long id){
        try {
            return ResponseEntity.ok(toDoService.delete(id));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());        }
    }
}
