/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transfer.dto;


import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author REACH_MFAYEK
 */
@XmlRootElement
public class CommonDTO implements Serializable {

    /** error Message **/
    private String errorMessage;

    /** status **/
    private String status;

    /** Status Message **/
    private String statusMessage;

    /** exception Cause **/
    private String exceptionCause;

    public CommonDTO() {
    }



    /**
     *
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     *
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     *
     * @param statusMessage
     */
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getExceptionCause() {
        return exceptionCause;
    }

    public void setExceptionCause(String exceptionCause) {
        this.exceptionCause = exceptionCause;
    }




}

