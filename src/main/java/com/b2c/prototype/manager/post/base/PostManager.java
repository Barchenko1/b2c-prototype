package com.b2c.prototype.manager.post.base;

import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.modal.dto.payload.PostDto;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.manager.post.IPostManager;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.util.TransitiveSelfEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.b2c.prototype.util.Util.getCurrentTimeMillis;
import static com.b2c.prototype.util.Util.getUUID;

@Slf4j
public class PostManager implements IPostManager {

    private final IEntityOperationManager entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public PostManager(IPostDao postDao,
                       ITransformationFunctionService transformationFunctionService,
                       ISupplierService supplierService,
                       IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(postDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void savePost(String articularId, PostDto requestPostDto) {
        Post newPost = buildPost(requestPostDto, getUUID());
        entityOperationDao.saveEntity(newPost);
    }

    @Override
    public void updatePost(String articularId, String uniqueId, PostDto postDto) {
        Post newPost = transformationFunctionService.getEntity(Post.class, postDto);

        Parameter parameter = parameterFactory.createStringParameter("uniquePostId", uniqueId);
        entityOperationDao.updateEntity(newPost);
    }

    @Override
    public void deletePostByUniqueId(String articularId, String uniqueId) {
        Parameter parameter = parameterFactory.createStringParameter("uniquePostId", uniqueId);
        entityOperationDao.deleteEntity(parameter);
    }

    @Override
    public List<Post> getPostListByPostTitle(String title) {
        Parameter parameter = parameterFactory.createStringParameter("title", title);
        return entityOperationDao.getNamedQueryEntityList("");
    }

    @Override
    public List<Post> getPostListByEmail(String email) {
        Parameter parameter = parameterFactory.createStringParameter("email", email);
        return entityOperationDao.getNamedQueryEntityList("");
    }

    @Override
    public List<Post> getPostListByUserName(String username) {
        Parameter parameter = parameterFactory.createStringParameter("username", username);
        return entityOperationDao.getNamedQueryEntityList("");
    }

    @Override
    public Post getPostByUniqueId(String uniqueId) {
        Parameter parameter = parameterFactory.createStringParameter("uniqueId", uniqueId);
        Optional<Post> optionalPost = entityOperationDao.getGraphOptionalEntity(
                "",
                parameter);

        if (optionalPost.isEmpty()) {
            throw new RuntimeException();
        }

        return optionalPost.get();
    }

    @Override
    public Map<TransitiveSelfEnum, List<Post>> getAllPostsTree() {
        return null;
    }

    private Post buildPost(PostDto requestPostDto, String uniquePostId) {
        Post parentPost = null;
        if (requestPostDto.getParent() != null) {
            Parameter parameter = parameterFactory.createStringParameter("uniquePostId", uniquePostId);
            Optional<Post> optionalPost = entityOperationDao.getGraphOptionalEntity(
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

        post.setChildNodeList(null);

        return post;
    }

}
