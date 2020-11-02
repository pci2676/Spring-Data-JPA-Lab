package com.javabom.bomjpa.repository;

import com.javabom.bomjpa.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
