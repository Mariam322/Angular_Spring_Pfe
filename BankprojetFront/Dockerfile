# Étape 1 : Build Angular
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install -g @angular/cli@16.2.7
RUN npm ci --legacy-peer-deps
COPY . .
RUN ng build --configuration production

# Étape 2 : Serveur NGINX
FROM nginx:alpine
# SUPPRIMER la configuration par défaut
RUN rm /etc/nginx/conf.d/default.conf
# COPIER les fichiers Angular
COPY --from=build /app/dist/bankprojet-front/ /usr/share/nginx/html/
# COPIER votre configuration nginx ← CORRECTION ICI
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
