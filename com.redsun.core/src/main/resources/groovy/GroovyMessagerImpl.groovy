package groovy

import com.redsun.platf.groovy.IMessager
import org.springframework.stereotype.Service

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-7-10</br>
 * Time: 上午11:57</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-7-10    joker pan    created
 * <pre/>
 *
 * */

@Service
 class GroovyMessagerImpl  implements IMessager{
    String message
    public String getMessage() {
            // change the implementation to surround the message in quotes
            return "'" + this.message + "'"
        }

        public void setMessage(String message) {
            this.message = message
        }
}
