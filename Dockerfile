FROM openjdk:11

WORKDIR /floozz

COPY src /floozz/src
COPY lib /floozz/lib

RUN javac -cp "lib/*" src/Agents/*.java \
           src/Behaviors/*.java \
           src/Utils/*.java \
           src/Ontology/*.java \
           src/Launcher.java

CMD ["java", "-cp", "/floozz/src:/floozz/lib/*", "Launcher"]
