<h1>NexBuy 💻</h1>

<p>
    <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="java badge"/>
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="spring badge"/>
    <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="spring security badge" />
    <img src="https://img.shields.io/badge/Spring_data_jpa-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white" alt="spring data jpa badge" />
    <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="postgres badge"/>
    <img src="https://img.shields.io/badge/-rabbitmq-%23FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white" alt="rabbit mq badge"/>
    <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="docker badge"/>
    <img src="https://img.shields.io/badge/Stripe-5851DD?style=for-the-badge&logo=stripe&logoColor=white&colorB=blue" alt="stripe badge"/>
    <img src="https://img.shields.io/badge/junit-%23E33332?style=for-the-badge&logo=junit5&logoColor=white" alt="junit badge"/>
    <img src="https://img.shields.io/badge/Mockito-green?style=for-the-badge&&logo=mockito&logoColor=white" alt="mockito badge" />
</p>

<p>
To summarize, NexBuy is a back-end application designed to operate efficiently as an e-commerce. 
It provides a complete set of functionality that an e-commerce typically offers through a well-structured API.
</p>

<p>
It's worth noting that this is a study project, designed to address and apply complex concepts such as: 
Hexagonal Architecture, SOLID principles, Messaging, Database Transactions and Unit Tests.
</p>

<p>
Since it's a study project, some parts are simulated, but not all. For instance, 
while order shipping and its status updates are simulated, the freight queries are real using the API Melhor Envio.
</p>

<p>
This is just a small text to clarify the purpose of the project.
</p>

<h2>🚀 Getting started</h2>

<h3>💻 Prerequisites</h3>

- [JDK 21](https://www.oracle.com/br/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://docs.docker.com/)

<h3>🛸 Cloning</h3>

```
git clone https://github.com/MiguelSperle/nexbuy.git
```

📂 Access at folder

```
cd nexbuy
```

📡 Install dependencies

```
mvn clean install
```

<h3>⌨️ Command to run Docker Compose</h3>

```
docker-compose up -d
```

<h3>🔑 System Environment Variables</h3>

```
spring:
  application:
    name: nexbuy

  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

  jpa:
    hibernate:
      ddl-auto: none

  flyway:
    schemas: public
    default-schema: public

  api:
    security:
      token:
        secret: ${JWT_SECRET}

  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true

  rabbitmq:
    username: ${RABBITMQ_USERNAME}
    password: ${RABBITMQ_PASSWORD}
    listener:
      simple:
        retry:
          enabled: true
          max-attempts: 4
          initial-interval: 3000
          multiplier: 2
          max-interval: 12000

  freight:
    api:
      url: ${FREIGHT_QUOTE_URL}
      token: ${FREIGHT_QUOTE_TOKEN}
      email: ${FREIGHT_QUOTE_EMAIL}
      cep:
        from: ${FREIGHT_QUOTE_FROM}

  stripe:
    api:
      secret:
        key: ${STRIPE_API_SECRET_KEY}
      webhook:
        secret:
          key: ${STRIPE_WEBHOOK_SECRET_KEY}
```

<h3>👨🏻‍💻 Developer</h3>

<table>
  <tr>
    <td>
      <a href="https://github.com/MiguelSperle">
        <img src="https://avatars.githubusercontent.com/u/102910354?v=4" width="100px;" alt="Miguel Sperle Profile Picture"/><br>
      </a>
    </td>
  </tr>
</table>