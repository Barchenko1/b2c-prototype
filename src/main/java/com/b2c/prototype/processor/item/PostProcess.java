package com.b2c.prototype.processor.item;

import com.b2c.prototype.manager.post.IPostManager;
import com.b2c.prototype.modal.dto.payload.PostDto;
import com.b2c.prototype.modal.entity.post.Post;

import java.util.List;
import java.util.Map;

public class PostProcess implements IPostProcess {

    private final IPostManager postManager;

    public PostProcess(IPostManager postManager) {
        this.postManager = postManager;
    }

    @Override
    public void saveUpdatePost(Map<String, String> requestParams, PostDto postDto) {

    }

    @Override
    public void deletePostByUniqueId(Map<String, String> requestParams) {

    }

    @Override
    public List<Post> getPostListByPostTitle(Map<String, String> requestParams) {
        return List.of();
    }

    @Override
    public List<Post> getPostListByEmail(Map<String, String> requestParams) {
        return List.of();
    }

    @Override
    public List<Post> getPostListByUserId(Map<String, String> requestParams) {
        return List.of();
    }

    @Override
    public Post getPostByUniqueId(Map<String, String> requestParams) {
        return null;
    }
}
