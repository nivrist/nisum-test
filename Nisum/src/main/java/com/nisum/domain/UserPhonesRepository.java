package com.nisum.domain;

import com.nisum.domain.model.UserPhones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserPhonesRepository extends JpaRepository<UserPhones, Integer> {
    @Query(nativeQuery = true, value = "SELECT COALESCE(MAX(id) + 1, 1) AS nuevo_valor FROM user_phones")
    public int getNextValId();
}
