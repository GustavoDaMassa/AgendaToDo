package br.com.Agenda.Controller;

import br.com.Agenda.Model.RequestDTO;
import br.com.Agenda.Model.ResponseDTO;
import br.com.Agenda.Model.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ToDoControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Return Task created Successfully")
    void testCreateTaskSuccess() {

        var taskDTO= new RequestDTO("task","test",0);


        ResponseEntity<ToDo> toDoResponseEntity = testRestTemplate.postForEntity("/tasks", taskDTO, ToDo.class);

        assertEquals(HttpStatus.CREATED,toDoResponseEntity.getStatusCode());
        assertNotNull(toDoResponseEntity.getBody());
        assertEquals("task",toDoResponseEntity.getBody().getName());
        assertEquals("test",toDoResponseEntity.getBody().getDescription());
        assertEquals(0,toDoResponseEntity.getBody().getPriority());
        assertFalse(toDoResponseEntity.getBody().isDone());
    }

    @Test
    @DisplayName("Returns an exception when task created has null name")
    void testCreateTaskFailure() throws Exception {

        var taskDTO= new RequestDTO("","test",11);

        ResponseEntity<ToDo> toDoResponseEntity = testRestTemplate.postForEntity("/tasks", taskDTO, ToDo.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,toDoResponseEntity.getStatusCode());

    }

    @Test
    @DisplayName("Return all tasks")
    void testShowTaskSuccess() {

        var task1 = new ToDo("task1", "test", false, 9);
        var task2 = new ToDo("task2", "test", false, 9);

        testRestTemplate.postForEntity("/tasks", task1, ToDo.class);
        testRestTemplate.postForEntity("/tasks", task2, ToDo.class);

        ResponseEntity<ToDo[]> forEntity = testRestTemplate.getForEntity("/tasks", ToDo[].class);

        assertEquals(HttpStatus.OK,forEntity.getStatusCode());
        assertNotNull(forEntity.getBody());
        assertTrue(forEntity.getBody().length >= 2);

        ToDo[] tasks = forEntity.getBody();

        assertEquals("task1",tasks[0].getName());
        assertEquals("test",tasks[0].getDescription());
        assertEquals(9,tasks[0].getPriority());
        assertFalse(tasks[0].isDone());


        assertEquals("task2", tasks[1].getName());
        assertEquals("test", tasks[1].getDescription());
        assertEquals(9, tasks[1].getPriority());
        assertFalse(tasks[1].isDone());
    }

    @Test
    @DisplayName(" Task updated successfully")
    void testUpdateTaskSuccess(){

        var oldTask = new RequestDTO("task", "test", 0);
        ResponseEntity<ResponseDTO> toDoResponseEntity = testRestTemplate.postForEntity("/tasks", oldTask, ResponseDTO.class);

        var newTask = new ToDo("new task", "new test", true, 0);

        ResponseEntity<ToDo[]> exchange = testRestTemplate.exchange("/tasks/" + toDoResponseEntity.getBody().id(), HttpMethod.PUT, new HttpEntity<>(newTask), ToDo[].class);

        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertNotNull(exchange.getBody());

        // estudar stream para recuperar a nova task com o index correspondente à task antiga com o mesmo id .
//       // List<ToDo> tasksUpdate = Arrays.asList(exchange.getBody());
//
//        assertEquals("new task", tasksUpdate.get(toDoResponseEntity.getBody().getId()));
//        assertEquals("new test", exchange.getBody().getDescription());
//        assertEquals(0, exchange.getBody().getPriority());
//        assertTrue(exchange.getBody().isDone());

    }

    @Test
    @DisplayName("Return IllegalArgumentException when the id of the task to be updated is non-existent")
    void testUpdateTaskFailure(){

        var newTask = new ToDo("new task", "new test", true, 0);
        var nonExistentId = -1L;

        ResponseEntity<String> exchange= testRestTemplate.exchange("/tasks/" + nonExistentId, HttpMethod.PUT, new HttpEntity<>(newTask), String.class);

        assertEquals(HttpStatus.NOT_FOUND, exchange.getStatusCode());
        assertNotNull(exchange.getBody());


    }

    @Test
    @DisplayName("Returns an exception when new task has invalid data")
    void testUpdateTaskInvalidData() throws Exception {

        var oldTask = new ToDo("task", "test", false, 0);
        ResponseEntity<ToDo> toDoResponseEntity = testRestTemplate.postForEntity("/tasks", oldTask, ToDo.class);

        var newTask = new ToDo("", "new test", true, 0);

        ResponseEntity<String> exchange = testRestTemplate.exchange("/tasks/" + toDoResponseEntity.getBody().getId(), HttpMethod.PUT, new HttpEntity<>(newTask), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, exchange.getStatusCode());
        assertNotNull(exchange.getBody());
    }


}