package com.burukeyou.uniapi.http.extension.processor;

import java.lang.annotation.Annotation;

import com.burukeyou.uniapi.http.core.channel.HttpApiMethodInvocation;
import com.burukeyou.uniapi.http.core.channel.HttpSender;
import com.burukeyou.uniapi.http.core.response.UniHttpResponse;
import com.burukeyou.uniapi.http.core.request.HttpMetadata;

/**
 * HttpAPI lifecycle processor Extension point
 *
 * execution flow:
 *
 * <pre>
 *
 *                postBeforeHttpMetadata
 *                        |
 *                        V
 *                 postSendingHttpRequest
 *                        |
 *                        V
 *              postAfterHttpResponseBodyString
 *                        |
 *                        V
 *             postAfterHttpResponseBodyResult
 *                        |
 *                        V
 *             postAfterMethodReturnValue
 *</pre>
 *
 * @author caizhihao
 * @param <T>
 */
public interface HttpApiProcessor<T extends Annotation> {

    /**
     * Before sending the request
     *          you can revise the requested data for HttpMetadata
     *          if return null will stop to send the request
     * @param httpMetadata              request data
     * @param methodInvocation          the method of proxy execution
     * @return                          the new request data
     */
    default HttpMetadata postBeforeHttpMetadata(HttpMetadata httpMetadata, HttpApiMethodInvocation<T> methodInvocation){
        return httpMetadata;
    }

    /**
     * When sending HTTP requests
     *          Send an HTTP request using HttpMetadata
     * @param httpSender                 Request Sender
     * @param httpMetadata               request data
     */
    default UniHttpResponse postSendingHttpRequest(HttpSender httpSender, HttpMetadata httpMetadata, HttpApiMethodInvocation<T> methodInvocation){
        return httpSender.sendHttpRequest(httpMetadata);
    }

    /**
     * Post-processing of HTTP response body string, when content-type is text
     * @param bodyString          http body string
     * @param rsp                 Original  Http Response
     * @param methodInvocation    The method of agency
     * @param httpMetadata        request data
     * @return                    the new response body string
     */
    default String postAfterHttpResponseBodyString(String bodyString, UniHttpResponse rsp, HttpApiMethodInvocation<T> methodInvocation){
        return bodyString;
    }


    /**
     * Post-processing of HTTP response body objects
     * @param bodyResult                     response body object。
     *                                       The specific object deserialized by the HTTP response body,
     *                                       with the specific type being the return value type of the proxy method
     * @param rsp                            Original  Http Response
     * @param methodInvocation               The method of agency
     * @return                               the new response body object。
     */
    default Object postAfterHttpResponseBodyResult(Object bodyResult, UniHttpResponse rsp, HttpApiMethodInvocation<T> methodInvocation){
        return bodyResult;
    }

    /**
     * The post-processing method returns a value, similar to the post-processing of AOP
     * @param methodReturnValue             Method return value
     * @param rsp                           Original  Http Response
     * @param methodInvocation              The method of agency
     * @param httpMetadata                  request data
     * @return                              the new Method return value
     */
    default Object postAfterMethodReturnValue(Object methodReturnValue, UniHttpResponse rsp, HttpApiMethodInvocation<T> methodInvocation){
        return methodReturnValue;
    }

}
