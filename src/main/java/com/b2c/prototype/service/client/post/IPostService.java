package com.b2c.prototype.service.client.post;

import com.b2c.prototype.modal.client.dto.request.RequestPostDto;
import com.b2c.prototype.modal.client.dto.update.RequestPostDtoUpdate;
import com.b2c.prototype.modal.client.entity.post.Post;
import com.tm.core.util.TransitiveSelfEnum;

import java.util.List;
import java.util.Map;

public interface IPostService {
    void createNewPost(RequestPostDto requestPostDto);
    Post createNewPostWithResponse(RequestPostDto requestPostDto);
    void updatePost(RequestPostDtoUpdate requestUpdatePostDto);
    void deletePostByUniqueId(String uniqueId);

    List<Post> getPostListByPostTitle(String title);
    List<Post> getPostListByEmail(String email);
    List<Post> getPostListByUserName(String username);
    Post getPostByUniqueId(String uniqueId);

    Map<TransitiveSelfEnum, List<Post>> getAllPostsTree();
}
