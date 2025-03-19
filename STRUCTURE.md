📦 my-quarkus-app
┣ 📂 src
┃ ┣ 📂 main
┃ ┃ ┣ 📂 java/com/example
┃ ┃ ┃ ┣ 📂 application  # (Business Logic)
┃ ┃ ┃ ┃ ┣ 📂 dto        # (Data Transfer Objects)
┃ ┃ ┃ ┃ ┣ 📂 service    # (Business Services)
┃ ┃ ┃ ┣ 📂 domain       # (Entities and Core Domain)
┃ ┃ ┃ ┃ ┣ 📂 model      # (JPA Entities)
┃ ┃ ┃ ┃ ┣ 📂 repository # (JPA Repositories)
┃ ┃ ┃ ┣ 📂 infrastructure  # (External Integrations)
┃ ┃ ┃ ┃ ┣ 📂 config     # (Configurations)
┃ ┃ ┃ ┃ ┣ 📂 persistence # (JPA Implementation, DAO if needed)
┃ ┃ ┃ ┣ 📂 rest         # (Controllers / Endpoints)
┃ ┃ ┣ 📂 resources
┃ ┃ ┃ ┣ 📜 application.properties (or application.yaml)
┃ ┃ ┃ ┣ 📜 import.sql (if needed for DB init)
┣ 📂 test
┃ ┣ 📂 java/com/example
┃ ┃ ┣ 📂 integration  # (Integration Tests)
┃ ┃ ┣ 📂 unit         # (Unit Tests)
┃ ┣ 📂 resources
┃ ┃ ┣ 📜 application-test.properties
┣ 📜 pom.xml  # (Maven dependencies)
┣ 📜 Dockerfile (if containerized)
┣ 📜 README.md