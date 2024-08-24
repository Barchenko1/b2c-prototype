package com.b2c.prototype.service.post;

import com.b2c.prototype.modal.dto.request.RequestPostDto;
import com.b2c.prototype.modal.dto.update.RequestPostDtoUpdate;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.dao.post.IPostDao;
import com.tm.core.processor.EntityFinder;
import com.tm.core.util.TransitiveSelfEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.b2c.prototype.util.Query.SELECT_ALL_POSTS;
import static com.b2c.prototype.util.Query.SELECT_POST_BY_UNIQUE_ID;
import static com.b2c.prototype.util.Query.SELECT_POST_BY_USERNAME;
import static com.b2c.prototype.util.Query.SELECT_POST_BY_TITLE;
import static com.b2c.prototype.util.Query.SELECT_POST_BY_EMAIL;
import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

@Slf4j
public class PostService implements IPostService {

    private final IPostDao postDao;

    public PostService(IPostDao postDao) {
        this.postDao = postDao;
    }

    @Override
    public void createNewPost(RequestPostDto requestPostDto) {
        savePost(requestPostDto);
    }

    @Override
    public Post createNewPostWithResponse(RequestPostDto requestPostDto) {
        return savePost(requestPostDto);
    }

    @Override
    public void updatePost(RequestPostDtoUpdate requestUpdatePostDto) {
        Post newPost = buildPost(requestUpdatePostDto.getNewEntityDto(),
                requestUpdatePostDto.getOldEntityDto().getUniquePostId());
        EntityFinder entityFinder = new EntityFinder(SELECT_POST_BY_UNIQUE_ID, Post.class,
                requestUpdatePostDto.getOldEntityDto().getUniquePostId());

        postDao.updateEntityTree(entityFinder, newPost);
    }

    @Override
    public void deletePostByUniqueId(String uniqueId) {
        EntityFinder entityFinder = new EntityFinder(SELECT_POST_BY_UNIQUE_ID, Class.class, uniqueId);
        postDao.deleteEntityTree(entityFinder);
    }

    @Override
    public List<Post> getPostListByPostTitle(String title) {
        return postDao.getEntityListBySQLQueryWithParams(SELECT_POST_BY_TITLE, title);
    }

    @Override
    public List<Post> getPostListByEmail(String email) {
        return postDao.getEntityListBySQLQueryWithParams(SELECT_POST_BY_EMAIL, email);
    }

    @Override
    public List<Post> getPostListByUserName(String username) {
        return postDao.getEntityListBySQLQueryWithParams(SELECT_POST_BY_USERNAME, username);
    }

    @Override
    public Post getPostByUniqueId(String uniqueId) {
        Optional<Post> optionalPost = postDao.getOptionalEntityBySQLQueryWithParams(
                SELECT_POST_BY_UNIQUE_ID, uniqueId);

        if (optionalPost.isEmpty()) {
            throw new RuntimeException();
        }

        return optionalPost.get();
    }

    @Override
    public Map<TransitiveSelfEnum, List<Post>> getAllPostsTree() {
        return postDao.getEntitiesTreeBySQLQuery(SELECT_ALL_POSTS);
    }

    private Post savePost(RequestPostDto requestPostDto) {
        Post newPost = buildPost(requestPostDto, getUUID());
        postDao.saveEntityTree(newPost);
        return newPost;
    }

    private Post buildPost(RequestPostDto requestPostDto, String uniqueId) {
        Post parentPost = null;
        if (requestPostDto.getParent() != null) {
            Optional<Post> optionalPost = postDao.getOptionalEntityBySQLQueryWithParams(
                    SELECT_POST_BY_UNIQUE_ID, requestPostDto.getParent().getUniquePostId());

            if (optionalPost.isPresent()) {
                parentPost = optionalPost.get();
            }
        }

        Post post = Post.builder()
                .title(requestPostDto.getTitle())
                .message(requestPostDto.getMessage())
                .authorUserName(requestPostDto.getAuthorUserName())
                .authorEmail(requestPostDto.getAuthorEmail())
                .parent(parentPost)
                .uniquePostId(uniqueId)
                .dateOfCreate(System.currentTimeMillis())
                .build();

        post.setChildNodeList(null);

        return post;
    }

}
