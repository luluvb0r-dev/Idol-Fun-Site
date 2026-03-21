package jp.co.idolFunSite.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * 全エンティティクラスで共通利用する、システム監査項目（作成日時・更新日時）の基底クラス
 */
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * レコード作成日時
     * DB保存時に自動で設定され、以降は上書きされません。
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * レコード更新日時
     * DB保存時・更新時に自動で現在時刻に更新されます。
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    /**
     * 新規エンティティ永続化前のライフサイクルコールバック
     * 作成日時・更新日時を自動設定します。
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * エンティティ更新前のライフサイクルコールバック
     * 更新日時を自動設定します。
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
