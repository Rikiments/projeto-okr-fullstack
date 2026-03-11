# 🎯 Sistema de Gestão de OKRs (Full Stack)

> Plataforma completa para o gerenciamento de Objetivos e Resultados Chave (OKRs), desenvolvida com foco em automação de progresso e usabilidade. Projeto acadêmico para a disciplina de Programação de Sistemas II.

## 📖 Sobre o Projeto

O Sistema de Gestão de OKRs foi projetado para facilitar o acompanhamento de metas organizacionais ou pessoais. A aplicação permite a criação de **Objetivos**, o vínculo de **Key Results (KRs)** e o detalhamento em **Iniciativas** e tarefas. 

O grande diferencial técnico desta aplicação é o seu **motor de cálculo em cascata no Backend**: ao atualizar a conclusão de uma simples tarefa ou iniciativa, o sistema recalcula e atualiza automaticamente o progresso percentual do Key Result e do Objetivo pai correspondente, mantendo todos os dados sincronizados em tempo real.

## 🏗️ Arquitetura do Projeto (Monorepo)

O projeto está estruturado em um único repositório separando claramente as responsabilidades:

* `/backend`: API RESTful responsável pelas regras de negócio, cálculos e persistência de dados.
* `/frontend`: Interface de usuário dinâmica, responsiva e focada na experiência (SPA).

## 🚀 Tecnologias Utilizadas

### Backend
* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA (Hibernate)**
* **PostgreSQL**
* **Lombok**
* **Maven**

### Frontend
* **React.js**
* **Next.js 15**
* **JavaScript**
* **CSS Modules**
