package com.b2c.prototype.modal.entity.item;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articular_group")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticularGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "articular_group_uniq_id", unique = true, nullable = false)
    private String articularGroupId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private ItemType itemType;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private Brand brand;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "meta_data_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    private Set<ArticularItem> articularItemSet = new HashSet<>();
}
