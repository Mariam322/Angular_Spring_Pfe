# Étape 1 : Build Angular
FROM node:20-alpine AS build

# Installer Python et Make pour node-gyp
RUN apk add --no-cache python3 make g++

# Définir le dossier de travail
WORKDIR /app

# Copier les fichiers package.json et lock pour installer les dépendances
COPY package*.json ./

# Installation fiable sans cache et avec permissions root
RUN npm ci --legacy-peer-deps --no-cache --unsafe-perm=true

# Corriger les erreurs esbuild (ETXTBSY / binaire verrouillé)
RUN npm rebuild esbuild

# Copier le reste du code source
COPY . .

# Build Angular en production
RUN npm run build

# Étape 2 : Image finale légère Nginx
FROM nginx:alpine

# Copier les fichiers buildés depuis la première étape
COPY --from=build /app/dist/bankprojet-front /usr/share/nginx/html

# Exposer le port HTTP
EXPOSE 80

# Démarrer Nginx
CMD ["nginx", "-g", "daemon off;"]
