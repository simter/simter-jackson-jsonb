package tech.simter.jackson.jsonb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.spi.JsonProvider;

import static tech.simter.jackson.jsonb.JacksonJsonbUtils.DEFAULT_PROPERTY_INCLUSION;

/**
 * The {@link JsonbBuilder} implementation by Jackson.
 *
 * @author RJ
 */
class JacksonJsonbBuilder implements JsonbBuilder {
  private JsonbConfig config;

  @Override
  public JsonbBuilder withConfig(JsonbConfig config) {
    this.config = config;
    return this;
  }

  @Override
  public JsonbBuilder withProvider(JsonProvider jsonProvider) {
    return this;
  }

  @Override
  public Jsonb build() {
    return new JacksonJsonb(getMapper());
  }

  private ObjectMapper cacheMapper;

  ObjectMapper getMapper() {
    // get from di context bean
    cacheMapper = JacksonJsonbUtils.getObjectMapper();

    if (cacheMapper == null) {
      // create a new ObjectMapper instance
      cacheMapper = new ObjectMapper();

      try {
        // register jackson JavaTimeModule before findAndRegisterModules
        // because jackson-2.x auto-registration will only register older JSR310Module
        cacheMapper.registerModule((Module)
          Class.forName("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule")
            .getDeclaredConstructor().newInstance()
        );
      } catch (Exception ignored) {
      }

      // auto register jackson modules by dependencies
      // see https://github.com/FasterXML/jackson-modules-java8
      cacheMapper.findAndRegisterModules();
    }

    // config jackson ObjectMapper by JsonbConfig
    if (config != null) {
      // set default property inclusion
      config.getProperty(DEFAULT_PROPERTY_INCLUSION).ifPresent(value ->
        cacheMapper.setDefaultPropertyInclusion(JsonInclude.Include.valueOf(value.toString()))
      );
    }

    return cacheMapper;
  }
}