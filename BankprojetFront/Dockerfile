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

# Exposer le port Angular (par défaut 4200)
EXPOSE 4200

# Lancer Angular en mode dev avec ng serve
CMD ["ng", "serve", "--host", "0.0.0.0", "--port", "4200"]
