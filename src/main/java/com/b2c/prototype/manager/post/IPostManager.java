package com.b2c.prototype.manager.post;

import com.b2c.prototype.modal.dto.payload.PostDto;
import com.b2c.prototype.modal.entity.post.Post;

import java.util.List;

public interface IPostManager {
    void saveUpdatePost(String articularId, String uniqueId, PostDto postDto);
    void deletePostByUniqueId(String articularId, String uniqueId);

    List<Post> getPostListByPostTitle(String title);
    List<Post> getPostListByEmail(String email);
    List<Post> getPostListByUserId(String userId);
    Post getPostByUniqueId(String uniqueId);
}
