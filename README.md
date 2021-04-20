# Windows api service

[Агентство цифровых технологий](https://dta.agency)

## Install

`nssm.exe`

`nssm install ${Service name} ${DISC}:\Java\JavaPrograms\jdk-${version}\bin\java.exe -Xmx400m -jar \”${DISC}:${JAR_PATH}${JAR_NAME}-1.0-SNAPSHOT.jar\”`

`nssm set ${Service name} DisplayName “Test Service”`

`nssm set ${Service name} AppRestartDelay 1000`

`nssm set ${Service name} Description “Start java.exe with params -Xmx400m -jar mainTutorial.jar amazing prod”`

`nssm remove ${Service name}`
