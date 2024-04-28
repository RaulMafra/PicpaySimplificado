FROM maven:3.8.3-openjdk-17

ENV PROJECT_HOME /home/app/src

RUN mkdir -p ${PROJECT_HOME}

WORKDIR ${PROJECT_HOME}

COPY . .

RUN mvn clean package -DskipTests

RUN mv ${PROJECT_HOME}/target/PicpaySimplificado-0.0.1-SNAPSHOT.jar ${PROJECT_HOME}

RUN rm -r ${PROJECT_HOME}/target
RUN rm -r ${PROJECT_HOME}/src
RUN rm -r ${PROJECT_HOME}/pom.xml

ENTRYPOINT ["java", "-jar", "PicpaySimplificado-0.0.1-SNAPSHOT.jar"]



