package com.websocket.Lottery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, BigInteger> {
    Optional<List<Users>> findAllByIsActive(Boolean isActive);

    Optional<List<Users>> findAllByIsSelected(Boolean aTrue);
}
