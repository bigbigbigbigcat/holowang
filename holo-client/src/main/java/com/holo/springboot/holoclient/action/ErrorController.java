package com.holo.springboot.holoclient.action;

import com.holo.springboot.holoclient.beans.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 公共程序异常信息处理类
 * springboot中，MVC错误处理相关的配置内容，在ErrorMvcAutoConfiguration这个自动配置类中
 * 如果是浏览器请求会交给errorHtml方法处理
 *      对应的错误代码404、400、500等，会去templates或static目录中解析对应的页面。
 * 那除了text/html的其他请求都会交给error方法处理（ajax）
 */
@Controller
@RequestMapping("/error")
public class ErrorController extends AbstractErrorController {

    Logger logger= LoggerFactory.getLogger(ErrorController.class);

    public ErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
    }

    @Override
    public String getErrorPath() {
        return null;
    }

    /**
     * 处理浏览器请求的异常
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> model = Collections
                .unmodifiableMap(getErrorAttributes(request, getErrorAttributeOptions()));
        response.setStatus(status.value());
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
    }

    /**
     * 处理非浏览器请求的错误（ajax）
     * @param request
     * @return
     */
    @RequestMapping
    @ResponseBody
    public Result error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new Result(HttpStatus.NO_CONTENT.value(),HttpStatus.NO_CONTENT.getReasonPhrase());
        }
        Map<String, Object> body = getErrorAttributes(request, getErrorAttributeOptions());
        String code = body.get("status").toString();
        String message = body.get("message").toString();
        if (!ObjectUtils.isEmpty(body.get("trace"))){
            logger.error(body.get("trace").toString());
        }
        return new Result(Integer.parseInt(code),message);

//        return new Result(status.value(),status.getReasonPhrase());
    }

    /**
     * 异常信息的选项
     * @return
     */
    protected ErrorAttributeOptions getErrorAttributeOptions() {
        ErrorAttributeOptions of = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE,
                ErrorAttributeOptions.Include.STACK_TRACE,
                ErrorAttributeOptions.Include.EXCEPTION);
        return of;
    }


/*
    protected ErrorAttributeOptions getErrorAttributeOptions(HttpServletRequest request, MediaType mediaType) {
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
        options
        return options;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, options.isIncluded(Include.STACK_TRACE));
        if (Boolean.TRUE.equals(this.includeException)) {
            options = options.including(Include.EXCEPTION);
        }
        if (!options.isIncluded(Include.EXCEPTION)) {
            errorAttributes.remove("exception");
        }
        if (!options.isIncluded(Include.STACK_TRACE)) {
            errorAttributes.remove("trace");
        }
        if (!options.isIncluded(Include.MESSAGE) && errorAttributes.get("message") != null) {
            errorAttributes.put("message", "");
        }
        if (!options.isIncluded(Include.BINDING_ERRORS)) {
            errorAttributes.remove("errors");
        }
        return errorAttributes;
    }*/
}
