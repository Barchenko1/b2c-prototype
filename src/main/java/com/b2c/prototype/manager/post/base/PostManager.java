package com.b2c.prototype.manager.post.base;

import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.modal.dto.payload.PostDto;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.manager.post.IPostManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import java.util.Optional;

import static com.b2c.prototype.util.Util.getCurrentTimeMillis;
import static com.b2c.prototype.util.Util.getUUID;

@Slf4j
public class PostManager implements IPostManager {

    private final IEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public PostManager(IPostDao postDao,
                       IQueryService queryService,
                       ITransformationFunctionService transformationFunctionService,
                       IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(postDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdatePost(String articularId, String uniqueId, PostDto postDto) {
        Post newPost = transformationFunctionService.getEntity(Post.class, postDto);

        Parameter parameter = parameterFactory.createStringParameter("uniquePostId", uniqueId);
        entityOperationManager.updateEntity(newPost);
    }

    @Override
    public void deletePostByUniqueId(String articularId, String uniqueId) {
        Parameter parameter = parameterFactory.createStringParameter("uniquePostId", uniqueId);
        entityOperationManager.deleteEntity(parameter);
    }

    @Override
    public List<Post> getPostListByPostTitle(String title) {
        Parameter parameter = parameterFactory.createStringParameter("title", title);
        return entityOperationManager.getNamedQueryEntityList("");
    }

    @Override
    public List<Post> getPostListByEmail(String email) {
        Parameter parameter = parameterFactory.createStringParameter("email", email);
        return entityOperationManager.getNamedQueryEntityList("");
    }

    @Override
    public List<Post> getPostListByUserId(String userId) {
        Parameter parameter = parameterFactory.createStringParameter("userId", userId);
        return entityOperationManager.getNamedQueryEntityList("");
    }

    @Override
    public Post getPostByUniqueId(String uniqueId) {
        Parameter parameter = parameterFactory.createStringParameter("uniqueId", uniqueId);
        Optional<Post> optionalPost = entityOperationManager.getGraphOptionalEntity(
                "",
                parameter);

        if (optionalPost.isEmpty()) {
            throw new RuntimeException();
        }

        return optionalPost.get();
    }

    private Post buildPost(PostDto requestPostDto, String uniquePostId) {
        Post parentPost = null;
        if (requestPostDto.getParent() != null) {
            Parameter parameter = parameterFactory.createStringParameter("uniquePostId", uniquePostId);
            Optional<Post> optionalPost = entityOperationManager.getGraphOptionalEntity(
                    "",
                    parameter);

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

        post.setChildList(null);

        return post;
    }

}
