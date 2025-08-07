FROM gradle:8-jdk21-alpine

WORKDIR /usr/src/p-movies/backend

COPY gradlew .
COPY gradle gradle

COPY build.gradle.kts settings.gradle.kts ./

RUN ./gradlew dependencies --no-daemon || true

COPY . .

CMD ["./entrypoint.sh"]
