# TP01 - Relacionamento 1:N - Aeds 3

Alunos participantes: Isabel Cristina 
                      Laura Bargas
                      Pedro Mattar  
                      Yuri Penido

O EntrePares 1.0 (TP1) é um sistema acadêmico desenvolvido para gerenciar a oferta de cursos livres por alunos da PUC Minas.
Ele opera como um banco de dados customizado, realizando a persistência de Usuários e Cursos em arquivos binários de acesso aleatório e otimizando o armazenamento por meio do reaproveitamento de registros excluídos.
Para garantir a integridade e a alta performance nas buscas, o sistema emprega criptografia para senhas, Tabelas Hash Extensíveis para buscas diretas (como login e códigos de cursos) e Árvores B+ para ordenação alfabética e para solucionar o relacionamento 1:N entre os criadores e seus respectivos cursos.

## Estrutura de Classes Criadas

### 1. Classes Criadas
* **Principal** : Classe que contém o método main, é responsável por gerenciar o menu controlando o loop principal do programa, as telas de pré-login e pós-login.

<img width="785" height="662" alt="Screenshot 2026-04-15 214356" src="https://github.com/user-attachments/assets/cd6233e4-2713-4bef-a751-ac282915c54e" />

* **Console** : Classe utilitária responsável por limpar a tela do terminal.

<img width="787" height="371" alt="Screenshot 2026-04-15 214528" src="https://github.com/user-attachments/assets/4a568ff9-ba96-482f-ad4e-b8bac4c625e7" />

### 2. Núcleo de Armazenamento (Pacote aed3):
* **Arquivo<T>** :Implementa o CRUD genérico manipulando arquivos.

<img width="772" height="555" alt="Screenshot 2026-04-15 214556" src="https://github.com/user-attachments/assets/66151509-9d1e-4c4d-bc91-28c7460a517b" />

* **HashExtensivel** : Implementação da tabela hash que associa chaves a valores. Cada par de chave e seu valor deve ser armazenado em um endereço específico dessa tabela. Indice indireto, que é identificado por uma chave única. Usuário: e-mail e Curso: código compartilhável.

<img width="785" height="605" alt="Screenshot 2026-04-15 214623" src="https://github.com/user-attachments/assets/e33f3e16-c9ce-4a7b-a139-97126befb850" />
  
* **ArvoreBMais** : Implementação da ArvoreBMais em que cada nó comporta várias chaves. As árvores B são estruturas planejadas para o acesso de dados em blocos. Usuário: nome e Curso: nome do curso.

<img width="785" height="578" alt="Screenshot 2026-04-15 214732" src="https://github.com/user-attachments/assets/cacf3a31-364c-4212-ac8e-5b6031a65528" />

* **ParIDEndereco , ParIdId , ParNomeId** : Classes de suporte para os índices.

<img width="788" height="709" alt="Screenshot 2026-04-15 214757" src="https://github.com/user-attachments/assets/b61bf8f9-ba25-453d-83a2-d4c4a1bc5909" />

  

### 2. Entidade Usuário:
* **Usuario** : Entidade com Nome, Email, Hash da Senha e Pergunta Secreta.

<img width="787" height="562" alt="Screenshot 2026-04-15 214852" src="https://github.com/user-attachments/assets/4d9d6961-1fcb-4020-a413-1bdf6639cd9f" />
  
* **ArquivoUsuario** : Sobrescreve as operações de criação, atualização e exclusão para garantir que o arquivo principal permaneça sempre sincronizado em tempo real, gera índices por Email (Hash) e Nome (Árvore B+).

<img width="789" height="594" alt="Screenshot 2026-04-15 214906" src="https://github.com/user-attachments/assets/98023897-1d1b-47a7-a5b4-ed9067238c90" />
  
* **ControleUsuario** : Lógica de login, cadastro e recuperação de conta.

<img width="787" height="566" alt="Screenshot 2026-04-15 214923" src="https://github.com/user-attachments/assets/d19f4622-d6bb-4ea6-80a0-88d2aa69018b" />
  
