package com.creolophus.liuyi.common.security;

import com.creolophus.liuyi.common.api.ApiContext;
import com.creolophus.liuyi.common.api.ApiContextValidator;
import com.creolophus.liuyi.common.api.GlobalSetting;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author magicnana
 * @date 2019/5/27 下午6:08
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Resource
    protected UserDetailsService userDetailsService;

    @Resource
    protected ApiContextValidator apiContextValidator;

    @Resource
    private GlobalSetting globalSetting;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {


        preHandle(request);


        String auth_token = request.getHeader(GlobalSetting.HEADER_TOKEN_KEY);
        if(logger.isDebugEnabled()){
            logger.debug(auth_token);
        }

        final String auth_token_start = GlobalSetting.HEADER_TOKEN_PRE + " ";
        if(StringUtils.isNotEmpty(auth_token) && auth_token.startsWith(auth_token_start)) {
            auth_token = auth_token.substring(auth_token_start.length());
            ApiContext.getContext().setToken(auth_token);

            //UserToken userToken = TokenUtil.parse(auth_token);

            UserDetails userDetail;
            if(StringUtils.isNotBlank(auth_token) && (userDetail = userDetailsService.loadUserByUsername(auth_token)) != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail.getUsername(), null, userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                ApiContext.getContext().setUserId(Long.valueOf(userDetail.getUsername()));
                if(logger.isDebugEnabled()){
                    logger.debug(userDetail.getUsername());
                }
            }
        }

        chain.doFilter(request, response);

        postHandle(request);
    }

    protected void preHandle(HttpServletRequest request) {

    }

    protected void postHandle(HttpServletRequest request){

    }
}
