/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.ws.rest.service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import transfer.bus.AccountHelper;
import transfer.bus.TransferRequestHelper;
import transfer.dto.*;
import transfer.entities.Accounts;
import transfer.entities.TransferRequest;

/**
 * REST Web Service
 *
 * @author REACH_MFAYEK
 */
@Path("/account/")
@RequestScoped
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of
     * transfer.ws.rest.service.GenericResource
     *
     * @return an instance of ae.dm.common.dto.CommonDTO
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    @Path("/createAccount/")
    public CommonDTO createAccount(AccountRequestDTO accountRequestDTO) throws Exception {

        CommonDTO commonDTO = new CommonDTO();
        try {
            if (accountRequestDTO == null
                    || accountRequestDTO.getAccountId() == null
                    || accountRequestDTO.getBalance() == null
                    || accountRequestDTO.getEmployeeName() == null) {
                throw new Exception("Account Id or Balance or Employee Name are missing");
            }

            Accounts account = new Accounts();
            account.setAccountId(accountRequestDTO.getAccountId());
            account.setEmployeeName(accountRequestDTO.getEmployeeName());
            account.setBalance(accountRequestDTO.getBalance());
            AccountHelper accountHelper = new AccountHelper();

            if (accountHelper.findByAccountId(accountRequestDTO.getAccountId().intValue()) != null) {
                throw new Exception("Account Id already avaliable : (" + accountRequestDTO.getAccountId().intValue() + ")");
            }

            accountHelper.createAccount(account);
            commonDTO.setStatus("Success");
            commonDTO.setStatusMessage("Account Created Successfully");

            return commonDTO;
        } catch (Exception ex) {
            // if failed
            commonDTO.setStatus("failed");
            commonDTO.setStatusMessage("Failed To Create Account");
            commonDTO.setErrorMessage(ex.getMessage());
            if (ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
                String exceptionCause = ex.getStackTrace()[0].toString();
                commonDTO.setExceptionCause(exceptionCause);
            }
            return commonDTO;
        }
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    @Path("/transferMoney/")
    public CommonDTO transferMoney(TransferRequestDTO transferRequestDTO) throws Exception {

        CommonDTO commonDTO = new CommonDTO();
        try {
            if (transferRequestDTO == null
                    || transferRequestDTO.getYourAccount() == null
                    || transferRequestDTO.getToAccount() == null
                    || transferRequestDTO.getTransferredAmount() == null) {
                throw new Exception("YourAccount or ToAccount or TransferredAmount are missing");
            }
            
            AccountHelper accountHelper = new AccountHelper();
            Accounts yourAccount = accountHelper.findByAccountId(transferRequestDTO.getYourAccount().intValue());
            Accounts ToAccount = accountHelper.findByAccountId(transferRequestDTO.getToAccount().intValue());
            if (yourAccount == null) {
                throw new Exception("Your Account value is not valid Account");
            }
              
            if (ToAccount == null) {
                throw new Exception("To Account value is not valid Account");
            }
            
            if (transferRequestDTO.getYourAccount().doubleValue() == transferRequestDTO.getToAccount().doubleValue()) {
                throw new Exception("You Should Enter Two Different Accounts"); 
            }
            
            if (yourAccount.getBalance().doubleValue() < transferRequestDTO.getTransferredAmount().doubleValue()) {
                throw new Exception("you don't have available balance in your current account ");
            }
            
            yourAccount.setBalance(yourAccount.getBalance().subtract(transferRequestDTO.getTransferredAmount()));
            ToAccount.setBalance(ToAccount.getBalance().add(transferRequestDTO.getTransferredAmount()));
            
            TransferRequest transferRequest = new TransferRequest();
            transferRequest.setAccountFromId(yourAccount);
            transferRequest.setAccountToId(ToAccount);
            transferRequest.setTransferredAmount(transferRequestDTO.getTransferredAmount());
            
            System.out.println("yourAccount=" + yourAccount.getBalance().doubleValue());
            System.out.println("ToAccount= "  + ToAccount.getBalance().doubleValue());
            TransferRequestHelper transferRequestHelper = new TransferRequestHelper();
            transferRequestHelper.createTransferRequestAndUpdateAccountData(transferRequest);
            
            commonDTO.setStatus("Success");
            commonDTO.setStatusMessage("Transfered Money Successfully");
            return commonDTO;
        } catch (Exception ex) {
            // if failed
            commonDTO.setStatus("failed");
            commonDTO.setStatusMessage("Failed To Transfered Money");
            commonDTO.setErrorMessage(ex.getMessage());
            if (ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
                String exceptionCause = ex.getStackTrace()[0].toString();
                commonDTO.setExceptionCause(exceptionCause);
            }
            return commonDTO;
        }
    }
}
