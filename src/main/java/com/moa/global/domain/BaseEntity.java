package com.moa.global.domain;

// JPA (Java Persistence API) 관련 어노테이션을 사용하기 위한 임포트
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

// Spring Data JPA에서 제공하는 날짜 관련 어노테이션 및 리스너 클래스를 임포트
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// Java의 날짜 및 시간 API를 사용하기 위한 임포트
import java.time.LocalDateTime;

// 해당 클래스가 JPA 엔티티의 베이스 클래스로 매핑되기 위한 어노테이션
@MappedSuperclass
// JPA 엔티티가 데이터베이스와 상호작용할 때, AuditingEntityListener를 사용하여 생성 및 수정 날짜를 자동으로 관리하기 위한 어노테이션
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    // 생성 날짜를 반환하는 메서드
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    // 수정 날짜를 반환하는 메서드
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    // 생성 날짜를 자동으로 설정하기 위한 어노테이션
    @CreatedDate
    // 데이터베이스 테이블의 컬럼 이름을 "created_date"로 설정하기 위한 어노테이션
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    // 마지막 수정 날짜를 자동으로 설정하기 위한 어노테이션
    @LastModifiedDate
    // 데이터베이스 테이블의 컬럼 이름을 "updated_date"로 설정하기 위한 어노테이션
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
