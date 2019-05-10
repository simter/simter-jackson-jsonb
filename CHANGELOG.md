# simter-jsonb-provider-jackson changelog

## 0.2.0 2019-05-10

- Auto register jackson modules by dependencies, see [jackson-modules-java8](See https://github.com/FasterXML/jackson-modules-java8)
- Support jackson `JsonInclude.Include` enum config for `ObjectMapper.setDefaultPropertyInclusion(...)`

## 0.1.0 2019-05-10

- Very basic json-b support by simple use "new ObjectMapper()"