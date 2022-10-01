package noticeboard.entity.freeboard;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFreeCommit is a Querydsl query type for FreeCommit
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFreeCommit extends EntityPathBase<FreeCommit> {

    private static final long serialVersionUID = 1285754786L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeCommit freeCommit = new QFreeCommit("freeCommit");

    public final QPostBaseEntity _super = new QPostBaseEntity(this);

    //inherited
    public final StringPath content = _super.content;

    public final QFreePost freePost;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> postNumber = createNumber("postNumber", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> postTime = _super.postTime;

    public final StringPath postType = createString("postType");

    //inherited
    public final StringPath writer = _super.writer;

    public QFreeCommit(String variable) {
        this(FreeCommit.class, forVariable(variable), INITS);
    }

    public QFreeCommit(Path<? extends FreeCommit> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFreeCommit(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFreeCommit(PathMetadata<?> metadata, PathInits inits) {
        this(FreeCommit.class, metadata, inits);
    }

    public QFreeCommit(Class<? extends FreeCommit> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freePost = inits.isInitialized("freePost") ? new QFreePost(forProperty("freePost"), inits.get("freePost")) : null;
    }

}

