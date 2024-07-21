package com.burukeyou.demo.api;

import com.burukeyou.uniapi.http.annotation.HttpApi;
import com.burukeyou.uniapi.http.annotation.param.QueryPar;
import com.burukeyou.uniapi.http.annotation.request.GetHttpInterface;
import com.burukeyou.uniapi.http.core.channel.HttpApiMethodInvocation;
import com.burukeyou.uniapi.http.core.channel.HttpSender;
import com.burukeyou.uniapi.http.core.request.HttpMetadata;
import com.burukeyou.uniapi.http.core.response.HttpResponse;
import com.burukeyou.uniapi.http.extension.HttpApiProcessor;
import com.burukeyou.uniapi.support.arg.MethodArgList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ReflectionUtils;

import javax.script.ScriptEngineManager;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@HttpApi(url = "http://127.0.0.1:8999")
public interface SimpleServiceApi {

    @GetHttpInterface("/user-web/add")
    String add(@QueryPar("name") String name);


    @GetHttpInterface(path = "/user-web/add",processor = MyHttpProcessor.class)
    String add2(@QueryPar("name") String name);


    @Slf4j
    class MyHttpProcessor implements HttpApiProcessor<Annotation> {

        @Override
        public HttpMetadata postBeforeHttpMetadata(HttpMetadata httpMetadata, HttpApiMethodInvocation<Annotation> methodInvocation) {
            try {
                Object[] arguments = methodInvocation.getArguments();
                Object aThis = methodInvocation.getThis();
                Method method = methodInvocation.getMethod();

                Object proceed = methodInvocation.proceed();
                System.out.println();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }


            return httpMetadata;
        }

        @Override
         public HttpResponse<?> postSendingHttpRequest(HttpSender httpSender, HttpMetadata httpMetadata, HttpApiMethodInvocation<Annotation> methodInvocation) {
             HttpResponse<?> rsp = HttpApiProcessor.super.postSendingHttpRequest(httpSender, httpMetadata, methodInvocation);
             log.info("请求日志 {}",rsp.toHttpProtocol());
             return rsp;
         }
     }



     default String getxxxx(String aa){
        return "999-" + aa;
     }

    public static void main(String[] args) {
        Method add2 = ReflectionUtils.findMethod(SimpleServiceApi.class, "add2", String.class);

        String name = add2.getParameters()[0].getName();
        System.out.println();
    }

}

class AA implements SimpleServiceApi {

    @Override
    public String add(String name) {
        return null;
    }

    @Override
    public String add2(String name) {
        return null;
    }

    @Override
    public String getxxxx(String aa) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(String.class, String.class);

            // 获取父接口的方法句柄
            MethodHandle methodHandle = lookup.findSpecial(SimpleServiceApi.class, "getxxxx", methodType, getClass());

            return (String) methodHandle.invoke(this, aa);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }
}

