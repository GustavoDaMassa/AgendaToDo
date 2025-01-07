package br.com.Agenda;

import br.com.Agenda.Model.ToDo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgendaApplicationTests {

	@Test
	void testCreateToDoSuccess() {
		var task = new ToDo("Teste","usando o H2db",true, 5);


	}
	@Test
	void testCreateToDoFailure() {
	}
}
