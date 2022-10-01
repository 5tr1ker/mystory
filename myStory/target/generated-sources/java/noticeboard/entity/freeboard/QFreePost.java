package noticeboard.entity.freeboard;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFreePost is a Querydsl query type for FreePost
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFreePost extends EntityPathBase<FreePost> {

    private static final long serialVersionUID = -1213915701L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreePost freePost = new QFreePost("freePost");

    public final QPostBaseEntity _super = new QPostBaseEntity(this);

    public final BooleanPath blockComm = createBoolean("blockComm");

    //inherited
    public final StringPath content = _super.content;

    public final ListPath<FreeAttach, QFreeAttach> freeAttach = this.<FreeAttach, QFreeAttach>createList("freeAttach", FreeAttach.class, QFreeAttach.class, PathInits.DIRECT2);

    public final ListPath<FreeCommit, QFreeCommit> freeCommit = this.<FreeCommit, QFreeCommit>createList("freeCommit", FreeCommit.class, QFreeCommit.class, PathInits.DIRECT2);

    public final NumberPath<Long> freepostId = createNumber("freepostId", Long.class);

    public final ListPath<FreeTag, QFreeTag> freeTag = this.<FreeTag, QFreeTag>createList("freeTag", FreeTag.class, QFreeTag.class, PathInits.DIRECT2);

    public final ListPath<FreeWhoLike, QFreeWhoLike> freeWhoLike = this.<FreeWhoLike, QFreeWhoLike>createList("freeWhoLike", FreeWhoLike.class, QFreeWhoLike.class, PathInits.DIRECT2);

    public final noticeboard.entity.userdata.QIdInfo idInfo;

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final NumberPath<Long> numbers = createNumber("numbers", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> postTime = _super.postTime;

    public final BooleanPath privates = createBoolean("privates");

    public final StringPath title = createString("title");

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    //inherited
    public final StringPath writer = _super.writer;

    public QFreePost(String variable) {
        this(FreePost.class, forVariable(variable), INITS);
    }

    public QFreePost(Path<? extends FreePost> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFreePost(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFreePost(PathMetadata<?> metadata, PathInits inits) {
        this(FreePost.class, metadata, inits);
    }

    public QFreePost(Class<? extends FreePost> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.idInfo = inits.isInitialized("idInfo") ? new noticeboard.entity.userdata.QIdInfo(forProperty("idInfo"), inits.get("idInfo")) : null;
    }

}

