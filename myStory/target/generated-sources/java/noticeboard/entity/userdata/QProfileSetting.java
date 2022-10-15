package noticeboard.entity.userdata;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QProfileSetting is a Querydsl query type for ProfileSetting
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QProfileSetting extends EntityPathBase<ProfileSetting> {

    private static final long serialVersionUID = 1877527413L;

    public static final QProfileSetting profileSetting = new QProfileSetting("profileSetting");

    public final StringPath email = createString("email");

    public final NumberPath<Integer> option2 = createNumber("option2", Integer.class);

    public final StringPath phone = createString("phone");

    public final NumberPath<Long> profileNumber = createNumber("profileNumber", Long.class);

    public QProfileSetting(String variable) {
        super(ProfileSetting.class, forVariable(variable));
    }

    public QProfileSetting(Path<? extends ProfileSetting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProfileSetting(PathMetadata<?> metadata) {
        super(ProfileSetting.class, metadata);
    }

}

