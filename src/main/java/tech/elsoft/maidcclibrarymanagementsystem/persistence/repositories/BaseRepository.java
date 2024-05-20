package tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.AbstractAuditingEntity;

@NoRepositoryBean
public interface BaseRepository<T extends AbstractAuditingEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {


}