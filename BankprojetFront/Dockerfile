# Étape 1 : Image Node
FROM node:20-alpine

# Installer Python et make pour node-gyp si nécessaire
RUN apk add --no-cache python3 make g++

# Dossier de travail
WORKDIR /app

# Copier package.json et package-lock.json
COPY package*.json ./

# Installer Angular CLI globalement et les dépendances
RUN npm install -g @angular/cli@16.2.7
RUN npm ci --legacy-peer-deps --no-cache --unsafe-perm=true

# Copier tout le code source
COPY . .

# Construire l'application Angular (production)
RUN ng build --configuration production

# Étape 2 : Serveur NGINX pour héberger le build (au lieu de ng serve)
FROM nginx:alpine
COPY --from=0 /app/dist /usr/share/nginx/html

# Exposer le port 80 (serveur HTTP standard)
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
