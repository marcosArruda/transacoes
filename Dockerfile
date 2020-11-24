FROM adoptopenjdk/openjdk11:latest as build
WORKDIR /workspace/app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
RUN ./gradlew dependencies
EXPOSE 8080
COPY src src
RUN ./gradlew clean build -x test
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
COPY entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/entrypoint.sh
RUN ln -s /usr/local/bin/entrypoint.sh /workspace/app/entrypoint.sh

FROM adoptopenjdk/openjdk11:latest
ENV SPRING_PROFILE local
ENV DB_HOST mongo
ENV DB_PORT 27017
ENV DB_NAME local
ENV DB_USERNAME root
ENV DB_PASSWORD example
VOLUME /tmp
ARG DEPENDENCY_DIR=/workspace/app/build/dependency
ARG ENTRYSCRIPT_DIR=/workspace/app
COPY --from=build ${DEPENDENCY_DIR}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY_DIR}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY_DIR}/BOOT-INF/classes /app
COPY --from=build ${ENTRYSCRIPT_DIR}/entrypoint.sh /app/entrypoint.sh

ENTRYPOINT ["/app/entrypoint.sh"]