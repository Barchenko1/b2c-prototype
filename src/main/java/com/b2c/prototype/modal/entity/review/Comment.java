package com.b2c.prototype.modal.entity.review;

import com.b2c.prototype.modal.entity.user.UserProfile;
import com.tm.core.modal.TransitiveSelfEntity;
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

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends TransitiveSelfEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String uniqueCommentId;
    private String title;
    private String message;
    private long dateOfCreate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private UserProfile userProfile;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    @ToString.Exclude
    private Comment parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Comment> childNodeList = new ArrayList<>();

    @Override
    public void setParent(TransitiveSelfEntity transitiveSelfEntity) {
        this.parent = (Comment) transitiveSelfEntity;
    }

    @Override
    public String getRootField() {
        return uniqueCommentId;
    }

    @Override
    public <E extends TransitiveSelfEntity> void setChildNodeList(List<E> childNodeList) {
        this.childNodeList = (List<Comment>) childNodeList;
    }

    public void addChildComment(Comment comment) {
        this.childNodeList.add(comment);
        comment.setParent(this);
    }

    public void removeChildComment(Comment comment) {
        this.childNodeList.remove(comment);
        comment.setParent(null);
    }
}
