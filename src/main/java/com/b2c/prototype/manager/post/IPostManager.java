package com.b2c.prototype.manager.post;

import com.b2c.prototype.modal.dto.payload.PostDto;
import com.b2c.prototype.modal.entity.post.Post;
import com.tm.core.util.TransitiveSelfEnum;

import java.util.List;
import java.util.Map;

public interface IPostManager {
    void savePost(String articularId, PostDto postDto);
    void updatePost(String articularId, String uniqueId, PostDto postDto);
    void deletePostByUniqueId(String articularId, String uniqueId);

    List<Post> getPostListByPostTitle(String title);
    List<Post> getPostListByEmail(String email);
    List<Post> getPostListByUserName(String username);
    Post getPostByUniqueId(String uniqueId);

    Map<TransitiveSelfEnum, List<Post>> getAllPostsTree();
}
