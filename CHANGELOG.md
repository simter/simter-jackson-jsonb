# simter-jackson-jsonb changelog

## 0.7.0 - 2022-06-21

- Upgrade to simter-dependencies-3.0.0 (jdk-17)

## 0.6.0 - 2020-12-03

- Upgrade to simter-dependencies-2.0.0
- Make JacksonJsonbUtils.objectMapper public
- Add some logging config
- Auto register JavaTimeModule by java service
    > `mapper.findAndRegisterModules()` will do all the things
- Add unit test for simter and jackson JavaTimeModule

## 0.5.0 - 2019-07-03

No code changed, just polishing maven config and unit test.

- Use MockK instead of Mockito
- Change parent to simter-dependencies-1.2.0

## 0.4.0 2019-05-12

- Set below default jackson config:
    - Set serialization inclusion to `NON_EMPTY`
    - Disable some features:
        - DeserializationFeature.`FAIL_ON_UNKNOWN_PROPERTIES`
        - DeserializationFeature.`ADJUST_DATES_TO_CONTEXT_TIME_ZONE`
        - SerializationFeature.`WRITE_DATES_AS_TIMESTAMPS`
    - Enable feature DeserializationFeature.`ACCEPT_EMPTY_STRING_AS_NULL_OBJECT`
    - Add a custom `JavaTimeModule` by [simter-jackson-javatime]. The default data-time format is like '`yyyy-MM-dd HH:mm`', accurate to minute and without zone and offset info (global use local zone and offset default)

## 0.3.0 2019-05-11

- Support get ObjectMapper instance from DI context bean
- Add `JacksonJsonbUtils.DEFAULT_PROPERTY_INCLUSION = "jsonb.jackson.default-property-inclusion"` for config `ObjectMapper#setDefaultPropertyInclusion(JsonInclude.Include)`

## 0.2.0 2019-05-10

- Auto register jackson modules by dependencies, see [jackson-modules-java8](https://github.com/FasterXML/jackson-modules-java8)
- Support jackson `JsonInclude.Include` enum config for `ObjectMapper.setDefaultPropertyInclusion(...)`

## 0.1.0 2019-05-10

- Very basic json-b support by simple use "new ObjectMapper()"

[simter-jackson-javatime]: https://github.com/simter/simter-jackson-javatime