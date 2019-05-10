package tech.simter.jackson.jsonb;

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
  @Override
  public JsonbBuilder withConfig(JsonbConfig config) {
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
    }

    return cacheMapper;
  }
}