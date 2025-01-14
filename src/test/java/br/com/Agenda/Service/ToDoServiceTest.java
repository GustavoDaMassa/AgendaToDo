package br.com.Agenda.Service;

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
import org.springframework.test.context.ActiveProfiles;

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

        var task = new ToDo("Task","test",true,0);

        toDoService.create(task);
        Mockito.verify(toDoRepository, Mockito.times(1)).save(task);

    }
    @Test
    @DisplayName("Return an exception")
    void createToDoFailure(){


    }

    @Test
    void show() {
    }
}