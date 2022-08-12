package cl.patinaje.orion.cloud.ms.tests;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import cl.patinaje.orion.cloud.ms.models.entity.CuestionarioSalud;

public class TestCuestionarioSalud {

	
	@Test
	 public void testMetodos() {
		/*
		Method[] metodos = CuestionarioSalud.class.getMethods();
		List listaMetodos = Arrays.asList(metodos);
		
		
		List<Method> listaMetodos2 = new ArrayList<Method>();
		listaMetodos2.addAll(listaMetodos); */
		
		/*
		List<Method> listaMetodos3 = listaMetodos2.stream().filter(metodo ->  
			metodo.getName().startsWith("set")
		).collect(Collectors.toList()); */



		/*
		for (Method object : listaMetodos3) {
			System.out.println("cuestionarioDb." + object.getName() + "(" + "cuestionario." + object.getName().replaceFirst("set", "get") + "()" + ");"); 
		} */
		
		String resultado = "AlumnoServiceImpl$$EnhancerBySpringCGLIB$$b3fcbd4e".split("$")[0];
		
		
		
		StringTokenizer st = new StringTokenizer(resultado, "$");		
		System.out.println("resultado: " + st.nextToken());
				
		Assertions.assertNull(null);
	}
}
