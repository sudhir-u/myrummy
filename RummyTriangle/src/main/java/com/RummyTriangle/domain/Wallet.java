package com.RummyTriangle.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User wallet for game credits/balance.
 * Tied to app_user via userId.
 */
@Entity
@Table(name = "wallet")
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int userId;
	private long balanceCents;  // Store in cents to avoid float
	private long totalWinningsCents;
	private long totalCommissionPaidCents;

	public Wallet() {
	}

	public Wallet(int userId) {
		this.userId = userId;
		this.balanceCents = 0;
		this.totalWinningsCents = 0;
		this.totalCommissionPaidCents = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getBalanceCents() {
		return balanceCents;
	}

	public void setBalanceCents(long balanceCents) {
		this.balanceCents = balanceCents;
	}

	public long getTotalWinningsCents() {
		return totalWinningsCents;
	}

	public void setTotalWinningsCents(long totalWinningsCents) {
		this.totalWinningsCents = totalWinningsCents;
	}

	public long getTotalCommissionPaidCents() {
		return totalCommissionPaidCents;
	}

	public void setTotalCommissionPaidCents(long totalCommissionPaidCents) {
		this.totalCommissionPaidCents = totalCommissionPaidCents;
	}
}
