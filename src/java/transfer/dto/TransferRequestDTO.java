/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.dto;

import java.math.BigDecimal;

/**
 *
 * @author REACH_MFAYEK
 */
public class TransferRequestDTO {
    
    private Integer yourAccount;
    
    private Integer toAccount;
    
    private BigDecimal transferredAmount;

    public Integer getYourAccount() {
        return yourAccount;
    }

    public void setYourAccount(Integer yourAccount) {
        this.yourAccount = yourAccount;
    }

    public Integer getToAccount() {
        return toAccount;
    }

    public void setToAccount(Integer toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getTransferredAmount() {
        return transferredAmount;
    }

    public void setTransferredAmount(BigDecimal transferredAmount) {
        this.transferredAmount = transferredAmount;
    }
    
    
    
}
