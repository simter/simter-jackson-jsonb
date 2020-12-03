package tech.simter.jackson.jsonb.javatimemodule

import com.fasterxml.jackson.annotation.JsonFormat
import net.javacrumbs.jsonunit.assertj.JsonAssertion
import net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import tech.simter.jackson.jsonb.JacksonJsonbUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.json.bind.Jsonb
import javax.json.bind.JsonbBuilder

/**
 * Test JSON-B serialize JavaTime with jackson JavaTimeModule.
 *
 * @author RJ
 */
class JacksonJavaTimeModuleTest @Autowired constructor() {
  data class DateTimeExample(
    val localDate: LocalDate,
    @get:JsonFormat(pattern = "yyyy年MM月dd日") // 自定义格式
    val localDate2: LocalDate,
    val localDate3: LocalDate?, // 允许空值
    val localTime: LocalTime,
    val localDateTime: LocalDateTime
  )

  private val now = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS)!!
  private val now2Day = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))!!
  private val now2Minute = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd\'T\'HH:mm"))!!
  private val now2Second = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd\'T\'HH:mm:ss"))!!
  private val now2Cn = now.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))!!
  private lateinit var jsonb: Jsonb

  @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
  @BeforeEach
  fun setUp() {
    JacksonJsonbUtils.setObjectMapper(null) // remove ObjectMapper instance cache
    jsonb = JsonbBuilder.create()!!
  }

  @Test
  fun `single value`() {
    val localDateTime = LocalDateTime.of(2010, 1, 2, 13, 20, 30)
    assertEquals("\"2010-01-02T13:20:30\"", JsonbBuilder.create().toJson(localDateTime))
  }

  @Test
  fun `bean value`() {
    // init data
    val example = DateTimeExample(
      localDate = now.toLocalDate(),
      localDate2 = now.toLocalDate(),
      localDate3 = null,
      localTime = now.toLocalTime(),
      localDateTime = now.toLocalDateTime()
    )
    val json = jsonb.toJson(example)
    assertThatJson(json).and(
      JsonAssertion { it.node("localDate").isEqualTo(now2Day) }, // yyyy-MM-dd
      JsonAssertion { it.node("localDate2").isEqualTo(now2Cn) }, // yyyy年MM月dd日
      JsonAssertion { it.node("localDate3").isAbsent() }, // 空值默认不输出
      JsonAssertion { it.node("localTime").isEqualTo(now2Second.substring(11, 19)) }, // HH:mm:ss
      JsonAssertion { it.node("localDateTime").isEqualTo(now2Second) } // yyyy-MM-ddTHH:mm:ss
    )
  }
}