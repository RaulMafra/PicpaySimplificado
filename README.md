## Sobre o projeto

* Microserviço construído com Java 17 + Spring Boot para a resolução do challenge [Picpay Simplificado](https://github.com/PicPay/picpay-desafio-backend). 
* Para a resolução, foi aplicado os conceitos de API REST, SOLID, Testes Unitários com JUnit e Tests de Integração. O banco de dados utilizado é o H2 Database
justamente por ser um banco de dados em memória, facilitando no desenvolvimento e testes.
* Realizado a integração com Docker e Docker Compose para isolar a aplicação e o banco de dados em containers.


## Diagrama de classes

<img src="/UML/Diagrama de classes.jpg" alt="Diagrama de classes">


## Instalação e execução

* Antes de executar será necessário clonar o projeto.
  ```
  git clone https://github.com/RaulMafra/PicpaySimplificado.git
  ```
### 1ª opção para execução:
* A primeira opção consiste em criar o pacote e executá-lo a partir dos passos abaixo:
    * Acesse o diretório do projeto e execute o comando abaixo para criar o pacote *.jar.
       ```
       mvn package
       ```
    * Por fim, dentro do diretório do projeto, inicialize a aplicação a partir do *.jar criado.
      ```
      java -jar ./target/PicpaySimplificado-0.0.1-SNAPSHOT.jar
      ```
### 2ª opção para execução:
* A segunda opção é abrir o projeto em alguma IDE de sua escolha e inicializá-la a partir dela como qualquer outro projeto.


## Especificações

* A documentação está no formato OpenAPI 3.0, onde poderá ser acessada através do endereço http://localhost:8080/picpay-simplificado/v1/swagger-ui/index.html após a inicialização.
* Através da documentação será possível conhecer os endpoints, funcionalidade de cada um deles e realizar testes. Acredito que não cabe explicá-los aqui, uma vez que estão auto-explicativos na documentação.

## License

Veja o arquivo [LICENSE](https://github.com/RaulMafra/PicpaySimplificado/blob/main/LICENSE) para direitos de licença e limitações (MIT).
