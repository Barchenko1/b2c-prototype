package com.b2c.prototype.service.function;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TransformationFunctionServiceTest {

    private TransformationFunctionService transformationFunctionService;

    @BeforeEach
    void setUp() {
        transformationFunctionService = new TransformationFunctionService();
    }

    @Test
    void testAddAndRetrieveTransformationFunction() {
        // Arrange
        Class<String> classFrom = String.class;
        Class<Integer> classTo = Integer.class;
        Function<String, Integer> transformationFunction = String::length;

        
        transformationFunctionService.addTransformationFunction(classFrom, classTo, transformationFunction);
        Function<String, Integer> retrievedFunction = transformationFunctionService.getTransformationFunction(classFrom, classTo);

        
        assertNotNull(retrievedFunction);
        assertEquals(5, retrievedFunction.apply("Hello"));
    }

    @Test
    void testGetEntityWithTransformationFunction() {
        // Arrange
        Class<String> classFrom = String.class;
        Class<Integer> classTo = Integer.class;
        Function<String, Integer> transformationFunction = String::length;
        String dataEntity = "Test";

        transformationFunctionService.addTransformationFunction(classFrom, classTo, transformationFunction);

        
        Integer result = transformationFunctionService.getEntity(classTo, dataEntity);

        
        assertNotNull(result);
        assertEquals(4, result);
    }

    @Test
    void testAddAndRetrieveTransformationFunctionWithSol() {
        // Arrange
        Class<String> classFrom = String.class;
        Class<Integer> classTo = Integer.class;
        String sol = "custom";
        Function<String, Integer> transformationFunction = String::length;

        
        transformationFunctionService.addTransformationFunction(classFrom, classTo, sol, transformationFunction);
        Function<String, Integer> retrievedFunction = transformationFunctionService.getTransformationFunction(classFrom, classTo, sol);

        
        assertNotNull(retrievedFunction);
        assertEquals(6, retrievedFunction.apply("Custom"));
    }

    @Test
    void testGetEntityWithTransformationFunctionAndSol() {
        // Arrange
        Class<String> classFrom = String.class;
        Class<Integer> classTo = Integer.class;
        String sol = "specific";
        Function<String, Integer> transformationFunction = String::length;
        String dataEntity = "Example";

        transformationFunctionService.addTransformationFunction(classFrom, classTo, sol, transformationFunction);

        
        Integer result = transformationFunctionService.getEntity(classTo, dataEntity, sol);

        
        assertNotNull(result);
        assertEquals(7, result);
    }

    @Test
    void testGetTransformationFunction_NotFound() {
        
        Function<String, Integer> retrievedFunction = transformationFunctionService.getTransformationFunction(String.class, Integer.class);

        
        assertNull(retrievedFunction);
    }
}
