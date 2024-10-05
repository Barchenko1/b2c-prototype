package com.b2c.prototype.dao.cached;

import com.b2c.prototype.dao.cashed.EntityCachedMap;
import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EntityCachedMapTest {

    @Mock
    private IEntityIdentifierDao entityIdentifierDao;

    @Mock
    private IParameterFactory parameterFactory;

//    @InjectMocks
    private EntityCachedMap entityCachedMap;
    private Map<Class<?>, Map<?, ?>> initEntityMap;
    private Map<Class<?>, Map<Object, Object>> classEntityMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        initEntityMap = new HashMap<>();
        classEntityMap = new HashMap<>();
        entityCachedMap = new EntityCachedMap(initEntityMap, entityIdentifierDao);
        try {
            Field entityIdentifierDaoField = EntityCachedMap.class.getDeclaredField("classEntityMap");
            entityIdentifierDaoField.setAccessible(true);
            entityIdentifierDaoField.set(entityCachedMap, classEntityMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetEntityMap_shouldReturnEntityMapIfPresent() {
        Map<Object, Object> entityMap = new HashMap<>();
        classEntityMap.put(String.class, entityMap);

        Map<Object, Object> result = entityCachedMap.getEntityMap(String.class);

        assertEquals(entityMap, result);
    }

    @Test
    void testGetOptionEntityFromMap_shouldReturnEntityFromMap() {
        Map<Object, Object> entityMap = new HashMap<>();
        String key = "1";
        String value = "value";
        Object entity = new Object();
        entityMap.put(value, entity);
        classEntityMap.put(Object.class, entityMap);

        Optional<Object> result = entityCachedMap.getOptionalEntity(Object.class, key, value);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(entityIdentifierDao, never()).getOptionalEntity(any(), any());
    }

    @Test
    void testGetEntityFromMap_shouldReturnEntityFromMap() {
        Map<Object, Object> entityMap = new HashMap<>();
        String key = "1";
        String value = "value";
        Object entity = new Object();
        entityMap.put(value, entity);
        classEntityMap.put(Object.class, entityMap);

        Object result = entityCachedMap.getEntity(Object.class, key, value);

        assertEquals(entity, result);
        verify(entityIdentifierDao, never()).getOptionalEntity(any(), any());
    }

    @Test
    void testGetEntityFromDbIfNotInMap_shouldRetrieveFromDbAndThrowIfNotFound() {
        Map<Object, Object> entityMap = new HashMap<>();
        classEntityMap.put(Object.class, entityMap);
        when(entityIdentifierDao.getOptionalEntity(eq(Object.class), any(Parameter.class)))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> entityCachedMap.getEntity(Object.class, "id", 1L));

        verify(entityIdentifierDao, times(1)).getOptionalEntity(eq(Object.class), any(Parameter.class));
    }

    @Test
    void testGetEntityFromDb_shouldReturnEntityFromDbStringValue() {
        Map<Object, Object> entityMap = new HashMap<>();
        classEntityMap.put(Object.class, entityMap);
        Object entity = new Object();
        when(entityIdentifierDao.getOptionalEntity(eq(Object.class), any(Parameter.class)))
                .thenReturn(Optional.of(entity));

        Object result = entityCachedMap.getEntity(Object.class, "str", "str");

        assertEquals(entity, result);
        verify(entityIdentifierDao, times(1)).getOptionalEntity(eq(Object.class), any(Parameter.class));
    }

    @Test
    void testGetEntityFromDb_shouldReturnEntityFromDbLongValue() {
        Map<Object, Object> entityMap = new HashMap<>();
        classEntityMap.put(Object.class, entityMap);
        Object entity = new Object();

        when(entityIdentifierDao.getOptionalEntity(eq(Object.class), any(Parameter.class)))
                .thenReturn(Optional.of(entity));

        Object result = entityCachedMap.getEntity(Object.class, "long", 1L);

        assertEquals(entity, result);
        verify(entityIdentifierDao, times(1)).getOptionalEntity(eq(Object.class), any(Parameter.class));
    }

    @Test
    void testGetEntityFromDb_shouldReturnEntityFromDbBooleanValue() {
        Map<Object, Object> entityMap = new HashMap<>();
        classEntityMap.put(Object.class, entityMap);
        Object entity = new Object();
        when(entityIdentifierDao.getOptionalEntity(eq(Object.class), any(Parameter.class)))
                .thenReturn(Optional.of(entity));

        Object result = entityCachedMap.getEntity(Object.class, "boolean", true);

        assertEquals(entity, result);
        verify(entityIdentifierDao, times(1)).getOptionalEntity(eq(Object.class), any(Parameter.class));
    }

    @Test
    void testGetEntityFromDb_shouldReturnEntityFromDbDoubleValue() {
        Map<Object, Object> entityMap = new HashMap<>();
        classEntityMap.put(Object.class, entityMap);
        Object entity = new Object();
        when(entityIdentifierDao.getOptionalEntity(eq(Object.class), any(Parameter.class)))
                .thenReturn(Optional.of(entity));

        Object result = entityCachedMap.getEntity(Object.class, "double", 2.5);

        assertEquals(entity, result);
        verify(entityIdentifierDao, times(1)).getOptionalEntity(eq(Object.class), any(Parameter.class));
    }

    @Test
    void testPutEntity_shouldAddEntityToMap() {
        Map<Object, Object> entityMap = new HashMap<>();
        classEntityMap.put(Object.class, entityMap);
        Object entity = new Object();
        String key = "key";

        entityCachedMap.putEntity(Object.class, key, entity);

        assertEquals(entity, entityMap.get(key));
    }

    @Test
    void testUpdateEntity_shouldReplaceOldKeyWithNewKey() {
        Map<Object, Object> entityMap = new HashMap<>();
        classEntityMap.put(Object.class, entityMap);
        Object entity = new Object();
        entityMap.put("oldKey", entity);

        entityCachedMap.updateEntity(Object.class, "oldKey", "newKey", entity);

        assertNull(entityMap.get("oldKey"));
        assertEquals(entity, entityMap.get("newKey"));
    }

    @Test
    void testRemoveEntity_shouldRemoveEntityFromMap() {
        Map<Object, Object> entityMap = new HashMap<>();
        classEntityMap.put(Object.class, entityMap);
        Object entity = new Object();
        String key = "key";
        String value = "value";
        entityMap.put(value, entity);

        entityCachedMap.removeEntity(Object.class, value);

        assertNull(entityMap.get(key));
    }

    @Test
    void testGetEntityList_shouldReturnEntitiesForGivenKeys() {
        Map<Object, Object> entityMap = new HashMap<>();
        classEntityMap.put(Object.class, entityMap);
        Object entity1 = new Object();
        Object entity2 = new Object();
        entityMap.put(1, entity1);
        entityMap.put(2, entity2);

        List<Object> result = entityCachedMap.getEntityList(Object.class, "id", List.of(1, 2));

        assertEquals(List.of(entity1, entity2), result);
    }
}