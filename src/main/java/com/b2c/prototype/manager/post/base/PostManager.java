package com.b2c.prototype.manager.post.base;

import com.b2c.prototype.modal.dto.payload.PostDto;
import com.b2c.prototype.modal.dto.update.PostDtoUpdate;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.manager.post.IPostManager;
import com.b2c.prototype.manager.AbstractTransitiveSelfEntityManager;
import com.tm.core.dao.transitive.ITransitiveSelfEntityDao;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.util.TransitiveSelfEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.b2c.prototype.util.Util.getCurrentTimeMillis;
import static com.b2c.prototype.util.Util.getUUID;

@Slf4j
public class PostManager extends AbstractTransitiveSelfEntityManager implements IPostManager {

    private final IPostDao postDao;

    public PostManager(IPostDao postDao) {
        this.postDao = postDao;
    }

    @Override
    protected ITransitiveSelfEntityDao getEntityDao() {
        return this.postDao;
    }

    @Override
    public void savePost(PostDto requestPostDto) {
        createNewPost(requestPostDto);
    }

    @Override
    public Post createNewPostWithResponse(PostDto requestPostDto) {
        return createNewPost(requestPostDto);
    }

    @Override
    public void updatePost(PostDtoUpdate requestUpdatePostDto) {
        Post newPost = buildPost(requestUpdatePostDto.getNewEntity(),
                requestUpdatePostDto.getOldEntity().getUniquePostId());

        Parameter parameter = parameterFactory.createStringParameter(
                "uniquePostId",
                requestUpdatePostDto.getOldEntity().getUniquePostId());
        super.updateEntityTreeOldMain(newPost, parameter);
    }

    @Override
    public void deletePostByUniqueId(String uniqueId) {
        Parameter parameter = parameterFactory.createStringParameter("uniquePostId", uniqueId);
        postDao.deleteEntityTree(parameter);
    }

    @Override
    public List<Post> getPostListByPostTitle(String title) {
        Parameter parameter = parameterFactory.createStringParameter("title", title);
        return postDao.getTransitiveSelfEntityList(parameter);
    }

    @Override
    public List<Post> getPostListByEmail(String email) {
        Parameter parameter = parameterFactory.createStringParameter("email", email);
        return postDao.getTransitiveSelfEntityList(parameter);
    }

    @Override
    public List<Post> getPostListByUserName(String username) {
        Parameter parameter = parameterFactory.createStringParameter("username", username);
        return postDao.getTransitiveSelfEntityList(parameter);
    }

    @Override
    public Post getPostByUniqueId(String uniqueId) {
        Parameter parameter = parameterFactory.createStringParameter("uniqueId", uniqueId);
        Optional<Post> optionalPost = postDao.getOptionalTransitiveSelfEntity(parameter);

        if (optionalPost.isEmpty()) {
            throw new RuntimeException();
        }

        return optionalPost.get();
    }

    @Override
    public Map<TransitiveSelfEnum, List<Post>> getAllPostsTree() {
        return getEntityDao().getTransitiveSelfEntitiesTree();
    }

    private Post createNewPost(PostDto requestPostDto) {
        Post newPost = buildPost(requestPostDto, getUUID());

        super.saveEntityTree(newPost);
        return newPost;
    }

    private Post buildPost(PostDto requestPostDto, String uniquePostId) {
        Post parentPost = null;
        if (requestPostDto.getParent() != null) {
            Parameter parameter = parameterFactory.createStringParameter("uniquePostId", uniquePostId);
            Optional<Post> optionalPost = getEntityDao().getOptionalTransitiveSelfEntity(parameter);

            if (optionalPost.isPresent()) {
                parentPost = optionalPost.get();
            }
        }

        Post post = Post.builder()
                .title(requestPostDto.getTitle())
                .message(requestPostDto.getMessage())
                .parent(parentPost)
                .uniquePostId(uniquePostId)
                .dateOfCreate(getCurrentTimeMillis())
                .build();

        post.setChildNodeList(null);

        return post;
    }

}
