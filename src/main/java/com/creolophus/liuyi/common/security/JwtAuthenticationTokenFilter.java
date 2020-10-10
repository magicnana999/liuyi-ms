package com.creolophus.liuyi.common.security;

import com.creolophus.liuyi.common.api.Api;
import com.creolophus.liuyi.common.api.ApiContext;
import com.creolophus.liuyi.common.api.ApiContextValidator;
import com.creolophus.liuyi.common.api.GlobalSetting;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author magicnana
 * @date 2019/5/27 下午6:08
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    private static final ConcurrentHashMap<String, Api.Scope> apiScopeTable = new ConcurrentHashMap();

    @PostConstruct
    public void init() {
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            HandlerMethod hm = entry.getValue();
            for (String uri : entry.getKey().getPatternsCondition().getPatterns()) {
                Api api = hm.getMethodAnnotation(Api.class);
                if(api != null) {
                    apiScopeTable.putIfAbsent(uri, api.scope());

                }
            }
        }
    }

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Resource
    protected UserDetailsService userDetailsService;

    @Resource
    protected ApiContextValidator apiContextValidator;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {


        preHandle(request);

        handle(request);
//
//
//        String auth_token = request.getHeader(GlobalSetting.HEADER_TOKEN_KEY);
//        if(logger.isDebugEnabled()){
//            logger.debug(auth_token);
//        }
//
//        final String auth_token_start = GlobalSetting.HEADER_TOKEN_PRE + " ";
//        if(StringUtils.isNotEmpty(auth_token) && auth_token.startsWith(auth_token_start)) {
//            auth_token = auth_token.substring(auth_token_start.length());
//            ApiContext.getContext().setToken(auth_token);
//
//            //UserToken userToken = TokenUtil.parse(auth_token);
//
//            UserDetails userDetail;
//            if(StringUtils.isNotBlank(auth_token) && (userDetail = userDetailsService.loadUserByUsername(auth_token)) != null) {
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail.getUsername(), null, userDetail
// .getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                ApiContext.getContext().setUserId(Long.valueOf(userDetail.getUsername()));
//                if(logger.isDebugEnabled()){
//                    logger.debug(userDetail.getUsername());
//                }
//            }
//        }
//
        chain.doFilter(request, response);

        postHandle(request);
    }

    protected void preHandle(HttpServletRequest request) {
        apiContextValidator.initContext(request);
    }

    protected void postHandle(HttpServletRequest request) {

    }

    private void handle(HttpServletRequest request) {

        String uri = request.getRequestURI();
        Api.Scope scope = apiScopeTable.get(uri);
        if(scope == null) {
            return;
        }

        UserDetails ud = handle(request, scope);
        if(ud != null) {
            UsernamePasswordAuthenticationToken au = new UsernamePasswordAuthenticationToken(ud.getUsername(), null, ud.getAuthorities());
            au.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(au);
            ApiContext.getContext().setUserId(Long.valueOf(ud.getUsername()));
            if(logger.isDebugEnabled()) {
                logger.debug("{}:{}", ud.getUsername(), ud.getAuthorities());
            }
        }


//        String auth_token = request.getHeader(GlobalSetting.HEADER_TOKEN_KEY);
//        if(logger.isDebugEnabled()) {
//            logger.debug(auth_token);
//        }
//
//        final String auth_token_start = GlobalSetting.HEADER_TOKEN_PRE + " ";
//        if(StringUtils.isNotEmpty(auth_token) && auth_token.startsWith(auth_token_start)) {
//            auth_token = auth_token.substring(auth_token_start.length());
//            ApiContext.getContext().setToken(auth_token);
//
//            //UserToken userToken = TokenUtil.parse(auth_token);
//
//            UserDetails userDetail;
//            if(StringUtils.isNotBlank(auth_token) && (userDetail = userDetailsService.loadUserByUsername(auth_token)) != null) {
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail.getUsername(), null,
//                                                                                                             userDetail.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                ApiContext.getContext().setUserId(Long.valueOf(userDetail.getUsername()));
//                if(logger.isDebugEnabled()) {
//                    logger.debug(userDetail.getUsername());
//                }
//            }
//        }
    }

    private UserDetails handle(HttpServletRequest request, Api.Scope scope) {
        switch (scope) {
            case OPEN:
                ApiContext.getContext().setToken(Api.Scope.OPEN.toString());
                return defaultUserDetail();
            case INTERNAL:
                return handleInternalApi(request, scope);
            case PUBLIC:
                return handlePublicApi(request, scope);
            default:
                throw new RuntimeException("未知的 scope " + scope);
        }
    }

    private UserDetails handleInternalApi(HttpServletRequest request, Api.Scope scope) {
        String value = request.getHeader(GlobalSetting.HEADER_INTERNAL_KEY);
        if(StringUtils.isNotBlank(value) && value.equals(GlobalSetting.HEADER_INTERNAL_VAL)) {
            ApiContext.getContext().setToken(value);
            return defaultUserDetail();
        } else {
            return null;
        }
    }

    private UserDetails handlePublicApi(HttpServletRequest request, Api.Scope scope) {
        String auth_token = request.getHeader(GlobalSetting.HEADER_TOKEN_KEY);
        if(logger.isDebugEnabled()) {
            logger.debug("token:{}", auth_token);
        }

        final String auth_token_start = GlobalSetting.HEADER_TOKEN_PRE + " ";
        if(StringUtils.isNotEmpty(auth_token) && auth_token.startsWith(auth_token_start)) {
            auth_token = auth_token.substring(auth_token_start.length());
            ApiContext.getContext().setToken(auth_token);
            if(StringUtils.isNotBlank(auth_token)) {
                return userDetailsService.loadUserByUsername(auth_token);
            }
        }
        return null;
    }


    private UserDetails defaultUserDetail() {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Arrays.asList(new SimpleGrantedAuthority("ROLE_user"));
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return "0";
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }
}
