### Descrição 

Projeto que será desenvolvido durante a disciplina de Oficina De Integração 2 com o objetivo de gerenciar as reservas de quartos de um hotel.

### 🚀 Tecnologias

- [**Java**](https://www.java.com/pt-BR/)
- [**MySql**](https://www.mysql.com/) 

### 📚 Bibliotecas utilizadas

- **[mysql connector ](https://downloads.mysql.com/archives/c-j/)**
- **[mockito-all ](https://mvnrepository.com/artifact/org.mockito/mockito-all)**
- **[junit-test ](https://junit.org/junit4/)**
- **[checkstyle ](https://checkstyle.sourceforge.io/)**
- **[jasperReport](https://community.jaspersoft.com/project/jasperreports-library)**

### 🚩 Dependências

🚨 AVISO 🚨

- É Necessário ter o javajdk-11 para executar o programa

- **[JDK-11 ](https://www.oracle.com/java/technologies/downloads/#java11-windows)**

### :hammer: Ferramentas

- [NetBeans 12](https://netbeans.apache.org/download/nb120/nb120.html)
- [Workbench](https://dev.mysql.com/downloads/workbench/)

### :pushpin: Layout

Para acessar o layout utileze o [Figma](https://www.figma.com/file/ylmEe6nl3qQPGz4OZfxNXe/prototipo?node-id=0%3A1).

### :lock: Instruções:

- Com o mySQL instalado, faça a importação da base de dados presento no [caminho](/gerenciamento_reservas_hotel/reservars.sql)
- Faça uma alteração nos scripts de conexão para que o programa identifique seu banco:
    - No arquivo [ConnectionFactory.java](/gerenciamento_reservas_hotel/src/main/java/dao/ConnectionFactory.java), altere o ultimo parâmetro da string na linha 15, referente a sua senha do mySQL
    - No arquivo [AndarDAO.java](/gerenciamento_reservas_hotel/src/main/java/dao/AndarDAO.java), altere o ultimo parâmetro da string na linha 31, referente a sua senha do mySQL
    - No arquivo [FuncionarioDAO.java](/gerenciamento_reservas_hotel/src/main/java/dao/FuncionarioDAO.java), altere o ultimo parâmetro da string na linha 20, referente a sua senha do mySQL
    - No arquivo [QuartoDAO.java](/gerenciamento_reservas_hotel/src/main/java/dao/QuartoDAO.java), altere o ultimo parâmetro da string na linha 33, referente a sua senha do mySQL
    - No arquivo [ReservaDAO.java](/gerenciamento_reservas_hotel/src/main/java/dao/ReservaDAO.java), altere o ultimo parâmetro da string na linha 26, referente a sua senha do mySQL
- Execute um Build & Clean via NetBeans
- Para usar a aplicação execute a TelaLogin.java com o usuário=pedro e senha=0704

### :two_men_holding_hands: Membros da Equipe

- Nome: Pedro Henrique da Silva Pereira / RA:2102757
- Nome: Philippe Aparecido de Lima / RA:2102765
- Nome: Marcos Vinicius Dos Santos Rodrigues / RA:2102692
