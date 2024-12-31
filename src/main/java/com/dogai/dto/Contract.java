package com.dogai.dto;


import javax.persistence.*;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * TokenAddress
     */
    @Column(name = "token_address")
    private String tokenAddress;

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

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", tokenAddress='" + tokenAddress + '\'' +
                '}';
    }
}
