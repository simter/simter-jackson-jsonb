package tech.simter.jackson.jsonb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbConfig;
import javax.json.spi.JsonProvider;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test {@link JacksonJsonbBuilder}.
 *
 * @author RJ
 */
@ExtendWith(MockitoExtension.class)
class JacksonJsonbBuilderTest {
  @Mock
  private JsonProvider jsonProvider;

  @Test
  @DisplayName("with empty JsonbConfig")
  void withEmptyConfig() {
    JacksonJsonbBuilder builder = new JacksonJsonbBuilder();
    assertSame(builder, builder.withConfig(new JsonbConfig()));

    String actual = builder.build().toJson(new Example());
    assertEquals("{\"anInt\":0,\"str\":null,\"list\":[]}", actual);
  }

  @Test
  @DisplayName("with null JsonbConfig")
  void withNullConfig() {
    JacksonJsonbBuilder builder = new JacksonJsonbBuilder();
    assertSame(builder, builder.withConfig(null));

    String actual = builder.build().toJson(new Example());
    assertEquals("{\"anInt\":0,\"str\":null,\"list\":[]}", actual);
  }

  @Test
  @DisplayName("with not null JsonProvider")
  void withNotNullProvider() {
    JacksonJsonbBuilder builder = new JacksonJsonbBuilder();
    assertSame(builder, builder.withProvider(jsonProvider));
  }

  @Test
  @DisplayName("with null JsonProvider")
  void withNullProvider() {
    JacksonJsonbBuilder builder = new JacksonJsonbBuilder();
    assertSame(builder, builder.withProvider(null));
  }

  @Test
  @DisplayName("should build a not null Jsonb")
  void build() {
    Jsonb jsonb = new JacksonJsonbBuilder().build();
    assertNotNull(jsonb);
  }
}