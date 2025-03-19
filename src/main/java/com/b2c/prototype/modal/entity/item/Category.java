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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Category.findByValue",
                query = "SELECT c FROM Category c " +
                        "LEFT JOIN FETCH c.childList cl1 " +
                        "WHERE c.value = :value"
        ),
        @NamedQuery(
                name = "Category.allParent",
                query = "SELECT c FROM Category c " +
                        "LEFT JOIN FETCH c.childList cl1 " +
                        "WHERE c.parent IS NULL"
        ),
        @NamedQuery(
                name = "Category.all",
                query = "SELECT c FROM Category c " +
                        "LEFT JOIN FETCH c.childList cl1"
        )
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "label", nullable = false)
    private String label;
    @Column(name = "value", unique = true, nullable = false)
    private String value;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Category> childList = new ArrayList<>();

    public void addChildEntity(Category child) {
        this.getChildList().add(child);
        child.setParent(this);
    }

    public void removeChildEntity(Category child) {
        this.getChildList().remove(child);
        child.setParent(null);
    }

}
