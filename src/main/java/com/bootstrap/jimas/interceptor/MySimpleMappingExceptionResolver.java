package com.bootstrap.jimas.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.jimas.common.ResultVo;
import com.jimas.common.util.GsonUtil;
@Component
public class MySimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	
    private static final Logger log = Logger.getLogger(MySimpleMappingExceptionResolver.class);
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex) {
		String viewName = determineViewName(ex, request);
		if(null==viewName) viewName = "404";
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || 
				(request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			// 如果不是异步请求
			// Apply HTTP status code for error views, if specified.
			// Only apply it if we're processing a top-level request.
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
				if(500==statusCode.intValue()){
					viewName = "404";
				}
			}
			return getModelAndView(viewName, ex, request);
		} else {// JSON格式返回
			try {
				log.error("ModelAndView("+viewName+") error[500]:", ex);
				ResultVo<String> result = new ResultVo<String>();
				result.setStatus(500);
				result.setMessage("服务异常");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(GsonUtil.toJsonString(result));
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
