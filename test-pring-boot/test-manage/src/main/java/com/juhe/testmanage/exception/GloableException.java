package com.juhe.testmanage.exception;

import com.juhe.testcommon.pojo.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

@ResponseBody
@ControllerAdvice
public class GloableException {

    private Logger logger= LoggerFactory.getLogger(GloableException.class);

    @ExceptionHandler(RuntimeException.class)
    public ReturnData doHandleRuntimeException(RuntimeException e) {
        e.printStackTrace();

        /**
         * 错误日志输出
         */
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String errMsg = sw.toString();
        logger.error(errMsg);

        ReturnData returnData = new ReturnData();
         returnData.setSuccess(false);
        if(e instanceof BadSqlGrammarException){
            returnData.setResultMessage("系统异常");
        }else if(e instanceof SelfException) {
            returnData.setResultMessage(e.getMessage());
        }else if(e instanceof RuntimeException) {
            returnData.setResultMessage("系统异常");
        }else{
            returnData.setResultMessage("系统异常");
        }
        return returnData;
    }
}
