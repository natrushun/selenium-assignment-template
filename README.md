# The test image must be copied into the Selenium container:

```bash
docker exec -u root docker-sandbox-selenium mkdir -p /home/selenium/tests/< projectname >/src/test/resources
docker cp ./tests/task3/src/test/resources/test.png docker-sandbox-selenium:/home/selenium/tests/< projectname >/src/test/resources/test.png
```