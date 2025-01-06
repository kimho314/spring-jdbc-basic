package com.example.basic_db.repository;

import com.example.basic_db.domain.Member;

public interface MemberRepository {
    void delete(String memberId);

    void deleteAll();

    void update(String memberId, int money);

    Member findById(String memberId);

    Member save(Member member);

}
