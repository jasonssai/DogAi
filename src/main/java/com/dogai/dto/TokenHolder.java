package com.dogai.dto;


import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "token_holders")
public class TokenHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tokenAddress")
    private String tokenAddress;

    @Column(name = "tokenAccountAddress", unique = true)
    String tokenAccountAddress;

    @Column(name = "tokenAccountOwnerAddress")
    String tokenAccountOwnerAddress;

    @Column(name = "amount", precision = 38)
    BigInteger amount;

    @Column(name = "tokenDecimals")
    private int tokenDecimals;

    @Column(name = "tokenAmountRank", unique = true)
    private int tokenAmountRank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public String getTokenAccountAddress() {
        return tokenAccountAddress;
    }

    public void setTokenAccountAddress(String tokenAccountAddress) {
        this.tokenAccountAddress = tokenAccountAddress;
    }

    public String getTokenAccountOwnerAddress() {
        return tokenAccountOwnerAddress;
    }

    public void setTokenAccountOwnerAddress(String tokenAccountOwnerAddress) {
        this.tokenAccountOwnerAddress = tokenAccountOwnerAddress;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public int getTokenDecimals() {
        return tokenDecimals;
    }

    public void setTokenDecimals(int tokenDecimals) {
        this.tokenDecimals = tokenDecimals;
    }

    public int getTokenAmountRank() {
        return tokenAmountRank;
    }

    public void setTokenAmountRank(int tokenAmountRank) {
        this.tokenAmountRank = tokenAmountRank;
    }

    @Override
    public String toString() {
        return "TokenHolder{" +
                "id=" + id +
                ", tokenAddress='" + tokenAddress + '\'' +
                ", tokenAccountAddress='" + tokenAccountAddress + '\'' +
                ", tokenAccountOwnerAddress='" + tokenAccountOwnerAddress + '\'' +
                ", amount=" + amount +
                ", tokenDecimals=" + tokenDecimals +
                ", tokenAmountRank=" + tokenAmountRank +
                '}';
    }
}
