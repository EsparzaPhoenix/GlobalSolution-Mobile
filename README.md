=====================================================================
                          Innov8Tech - Mobile App
=====================================================================

Projeto **Innov8Tech**, desenvolvido para realizar a análise de perfis 
no Bluesky. Os usuários podem solicitar relatórios sobre perfis do 
Bluesky, onde são gerados dados de análise sobre a conta inserida. 
O app inclui funcionalidades de cadastro, login, recuperação de senha, 
solicitação de relatórios e uma interface de dashboard onde os relatórios 
são exibidos.

---------------------------------------------------------------------
                          FUNCIONALIDADES
---------------------------------------------------------------------

1. **Login e Cadastro de Usuário:**
   - O app permite que os usuários se cadastrem, façam login ou 
     recuperem suas senhas.
   - As contas de usuário são gerenciadas com o Firebase Authentication.
   
2. **Solicitação de Relatórios:**
   - Os usuários autenticados podem inserir um @conta do Bluesky 
     e solicitar um relatório.
   - O relatório gerado é armazenado no Firebase Storage e exibido 
     no dashboard.

3. **Dashboard de Relatórios:**
   - O dashboard exibe os relatórios solicitados, junto com a data 
     de criação e status do relatório (Em Processamento ou Concluído).
   - A lista de relatórios é atualizada em tempo real.

4. **Notificações de Relatórios Concluídos:**
   - Relatórios que mudam de status para "Concluído" são exibidos na 
     aba de notificações.

---------------------------------------------------------------------
                          TECNOLOGIAS UTILIZADAS
---------------------------------------------------------------------

- **Kotlin**: Linguagem de programação utilizada para o desenvolvimento 
              do aplicativo.
- **Android SDK**: Ferramentas e bibliotecas para o desenvolvimento Android.
- **Firebase Authentication**: Gerenciamento de usuários e autenticação.
- **Firebase Storage**: Armazenamento dos relatórios gerados.
- **LiveData & ViewModel**: Arquitetura MVVM, garantindo separação de 
                            responsabilidades e maior reatividade dos dados.
- **ConstraintLayout**: Layout utilizado para o design responsivo e adaptável 
                        das telas.

---------------------------------------------------------------------
                          ESTRUTURA DO PROJETO
---------------------------------------------------------------------

O projeto está dividido em pacotes, cada um com uma responsabilidade 
específica, conforme listado abaixo:

### Pacotes Principais

- **ui.dashboard**: Contém as classes relacionadas à tela principal 
                   (dashboard) onde os relatórios são exibidos.
- **ui.login**: Gerencia a tela de login.
- **ui.register**: Gerencia o cadastro de novos usuários.
- **ui.forgotpass**: Gerencia a recuperação de senha.
- **ui.notifications**: Gerencia as notificações de relatórios concluídos.

### Arquivos Importantes

- **DashboardFragment.kt**: Gerencia a exibição dos relatórios e a 
                            solicitação de novos relatórios.
- **DashboardViewModel.kt**: Gerencia a lógica de negócios relacionada 
                             à dashboard, incluindo a integração com o 
                             Firebase Storage.
- **LoginFragment.kt**: Gerencia o login dos usuários.
- **RegisterFragment.kt**: Gerencia o cadastro de novos usuários.
- **ForgotPassFragment.kt**: Gerencia a recuperação de senha por email.
- **NotificationsFragment.kt**: Exibe as notificações de relatórios 
                                concluídos.

---------------------------------------------------------------------
                          CONFIGURAÇÃO DO FIREBASE
---------------------------------------------------------------------

Para rodar o projeto localmente, você precisará configurar o Firebase 
Authentication e o Firebase Storage:

1. Crie um projeto no [Firebase Console](https://console.firebase.google.com/).
2. Ative a autenticação por email/senha no Firebase Authentication.
3. Configure o Firebase Storage para armazenar os relatórios dos usuários.
4. Baixe o arquivo `google-services.json` do seu projeto Firebase e coloque-o 
   no diretório `app/` do projeto Android.

---------------------------------------------------------------------
                          INSTALAÇÃO E EXECUÇÃO
---------------------------------------------------------------------

### Pré-requisitos

- **Android Studio**: Última versão disponível.
- **Kotlin**: O projeto está escrito em Kotlin.

### Passos para Clonar e Executar

1. Clone o repositório do projeto:
   ```bash
   git clone https://github.com/seu-usuario/innov8tech.git

---------------------------------------------------------------------
                          MEMBROS
---------------------------------------------------------------------

- **Lucas Serbato de Barros - 551821**
- **Ronald de Oliveira Farias - 552364**
- **Vitor Teixeira - RM 552228**
- **Phablo Isaias Silva Santos - RM550687**
- **Gustavo Carvalho - RM 552466**
