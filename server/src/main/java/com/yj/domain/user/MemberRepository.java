package com.yj.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsById(String id);
    boolean existsByEmail(String email);
}
