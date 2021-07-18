package com.backend.test.config.Interceptor;

import com.backend.test.helper.LoginTokenHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private LoginTokenHelper loginTokenHelper;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws SignatureException {
        /** 允许option请求通过 **/
        if(request.getMethod().toUpperCase().equals("OPTIONS")){
            return true;
        }
        /** 设置返回头跨域 **/
        crossDomain(request, response);

        /** 地址过滤 */
        String uri = request.getRequestURI() ;
        if(uri.contains("swagger")){
            return true;
        }

        /** Token 验证，放在param或者header都可以**/
        String token = request.getHeader(loginTokenHelper.getHeader());
        String methodName = request.getHeader(loginTokenHelper.getMethodName());
        //如果不在Header就在Params里找
        if(StringUtils.isEmpty(token)){
            token = request.getParameter(loginTokenHelper.getHeader());
            methodName = request.getParameter(loginTokenHelper.getMethodName());
        }

        //Token为空
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(token)){
            throw new SignatureException(loginTokenHelper.getHeader()+ "或方法名为空");
        }

        try{
            if(!loginTokenHelper.isValid(token,methodName)){
                throw new SignatureException(loginTokenHelper.getHeader() + "验证失败");
            }
        }catch (Exception e){
            throw new SignatureException(loginTokenHelper.getHeader() + "验证失败");
        }

        return true;
    }

    public void crossDomain(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
        response.setHeader("Access-Control-Expose-Headers", "Verify-Token");
    }
}
