package tech.simter.jackson.jsonb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.spi.JsonProvider;

/**
 * The {@link JsonbBuilder} implementation by Jackson.
 *
 * @author RJ
 */
class JacksonJsonbBuilder implements JsonbBuilder {
  /**
   * Property name for config jackson through {@link ObjectMapper#setDefaultPropertyInclusion(JsonInclude.Include)}.
   * <p>
   * All valid values are the enum name of {@link JsonInclude.Include}.
   */
  static final String DEFAULT_PROPERTY_INCLUSION = "jsonb.jackson.default-property-inclusion";

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

  private ObjectMapper getMapper() {
    if (cacheMapper == null) {
      // create a new ObjectMapper instance
      cacheMapper = new ObjectMapper();

      // config jackson ObjectMapper by JsonbConfig
      if (config != null) {
        // set default property inclusion
        config.getProperty(DEFAULT_PROPERTY_INCLUSION).ifPresent(value ->
          cacheMapper.setDefaultPropertyInclusion(JsonInclude.Include.valueOf(value.toString()))
        );
      }

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

    return cacheMapper;
  }
}