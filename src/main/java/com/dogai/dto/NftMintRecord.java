package com.dogai.dto;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "nft_mint_record")
public class NftMintRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "tx_hash", unique = true)
    String txHash;

    @Column(name = "token_address")
    String tokenAddress;

    @Column(name = "status")
    int status = 2;

    @Column(name = "created_time")
    Date createdTime;

    public NftMintRecord(String address, String txHash) {
        this.address = address;
        this.txHash = txHash;
        this.createdTime = new Date();
    }

    public NftMintRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public int getStatus() {
        return status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "NftMintRecord{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", txHash='" + txHash + '\'' +
                ", status=" + status +
                ", createdTime=" + createdTime +
                '}';
    }
}
