package com.RummyTriangle.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWalletRepository extends JpaRepository<Wallet, Integer> {

	Optional<Wallet> findByUserId(int userId);
}
