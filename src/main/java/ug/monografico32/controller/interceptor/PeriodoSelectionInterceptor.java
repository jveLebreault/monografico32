package ug.monografico32.controller.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PeriodoSelectionInterceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler)throws Exception{

        HttpSession session = request.getSession();

        if ( session.getAttribute("periodo") == null ){
            String servletPath = request.getServletPath();
            
            if(request.getQueryString() != null)
                servletPath = servletPath.concat("?").
                                            concat(request.getQueryString());
            
            request.setAttribute("previousServletPath", servletPath);
            
            request.getRequestDispatcher("/periodo/seleccionar").
                    forward(request, response);

            return false;
        }

        return true;

    }
}
