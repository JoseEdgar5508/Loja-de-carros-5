## 📚 Projeto Concessionária de Veículos
Este projeto é um sistema de gestão para concessionária de veículos, desenvolvido para controlar:

Carros (estoque, marcas, modelos)

Clientes (cadastro, histórico)

Vendas (transações, métodos de pagamento)

## ⚙️ Pré-requisitos
Para executar o projeto corretamente, você precisará:

Biblioteca do Banco de Dados:

Acesse a pasta lib/ e instale o arquivo da biblioteca necessária para conexão com o banco de dados.

Configuração do BD:

Crie um banco de dados usando phpMyAdmin e altere as seguintes variáveis no arquivo 'conectarbd':
<code>
  URL = "jdbc:mysql://localhost:3306/NOME DO SEU BANCO";
  USER =seu_usuario
  PASS =sua_senha
</code>
(As configurações padrão do Xampp estão presentes no código).
