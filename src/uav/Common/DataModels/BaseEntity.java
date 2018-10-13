package uav.Common.DataModels;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Common 'id' part of all entities.
 */
@Entity
@MappedSuperclass
public abstract class BaseEntity {
    public static final String ID_PROPERTY = "id";
    public static final String CREATION_TIME = "creationTime";
    public static final String MODIFICATION_TIME = "modificationTime";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "creation_time", nullable = false, updatable = false)
    private Timestamp creationTime;

    @Column(name = "modification_time", nullable = false)
    private Timestamp modificationTime;

    @Nonnull
    public Long getId() {
        return id;
    }

    public void setId(@Nonnull Long id) {
        this.id = id;
    }

    @Nonnull
    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(@Nonnull Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    @Nonnull
    public Timestamp getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(@Nonnull Timestamp modificationTime) {
        this.modificationTime = modificationTime;
    }
}
