package com.b2c.prototype.transform.post;

import com.b2c.prototype.modal.entity.post.Post;

public interface IPostTransformService {
    Post mapPostValueToEntity(String value);
}
