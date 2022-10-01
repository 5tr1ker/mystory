package noticeboard.entity.freeboard;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFreeWhoLike is a Querydsl query type for FreeWhoLike
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFreeWhoLike extends EntityPathBase<FreeWhoLike> {

    private static final long serialVersionUID = 1574356842L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeWhoLike freeWhoLike = new QFreeWhoLike("freeWhoLike");

    public final QFreePost freePost;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath recommender = createString("recommender");

    public QFreeWhoLike(String variable) {
        this(FreeWhoLike.class, forVariable(variable), INITS);
    }

    public QFreeWhoLike(Path<? extends FreeWhoLike> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFreeWhoLike(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFreeWhoLike(PathMetadata<?> metadata, PathInits inits) {
        this(FreeWhoLike.class, metadata, inits);
    }

    public QFreeWhoLike(Class<? extends FreeWhoLike> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freePost = inits.isInitialized("freePost") ? new QFreePost(forProperty("freePost"), inits.get("freePost")) : null;
    }

}

