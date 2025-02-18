package com.b2c.prototype.modal.entity.item;

import com.tm.core.modal.TransitiveSelfEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends TransitiveSelfEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "label", nullable = false)
    private String label;
    @Column(name = "value", nullable = false)
    private String value;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Category> childNodeList = new ArrayList<>();

    @Override
    public void setParent(TransitiveSelfEntity transitiveSelfEntity) {
        this.parent = (Category) transitiveSelfEntity;
    }

    @Override
    public String getRootField() {
        return value;
    }

    @Override
    public <E extends TransitiveSelfEntity> void setChildNodeList(List<E> childNodeList) {
        this.childNodeList = (List<Category>) childNodeList;
    }

}
