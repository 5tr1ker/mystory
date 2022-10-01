package noticeboard.entity.freeboard;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFreeTag is a Querydsl query type for FreeTag
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFreeTag extends EntityPathBase<FreeTag> {

    private static final long serialVersionUID = -316249841L;

    public static final QFreeTag freeTag = new QFreeTag("freeTag");

    public final ListPath<FreePost, QFreePost> freePost = this.<FreePost, QFreePost>createList("freePost", FreePost.class, QFreePost.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath tagData = createString("tagData");

    public QFreeTag(String variable) {
        super(FreeTag.class, forVariable(variable));
    }

    public QFreeTag(Path<? extends FreeTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFreeTag(PathMetadata<?> metadata) {
        super(FreeTag.class, metadata);
    }

}

