package com.b2c.prototype.manager.post.base;

import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.post.ResponsePostDto;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.manager.post.IPostManager;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.util.PostUtil;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.EMAIL;
import static com.b2c.prototype.util.Constant.USER_ID;
import static com.b2c.prototype.util.PostUtil.postMap;

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
    public void savePost(String articularId, PostDto postDto) {
        entityOperationManager.executeConsumer(session -> {
            Post newPost = transformationFunctionService.getEntity(session, Post.class, postDto);
            Item item = queryService.getNamedQueryEntity(
                    session,
                    Item.class,
                    "Item.findItemByArticularId",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));

            item.getPosts().add(newPost);
            session.merge(item);
        });
    }

    @Override
    public void updatePost(String articularId, String postId, PostDto postDto) {
        entityOperationManager.executeConsumer(session -> {
            Post newPost = transformationFunctionService.getEntity(session, Post.class, postDto);
            Item item = queryService.getNamedQueryEntity(
                    session,
                    Item.class,
                    "Item.findItemByArticularId",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));

            Optional<Post> optionalPost = findOptionalPost(item.getPosts(), postId);
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();

                post.setTitle(newPost.getTitle());
                post.setMessage(newPost.getMessage());
                post.setAuthorEmail(newPost.getAuthorEmail());
                post.setAuthorName(newPost.getAuthorName());
                post.setDateOfCreate(newPost.getDateOfCreate());

                session.merge(item);
            }
        });
    }


    @Override
    public void deletePostByPostId(String articularId, String postId) {
        entityOperationManager.executeConsumer(session -> {
            Item item = queryService.getNamedQueryEntity(
                    session,
                    Item.class,
                    "Item.findItemByArticularId",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));

            item.getPosts().stream()
                    .filter(post -> postId.equals(post.getPostId()))
                    .findFirst()
                    .ifPresent(post -> item.getPosts().remove(post));
            session.merge(item);
        });
    }

    @Override
    public List<ResponsePostDto> getPostListByArticularId(String articularId) {
        AtomicReference<List<ResponsePostDto>> responsePostDtoList = new AtomicReference<>();
        entityOperationManager.executeConsumer(session -> {
            Item item = queryService.getNamedQueryEntity(
                    session,
                    Item.class,
                    "Item.findItemWithTopLevelPostsByArticularId",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
            Set<Post> posts = item.getPosts();
            responsePostDtoList.set(posts.stream()
                    .map(PostUtil::toDto)
                    .toList());
        });
        return responsePostDtoList.get();
    }

    @Override
    public List<ResponsePostDto> getPostListByEmail(String email) {
        AtomicReference<List<ResponsePostDto>> responsePostDtoList = new AtomicReference<>();
        entityOperationManager.executeConsumer(session -> {
            List<Post> posts = queryService.getNamedQueryEntityList(
                    session,
                    Post.class,
                    "Post.findPostWithByEmail",
                    parameterFactory.createStringParameter(EMAIL, email));
            responsePostDtoList.set(posts.stream()
                    .map(PostUtil::toDto)
                    .toList());
        });
        return responsePostDtoList.get();
    }

    @Override
    public List<ResponsePostDto> getPostListByUserId(String userId) {
        AtomicReference<List<ResponsePostDto>> responsePostDtoList = new AtomicReference<>();
        entityOperationManager.executeConsumer(session -> {
            List<Post> posts = queryService.getNamedQueryEntityList(
                    session,
                    Post.class,
                    "Post.findPostWithByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            responsePostDtoList.set(posts.stream()
                    .map(PostUtil::toDto)
                    .toList());
        });
        return responsePostDtoList.get();
    }

    @Override
    public ResponsePostDto getPostByArticularIdPostId(String articularId, String postId) {
        AtomicReference<ResponsePostDto> responsePostDto = new AtomicReference<>();
        entityOperationManager.executeConsumer(session -> {
            Item item = queryService.getNamedQueryEntity(
                    session,
                    Item.class,
                    "Item.findItemByArticularId",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
            Set<Post> posts = item.getPosts();
            responsePostDto.set(posts.stream()
                    .map(PostUtil::toDto)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Post not found")));
        });
        return responsePostDto.get();
    }

    private Optional<Post> findOptionalPost(Set<Post> posts, String postId) {
        return Optional.ofNullable(postMap(posts).get(postId));
    }

}
