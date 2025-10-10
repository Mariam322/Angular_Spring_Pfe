FROM node:20-alpine
# Installer Python et Make pour node-gyp
RUN apk add --no-cache python3 make g++
WORKDIR /app
COPY package*.json ./
RUN npm ci --legacy-peer-deps
COPY . .
RUN npm run build --configuration production

# Étape 2 : Servir avec Nginx
FROM nginx:alpine
COPY --from=build /app/dist/BankprojetFront /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
