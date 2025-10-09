FROM node:20-slim
RUN rm -rf /var/mail /var/spool/mail /usr/share/zoneinfo /usr/share/terminfo || true
WORKDIR /app

# Copier les fichiers de dépendances
COPY package.json package-lock.json ./
COPY proxy.conf.json ./

# Installer Angular CLI et les dépendances
RUN npm install -g @angular/cli@18 && \
    npm config set registry https://registry.npmjs.org/ && \
    npm ci --legacy-peer-deps

# Copier le code source
COPY . .

# Exposer le port
EXPOSE 4200

# Commande pour développement
CMD ["ng", "serve", "--host", "0.0.0.0", "--disable-host-check", "--proxy-config", "proxy.conf.json"]
