package noticeboard.entity.userdata;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QIdInfo is a Querydsl query type for IdInfo
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QIdInfo extends EntityPathBase<IdInfo> {

    private static final long serialVersionUID = -344654473L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIdInfo idInfo = new QIdInfo("idInfo");

    public final EnumPath<IdInfo.Admin> admin = createEnum("admin", IdInfo.Admin.class);

    public final ListPath<noticeboard.entity.freeboard.FreePost, noticeboard.entity.freeboard.QFreePost> freePost = this.<noticeboard.entity.freeboard.FreePost, noticeboard.entity.freeboard.QFreePost>createList("freePost", noticeboard.entity.freeboard.FreePost.class, noticeboard.entity.freeboard.QFreePost.class, PathInits.DIRECT2);

    public final StringPath id = createString("id");

    public final NumberPath<Long> idInfoID = createNumber("idInfoID", Long.class);

    public final DatePath<java.util.Date> joinDate = createDate("joinDate", java.util.Date.class);

    public final QProfileSetting profileSetting;

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath userPassword = createString("userPassword");

    public QIdInfo(String variable) {
        this(IdInfo.class, forVariable(variable), INITS);
    }

    public QIdInfo(Path<? extends IdInfo> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QIdInfo(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QIdInfo(PathMetadata<?> metadata, PathInits inits) {
        this(IdInfo.class, metadata, inits);
    }

    public QIdInfo(Class<? extends IdInfo> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.profileSetting = inits.isInitialized("profileSetting") ? new QProfileSetting(forProperty("profileSetting")) : null;
    }

}

