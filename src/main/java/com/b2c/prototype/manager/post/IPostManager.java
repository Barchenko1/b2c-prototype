package com.b2c.prototype.manager.post;

import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.post.ResponsePostDto;

import java.util.List;

public interface IPostManager {
    void savePost(String articularId, PostDto postDto);
    void updatePost(String articularId, String postId, PostDto postDto);
    void deletePostByPostId(String articularId, String postId);

    List<ResponsePostDto> getPostListByArticularId(String articularId);
    List<ResponsePostDto> getPostListByEmail(String email);
    List<ResponsePostDto> getPostListByUserId(String userId);
    ResponsePostDto getPostByArticularIdPostId(String articularId, String postId);
}
