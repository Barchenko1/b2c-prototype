package com.b2c.prototype.processor.item;

import com.b2c.prototype.manager.post.IPostManager;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.post.ResponsePostDto;

import java.util.List;
import java.util.Map;

public class PostProcess implements IPostProcess {

    private final IPostManager postManager;

    public PostProcess(IPostManager postManager) {
        this.postManager = postManager;
    }

    @Override
    public void saveUpdatePost(Map<String, String> requestParams, PostDto postDto) {
        String articularId = requestParams.get("articularId");
        String postId = requestParams.get("postId");
        String userId = requestParams.get("userId");
        if (postId != null) {
            postManager.updatePost(articularId, postId, postDto);
        } else {
            postManager.savePost(articularId, postDto);
        }
    }

    @Override
    public void deletePostByUniqueId(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        String postId = requestParams.get("postId");
        postManager.deletePostByPostId(articularId, postId);
    }

    @Override
    public List<ResponsePostDto> getPostList(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String articularId = requestParams.get("articularId");
        String email = requestParams.get("email");
        if (userId != null && articularId == null && email == null) {
            return postManager.getPostListByUserId(userId);
        }
        if (userId == null && articularId != null && email == null) {
            return postManager.getPostListByArticularId(articularId);
        }
        if (userId == null && articularId == null && email != null) {
            return postManager.getPostListByEmail(email);
        }
        return List.of();
    }

    @Override
    public ResponsePostDto getPostByArticularIdPostId(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        String postId = requestParams.get("postId");
        return postManager.getPostByArticularIdPostId(articularId, postId);
    }
}
