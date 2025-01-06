package com.b2c.prototype.service.supplier;

import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SupplierServiceTest {

    @Mock
    private IParameterFactory parameterFactory;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private SupplierService supplierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEntityFieldSupplier_WithFieldExtractor() {
        // Arrange
        Class<String> entityClass = String.class;
        Supplier<Parameter> parameterSupplier = () -> mock(Parameter.class);
        String entity = "mockEntity";
        String extractedField = "mockField";
        Function<String, String> fieldExtractor = mock(Function.class);

        when(queryService.getEntity(entityClass, parameterSupplier)).thenReturn(entity);
        when(fieldExtractor.apply(entity)).thenReturn(extractedField);

        
        Supplier<String> resultSupplier = supplierService.entityFieldSupplier(entityClass, parameterSupplier, fieldExtractor);

        
        assertEquals(extractedField, resultSupplier.get());
        verify(queryService).getEntity(entityClass, parameterSupplier);
        verify(fieldExtractor).apply(entity);
    }

    @Test
    void testEntityFieldSupplier_WithoutFieldExtractor() {
        Class<String> entityClass = String.class;
        Supplier<Parameter> parameterSupplier = () -> mock(Parameter.class);
        String entity = "mockEntity";

        when(queryService.getEntity(entityClass, parameterSupplier)).thenReturn(entity);

        Supplier<String> resultSupplier = supplierService.entityFieldSupplier(entityClass, parameterSupplier);

        
        assertEquals(entity, resultSupplier.get());
        verify(queryService).getEntity(entityClass, parameterSupplier);
    }

    @Test
    void testGetSupplier_WithNoSol() {
        // Arrange
        String dataEntity = "mockDataEntity";
        Class<String> classTo = String.class;
        String transformedEntity = "transformedEntity";

        when(transformationFunctionService.getEntity(classTo, dataEntity, null)).thenReturn(transformedEntity);

        
        Supplier<String> resultSupplier = supplierService.getSupplier(classTo, dataEntity);

        
        assertEquals(transformedEntity, resultSupplier.get());
        verify(transformationFunctionService).getEntity(classTo, dataEntity, null);
    }

    @Test
    void testGetSupplier_WithSol() {
        // Arrange
        String dataEntity = "mockDataEntity";
        Class<String> classTo = String.class;
        String sol = "mockSol";
        String transformedEntity = "transformedEntity";

        when(transformationFunctionService.getEntity(classTo, dataEntity, sol)).thenReturn(transformedEntity);

        
        Supplier<String> resultSupplier = supplierService.getSupplier(classTo, dataEntity, sol);

        
        assertEquals(transformedEntity, resultSupplier.get());
        verify(transformationFunctionService).getEntity(classTo, dataEntity, sol);
    }

    @Test
    void testParameterStringSupplier() {
        // Arrange
        String key = "mockKey";
        String value = "mockValue";
        Parameter parameter = mock(Parameter.class);

        when(parameterFactory.createStringParameter(key, value)).thenReturn(parameter);

        
        Supplier<Parameter> resultSupplier = supplierService.parameterStringSupplier(key, value);

        
        assertEquals(parameter, resultSupplier.get());
        verify(parameterFactory).createStringParameter(key, value);
    }
}
