package com.b2c.prototype.modal.entity.post;

import com.b2c.prototype.modal.entity.item.Item;
import com.tm.core.modal.TransitiveSelfEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends TransitiveSelfEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    private String authorUserName;
    private String authorEmail;
    private String title;
    private String message;
    private String uniquePostId;
    private long dateOfCreate;
    @ManyToOne
    @JoinColumn(name = "post_id")
    @ToString.Exclude
    private Post parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Post> childNodeList = new ArrayList<>();
    @ManyToMany(mappedBy = "posts", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Item> items = new HashSet<>();

    @Override
    public void setParent(TransitiveSelfEntity transitiveSelfEntity) {
        this.parent = (Post) transitiveSelfEntity;
    }

    @Override
    public String getRootField() {
        return uniquePostId;
    }

    @Override
    public <E extends TransitiveSelfEntity> void setChildNodeList(List<E> childNodeList) {
        this.childNodeList = (List<Post>) childNodeList;
    }

    public void addChildPost(Post post) {
        this.childNodeList.add(post);
        post.setParent(this);
    }

    public void removeChildPost(Post post) {
        this.childNodeList.remove(post);
        post.setParent(null);
    }

}
