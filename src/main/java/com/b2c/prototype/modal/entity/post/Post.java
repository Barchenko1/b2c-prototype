package com.b2c.prototype.modal.entity.post;

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

import java.util.List;

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
    private Post parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Post> childNodeList;

    @Override
    public void setParent(TransitiveSelfEntity transitiveSelfEntity) {
        this.parent = (Post) transitiveSelfEntity;
    }

    @Override
    public String getRootField() {
        return uniquePostId;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", authorUserName='" + authorUserName + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", uniquePostId='" + uniquePostId + '\'' +
                ", dateOfCreate=" + dateOfCreate +
                ", parentId=" + (parent != null ? parent.getId() : "null") +
                ", childNodeCount=" + (childNodeList != null ? childNodeList.size() : "null") +
                '}';
    }
}
