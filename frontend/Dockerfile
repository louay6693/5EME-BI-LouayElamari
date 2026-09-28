# ---------- Étape 1 : build Angular ----------
FROM node:22-alpine AS build
WORKDIR /app

# URL publique du backend (celle que le navigateur va appeler)
ARG API_URL
ENV API_URL=${API_URL}

COPY package*.json ./
RUN npm ci

COPY . .

# Remplace l'URL codée en dur dans les services Angular
RUN sed -i "s#http://localhost:8080#${API_URL}#g" src/app/services/*.ts

RUN npm run build

# ---------- Étape 2 : serveur Nginx ----------
FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
EXPOSE 80