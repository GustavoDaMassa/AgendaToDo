package br.com.Agenda.Service;

import br.com.Agenda.Model.RequestDTO;
import br.com.Agenda.Model.ToDo;
import br.com.Agenda.Repository.ToDoRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;

    @Autowired
    @InjectMocks
    private ToDoService toDoService;

    @Test
    @DisplayName(" task created and save ")
    void createToDoSucsess() {

        var taskDTO = new RequestDTO("Task","test",0);
        var task = new ToDo(taskDTO.name(), taskDTO.description(), true, taskDTO.priority());

        Mockito.when(toDoRepository.save(Mockito.any(ToDo.class))).thenReturn(task);
        ToDo toDo = toDoService.create(taskDTO);

        Mockito.verify(toDoRepository, Mockito.times(1)).save(Mockito.any(ToDo.class));
        assertEquals(task.getName(), toDo.getName());
        assertEquals(task.getDescription(), toDo.getDescription());
        assertEquals(task.getPriority(), toDo.getPriority());
        assertTrue(toDo.isDone());

    }
    @Test
    @DisplayName("Return an exception when the name isn't provided")
    void createToDoFailure() throws Exception{

        var taskDTO = new RequestDTO("","test",-1);
        var task = new ToDo(taskDTO.name(), taskDTO.description(), true, taskDTO.priority());


        Mockito.when(toDoRepository.save(Mockito.any(ToDo.class))).thenThrow( new IllegalArgumentException("Invalid task data"));

        assertThrows(IllegalArgumentException.class, () -> toDoService.create(taskDTO));
        Mockito.verify(toDoRepository, Mockito.times(1)).save(Mockito.any(ToDo.class));

    }

    @Test
    @DisplayName("Task visualization working correctly")
    void showToDoSuccess() {

        var task1 = new ToDo("task1","test",false, 4);
        var task2 = new ToDo("task2","test",false, 8);
        var task3 = new ToDo("task3","test",false, 8);

        List<ToDo> tasks = Arrays.asList(task1,task2,task3);
        tasks.sort(Comparator.comparingInt(ToDo::getPriority).reversed().thenComparing(ToDo::getName));

        Sort sorted = Sort.by("priority").descending().and(Sort.by("name").ascending());
        Mockito.when(toDoRepository.findAll(sorted)).thenReturn(tasks);

        List<ToDo> show = toDoService.show();

        assertNotNull(show);
        assertEquals(3,show.size());
        assertEquals("task2",show.get(0).getName());
        assertEquals("task3",show.get(1).getName());
        assertEquals("task1",show.get(2).getName());

        Mockito.verify(toDoRepository, Mockito.times(1)).findAll(sorted);

    }

    @Test
    @DisplayName("Task Updated successfully")
    void updateTaskSuccess(){

    }
}
