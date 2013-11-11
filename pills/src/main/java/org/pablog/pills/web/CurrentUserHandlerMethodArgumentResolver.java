package org.pablog.pills.web;

import java.security.Principal;

import org.pablog.pills.domain.User;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Autowired UserRepository userRepository;
	
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
         return
             methodParameter.getParameterAnnotation(ActiveUser.class) != null
             && methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                        ModelAndViewContainer mavContainer,
                        NativeWebRequest webRequest,
                        WebDataBinderFactory binderFactory) throws Exception {

         if (this.supportsParameter(methodParameter)) {
             Principal principal = webRequest.getUserPrincipal();
             
             return userRepository.findByUsername(principal.getName());
         } else {
             return WebArgumentResolver.UNRESOLVED;
         }
    }

}
