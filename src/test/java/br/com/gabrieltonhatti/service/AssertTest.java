package br.com.gabrieltonhatti.service;

import br.com.gabrieltonhatti.entidades.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AssertTest {

	@Test
	public void test() {
		Assertions.assertTrue(true);
		Assertions.assertFalse(false);

		Assertions.assertEquals(1, 1);
		Assertions.assertEquals(0.51234, 0.512, 0.001);
		Assertions.assertEquals(Math.PI, 3.14, 0.01);

		int i = 5;
		Integer i2 = 5;
		Assertions.assertEquals(Integer.valueOf(i), i2);
		Assertions.assertEquals(i, i2.intValue());

		Assertions.assertEquals("bola", "bola");
		Assertions.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assertions.assertTrue("bola".startsWith("bo"));
		
		Usuario u1 = new Usuario("Usuário 1");
		Usuario u2 = new Usuario("Usuário 1");
		Usuario u3 = u2;
		
		Assertions.assertEquals(u1, u2);
		
		Assertions.assertSame(u3, u2);
	}

}
