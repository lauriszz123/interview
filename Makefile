# Makefile for Spring Boot interview project

MAVEN_CMD = mvn
PROJECT_NAME = interview
VERSION = 0.0.1
JAR_FILE = target/$(PROJECT_NAME)-$(VERSION)-SNAPSHOT.jar

.PHONY: help
help:
	@echo "Available targets:"
	@echo "  clean  - Clean the project"
	@echo "  build  - Built the project" 
	@echo "  run    - Run the project locally"
	@echo "  help   - Show this help message"

.PHONY: clean
clean:
	@echo "Cleaning project..."
	$(MAVEN_CMD) clean

.PHONY: build
build:
	@echo "Building project..."
	$(MAVEN_CMD) clean compile package -DskipTests

.PHONY: run
run:
	@echo "Running project locally..."
	$(MAVEN_CMD) spring-boot:run