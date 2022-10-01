package noticeboard.entity.freeboard;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFreeAttach is a Querydsl query type for FreeAttach
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFreeAttach extends EntityPathBase<FreeAttach> {

    private static final long serialVersionUID = 1233310896L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeAttach freeAttach = new QFreeAttach("freeAttach");

    public final StringPath changedFile = createString("changedFile");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final QFreePost freePost;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QFreeAttach(String variable) {
        this(FreeAttach.class, forVariable(variable), INITS);
    }

    public QFreeAttach(Path<? extends FreeAttach> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFreeAttach(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFreeAttach(PathMetadata<?> metadata, PathInits inits) {
        this(FreeAttach.class, metadata, inits);
    }

    public QFreeAttach(Class<? extends FreeAttach> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freePost = inits.isInitialized("freePost") ? new QFreePost(forProperty("freePost"), inits.get("freePost")) : null;
    }

}

