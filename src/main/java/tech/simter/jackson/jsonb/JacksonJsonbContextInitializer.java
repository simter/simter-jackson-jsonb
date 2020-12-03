package tech.simter.jackson.jsonb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * A simple bean for get {@link ObjectMapper} instance from this context
 * and set it by {@link JacksonJsonbUtils#setObjectMapper(ObjectMapper)}.
 *
 * @author RJ
 */
@Named
@Singleton
public class JacksonJsonbContextInitializer {
  private static final Logger logger = LoggerFactory.getLogger(JacksonJsonbContextInitializer.class);
  @Inject
  private ObjectMapper objectMapper;

  @PostConstruct
  public void init() {
    logger.info("Use injected ObjectMapper instance {} for simter-jackson-jsonb", objectMapper.hashCode());
    JacksonJsonbUtils.setObjectMapper(objectMapper);
  }
}