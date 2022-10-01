package noticeboard.entity.freeboard;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPostBaseEntity is a Querydsl query type for PostBaseEntity
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QPostBaseEntity extends EntityPathBase<PostBaseEntity> {

    private static final long serialVersionUID = -970080205L;

    public static final QPostBaseEntity postBaseEntity = new QPostBaseEntity("postBaseEntity");

    public final StringPath content = createString("content");

    public final DateTimePath<java.util.Date> postTime = createDateTime("postTime", java.util.Date.class);

    public final StringPath writer = createString("writer");

    public QPostBaseEntity(String variable) {
        super(PostBaseEntity.class, forVariable(variable));
    }

    public QPostBaseEntity(Path<? extends PostBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostBaseEntity(PathMetadata<?> metadata) {
        super(PostBaseEntity.class, metadata);
    }

}

