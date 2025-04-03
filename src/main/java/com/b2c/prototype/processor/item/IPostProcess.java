package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.post.ResponsePostDto;

import java.util.List;
import java.util.Map;

public interface IPostProcess {
    void saveUpdatePost(Map<String, String> requestParams, PostDto postDto);
    void deletePostByUniqueId(Map<String, String> requestParams);

    List<ResponsePostDto> getPostList(Map<String, String> requestParams);
    ResponsePostDto getPostByArticularIdPostId(Map<String, String> requestParams);
}
