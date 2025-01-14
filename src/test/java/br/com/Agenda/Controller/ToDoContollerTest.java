package br.com.Agenda.Controller;

import br.com.Agenda.Model.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ToDoContollerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Return Task Successfully")
    void testcreateTaskSuccess() {

        var task = new ToDo("task","test",true,0);

        ResponseEntity<ToDo> toDoResponseEntity = testRestTemplate.postForEntity("/tasks", task, ToDo.class);

        assertEquals(HttpStatus.CREATED,toDoResponseEntity.getStatusCode());
        assertNotNull(toDoResponseEntity.getBody());
        assertEquals("task",toDoResponseEntity.getBody().getName());
        assertEquals("test",toDoResponseEntity.getBody().getDescription());
        assertEquals(0,toDoResponseEntity.getBody().getPriority());
        assertTrue(toDoResponseEntity.getBody().isDone());
    }

    @Test
    @DisplayName("Returns an exception when task created has null name")
    void testcreateTaskFailure(){

        var task =new ToDo("","test",false,0);

        testRestTemplate.postForEntity("/tasks", task, ToDo.class);



    }
}