package com.b2c.prototype.modal.entity.item;

import com.b2c.prototype.transform.converter.ItemDataDescriptionConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

//@Entity
//@Table(name = "metadata")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "metadata_uniq_id", unique = true, nullable = false)
    private String metadataUniqId;
    @Column(name = "description", columnDefinition = "TEXT")
    @Convert(converter = ItemDataDescriptionConverter.class)
    @Builder.Default
    private Map<String, String> description = new LinkedHashMap<>();
}
