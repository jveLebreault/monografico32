package ug.monografico32.controller.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Jose Elias on 23/05/2017.
 */
public class PeriodoSelectionInterceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler)throws Exception{

        HttpSession session = request.getSession();

        if ( session.getAttribute("periodo") == null ){
            String servletPath = request.getServletPath();

            request.setAttribute("previousServletPath", servletPath);
            
            request.getRequestDispatcher("/periodo/seleccionar").
                    forward(request, response);

            return false;
        }

        return true;

    }
}
