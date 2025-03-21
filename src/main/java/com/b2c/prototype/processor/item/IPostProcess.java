package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.PostDto;
import com.b2c.prototype.modal.entity.post.Post;

import java.util.List;
import java.util.Map;

public interface IPostProcess {
    void saveUpdatePost(Map<String, String> requestParams, PostDto postDto);
    void deletePostByUniqueId(Map<String, String> requestParams);

    List<Post> getPostListByPostTitle(Map<String, String> requestParams);
    List<Post> getPostListByEmail(Map<String, String> requestParams);
    List<Post> getPostListByUserId(Map<String, String> requestParams);
    Post getPostByUniqueId(Map<String, String> requestParams);
}
