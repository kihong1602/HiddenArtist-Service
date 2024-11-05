package com.hiddenartist.backend.global.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

  private final ObjectWriter objectWriter;

  public ControllerLoggingAspect(ObjectMapper objectMapper) {
    this.objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
  }

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  private void restControllerPointCut() {
  }

  @Around("restControllerPointCut()")
  public Object loggingControllerAround(ProceedingJoinPoint joinPoint) throws Throwable {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String principal = Objects.requireNonNullElse(authentication.getPrincipal().toString(), "Anonymous");

    Object result = null;
    try {
      result = joinPoint.proceed(joinPoint.getArgs());
      return result;
    } finally {
      log.info("{} | Principal: {}", getRequestUrl(joinPoint), principal);
      log.info("parameters: {}", objectWriter.writeValueAsString(params(joinPoint)));
      log.info("response: {}", objectWriter.writeValueAsString(result));
    }
  }

  private String getRequestUrl(JoinPoint joinPoint) {
    Class<?> clazz = joinPoint.getTarget().getClass();
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
    String baseUrl = requestMapping.value()[0];
    return Stream.of(GetMapping.class, PostMapping.class, PutMapping.class, PatchMapping.class, DeleteMapping.class,
                     RequestMapping.class)
                 .filter(method::isAnnotationPresent)
                 .map(mappingClass -> getUrl(method, mappingClass, baseUrl))
                 .findFirst()
                 .orElse(null);
  }

  private String getUrl(Method method, Class<? extends Annotation> annotationClass, String baseUrl) {
    Annotation annotation = method.getAnnotation(annotationClass);
    String[] values;
    String httpMethod;
    try {
      values = (String[]) annotationClass.getMethod("value").invoke(annotation);
      httpMethod = annotationClass.getSimpleName().replace("Mapping", "").toUpperCase();
    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      return "Logging Fail";
    }
    return """
        %s %s%s""".formatted(httpMethod, baseUrl, values.length > 0 ? values[0] : "");
  }

  private Map<String, Object> params(JoinPoint joinPoint) {
    CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    String[] parameterNames = codeSignature.getParameterNames();
    Object[] args = joinPoint.getArgs();
    Map<String, Object> params = new HashMap<>();
    for (int i = 0; i < parameterNames.length; i++) {
      Object arg = args[i];
      if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
        continue;
      }
      if (arg instanceof MultipartFile file) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        params.put(parameterNames[i], Map.of("filename", fileName, "size", file.getSize()));
      } else {
        params.put(parameterNames[i], arg);
      }
    }
    return params;
  }

}