* **VisaoUsuario** : Interface que lê dados e gera hashes para segurança, é possível recuperar senha.

<img width="784" height="658" alt="Screenshot 2026-04-15 214947" src="https://github.com/user-attachments/assets/037ea5b6-4013-46e9-8949-34ca673c0d09" />
  

### 3. Entidade curso:
* **Curso** : Entidade com Nome, Descrição, Data, Código Único e Estado.

<img width="783" height="576" alt="Screenshot 2026-04-15 215048" src="https://github.com/user-attachments/assets/9fded8c5-d1e3-469f-9d05-30aeed35d896" />
  
* **ArquivoCurso** : Insere o vetor de bytes gerado pelo curso e o salva no arquivo de dados. Logo em seguida, ele obrigatoriamente insere as chaves correspondentes na Tabela Hash Extensível e nas duas Árvores B+, para que as buscas futuras sejam otimizadas e não precisem varrer o arquivo sequencialmente.

<img width="784" height="483" alt="Screenshot 2026-04-15 215131" src="https://github.com/user-attachments/assets/375b9778-598f-47f1-931f-c4b14532ac4e" />
  
* **ControleCurso** : Gere o menu de cursos, alteração de estados e criação.

<img width="786" height="570" alt="Screenshot 2026-04-15 215203" src="https://github.com/user-attachments/assets/cb0af2ba-f71a-472b-8e6d-c064aa97e0ad" />
  
* **VisaoCurso** : Interface para entrada e exibição de dados de cursos.

<img width="787" height="564" alt="Screenshot 2026-04-15 215447" src="https://github.com/user-attachments/assets/afba1a49-f37e-41c0-8c86-aae2876cdeaf" />
  

## Operações Especiais Implementadas
* **Reuso de Espaço (Lista)** : Ao excluir ou atualizar um registo (se o tamanho diminuir), o endereço é guardado numa lista de espaços vazios no cabeçalho do arquivo. Novos registos tentam reutilizar estes espaços antes de adicionar no final do arquivo.

<img width="782" height="603" alt="Screenshot 2026-04-15 215517" src="https://github.com/user-attachments/assets/8a8cb64e-a848-452f-8a47-f3025d71ed59" />
  
* **Relacionamento 1:N** : Foi utilizada uma Árvore B+ (indiceUsuarioCurso) que armazena pares de IDs (idUsuario e idCurso), permitindo listar rapidamente todos os cursos de um utilizador específico.

<img width="710" height="538" alt="Screenshot 2026-04-15 215539" src="https://github.com/user-attachments/assets/eea0a2fc-2ff8-448e-aa55-4656d04d657c" />
  
* **Segurança com SHA-256** : As senhas e respostas secretas nunca são gravadas em texto limpo. O sistema gera um hash criptográfico antes da persistência.

<img width="784" height="609" alt="Screenshot 2026-04-15 215556" src="https://github.com/user-attachments/assets/c7ed7980-bc25-44a4-b66e-20606a0ff6a3" />
  
* **Geração de Código Único** : Cada curso recebe um código alfanumérico de 10 caracteres gerado via SecureRandom. O sistema verifica no índice de códigos se já existe antes de confirmar a criação.

<img width="783" height="445" alt="Screenshot 2026-04-15 215615" src="https://github.com/user-attachments/assets/3f5fb574-4e74-4cc0-86bf-342f0b37131e" />
  

## Checklist:
* **Há um CRUD de utilizadores que funciona corretamente?**
  Sim.
* **Há um CRUD de cursos que funciona corretamente?**
  Sim.
* **Os cursos estão vinculados aos utilizadores usando o idUsuario como chave estrangeira?**
  Sim, o idUsuario é armazenado em cada curso e é utilizado para filtragem.
* **Há uma árvore B+ que registre o relacionamento 1:N entre usuários e cursos?**
  Sim.
* **O trabalho compila corretamente?**
  Sim.
* **O trabalho está completo e funcionando sem erros de execução?**
  Sim.
* **O trabalho é original e não a cópia de um trabalho de outro grupo?**
  Sim.
