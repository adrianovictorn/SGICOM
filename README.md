# Mercado Municipal

Sistema web para cadastro e consulta de feirantes, empresas, enderecos, categorias e indicadores do municipio.

## Stack
- Frontend: SvelteKit + Vite
- Backend: Spring Boot + JPA + Flyway + JWT
- Banco: PostgreSQL

## Estrutura do repo
- `frontend/`: app web (SvelteKit)
- `backend/`: API (Spring Boot)
- `compose.yaml`: stack completa (frontend + backend + postgres)

## Executar com Docker
```sh
docker compose up --build
```

Acesse:
- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`

## Desenvolvimento local
Backend (usa as migracoes do Flyway em `backend/src/main/resources/db/migration`):
```sh
cd backend
./mvnw spring-boot:run
```

Frontend:
```sh
cd frontend
npm install
npm run dev
```

O Vite faz proxy de `/api` e `/auth` para `http://localhost:8080` (veja `frontend/vite.config.ts`).

## Configuracao de banco
O backend le as credenciais via variaveis de ambiente:
- `POSTGRES_DB`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`

Essas variaveis sao usadas em `backend/src/main/resources/application.properties`.

## Producao (APIs diferentes)
Neste projeto, considere que as APIs de producao sao diferentes das locais. Exemplo ficticio:
- Frontend: `https://app.mercado-municipal.gov.br`
- API: `https://api.mercado-municipal.gov.br`
- Auth: `https://auth.mercado-municipal.gov.br`

Como o frontend chama endpoints relativos (`/api` e `/auth`), duas opcoes comuns:
1) Ajustar o reverse proxy (por exemplo `frontend/nginx.conf`) para apontar `/api` e `/auth` para os hosts de producao.
2) Criar um gateway que exponha `/api` e `/auth` no mesmo host do frontend.

Se optar por expor a API em um host unico e acessar direto do navegador, atualize a base em `frontend/src/lib/api.js` (const `BASE`) e garanta CORS no backend.
